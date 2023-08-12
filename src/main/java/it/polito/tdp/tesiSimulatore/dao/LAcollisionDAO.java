package it.polito.tdp.tesiSimulatore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.tesiSimulatore.model.Area;


public class LAcollisionDAO {
	
	/**
	 * Metodo per leggere la lista di tutte le vendite nel database
	 * @return
	 */

	public List<Area> getVertici(Integer year) {
		
		String query = "SELECT DISTINCT la.`Area ID`, la.`Area Name` "
				+ "FROM la_traffic_collision la "
				+ "WHERE YEAR(la.`Data`) = ? "
				+ "ORDER BY la.`Area ID` ASC";
		List<Area> result = new ArrayList<Area>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Area (rs.getInt("Area ID"), rs.getString("Area Name"), null) );
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public LatLng getCoordsYearAndAreaSpecified(Integer year, int areaID) {
		
		String query = "SELECT l.`Area ID`, l.`Area Name`, AVG(l.Latitude) AS avgLat, AVG(l.Longitude) AS avgLon "
				+ "FROM la_traffic_collision l "
				+ "WHERE YEAR(l.`Data`) = ? AND l.`Area ID` = ? "
				+ "GROUP BY l.`Area ID`, l.`Area Name`";
		LatLng result = null;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, year);
			st.setInt(2, areaID);
			ResultSet rs = st.executeQuery();

			if (rs.first()) {
				result = new LatLng(rs.getDouble("avgLat"),rs.getDouble("avgLon"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return result;
	}


}
