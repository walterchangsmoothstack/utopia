/**
 * 
 */
package com.ss.utopia.menu;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminFlight;

/**
 * @author Walter Chang
 *
 */
public class FlightMenuOld extends Menu {
	final private double DEFAULT_PRICE = 100.0;
	final private int maxAdditions = 5;
	private AdminFlight admin = new AdminFlight();
	private Set<Integer> uniqueAirplaneId = new HashSet<>();
	private int reserved_seats = 0;
	private float seat_price = 0;
	private int startDate = 5; // hard code the range of available dates for now
	private int endDate = 15;
	private LocalDate date = LocalDate.now();
	private LocalDateTime time = date.atStartOfDay();

	/*
	 * ADD FLIGHT GOES THROUGH THE PROCESS OF ADDING A FLIGHT CALLS HELPER METHODS
	 * FROM THIS CLASS: pickAirport() -- picks two airports for the route
	 * pickAirplaneType(params) -- displays all airplanetypes then airplanes based
	 * off the type
	 */
	public void addFlight() {
		int index = 1;

		List<Airport> airports = admin.createAirportList();
		List<LocalDateTime> dates = new ArrayList<>();

		/* Choose a route */
		System.out.println("Add Flights");
		System.out.println("Please pick an origin/departure airport");
		Airport start = pickAirport();
		System.out.println("Please pick a destination/arrival airport");
		Airport end = pickAirport();

		if (start.getAirportId().equals(end.getAirportId())) {
			System.out.println("*****Please pick unique airports*****\n");
			addFlight();
			return;
		}
		/* Attain the route Id using Service */
		Route route = admin.findRouteByAirports(start, end);
		index = 0; // used for indexing bullet points
		/* First pick departure dates/times. Each will be converted into a flight */
		while (true) {
			System.out.println("Please pick one or more departure dates. " + Integer.toString(dates.size()) + "/"
					+ maxAdditions + " picked.");
			for (int i = startDate; i < endDate; i++) { // Allows for 10 flight additions 5 days ahead of today
				System.out.println(Integer.toString(i - 4) + ") " + date.plusDays(i));
			}
			System.out.println(Integer.toString(endDate - startDate + 1) + ") Continue");
			index = readInput(1, endDate - startDate + 1);
			if (index == endDate - startDate + 1) {
				if (dates.size() == 0) {
					System.out.println("*****Must choose at least 1 date*****");
					continue;
				}
				break;
			}
			/* Handles choosing a time, allow the user to also input a time. */
			System.out.println("Please choose a time.");
			for (int t = 0; t < 24; t += 3) {
				System.out.println(t / 3 + 1 + ") " + (t < 10 ? "0" : "") + t + ":00:00"); // hard coded to 8 time slots
																							// for now
			}
			System.out.println("9) Choose a departure time.");
			int t = 0;

			if ((t = readInput(1, 9)) == 9) {
				System.out.println("Please enter HH:MM");
				time = readTime(date);

				dates.add(time);
			} else {
				dates.add(date.plusDays(index + startDate - 1).atStartOfDay().plusHours(t * 3));
			}
			if (dates.size() == maxAdditions)
				break;
		}
		List<Airplane> pickedAirplanes = new ArrayList<>();
		List<AirplaneType> pickedAirplaneTypes = new ArrayList<>();
		List<AirplaneType> airplanetypes = admin.createAirplaneTypeList();
		List<Flight> flights = new ArrayList<>();
		uniqueAirplaneId.clear();
		/* Fill in additional values for each flight on dates selected */
		for (int i = 0; i < dates.size(); i++) {

			Airplane airplane = new Airplane();
			AirplaneType airplaneType = new AirplaneType();
			pickAirplaneType(airplanetypes, airplaneType, airplane, dates.get(i));
			System.out.println("Flight Overview");
			/* Print out all previous flights */
			for (int j = 0; j < pickedAirplaneTypes.size(); j++) {
				System.out.println(Integer.toString(j + 1) + ") " + "Airplane Id: "
						+ pickedAirplanes.get(j).getAirplaneId() + "	Airplane Type: "
						+ pickedAirplanes.get(j).getType().getAirplaneTypeId() + "	Max Capacity: "
						+ pickedAirplaneTypes.get(j).getMaxCapacity() + "	Reserved Seats: " + reserved_seats
						+ "   Seat Price: " + seat_price + "\n -----------------\n");
				System.out.println("Confirm Flight Addition (y/n).");

			}
			/* Print out current flight values and prompt user for confirmation */
			System.out.println(Integer.toString(pickedAirplaneTypes.size() + 1) + ") " + " From: "
					+ route.getOriginAirport() + "   To: " + route.getDestinationAirport() + "\n" + "Airplane Id: "
					+ airplane.getAirplaneId() + "	Airplane Type: " + airplaneType.getAirplaneTypeId()
					+ "	Max Capacity: " + airplaneType.getMaxCapacity() + "	Reserved Seats: " + reserved_seats
					+ "   Seat Price: $" + seat_price + "\n -----------------\n");
			System.out.println("Confirm Flight Addition (y/n).");
			/* create flight object and insert into Database one at a time. */
			if (readYesNo()) {
				pickedAirplaneTypes.add(airplaneType);
				pickedAirplanes.add(airplane);
				Flight flight = new Flight(null, route, airplane, reserved_seats, Timestamp.valueOf(dates.get(i)),
						seat_price);
				System.out.println(admin.addFlight(flight));
			} else {
				i--; // redo the previous date
				uniqueAirplaneId.remove(airplane.getAirplaneId()); // remove the id from hashset
				continue;
			}

		}
		/* Return to the beginning */
		runProgram();

	}
	/* Read Flight calls the service which returns the flights in string format*/
	public void readFlight() {
		
		System.out.println(admin.readFlight());
		System.out.println("1) Go Back");
		if(readInput(1, 1) == 1) {
			runProgram();
		}
		
	}
	
