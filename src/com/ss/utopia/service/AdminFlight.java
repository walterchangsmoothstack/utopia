/**
 * 
 */
package com.ss.utopia.service;

import java.util.List;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
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
	
	/* Update a certain airport code using its airport code (changes code)*/
	public String updateAirportCode(String airportCode) {
		if(airportCode == null) {
			return "Failed to update AIRPORT";
		}
		AdminServices admin = new AdminServices();
		return admin.update(airportCode, Service.AIRPORT, connUtil);
	}

	/* Delete a certain airport using its airport code */
	public String deleteAirport(String airportId) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] { airportId }, Service.AIRPORT, connUtil);

	}
	
	/* Read a list of airports for creation of airport*/
	public List<Airport> createAirportList(){
		AdminServices admin = new AdminServices();
		return (List<Airport>)admin.create(null, Service.AIRPORT, connUtil);
	}
	
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
	
	public Route findRouteByAirports(Airport airport1, Airport airport2) {
		
		AdminServices admin = new AdminServices();
		return (Route)admin.create(new Object[] {airport1, airport2}, Service.ROUTE, connUtil).get(0);

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
	
	public List<Airplane> createAirplaneList(){
		AdminServices admin = new AdminServices();
		return (List<Airplane>)admin.create(null, Service.AIRPLANE, connUtil);
	}
	public List<Flight> createFlightList(){
		AdminServices admin = new AdminServices();
		return (List<Flight>)admin.create(null, Service.FLIGHT, connUtil);
	}
	
	public List<Airplane> createAirplaneListByType(AirplaneType type){
		AdminServices admin = new AdminServices();
		return (List<Airplane>)admin.create(new Object[] {type}, Service.AIRPLANE, connUtil);
	}
	
	public List<AirplaneType> createAirplaneTypeList(){
		AdminServices admin = new AdminServices();
		return (List<AirplaneType>)admin.create(null, Service.AIRPLANETYPE, connUtil);
	}
	public List<Route> createRouteList(){
		AdminServices admin = new AdminServices();
		return (List<Route>)admin.create(null, Service.ROUTE, connUtil);
	}
	public List<FlightBooking> createFlightBookingList(){
		AdminServices admin = new AdminServices();
		return (List<FlightBooking>)admin.create(null, Service.FLIGHT_BOOKING, connUtil);
	}
	public String updateAirplaneType(AirplaneType airplaneType) {
		AdminServices admin = new AdminServices();
		return admin.update(airplaneType, Service.AIRPLANETYPE, connUtil);
	}
	
}
