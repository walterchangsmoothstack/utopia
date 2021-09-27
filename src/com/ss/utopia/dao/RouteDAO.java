/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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


public class RouteDAO extends BaseDAO<Route> {
	Connection conn = null;

	public RouteDAO(Connection conn) {
		super(conn);
	}
	
	public Integer addRoute(Route route) throws SQLException, ClassNotFoundException {
		return savePK("INSERT INTO route (origin_id, destination_id) VALUES (?, ?)",
				new Object[] {route.getOriginAirport(), route.getDestinationAirport()});
	}
	public void deleteRoute(Integer route_id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM route WHERE id = ?",
				new Object[] {route_id});
	}
	public void deleteRouteFK(String originAirport, String destinationAirport) throws ClassNotFoundException, SQLException {
		save("DELETE FROM route WHERE origin_id = ? AND destination_id = ?",
				new Object[] {originAirport, destinationAirport});
	}
	public void updateRoute(Route route) throws ClassNotFoundException, SQLException {

			save("UPDATE route SET origin_id = ? , destination_id = ? WHERE id = ?",
					new Object[] {route.getOriginAirport(), route.getDestinationAirport(), route.getRouteId()});

	}
	public List<Route> readRoutesByAirportId(String airportId) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM route WHERE origin_id = ? OR destination_id = ?", 
						new Object[] { airportId, airportId });
	}
	public List<Route> readRouteByAirports(Airport airport1, Airport airport2) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM route WHERE origin_id = ? AND destination_id = ?", 
						new Object[] { airport1.getAirportId(), airport2.getAirportId() });
	}
	
	public List<Route> readRoutes() throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM route", null);
	}
	
	@Override
	protected List<Route> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Route> routes = new ArrayList<>();
		while (rs.next()) {
			Route route = new Route(rs.getInt("id"), rs.getString("origin_id"), rs.getString("destination_id"));

			routes.add(route);
		}
		return routes;
	}
}
