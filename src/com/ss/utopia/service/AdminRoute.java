/**
 * 
 */
package com.ss.utopia.service;

import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang Class function: takes care of calls from the menu having
 *         to do with administration of Route data. Calls functions in
 *         AdminServices organized by Create, Read, Update, and Delete.
 *
 */
public class AdminRoute {
	
	/* Have a connUtil to pass to AdminServices for getting a connection*/
	ConnectionUtil connUtil = new ConnectionUtil();

	/*
	 * Basic add route function. Takes in a route and adds it to the DB and
	 * generates a key
	 */
	public String addRoute(Route route) {
		AdminServices admin = new AdminServices();
		return admin.add(route, Service.ROUTE, connUtil);
	}

	/* Read all routes */
	public String readRoutes() {
		AdminServices admin = new AdminServices();
		return admin.read(null, Service.ROUTE, connUtil);
	}

	/* Read all going to and from an airport using airportId */
	public String readRoutesByAirportId(String airportId) {
		if (airportId == null) {
			return "Failed to read ROUTE";
		}
		AdminServices admin = new AdminServices();
		return admin.read(airportId, Service.ROUTE, connUtil);

	}

	/* Update a route by taking in a Route object and updating based on id */
	public String updateRoute(Route route) {
		/* Does not allow duplicate values. Null-safe */
		if (route == null || java.util.Objects.equals(route.getOriginAirport(), route.getDestinationAirport())) {
			return "Failed to update ROUTE";
		}
		AdminServices admin = new AdminServices();
		return admin.update(route, Service.ROUTE, connUtil);
	}

	/* Delete a route based off the route id */
	public String deleteRoute(Integer routeId) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] { routeId }, Service.ROUTE, connUtil);
	}

	/* Delete a route using the Foreign Key in case the route id is unknown */
	public String deleteRouteFK(String originAirport, String destinationAirport) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] { originAirport, destinationAirport }, Service.ROUTE, connUtil);
	}
}
