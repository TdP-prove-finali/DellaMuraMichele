package it.polito.tdp.tesiSimulatore.model;

import java.time.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import java.util.Random;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.tesiSimulatore.dao.LAcollisionDAO;
import it.polito.tdp.tesiSimulatore.model.Ambulance.State;
import it.polito.tdp.tesiSimulatore.model.Event.EventType;


public class Simulatore {
	
	//parametri di ingresso
	private Integer year;
	private Integer month;
	private int txtNumberAmbulance;
	private int txtNumberHospital;
	private double txtProbability;
	
	//parametri
	
	private LAcollisionDAO dao;
	private List<Area> areas;
	// lista di tutti gli incidenti avvenuti nell'anno e dal mese specificati
	List<Collision> collisionsYearAndMonthSpecifiedList;
	// mappa on chiave: ID ambulanza, valore: classe Ambulance
	private Map<Integer, Ambulance> ambulancesMap;
	
	DijkstraShortestPath<Area, DefaultWeightedEdge> dijkstra;
	
	// l'ambulanza lavora tutto il giorno
    private LocalTime startHour = LocalTime.of(0, 0); //rappresenterebbe mezzanotte 00:00
    private LocalTime stopHour = LocalTime.of(23, 59);
    long dayHours;
	
//	private Duration timeNoTraffic = Duration.of(5, ChronoUnit.MINUTES);
//	private Duration timeTraffic = Duration.of(8, ChronoUnit.MINUTES);
//	
//	
	private Duration timeoutYellow = Duration.of(20, ChronoUnit.MINUTES);
	private Duration timeoutRed = Duration.of(10, ChronoUnit.MINUTES);

	private Duration healYellow = Duration.of(30, ChronoUnit.MINUTES);
	private Duration healRed = Duration.of(15, ChronoUnit.MINUTES);
	

	
	//variabili di uscita
	private int collisionsNumber;
	private int peopleSavedNumber;
	private Set<String> placeCollisionSet;


	
	//coda degli eventi
	PriorityQueue<Event> queue;

	
	
	
	
	
	public Simulatore(Integer year, Integer month, int txtNumberAmbulance, int txtNumberHospital, double txtProbability, DijkstraShortestPath<Area, DefaultWeightedEdge> dijkstra) {
		
		this.year = year;
		this.month = month;
		this.txtNumberAmbulance = txtNumberAmbulance;
		this.txtNumberHospital = txtNumberHospital;
		this.txtProbability = txtProbability;
		this.dijkstra = dijkstra;
		this.dao = new LAcollisionDAO();
		this.collisionsYearAndMonthSpecifiedList = new ArrayList<>();
		this.peopleSavedNumber = 0;
		this.placeCollisionSet = new HashSet<>();
		this.ambulancesMap = new HashMap<>();
		
		this.dayHours = ChronoUnit.HOURS.between(startHour, stopHour);
		
		// script per recupare le coordinate geografiche di ogni area presente nella simulazione
		this.areas = this.dao.getAllAreas();	
		for (Area a : areas) {
			a.setCoords(dao.getCoordsYearAndAreaSpecified(year, a.getAreaID()) );
		}
		
		
		// scelta casuali dei distretti che presentano un ospedale
        List<Integer> indices = new ArrayList<>();
        List<Area> randomAreas = new ArrayList<>();

        int z = 0;
        while (z < txtNumberHospital) {
            int randomIndex = (int) (Math.random() * areas.size());
            if (!indices.contains(randomIndex)) {
                indices.add(randomIndex);
                randomAreas.add(areas.get(randomIndex));
                z++;
            }
        }
         
        // associo il numero di ambulanze totali ad una delle aree scelte casualmente nel ciclo while precedente
        int j = 0;
        for (int i=1; i<= txtNumberAmbulance; i++){
        	if (j == randomAreas.size()) {
        		j = 0;
        		Ambulance a = new Ambulance(i, randomAreas.get(j), State.FREE);
        		ambulancesMap.put(i, a); 
        		j++;
        	}
        	else {
            	Ambulance a = new Ambulance(i, randomAreas.get(j), State.FREE);
            	ambulancesMap.put(i, a);   
            	j++;
        	}
        }
            
   }
		


	public void popolaCoda() {
		
		this.queue = new PriorityQueue<Event>();
		
		Random rand = new Random();
		
		
		this.collisionsYearAndMonthSpecifiedList = dao.getCollisionsYearAndMonthSpec(year, month);
		
		this.collisionsNumber = collisionsYearAndMonthSpecifiedList.size();
		
		List<Event> events = new ArrayList<>();
		
		LocalTime ora = startHour;
		
		for (Collision c : collisionsYearAndMonthSpecifiedList) {
			Event e = null;
			LatLng coords = new LatLng(c.getLatitude(), c.getLongitude());
			Area areaIncidente = new Area (c.getAreaID(), c.getAreaName(), coords);
			LocalDate date = c.getDate();
			// attribuisco ad ogni incidente un tempo casuale in cui avviene
			int minutes = rand.nextInt(60 * ((int) dayHours));
			ora = ora.plusMinutes(minutes);
			
			// aggiungo al set  il tipo di struttura o luogo in cui si è verificato l' incidente
			this.placeCollisionSet.add(c.getLocation());
			
			if (c.getVictimAge() < 18 || c.getVictimAge() > 70) {
				e = new Event(LocalDateTime.of(date, ora), EventType.RED, areaIncidente);
			}
			else {
				e = new Event(LocalDateTime.of(date, ora), EventType.YELLOW, areaIncidente);
				
			}
	
			events.add(e);
		}
		
		
		for (Event e : events) {		
			this.queue.add(e);			
		}
		
			
		
	}