	/* Prompts the user to select one of the flights displayed to be deleted*/
	public void deleteFlight() {
		System.out.println("Choose a flight to delete");
		List<Flight> flights = admin.createFlightList();
		int index = 1; // indexing bulletpoint
		for (Flight f : flights) {
			prepareString(
					new String[] { "id", "route_id", "airplane_id", "departure_time", "reserved_seats", "seat_price" },
					new Object[] { f.getId(), f.getRoute().getRouteId(), f.getAirplane().getAirplaneId(),
							f.getDepartureTime(), f.getReservedSeats(), f.getSeatPrice() },
					index++);
		}
		System.out.println(flights.size() + 1 + ") Go Back"); // return to previous menu
		int choice = readInput(1, flights.size() + 1);
		if (choice == flights.size() + 1) {
			chooseCategory(4); // Return to Select Delete Menu from Menu.java
			return;
		}
		Flight flightToDelete = flights.get(choice-1); //save flight choice as flightToDelete
		/* Call findRoute to display the airport names of the flight's route*/
		List<String> cities = findRoute(flightToDelete.getRoute());
		flightToDelete.getRoute().setOriginAirport(cities.get(0));
		flightToDelete.getRoute().setDestinationAirport(cities.get(1));
		System.out.println("---------Confirm Flight Deletion---------");
		System.out.println(
				"Flight Id: " + flightToDelete.getId() + "   Route: " + flightToDelete.getRoute().getOriginAirport()
						+ "-" + flightToDelete.getRoute().getDestinationAirport() + "   Airplane Id: "
						+ flightToDelete.getAirplane().getAirplaneId() + "   Departure Time: "
						+ flightToDelete.getDepartureTime() + "   Reserved Seats: "
						+ flightToDelete.getReservedSeats() + "   Seat Price: " + flightToDelete.getSeatPrice());
		System.out.println("Delete? y/n");
		/* Confirm intent to delete */
		if(readYesNo()) {
			System.out.println(admin.deleteFlight(flightToDelete));
			runProgram();
		}
		else {
			/* User has selected no. Should take us back to the previous menu (flight selection)*/
			deleteFlight();
		}
		
		
	}
	

	/*
	 * START OF UPDATE FLIGHT PROCESS CALLS METHODS FROM THIS CLASS findRoute() --
	 * finds all routes and matches them to the route_id to provide airport names
	 * flightUpdateMenu() -- handles most features of the updating process
	 */
	public void updateFlight() {
		Flight flightToUpdate;
		System.out.println("Which Flight would you like to update?");
		/* Displays all flights using services for user to choose */
		List<Flight> flights = admin.createFlightList();
		int index = 1; // indexing bulletpoint
		for (Flight f : flights) {
			prepareString(
					new String[] { "id", "route_id", "airplane_id", "departure_time", "reserved_seats", "seat_price" },
					new Object[] { f.getId(), f.getRoute().getRouteId(), f.getAirplane().getAirplaneId(),
							f.getDepartureTime(), f.getReservedSeats(), f.getSeatPrice() },
					index++);
		}
		System.out.println(flights.size() + 1 + ") Go Back"); // return to previous menu
		int choice = readInput(1, flights.size() + 1);
		if (choice == flights.size() + 1) {
			chooseCategory(3); // Return to Select Update Menu from Menu.java
			return;
		}
		flightToUpdate = flights.get(choice - 1);
		/* Call findRoute to get city names, which are unavailable from FlightDAO */
		List<String> cities = findRoute(flightToUpdate.getRoute());
		flightToUpdate.getRoute().setOriginAirport(cities.get(0));
		flightToUpdate.getRoute().setDestinationAirport(cities.get(1));
		/* Call the next step of update flight */
		flightUpdateMenu(flightToUpdate);

	}

