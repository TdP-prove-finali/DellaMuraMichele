package it.polito.tdp.tesiSimulatore.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;


import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.tesiSimulatore.model.Area;
import it.polito.tdp.tesiSimulatore.model.Collision;


public class LAcollisionDAO {
	

	/**
	 * Metodo per identificare le zone presenti nella città di Los Angeles nell'anno specificato
	 * @param year
	 * @return List
	 */

	public List<Area> getVertici(Integer year) {
		
		String query = "SELECT DISTINCT la.`Area ID`, la.`Area Name`, AVG(la.Latitude) AS avgLat, AVG(la.Longitude) AS avgLon "
				+ "FROM la_traffic_collision la "
				+ "WHERE YEAR(la.`Data`) = ? "
				+ "GROUP BY la.`Area ID`, la.`Area Name`";
		List<Area> result = new ArrayList<Area>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				LatLng coords = new LatLng(rs.getDouble("avgLat"),rs.getDouble("avgLon"));
				result.add(new Area (rs.getInt("Area ID"), rs.getString("Area Name"), coords) );
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
//	/**
//	 * Metodo per ottenere le coordinate di una zona presente nel database nell'anno e ID dell'area specificati
//	 * @param year, areaID
//	 * @return LatLng
//	 */
//
//	public LatLng getCoordsYearAndAreaSpecified(Integer year, int areaID) {
//		
//		String query = "SELECT l.`Area ID`, l.`Area Name`, AVG(l.Latitude) AS avgLat, AVG(l.Longitude) AS avgLon "
//				+ "FROM la_traffic_collision l "
//				+ "WHERE YEAR(l.`Data`) = ? AND l.`Area ID` = ? "
//				+ "GROUP BY l.`Area ID`, l.`Area Name`";
//		LatLng result = null;
//
//		try {
//			Connection conn = DBConnect.getConnection();
//			PreparedStatement st = conn.prepareStatement(query);
//			st.setInt(1, year);
//			st.setInt(2, areaID);
//			ResultSet rs = st.executeQuery();
//
//			if (rs.first()) {
//				result = new LatLng(rs.getDouble("avgLat"),rs.getDouble("avgLon"));
//			}
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Errore connessione al database");
//			throw new RuntimeException("Error Connection Database");
//		}
//		return result;
//	}
	
	/**
	 * Metodo per ottenere gli incidenti stradali avvenuti nella città di LA
	 * nell'anno e dal mese specificati.
	 * Si noti che non vengono considerate le collisioni con età della vittima sconosciuta
	 * @param  year, month
	 * @return List
	 */

	// non considero gli incidenti che non hanno indicato l'età della vittima (l.`Victim Age` > 0)
	public List<Collision> getCollisionsYearAndMonthSpec(Integer year, Integer month) {
		
		String query = "SELECT l.`DR Number`, l.`Area ID`, l.`Area Name`, l.`Victim Age`, l.`Premise Description`, l.`Data`, l.Latitude, l.Longitude "
				+ "FROM la_traffic_collision l "
				+ "WHERE YEAR(l.`Data`) = ? AND MONTH(l.`Data`) >= ? AND l.`Victim Age` > 0 ";
		List<Collision> result = new ArrayList<Collision>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, year);
			st.setInt(2, month);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add( new Collision (rs.getInt("DR Number"),
											rs.getInt("Area ID"), rs.getString("Area Name"),
											rs.getInt("Victim Age"), rs.getString("Premise Description"),
											rs.getDate("Data").toLocalDate(), rs.getDouble("Latitude"), rs.getDouble("Longitude")) );
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}


}
