/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;

/**
 * @author Walter Chang
 *
 */
public class AirportDAO extends BaseDAO<Airport>{

	
	
	public AirportDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void addAirport(Airport airport) throws SQLException, ClassNotFoundException {
		save("INSERT INTO airport VALUES(?,?)", 
				new Object[] {airport.getAirportId(), airport.getCity()});
	
	}
	
	public void updateAirport(Airport airport) throws SQLException, ClassNotFoundException {
		save("UPDATE airport SET city = ? WHERE iata_id = ?", 
				new Object[] {airport.getCity(), airport.getAirportId()});
	}
	public void updateAirportCode(String oldCode, String newCode) throws SQLException, ClassNotFoundException {
		save("UPDATE airport SET iata_id = ?  WHERE iata_id = ?  ", 
				new Object[] {newCode, oldCode});
	}
	
	public void deleteAirport(String airportId) throws SQLException, ClassNotFoundException {
		save("DELETE FROM airport WHERE iata_id = ?",
				new Object[] {airportId});
	}
	
	public List<Airport> readAirports() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airport", null);
	}
	public List<Airport> readAirportsInCity(String city) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airport WHERE city = ?", new Object[] {city});
	}
	
	protected List<Airport> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Airport> airportList = new ArrayList<>();
		while(rs.next()) { 
			Airport airport = new Airport();
			airport.setAirportId(rs.getString("iata_id")); 
			airport.setCity(rs.getString("city"));
			airportList.add(airport);
	}
		return airportList;
	}
	
	
	
}