	/*
	 * HANDLES DIFFERENT VALUES FOR UPDATE OF FLIGHT CALLS METHODS FROM THIS CLASS:
	 * pickAirport() -- pick airports for new route pickAirplane(AirplaneType) --
	 * pick airplane based on type pickDepartureDate() -- pick a date from a given
	 * set of dates pickDepartureTime(LocalDate) -- pick or input a time on given
	 * LocalDate --readPositiveFloat() -- parses float for seat pricing
	 */
	public void flightUpdateMenu(Flight flightToUpdate) {
		int choice = 0;
		/* Lists all options to update */
		System.out.println("What would you like to update?");
		System.out.println("1) Routes\n2) Airplane\n3) Departure Time\n4) Reserved Seats\n5) Seat Price");
		System.out.println("6) Go Back");
		choice = readInput(1, 6);
		/*
		 * Reset to updateFlight and return if yes. Otherwise save flightToUpdate and
		 * start from flight menu
		 */
		if (choice == 6) {
			System.out.println("Are you sure? All progress will be lost.");
			if (readYesNo()) {
				updateFlight();
				return;
			} else {
				flightUpdateMenu(flightToUpdate);
				return;
			}
		}

		switch (choice) {
		case 1:
			/* ROUTE */
			Airport origin = null;
			Airport destination = null;
			/* Use null to see if user has chosen a 'GO BACK' statement */
			while (origin == null || destination == null || origin.getAirportId().equals(destination.getAirportId())) {
				System.out.println("Choose new origin airport");
				origin = pickAirport();
				System.out.println("Choose new destination airport");
				destination = pickAirport();
				/* origin will not be null if received from RouteDAO */
				if (origin.getAirportId().equals(destination.getAirportId())) {
					System.out.println("*****Please pick unique airports*****\n");
				}
			}
			/* Receive the route id */
			Route route = admin.findRouteByAirports(origin, destination); // find the route through adminservices
			// save change
			flightToUpdate.setRoute(route);
			break;
		case 2:
			/* AIRPLANE */
			Airplane airplane = null; // use airplane null as indicator user has selected 'GO BACK'
			boolean reset_seats = false; // if seats exceed max cap
			while (airplane == null) {
				System.out.println("Choose a new airplane type.");
				AirplaneType airplanetype = pickAirplaneType();
				if (airplanetype == null) {
					flightUpdateMenu(flightToUpdate);
					return;
				}
				/*
				 * The user may change reserved seats first, then return and change airplane.
				 * Check if the reserved seats have exceeded max capacity. Otherwise, allow the
				 * user to make the change.
				 */
				if (flightToUpdate.getReservedSeats() > airplanetype.getMaxCapacity()) {
					System.out.println("***Warning***");
					System.out.println("Reserved seats set to over maximum capacity");
					System.out
							.println("Reserved Seats will be set from " + flightToUpdate.getReservedSeats() + " to 0");
					System.out.println("Continue? y/n");
					reset_seats = true;
					if (!readYesNo()) {
						flightUpdateMenu(flightToUpdate);
						return;
					}

				}
				/* Choose airplane */
				System.out.println("Choose a new airplane");
				if (reset_seats)
					flightToUpdate.setReservedSeats(0);

				airplane = pickAirplane(airplanetype);
			}
			// save change
			flightToUpdate.setAirplane(airplane);
			break;
		case 3:
			/* DEPARTURE TIME */
			LocalDateTime dt = null;
			/*
			 * If User chooses 'Go Back' while choosing time, we can reset to date. If User
			 * chooses 'Go Back' while choosing date, we can reset to previous menu
			 */
			while (dt == null) {
				System.out.println("Choose a new departure date.");
				LocalDate d = pickDepartureDate();
				if (d == null) {
					flightUpdateMenu(flightToUpdate);
					return;
				}
				System.out.println(d);
				System.out.println("Choose a new departure time");
				dt = pickDepartureTime(d);
			}
			flightToUpdate.setDepartureTime(Timestamp.valueOf(dt));
			break;
		case 4:
			/*
			 * RESERVED SEATS Has to call services to find the max capacity of airplane to
			 * ensure reserved seats does not exceed
			 */
			System.out.println("Current number of reserved seats: " + flightToUpdate.getReservedSeats()
					+ " Enter the number of seats to reserve.");
			AirplaneType at = pickAirplaneTypeByAirplane(flightToUpdate.getAirplane());
			while ((reserved_seats = readPositiveInt()) > at.getMaxCapacity()) {
				System.out.println("Cannot reserve more than " + at.getMaxCapacity() + " for this plane.");
			}
			flightToUpdate.setReservedSeats(reserved_seats); //save changes
			break;
		case 5:
			/* SEAT PRICE*/
			System.out.println("Current Price: $" + flightToUpdate.getSeatPrice() + " Enter new seat price.");
			flightToUpdate.setSeatPrice(readPositiveFloat());
			break;
		}
		
		/* USER HAS REACHED THE END OF ONE UPDATIG PROCESS. PROMPT THE USER FOR MORE CHANGES*/
		System.out.println("1) Update another value.");
		System.out.println("2) Continue.");
		if (readInput(1, 2) == 1) {
			flightUpdateMenu(flightToUpdate); //Values stored in flightToUpdate
			return;
		} else {
			/* One more confirmation step */
			System.out.println("---------Review Flight Addition---------");
			System.out.println(
					"Flight Id: " + flightToUpdate.getId() + "   Route: " + flightToUpdate.getRoute().getOriginAirport()
							+ "-" + flightToUpdate.getRoute().getDestinationAirport() + "   Airplane Id: "
							+ flightToUpdate.getAirplane().getAirplaneId() + "   Departure Time: "
							+ flightToUpdate.getDepartureTime() + "   Reserved Seats: "
							+ flightToUpdate.getReservedSeats() + "   Seat Price: " + flightToUpdate.getSeatPrice());
			System.out.println("Confirm: y/n");
			if (readYesNo()) {
				//Update the flight and printout the result
				System.out.println(admin.updateFlight(flightToUpdate));
			} else {
				/* If the user enters NO, all progress will be lost.*/
				updateFlight();
			}
		}

	}
	
