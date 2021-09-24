/**
 * 
 */
package com.ss.utopia.service;

import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang
 * No check currently for multiple flights of same airplane or regard for departure time
 *
 */
public class AdminFlight {

	ConnectionUtil connUtil = new ConnectionUtil();
	
	public String addFlight(Flight flight) {
		AdminServices admin = new AdminServices();
		return admin.add(flight, Service.FLIGHT, connUtil);
	}
	public String readFlight() {
		AdminServices admin = new AdminServices();
		return admin.read(null, Service.FLIGHT, connUtil);
	}
	public String readFlightRoute(Route route) {
		AdminServices admin = new AdminServices();
		return admin.read(route, Service.FLIGHT, connUtil);
	}
	public String updateFlight(Flight flight) {
		AdminServices admin = new AdminServices();
		return admin.update(flight, Service.FLIGHT, connUtil);
	}
	public String deleteFlight(Flight flight) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] {flight}, Service.FLIGHT, connUtil);
	}
}
