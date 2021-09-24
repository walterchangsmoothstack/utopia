/**
 * 
 */
package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.RouteDAO;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang Class Function: handles administration commands that
 *         have to do with airport data manipulation Calls the AdminService
 *         which organizes the commands by Create, Read, Update, and Delete
 */
public class AdminAirport {

	/* Have a connUtil to pass to AdminServices for getting a connection */
	ConnectionUtil connUtil = new ConnectionUtil();

	/* Adds an airport, must have airportId and city */
	public String addAirport(Airport airport) {
		/*
		 * If airport's values are null, it will break the query. AirportCode must
		 * consist of 3 uppercase characters. The city should not be an empty string.
		 */
		if (airport == null || airport.getAirportId() == null || "".equals(airport.getCity())
				|| airport.getAirportId().length() != 3
				|| !airport.getAirportId().equals(airport.getAirportId().toUpperCase())) {
			return "Failed to add AIRPORT";
		}
		AdminServices admin = new AdminServices();
		return admin.add(airport, Service.AIRPORT, connUtil);
	}
	/* Read all airports */
	public String readAirports() {

		AdminServices admin = new AdminServices();
		return admin.read(null, Service.AIRPORT, connUtil);
	}

	/* Read all airports that are in a certain city */
	public String readAirportsInCity(String city) {
		if(city == null) {
			return "Failed to read AIRPORT";
		}
		AdminServices admin = new AdminServices();
		return admin.read(city, Service.AIRPORT, connUtil);
	}

	/* Update a certain airport using its airport code (changes city)*/
	public String updateAirport(Airport airport) {
		if(airport == null || airport.getAirportId() == null) {
			return "Failed to update AIRPORT";
		}
		AdminServices admin = new AdminServices();
		return admin.update(airport, Service.AIRPORT, connUtil);
	}

	/* Delete a certain airport using its airport code */
	public String deleteAirport(String airportId) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] { airportId }, Service.AIRPORT, connUtil);

	}

}