	/* Two-Step pick airplanetype then pick airplane of type process. */
	public void pickAirplaneType(List<AirplaneType> airplanetypes, AirplaneType airplaneType, Airplane airplane,
			LocalDateTime date) {
		int index = 0;
		System.out.println("Pick an airplane type for flight on " + date);
		for (int k = 0; k < airplanetypes.size(); k++) {
			System.out.println(Integer.toString(k + 1) + ") " + "Type Id: " + airplanetypes.get(k).getAirplaneTypeId()
					+ " Max capacity: " + airplanetypes.get(k).getMaxCapacity());
		}
		index = readInput(1, airplanetypes.size());
		airplaneType.setAirplaneTypeId(airplanetypes.get(index - 1).getAirplaneTypeId());
		airplaneType.setMaxCapacity(airplanetypes.get(index - 1).getMaxCapacity());

		pickAirplane(airplanetypes, airplaneType, airplane, date);

	}

	public LocalDate pickDepartureDate() {
		for (int i = startDate; i < endDate; i++) { // Allows for 10 flight additions 5 days ahead of today
			System.out.println(Integer.toString(i - 4) + ") " + date.plusDays(i));
		}
		System.out.println(endDate - startDate + 1 + ") Go Back");
		int choice = readInput(1, endDate - startDate + 1);
		if (choice == endDate - startDate + 1) {
			return null;
		}
		return date.plusDays(choice + startDate - 1);

	}

