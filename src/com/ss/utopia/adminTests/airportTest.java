package com.ss.utopia.adminTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.SQLException;

import org.junit.Test;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.service.AdminAirport;

public class airportTest {
	AdminAirport admin = new AdminAirport();
	Airport airport = new Airport();

	@Test
	public void testAddAirport(){
		airport.setAirportId("LAX");
		airport.setCity("Los Angeles");
		//assertEquals("Added AIRPORT successfully", admin.addAirport(airport));
		
	}
	
	@Test
	public void testAddAirportNull() {
		assertNotEquals("Added AIRPORT successfully", admin.addAirport(null));

		airport.setAirportId("LAx");
		airport.setCity("Los Angeles");
		assertNotEquals("Added AIRPORT successfully", admin.addAirport(airport));
		
		airport.setAirportId("");
		airport.setCity("Los Angeles");
		assertNotEquals("Added AIRPORT successfully", admin.addAirport(airport));
		
		airport.setAirportId("LAXX");
		airport.setCity("Los Angeles");
		assertNotEquals("Added AIRPORT successfully", admin.addAirport(airport));
		

		airport.setAirportId("LAX");
		airport.setCity("");
		assertNotEquals("Added AIRPORT successfully", admin.addAirport(airport));
		
		airport.setAirportId(null);
		airport.setCity("Los Angeles");
		assertNotEquals("Added AIRPORT successfully", admin.addAirport(airport));
		
		airport.setAirportId("LAX");
		airport.setCity(null);
		assertNotEquals("Added AIRPORT successfully", admin.addAirport(airport));
	
	}
	@Test
	public void testDeleteAirport() {
		//assertEquals("Deleted AIRPORT successfully", admin.deleteAirport("LAX"));
			
	}
	@Test
	public void testDeleteAirportFalse() {
		assertEquals("Deleted AIRPORT successfully", admin.deleteAirport(""));
	}
	@Test
	public void testReadAirports() {
		//System.out.println(admin.readAirports());
		assertNotEquals("Unable to read AIRPORT", admin.readAirports());
	}
	@Test
	public void testReadAirportsInCityFalse() {
		System.out.println(admin.readAirportsInCity(null));
		assertNotEquals("Unable to read AIRPORT", admin.readAirportsInCity("San Diego"));
	}
	@Test
	public void testReadAirportsInCityTrue() {
		assertEquals("No AIRPORT found", admin.readAirportsInCity("Saskatchewan"));
	}
	@Test
	public void testUpdateAirport() {
		airport.setAirportId(null);
		airport.setCity("Los Angeles");
		System.out.println(admin.updateAirport(airport));
	}
}
