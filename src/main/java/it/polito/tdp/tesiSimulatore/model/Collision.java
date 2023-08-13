package it.polito.tdp.tesiSimulatore.model;

import java.time.LocalDate;
import java.util.Objects;


public class Collision {
	
	private int  drNumber;
	private int timeOccured;
	private int areaID;
	private String areaName;
	private int victimAge;
	private String location;
	private LocalDate date;
	private double latitude;
	private double longitude;
	
	
	public Collision(int drNumber, int timeOccured, int areaID, String areaName, int victimAge, String location,
			LocalDate date, double latitude, double longitude) {
		this.drNumber = drNumber;
		this.timeOccured = timeOccured;
		this.areaID = areaID;
		this.areaName = areaName;
		this.victimAge = victimAge;
		this.location = location;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	public int getDrNumber() {
		return drNumber;
	}


	public void setDrNumber(int drNumber) {
		this.drNumber = drNumber;
	}


	public int getTimeOccured() {
		return timeOccured;
	}


	public void setTimeOccured(int timeOccured) {
		this.timeOccured = timeOccured;
	}


	public int getAreaID() {
		return areaID;
	}


	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}


	public String getAreaName() {
		return areaName;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public int getVictimAge() {
		return victimAge;
	}


	public void setVictimAge(int victimAge) {
		this.victimAge = victimAge;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
	


	@Override
	public int hashCode() {
		return Objects.hash(areaID, areaName, date, drNumber, latitude, location, longitude, timeOccured, victimAge);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collision other = (Collision) obj;
		return areaID == other.areaID && Objects.equals(areaName, other.areaName) && Objects.equals(date, other.date)
				&& drNumber == other.drNumber
				&& Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude)
				&& Objects.equals(location, other.location)
				&& Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude)
				&& timeOccured == other.timeOccured && victimAge == other.victimAge;
	}
	
	
	
	
	
	
	

}
