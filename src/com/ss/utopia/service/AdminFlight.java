/**
 * 
 */
package com.ss.utopia.service;

import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang 
 * No check currently for multiple flights of same airplane
 * or regard for departure time 
 * --Class Function: handles administration
 * commands that have to do with passenger data manipulation. Calls the
 * AdminService which organizes the commands by Create, Read, Update,
 * and Delete
 */
public class AdminFlight {

	ConnectionUtil connUtil = new ConnectionUtil();

	/* Add a flight*/
	public String addFlight(Flight flight) {
		AdminServices admin = new AdminServices();
		return admin.add(flight, Service.FLIGHT, connUtil);
	}
	/* Read all flights */
	public String readFlight() {
		AdminServices admin = new AdminServices();
		return admin.read(null, Service.FLIGHT, connUtil);
	}
	
	/* Read flights based off of origin and destination (Route) using id*/
	public String readFlightRoute(Route route) {
		AdminServices admin = new AdminServices();
		return admin.read(route, Service.FLIGHT, connUtil);
	}
	
	/* Update a flight*/
	public String updateFlight(Flight flight) {
		AdminServices admin = new AdminServices();
		return admin.update(flight, Service.FLIGHT, connUtil);
	}
	
	/* Delete a flight */
	public String deleteFlight(Flight flight) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] { flight }, Service.FLIGHT, connUtil);
	}
}
