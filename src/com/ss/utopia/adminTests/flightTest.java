package com.ss.utopia.adminTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Date;
import java.sql.Timestamp;

import org.junit.Test;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminFlight;

public class flightTest {
	AdminFlight admin = new AdminFlight();
	Flight flight = new Flight(10, new Route(47, null, null), new Airplane(1, null), 100,
			new Timestamp(System.currentTimeMillis()), (float) 50.0);
	@Test
	public void testAddFlightTrue() {
		flight.setId(22);
		flight.setAirplane(new Airplane(3, null));
		flight.setReservedSeats(400);
		assertEquals("Added FLIGHT successfully", admin.addFlight(flight));
	}
	@Test
	public void testAddFlightFalse() {
		//assertNotEquals("Added FLIGHT successfully", admin.addFlight(flight));
	}
	@Test
	public void testReadFlight() {
		System.out.println(admin.readFlight());
	}
	@Test
	public void testReadFlightByRoute() {
		System.out.println(admin.readFlightRoute(new Route(47, null, null)));
	}
	@Test
	public void testUpdateFlight() {
		flight.setId(12);
		flight.setAirplane(new Airplane(1, null));
		flight.setRoute(new Route(49, null, null));
		System.out.println(admin.updateFlight(flight));
	}
	@Test
	public void testUpdateNull() {
		System.out.println(admin.updateFlight(null));
	}
	@Test
	public void testDeleteFlight() {
		flight.setId(11);
		admin.deleteFlight(flight);
	}
	@Test
	public void testDeleteFlightNull() {
		System.out.println(admin.deleteFlight(null));
	}
	
	
}
