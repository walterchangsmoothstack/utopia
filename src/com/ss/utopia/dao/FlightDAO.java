/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;

/**
 * @author Walter Chang
 *
 */
public class FlightDAO extends BaseDAO<Flight>{

	public FlightDAO(Connection conn) {
		super(conn);
	}
	

	public void addFlight(Flight flight) throws SQLException, ClassNotFoundException {
		
		
		savePK("INSERT INTO flight (id, route_id, airplane_id, departure_time, reserved_seats, "
				+ "seat_price) VALUES(?,?,?,?,?,?)", 
				new Object[] {flight.getId(), flight.getRoute().getRouteId(), flight.getAirplane().getAirplaneId(), 
						flight.getDepartureTime(), flight.getReservedSeats(), flight.getSeatPrice()});
	}
	public void updateFlight(Flight flight) throws SQLException, ClassNotFoundException {
		save("UPDATE flight SET route_id = ? , airplane_id = ?, "
				+ " departure_time = ?, reserved_seats = ?, seat_price =? WHERE id = ?", 
				new Object[] {flight.getRoute().getRouteId(), flight.getAirplane().getAirplaneId()
						, flight.getDepartureTime(), flight.getReservedSeats(), flight.getSeatPrice()
						, flight.getId()});
	}
	
	public void deleteFlight(Integer id) throws SQLException, ClassNotFoundException {
		save("DELETE FROM flight WHERE id = ?",
				new Object[] {id});
	}
	
	public List<Flight> readFlight() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight", null);
	}
	public List<Flight> readFlightByRoute(Route route) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight WHERE route_id = ?", new Object[] {route.getRouteId()});
	}
	
	protected List<Flight> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Flight> flightList = new ArrayList<>();
		while(rs.next()) { 
			Flight flight = new Flight();
			flight.setId(rs.getInt("id"));
			flight.setAirplane(new Airplane(rs.getInt("airplane_id"), null));
			flight.setRoute(new Route(rs.getInt("route_id"), null, null));
			flight.setDepartureTime(rs.getTimestamp("departure_time"));
			flight.setReservedSeats(rs.getInt("reserved_seats"));
			flight.setSeatPrice(rs.getFloat("seat_price"));
			flightList.add(flight);
	}
		return flightList;
	}
	

	
}