	public LocalDateTime pickDepartureTime(LocalDate d) {
		for (int t = 0; t < 24; t += 3) {
			System.out.println(t / 3 + 1 + ") " + (t < 10 ? "0" : "") + t + ":00:00"); // hard coded to 8 time slots
																						// for now
		}
		System.out.println("9) Choose a departure time.");
		System.out.println("10) Go Back");
		int t = readInput(1, 10);
		if (t == 10) {
			return null;
		} else if (t == 9) {
			System.out.println("Please enter HH:MM");
			time = readTime(date);
			return time;
		} else {
			return d.atStartOfDay().plusHours(t * 3);
		}
	}
	/* Coupled with pickAirplaneType. The former calls this with the AirplaneType. 
	 * Takes in user input values for reserved seats and seat pricing. Handles menus also.*/
	public void pickAirplane(List<AirplaneType> airplanetypes, AirplaneType airplaneType, Airplane airplane,
			LocalDateTime date) {
		List<Airplane> airplanes = admin.createAirplaneListByType(airplaneType);
		int index = 0;
		while (true) {
			System.out.println("Pick an airplane for flight on " + date);
			for (int j = 0; j < airplanes.size(); j++) {
				System.out.println(Integer.toString(j + 1) + ") " + "Airplane Id: " + airplanes.get(j).getAirplaneId()
						+ " Airplane type: " + airplanes.get(j).getType().getAirplaneTypeId());
			}
			System.out.println(Integer.toString(airplanes.size() + 1) + ") Go Back");
			index = readInput(1, airplanes.size() + 1);
			if (index == airplanes.size() + 1) {
				pickAirplaneType(airplanetypes, airplaneType, airplane, date);
				return;
			}
			if (uniqueAirplaneId.contains(airplanes.get(index - 1).getAirplaneId())) {
				System.out.println("Cannot choose the same airplane more than once"); // TODO check dates to see if
																						// airplane can fly two flights
				continue;
			}
			airplane.setAirplaneId(airplanes.get(index - 1).getAirplaneId());
			airplane.setType(airplaneType);
			System.out.println("Enter number of seats to reserve.");

			while ((reserved_seats = readPositiveInt()) > airplaneType.getMaxCapacity()) {
				System.out.println("Cannot reserve more than " + airplaneType.getMaxCapacity() + " for this plane");
			}
			System.out.println("Enter ticket price.");
			seat_price = readPositiveFloat();
			seat_price = (float) (Math.round(seat_price * 100.0) / 100.0);

			uniqueAirplaneId.add(airplane.getAirplaneId());
			break;
		}

	};

	/* Take in a route, and finds the airports of that route's id*/
	public List<String> findRoute(Route route) {
		List<String> cities = new ArrayList<>();
		for (Route r : admin.createRouteList()) {
			if (r.getRouteId() == r.getRouteId()) {
				cities.add(r.getOriginAirport());
				cities.add(r.getDestinationAirport());
			}
		}
		return cities;
	}

	/* Takes in an airplane and finds the airplanetype. Useful for finding max capacity.*/
	public AirplaneType pickAirplaneTypeByAirplane(Airplane airplane) {
		List<AirplaneType> airplanetypes = admin.createAirplaneTypeList();
		for (AirplaneType at : airplanetypes) {
			if (at.getAirplaneTypeId() == airplane.getAirplaneId()) {
				return at;
			}
		}
		return null;

	}
	/* Displays all airplanetypes and returns the one the user has selected.*/
	public AirplaneType pickAirplaneType() {
		List<AirplaneType> airplanetypes = admin.createAirplaneTypeList();
		int index = 1;
		for (AirplaneType a : airplanetypes) {
			System.out.println(Integer.toString(index++) + ") Id" + a.getAirplaneTypeId() + "   Max Capacity: "
					+ a.getMaxCapacity());
		}
		System.out.println(airplanetypes.size() + 1 + ") Go Back");
		int choice = readInput(1, airplanetypes.size() + 1);
		if (choice == airplanetypes.size() + 1) {
			return null;
		}
		return airplanetypes.get(choice - 1);
	}

	/* displays all airplanes based on a type and returns the airplane the user selects*/
	public Airplane pickAirplane(AirplaneType airplanetype) {
		List<Airplane> airplanes = admin.createAirplaneListByType(airplanetype);
		int index = 1;
		for (Airplane a : airplanes) {
			System.out.println(Integer.toString(index++) + ") Id: " + a.getAirplaneId() + "   Airplane type: "
					+ a.getType().getAirplaneTypeId());
		}
		System.out.println(index + ") Go Back");
		int choice = readInput(1, airplanes.size() + 1);
		if (choice == airplanes.size() + 1) {
			return null;
		}
		return airplanes.get(choice - 1);
	}

	/* Displays all airports and returns the one the user selects*/
	public Airport pickAirport() {
		List<Airport> airports = admin.createAirportList();
		int index = 1;
		for (Airport a : airports) {
			System.out.println(Integer.toString(index++) + ") " + a.getAirportId());
		}
		int choice = readInput(1, airports.size());
		return airports.get(choice - 1);
	}
	
//	public BookingAgent pickBookingAgent() {
//		List<BookingAgent> = 
//		
//	}
//	
//	public void printOneBookingAgent(BookingAgent ba) {
//		
//	}

	/* Helper method to print out menu options*/
	public void prepareString(String[] strings, Object[] obj, int index) {
		System.out.print(index + ") ");
		for (int i = 0; i < strings.length; i++) {
			System.out.print(strings[i] + ": " + obj[i] + "	");
		}
		System.out.println("\n-----------------------------------------");

	}

}
