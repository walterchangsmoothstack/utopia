package com.ss.utopia.adminTests;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.service.AdminPassenger;

public class passengerTest {
	
	
	AdminPassenger admin = new AdminPassenger();
	Passenger passenger = new Passenger(null, new Book(30, null, null), "Bruce", "Wayne", LocalDate.now().minusYears(40), "male", "123 Batman Rd, Gotham City");
	
	@Test
	public void testAddPassengerTrue() {
		System.out.println(passenger);
		assertEquals("Added PASSENGER successfully", admin.addPassenger(passenger));
		passenger.setBookId(new Book(30, null, null));
		System.out.println(admin.addPassenger(passenger));
		passenger.setBookId(new Book(29, null, null));
		System.out.println(admin.addPassenger(passenger));
		passenger.setBookId(new Book(28, null, null));
		System.out.println(admin.addPassenger(passenger));
		
	}
	@Test
	public void testAddPassengerFalse() {
		passenger.getBookId().setId(999);;
		assertEquals("Unable to add PASSENGER", admin.addPassenger(passenger));
	}
	@Test
	public void testAddPassengerNull() {
		assertEquals("Unable to add PASSENGER", admin.addPassenger(null));
	}
	@Test
	public void testAddPassengerNullValue() {
		passenger.setFamilyName(null);
		assertEquals("Unable to add PASSENGER", admin.addPassenger(passenger));
		
		passenger.setDob(null);
		assertEquals("Unable to add PASSENGER", admin.addPassenger(passenger));
		
		passenger.setAddress(null);
		assertEquals("Unable to add PASSENGER", admin.addPassenger(passenger));
		
		passenger.setGender(null);
		assertEquals("Unable to add PASSENGER", admin.addPassenger(passenger));
		
		passenger.setGivenName(null);
		assertEquals("Unable to add PASSENGER", admin.addPassenger(passenger));
	}
	@Test
	public void testReadPassenger() {
		//System.out.println(admin.readPassengers());
	}
	@Test
	public void testReadPassengerName() {
		passenger.setGivenName("Bruce");
		passenger.setFamilyName("Wayne");
		//System.out.println(admin.readPassengerName(passenger));
	}
	@Test
	public void testUpdatePassenger() {
		assertEquals("Failed to update PASSENGER", admin.updatePassenger(null));
		passenger.setId(13);
		passenger.setFamilyName("Willis");
		assertEquals("Updated PASSENGER successfully", admin.updatePassenger(passenger));
	}
	@Test
	public void testDeletePassengerId() {
		passenger.setId(60);
		//assertEquals("Deleted PASSENGER successfully", admin.deletePassengerId(passenger));
	}
	@Test
	public void testDeletePassengerByBookingId() {
		//assertEquals("Deleted PASSENGER successfully", admin.deletePassengerBookingId(new Book(10, null, null)));
	}
	
	
}
