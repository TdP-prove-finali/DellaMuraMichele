package it.polito.tdp.tesiSimulatore.model;

import java.time.LocalDate;


public class Event implements Comparable<Event>{
	
	public enum EventType {
		
		YELLOW,
		RED
	}
	
	private LocalDate date ;
	private EventType type ;
	private Area area;
	
	public Event(LocalDate date, EventType type, Area area) {
		this.date = date;
		this.type = type;
		this.area = area;
	}
	

	public LocalDate getDate() {
		return date;
	}


	public EventType getType() {
		return type;
	}
	

	public Area getArea() {
		return area;
	}


	@Override
	public int compareTo(Event other) {
		// TODO Auto-generated method stub
		return this.date.compareTo(other.date) ;
	}


	@Override
	public String toString() {
		return "Event: " + date + ", " + type + ", " + area;
	}



	
	
	
	
	
	

}
