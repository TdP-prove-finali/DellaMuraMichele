package it.polito.tdp.tesiSimulatore.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class Event implements Comparable<Event>{
	
	public enum EventType {
		
		RED,
		YELLOW

	}
	
	private LocalDateTime istant ;
	private EventType type ;
	private Area areaCollision ;
	

	public Event(LocalDateTime istant, EventType type, Area areaCollision) {
		super();
		this.istant = istant;
		this.type = type;
		this.areaCollision = areaCollision;
	}
	
	



	public LocalDateTime getIstant() {
		return istant;
	}





	public void setIstant(LocalDateTime istant) {
		this.istant = istant;
	}





	public EventType getType() {
		return type;
	}





	public void setType(EventType type) {
		this.type = type;
	}





	public Area getAreaCollision() {
		return areaCollision;
	}





	public void setAreaCollision(Area areaCollision) {
		this.areaCollision = areaCollision;
	}





	@Override
	public int compareTo(Event other) {

		return this.istant.compareTo(other.istant);
	}
	
	


	@Override
	public int hashCode() {
		return Objects.hash(areaCollision, istant, type);
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(areaCollision, other.areaCollision) && Objects.equals(istant, other.istant)
				&& type == other.type;
	}





	@Override
	public String toString() {
		return "Event: " + istant + ", " + type + ", " + areaCollision;
	}
	
	
	



	
	
	
	
	
	

}
