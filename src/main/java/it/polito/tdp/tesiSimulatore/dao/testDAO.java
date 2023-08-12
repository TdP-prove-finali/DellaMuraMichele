package it.polito.tdp.tesiSimulatore.dao;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.tesiSimulatore.model.Area;


public class testDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LAcollisionDAO dao = new LAcollisionDAO();
		
		List<Area> listAreas = new ArrayList<>();
		
		
		listAreas = dao.getVertici(2022);
		System.out.println(listAreas.size());

	}

}
