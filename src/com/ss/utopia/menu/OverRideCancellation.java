/**
 * 
 */
package com.ss.utopia.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.service.AdminBooking;
import com.ss.utopia.service.AdminFlight;
import com.ss.utopia.service.AdminPassenger;

/**
 * @author Walter Chang
 *
 */
public class OverRideCancellation extends Menu{

	AdminFlight admin = new AdminFlight();
	
	public void checkForOveride() {
		/* Triple Nested for loop to find flights that violate max capacity. Should use 
		 * some MYSQL stored procedure perhaps instead.*/
		AdminPassenger adminP = new AdminPassenger();
		AdminBooking adminB = new AdminBooking();
		List<Flight> flights = admin.createFlightList();
		List<AirplaneType> airplaneTypes = admin.createAirplaneTypeList();
		List<Airplane> airplanes = admin.createAirplaneList();
		List<Flight> overbookedFlights = new ArrayList<>();
		List<Integer> maxCapacityList = new ArrayList<>();
		for(int i =0; i<flights.size(); i++) {
			for(int j = 0; j<airplanes.size(); j++) {
				if(flights.get(i).getAirplane().getAirplaneId().equals(airplanes.get(j).getAirplaneId())) {
					for(int k = 0; k<airplaneTypes.size(); k++) {
						if(airplaneTypes.get(k).getAirplaneTypeId().equals(airplanes.get(j).getType().getAirplaneTypeId())) {
							if(airplaneTypes.get(i).getMaxCapacity()<flights.get(i).getReservedSeats()) {
								maxCapacityList.add(airplaneTypes.get(i).getMaxCapacity());
								overbookedFlights.add(flights.get(i));
							}
								
						}
					}
					
				}
			}
		}
		/* Print out all overbooked flights*/
		System.out.println("Overbooked Flights:");
		for(int i =0; i<overbookedFlights.size(); i++) {
			
			System.out.println((i+1)+") HANDLE OVERBOOKED");
			System.out.println("Flight No. "+ overbookedFlights.get(i).getId());
			System.out.println("Reserved Seats: "+ overbookedFlights.get(i).getReservedSeats());
			System.out.println("Max capacity of airplane: "+ maxCapacityList.get(i));
		}
		System.out.println(overbookedFlights.size()+1+") Go Back");
		int choice = readInput(1, overbookedFlights.size()+1);
		if(choice == overbookedFlights.size()+1){
			runProgram();
			return;
		}
		//Link flights with flightbookings
		Flight flight = overbookedFlights.get(choice-1);

		List<FlightBooking> flightBookings = admin.createFlightBookingList();
		List<FlightBooking> flightBookingsOnFlight = new ArrayList<>();
		
		for(FlightBooking fb : flightBookings) {
			if(fb.getFlight_id().equals(flight.getId())) {
				flightBookingsOnFlight.add(fb);

			}		
		}

		//Link flightBookings with Bookings
		List<Book> books = adminP.createBookList();
		List<Book> booksOnFlight = new ArrayList<>();
		
		/* Get all bookings that are connected to this flight*/
		for(FlightBooking fb : flightBookingsOnFlight) {
			for(Book b : books) {
				if(fb.getBooking_id().equals(b.getId())) {
					booksOnFlight.add(b);
				}
			}
		}
		
		List<Passenger> passengers = adminP.createPassengerList();
		int[] numPassengers = new int[booksOnFlight.size()];
		for(int i =0; i<booksOnFlight.size(); i++) {
			for(Passenger p : passengers) {
				if(p.getBookId().getId().equals(booksOnFlight.get(i).getId())) {
					numPassengers[i]++;
				}
			}
		}
		
		int index = 1;
		for(Book b : booksOnFlight) {
			System.out.println((index++) + ") Delete Book No. " + b.getId());
			System.out.println("Number of passengers for this booking: "+ numPassengers[index-2]);
			System.out.println("-----------------------------------------------------------------");
		}
		System.out.println(index+" ) Go Back");
		choice = readInput(1, booksOnFlight.size());
		if(choice == 1 ) {
			checkForOveride();
			return;
		}
		Book booktoDelete = booksOnFlight.get(choice-1);
		
		//if we delete the book the passengers/flight_bookings/others are deleted automatically
		adminB.deleteBooking(booktoDelete);
		flight.setReservedSeats(flight.getReservedSeats()-numPassengers[choice-1]);
		admin.updateFlight(flight); //Update the flight reserved seats;
		checkForOveride();
	}
}
