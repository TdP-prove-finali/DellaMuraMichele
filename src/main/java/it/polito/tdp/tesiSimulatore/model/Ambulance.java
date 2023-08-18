package it.polito.tdp.tesiSimulatore.model;

import java.time.LocalDateTime;


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
	
	




	

}
