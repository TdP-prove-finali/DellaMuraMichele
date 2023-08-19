package it.polito.tdp.tesiSimulatore.model;

import java.util.Objects;

import com.javadocmd.simplelatlng.LatLng;

public class Area {
	
	private int areaID;
	private String areaName;
	private LatLng coords;
	
	
	public Area(int areaID, String areaName, LatLng coords) {
		this.areaID = areaID;
		this.areaName = areaName;
		this.coords = coords;
	}


	public int getAreaID() {
		return areaID;
	}


	public String getAreaName() {
		return areaName;
	}


	public LatLng getCoords() {
		return coords;
	}
	

	
	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public void setCoords(LatLng coords) {
		this.coords = coords;
	}
	
	


	@Override
	public String toString() {
		return "Area: "  + areaID + ", " + areaName + ", " + coords;
	}


	@Override
	public int hashCode() {
		return Objects.hash(areaID, areaName, coords);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Area other = (Area) obj;
		return areaID == other.areaID && Objects.equals(areaName, other.areaName)
				&& Objects.equals(coords, other.coords);
	}



	
	

	

}
