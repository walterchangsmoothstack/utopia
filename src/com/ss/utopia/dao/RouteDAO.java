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
	
	public void addRoute(Route route) throws SQLException {

	}
	public void deleteRoute(Route route) {
		
	}
	public void updateRoute(Route rout) {
		
	}
	public List<Route> readRoutes(){
		return null;
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
