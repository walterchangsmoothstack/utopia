package com.ss.utopia.adminTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.SQLException;

import org.junit.Test;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.service.AdminAirport;

public class airportTest {
	AdminAirport admin = new AdminAirport();
	
	@Test
	public void testAddAirport(){
		Airport airport = new Airport();
		airport.setAirportId("ABC");
		airport.setCity("Does Not Exist");
		assertEquals("Added AIRPORT successfully", admin.addAirport(airport));
		
	}
	@Test
	public void testDeleteAirport() {
		assertEquals("Deleted AIRPORT successfully", admin.deleteAirport("ABC"));
			
	}
	@Test
	public void testReadAirports() {
		System.out.println(admin.readAirports());
		assertNotEquals("Unable to read AIRPORT", admin.readAirports());
	}

}
