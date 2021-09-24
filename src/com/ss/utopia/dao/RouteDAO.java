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
	
	public void addRoute(Route route) throws SQLException, ClassNotFoundException {

		Integer pk = savePK("INSERT INTO route VALUES (?, ?)",
				new Object[] {route.getOriginAirport().getAirportId(), route.getDestinationAirport().getAirportId()});
		System.out.println(pk);
	}
	public void deleteRoute(String route_id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM route WHERE id = ?",
				new Object[] {route_id});
	}
	public void updateRoute(Route route) throws ClassNotFoundException, SQLException {
		save("UPDATE route SET origin_id = ? AND destination_id = ? WHERE origin_id = ?",
				new Object[] {route.getOriginAirport(), route.getDestinationAirport(), route.getRouteId()});
	}
	public List<Route> readRoutes() throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM routes", null);
	}
	
	@Override
	protected List<Route> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Route> routes = new ArrayList<>();
		while (rs.next()) {
			Route route = new Route();
			route.setRouteId(rs.getInt("id"));
			route.getOriginAirport().setAirportId(rs.getString("origin_id"));
			route.getDestinationAirport().setAirportId(rs.getString("destination_id"));
			routes.add(route);
		}
		return routes;
	}
}