	public void processaEventi() {
		
//		System.out.println(queue.size());
//		for (String s : this.placeCollisionSet)
//			System.out.println(s);
		
		while(!queue.isEmpty()) {
			
			Event e = queue.poll();
//			System.out.println(e);			
			Area area = e.getAreaCollision();
			
			switch (e.getType()) {
				
			case RED:				
				handleCase(timeoutRed, healRed, area);					
                break;			
		
			case YELLOW:	
				handleCase(timeoutYellow, healYellow, area);
				break;
			
			
			}
			break;
		} 
		
	}
	
	
	// timeout: tempo entro il quale l'ambulanza deve arrivare, altrimenti vittima deceduta
	// healingTime: tempo di cura della vittima
	private void handleCase(Duration timeout, Duration healingTime, Area areaCollison) {

		
		// verifico se vi sono ambulanze disponibili
		if (checkFreeAmbulances(ambulancesMap)) {				
				
				// verifico se delle ambulanze disponibli, ci sono quelle che si trovano nello stesso distretto dell'incidente		
				if (AmbulancesSameDistrictOfCollision(ambulancesMap, areaCollison) != null) {
					Ambulance a = AmbulancesSameDistrictOfCollision(ambulancesMap, areaCollison);
					a.setState(State.OCCUPIED);								
					
					// se si verifica ingorgo
					double r = Math.random();
					if (r < txtProbability) {
						
					}
					else {
						
					}
					
					// si libera dopo minutaggio = arrivo sul luogo + cura + ritorno all'ospedale	
			
					this.peopleSavedNumber++;
					a.setState(State.FREE);	
				}
				
				// caso in cui non vi sono ambulanze nello stesso distretto della collisione
				else {
					
					// prendo, attraverso dijkstra, i km minori ciclando su tutte le aree in cui
					// risultano disponibili delle ambulanze
					// tra i km minimi ottenuti tra le diversi nodi di origine e il medesimo nodo destinazione
					// prendo il valore minimo
					Set<Area> areaWithFreeAmbulancesSet = getAreaWithFreeAmbulances(ambulancesMap);
					
					double bestKm = 9999999999.0;
					Area bestArea = null;
					
					
					for (Area area : areaWithFreeAmbulancesSet) {
						double kmBetweenSourceTarget = dijkstra.getPath(area, areaCollison).getWeight();
						if (kmBetweenSourceTarget < bestKm) {
							bestKm = kmBetweenSourceTarget;	
							bestArea = area;
						}
					}
					
					Ambulance bestAmbulance = getBestAmbulanceFree(bestArea, ambulancesMap);
					
				
					int pathTime = 0;
					

					double r = Math.random();
					
					// se si verifica ingorgo
					if (r < txtProbability) {
						// velocità ambulanza con traffico pari a 70 km/h
						pathTime = (int) (70/bestKm)*60;
					}
					else {
						// velocità ambulanza con traffico pari a 40 km/h
						pathTime =  (int) (40/bestKm)*60;								
					}
					
					
					// se l'ambulanza arriva in tempo sul luogo dell'incidente
					
					if (Duration.of(pathTime, ChronoUnit.MINUTES).compareTo(timeout) <=0 ) {
						
						bestAmbulance.setState(State.OCCUPIED);
						this.peopleSavedNumber++;
						// si libera dopo minutaggio minutaggio arrivo sul luogo + cura + ritorno all'ospedale
						
						bestAmbulance.setState(State.FREE);	
						
					}
					
					// se l'ambulanza NON arriva in tempo sul luogo dell'incidente
					else {
						// verifica che effetivamente la vittima sia deceduta recandosi sul luogo
						bestAmbulance.setState(State.OCCUPIED);
						// si libera dopo minutaggio = arrivo sul luogo + ritorno all'ospedale	
						bestAmbulance.setState(State.FREE);
						
						
						
					}
					
				}									
		}
		
		
	}



	private Ambulance getBestAmbulanceFree(Area bestArea, Map<Integer, Ambulance> ambulancesMap) {
		
		Ambulance result = null;
		for (Ambulance a : ambulancesMap.values()) {
			if (a.getState().compareTo(State.FREE)==0 && a.getArea().equals(bestArea))
				result = a;
		}
		return result;
			
	}



	// metodo che prende solamente le aeree con ambulanze libere
	private Set<Area> getAreaWithFreeAmbulances(Map<Integer, Ambulance> ambulancesMap) {
		
		Set<Area> result = new HashSet<>();
		for (Ambulance a : ambulancesMap.values()) {
			if (a.getState().compareTo(State.FREE)==0)
				result.add(a.getArea());
		
		}
		return result;
	}



	private Ambulance AmbulancesSameDistrictOfCollision(Map<Integer, Ambulance> map, Area areaCollison) {
		// TODO Auto-generated method stub
		for (Ambulance a : map.values())
			if (a.getArea().equals(areaCollison))		
				return a;
		
		return null;
	}



	private boolean checkFreeAmbulances(Map<Integer, Ambulance> map) {
		// TODO Auto-generated method stub
		for (Ambulance a : map.values())
			if (a.getState().compareTo(State.FREE)==0)		
				return true;
				
		return false;
	}



	public Set<String> getPlaceCollisionSet() {
		return placeCollisionSet;
	}


	public int getCollisionsNumber() {
		return collisionsNumber;
	}


	public int getPeopleSavedNumber() {
		return peopleSavedNumber;
	}
	
	
	
	

}
