package it.polito.tdp.tesiSimulatore.model;




import java.util.List;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.tesiSimulatore.dao.LAcollisionDAO;


public class Model {
	
	private LAcollisionDAO dao;
	private Graph<Area, DefaultWeightedEdge> grafo;
	private Simulatore sim;
	private DijkstraShortestPath<Area, DefaultWeightedEdge> dijkstra;

	
	
	public Model() {
		this.dao = new LAcollisionDAO();
	
	}
	
	
	// svuota il grafo per crearne nel caso uno nuovo
	private void clearGraph() {
		this.grafo = new SimpleGraph<>(DefaultWeightedEdge.class);			
	}


	// GRAFO
	/**
	 * Metodo che crea il grafo
	 */
	public void creaGrafo(Integer year) {
		clearGraph();
		//costruzione di un nuovo grafo
		this.grafo = new SimpleWeightedGraph<Area, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		//assegnazione dei vertici
		List<Area> vertici = this.dao.getVertici(year);
		
		//assegnazione degli archi
		//calcoliamo gli archi da query
		
		// per ogni area vado a settare le sue coordinate geografiche
		for (Area a : vertici) {
			a.setCoords(dao.getCoordsYearAndAreaSpecified(year, a.getAreaID()) );
		}
		
		// Doppio ciclo per ottenere le distanze in km tra le varie aree della città
		for (int i = 0; i<vertici.size(); i++) {
			for (int j = i+1; j < vertici.size(); j ++) {
				Area area1 = vertici.get(i);
				Area area2 = vertici.get(j);
				double distanzaArea =  LatLngTool.distance(area1.getCoords(), area2.getCoords(), LengthUnit.KILOMETER);			 
				//aggiungo i vertici e i relativi archi al grafo
				Graphs.addEdgeWithVertices(this.grafo, area1, area2, distanzaArea);
			}
		}
		
		dijkstra = new DijkstraShortestPath<Area, DefaultWeightedEdge>(grafo);

		}


	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}


	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	


	public void eseguiSimulazione(Integer year, Integer month, int txtNumberAmbulance,
			int txtNumberHospital, double txtProbability) {
		
		// Creare simulatore e coda degli eventi
		this.sim = new Simulatore(year, month, txtNumberAmbulance, txtNumberHospital, txtProbability, dijkstra);
		sim.popolaCoda();	
		// Eseguire simulazione
		sim.processaEventi();
		
	}
	
	public Set<String> getPlaceCollisionSet() {
		return sim.getPlaceCollisionSet();
	}


	public int getCollisionsNumber() {
		return sim.getCollisionsNumber();
	}


	public int getPeopleSavedNumber() {
		return sim.getPeopleSavedNumber();
	}


	
	

}
