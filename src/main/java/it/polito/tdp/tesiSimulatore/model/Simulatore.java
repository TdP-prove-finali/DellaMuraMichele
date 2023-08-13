package it.polito.tdp.tesiSimulatore.model;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;

import java.util.PriorityQueue;
import java.util.Set;



import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.tesiSimulatore.dao.LAcollisionDAO;
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
	// lista di tutti gli incidenti avvenuti nell'anno e dal mese specificati
	List<Collision> collisionsYearAndMonthSpecifiedList;
	

	
	//variabili di uscita
	private int collisionNumber;
	private int peopleSavedNumber;
	private Set<String> placeCollisionSet;


	
	//coda degli eventi
	PriorityQueue<Event> queue;

	
	
	
	
	
	public Simulatore(Integer year, Integer month, int txtNumberAmbulance, int txtNumberHospital, double txtProbability) {
		
		this.year = year;
		this.month = month;
		this.txtNumberAmbulance = txtNumberAmbulance;
		this.txtNumberHospital = txtNumberHospital;
		this.txtProbability = txtProbability;
		this.dao = new LAcollisionDAO();
		this.collisionsYearAndMonthSpecifiedList = new ArrayList<>();
		this.collisionNumber = 0;
		this.peopleSavedNumber = 0;
		this.placeCollisionSet = new HashSet<>();
		
		
	}


	public void popolaCoda() {
		
		this.queue = new PriorityQueue<Event>();
		
		this.collisionsYearAndMonthSpecifiedList = dao.getCollisionsYearAndMonthSpec(year, month);
		
		List<Event> events = new ArrayList<>();
		
		for (Collision c : collisionsYearAndMonthSpecifiedList) {
			Event e = null;
			LatLng coords = new LatLng(c.getLatitude(), c.getLongitude());
			Area area = new Area (c.getAreaID(), c.getAreaName(), coords);
			LocalDate date = c.getDate();
			
			// aggiungo al set  il tipo di struttura o luogo in cui si è verificato l' incidente
			this.placeCollisionSet.add(c.getLocation());
			
			if (c.getVictimAge() < 18 || c.getVictimAge() > 70) {
				 e = new Event(date, EventType.RED, area);
			}
			else {
				 e = new Event(date, EventType.YELLOW, area);
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
			
			
			switch (e.getType()) {
			
			case RED:

				

				break;

			case YELLOW:

				
				
				break;
				}

				break;
			} // switch
		
	}
	

}
