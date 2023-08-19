package it.polito.tdp.tesiSimulatore.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class Ambulance {
	
	
	public enum State {
		FREE,
		OCCUPIED
	}
	
	private int id;
	private Area area;
	private LocalDateTime istant;
	private State state;
	
	
	public Ambulance(int id, Area area, LocalDateTime istant, State state) {
		this.id = id;
		this.area = area;
		this.istant = istant;
		this.state = state;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Area getArea() {
		return area;
	}


	public void setArea(Area area) {
		this.area = area;
	}


	public LocalDateTime getIstant() {
		return istant;
	}


	public void setIstant(LocalDateTime istant) {
		this.istant = istant;
	}


	public State getState() {
		return state;
	}


	public void setState(State state) {
		this.state = state;
	}


	@Override
	public int hashCode() {
		return Objects.hash(area, id, istant, state);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ambulance other = (Ambulance) obj;
		return Objects.equals(area, other.area) && id == other.id && Objects.equals(istant, other.istant)
				&& state == other.state;
	}
	
	
	
	




	

}
