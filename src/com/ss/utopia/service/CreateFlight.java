/**
 * 
 */
package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import com.ss.utopia.dao.FlightDAO;
import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;
import com.ss.utopia.menu.FlightMenu;
import com.ss.utopia.menu.PrintMenu;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang
 *
 */
public class CreateFlight {

	AdminServices admin = new AdminServices();
	
	FlightMenu previous_menu = new FlightMenu();
	PrintMenu pmenu = new PrintMenu();


	ConnectionUtil connUtil = new ConnectionUtil();
	
	
	Set<Integer> id_set = new HashSet<>();
	List<Flight> flights = admin.create(null, Service.FLIGHT, connUtil);
	List<AirplaneType> airplane_types = admin.create(null, Service.AIRPLANETYPE, connUtil);

	
	
	Scanner scan = new Scanner(System.in);
	Random random = new Random();
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
    private DecimalFormat df = new DecimalFormat("0.00");


	final private int HOURS_IN_DAY = 24;

	private int startDate = 5; // hard code the range of available dates for now
	private int endDate = 15;
	private LocalDate current_day = LocalDate.now();


	
	public Airport addAirport() {
		
		List<Airport> airports = admin.create(null, Service.AIRPORT, connUtil);
		List<String> formatted_airports = new ArrayList<>();
		
		for(Airport a : airports) {
			formatted_airports.add(a.toString());
		}
		
		Integer choice = pmenu.printMenu(formatted_airports, Boolean.TRUE);
		if(choice == -1) {
			return null;
		}
		
		return airports.get(choice);
		
	}
	
	
	
	
	
	/*
	 * CREATE FLIGHT GOES THROUGH THE PROCESS OF ADDING A FLIGHT WHILE CALLING
	 * HELPER METHODS FOR CHOOSING AIRPORT, ROUTES, DATETIMES, AIRPLANE_TYPE, AND AIRPLANE
	 */
	public void createFlight() {
		


		Airport origin_airport = addAirport();
		if(origin_airport == null) {
			previous_menu.displayMenu();
			return;
		}
		
		
		Airport destination_airport = addAirport();
		if(destination_airport == null) {
			previous_menu.displayMenu();
			return;
		}
		
		if(origin_airport.getAirportId().equals(destination_airport.getAirportId())) {
			System.out.println("Must choose different airports");
			createFlight();
			return;
		}
		
		Route route = new Route();
		
		if((route = addRoute(origin_airport, destination_airport)) == null) {
			System.out.println("This route does not exist.");
			createFlight();
			return;
		}
		
		
		route.setOriginAirport(origin_airport.getAirportId());
		route.setDestinationAirport(destination_airport.getAirportId());
		
		
		LocalDateTime date_time;
		List<LocalDateTime> date_times = new ArrayList<>();
		
		while(true) {
			date_time = addDateTime();
			if(date_time == null) {
				if(date_times.size() == 0) {
					System.out.println("Must select at least one date and time");
					continue;
				}
				break;
			}
			date_times.add(date_time);
		}
		
		
		List<Flight> flights_to_add = new ArrayList<>();
		
		
		
		
		for(Flight f : flights) { //load hashset once instead of for every flight
			id_set.add(f.getId());
		}
		
		for(int i =0; i<date_times.size(); i++) {
			
			
			
			//Choose an airplane type
			
			System.out.println("Choose an airplane for flight on: " +date_times.get(i).format(formatter));
			System.out.println("Select airplane type.");
			AirplaneType airplane_type = addAirplaneType();
			if(airplane_type == null) {
				if(i > 0) {
					i-=2;
				}
				else {
				i--;
				}
				continue;
			}
				
			//Choose an airplane
			
			System.out.println("Choose an airplane.");
			Airplane airplane = addAirplane(airplane_type);
			if(airplane == null) {
				i--;
				continue;
			}
			
			//Enter number of seats
			
			System.out.println("Enter the number of seats to reserve.");
			Integer reserved_seats = addReservedSeats(airplane_type.getMaxCapacity(), scan);
			
			//Enter seat price
			
			System.out.println("Enter the seat price.");
			Float seat_price = Float.parseFloat(df.format(addSeatPrice(scan)));
			System.out.println(seat_price);
			
			
			//Enter a flight id
	
			Integer flight_id = addId(scan);
			
			
			//Prep flight for addition then confirm and add
			
			Flight flight_to_add = new Flight();
			
			
			flight_to_add.setRoute(route);
			flight_to_add.setDepartureTime(Timestamp.valueOf(date_times.get(i)));
			flight_to_add.setAirplane(airplane);
			flight_to_add.setReservedSeats(reserved_seats);
			flight_to_add.setSeatPrice(seat_price);
			flight_to_add.setId(flight_id);
			
			flights_to_add.add(flight_to_add);
			
			
		}
		
		for(Flight f : flights_to_add) {
			System.out.println(f.toString());
			System.out.println("----------------------------------------------");
		}
		System.out.println("Confirm Flight Addition. y/n");
		
		if(!confirmAdd()) {
			previous_menu.displayMenu();
			return;
		}

		
		// Use Transaction
		
		
		Connection conn = null;
		
		try {
			conn = connUtil.getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			for(Flight f : flights_to_add) {
				fdao.addFlight(f);
			}
			conn.commit();
			
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		previous_menu.displayMenu();
		
		return;


}
	
	
/*************************************************************/
/**********************HELPER METHODS ************************/
/*************************************************************/
	
	
	public LocalDateTime addDateTime(){
		
		//Pick a date
		
		List<LocalDate> dates = new ArrayList<>();
		List<String> formatted_dates = new ArrayList<>();
		List<LocalDateTime> date_times = new ArrayList<>();
		
		LocalDate date;
		
		System.out.println("Please pick a departure date.");
		for (int i = startDate; i < endDate; i++) { // Allows for 10 flight additions 5 days ahead of today
			formatted_dates.add(current_day.plusDays(i).format(formatter));
		}
		formatted_dates.add("Done.");
		Integer choice = pmenu.printMenu(formatted_dates, Boolean.FALSE);
		if(choice == formatted_dates.size()-1) {
			return null;
		}
		
		date = current_day.plusDays(choice+startDate);
		
		//Pick a time
		
		LocalDateTime date_time;
		List<String> formatted_times = new ArrayList<>();
		
		System.out.println("Please pick a departure time OR input a time.");
		for(int i =0; i<HOURS_IN_DAY; i++) {
			formatted_times.add(((i<10) ? "0"+i : i) +":00");
		}
		formatted_times.add("Input a time.");
		choice = pmenu.printMenu(formatted_times, Boolean.FALSE);
		
		if(choice == HOURS_IN_DAY) { //user has chosen to input time.
			System.out.println("Enter a date. HH:MM");
			date_time = readTime(date, scan);
		}
		else {
			date_time = date.atStartOfDay().plusHours(choice);
		}
		
		System.out.println(date_time);
		return date_time;
	}
	
	public AirplaneType addAirplaneType() {
		
		List<String> formatted_airplane_types = new ArrayList<>();
		
		
		for(AirplaneType apt : airplane_types) {
			formatted_airplane_types.add(apt.toString());
		}
		Integer choice = pmenu.printMenu(formatted_airplane_types, Boolean.TRUE);
		
		if(choice == -1) return null;
		return airplane_types.get(choice);
	
	
	}
	
	public Airplane addAirplane(AirplaneType airplane_type) {
		
		List<Airplane> airplanes = admin.create(new Object[] {airplane_type}, Service.AIRPLANE, connUtil);
		List<String> formatted_airplanes = new ArrayList<>();
		
		for(Airplane a : airplanes) {
			formatted_airplanes.add(a.toString());
		}
		Integer choice = pmenu.printMenu(formatted_airplanes, Boolean.TRUE);
		
		if(choice == -1) {
			return null;
		}
		
		return airplanes.get(choice);
		
	}
	
	
	public Integer addReservedSeats(Integer max_capacity, Scanner scan) {
		
		Integer seats = -1;
		while(seats < 0 || seats > max_capacity) {
			
			try {
				seats = Integer.parseInt(scan.nextLine());
				if(seats <0 || seats> max_capacity) {
					System.out.println("Please enter a number greater than or equal to zer and less than " +max_capacity);
				}
			} catch(NumberFormatException e) {
					System.out.println("Please enter a number.");
			}
			
			
			
		}
		return seats;
			
	}
	
	
	public Double addSeatPrice(Scanner scan) {
		Double seat_price = new Double(-1);
		while(seat_price < 0) {
			try {
				seat_price = Double.parseDouble(scan.nextLine());
				
			} catch(NumberFormatException e) {
				
				System.out.println("Please enter a positive number");
			}
		}
		return seat_price;
	}
	
	
	public Route addRoute(Airport origin_airport, Airport destination_airport) {
		List<Route> route = admin.create(new Object[] {origin_airport, destination_airport}, Service.ROUTE, connUtil);
		if(route.size() == 0) return null;
		return route.get(0);
	}
	
	
	public Integer addId(Scanner scan) {
		
		Integer id = null;
		
		if(pmenu.printMenu(Arrays.asList("Enter a flight number.", "Generate a random flight number."), Boolean.FALSE)==0){
			
			while(true) {
				try {
					
					id = Integer.parseInt(scan.nextLine());
					if(id_set.contains(id)) {
						System.out.println("Flight Number already exists.");
					}
					else {
						break;
					}
					
					
				} catch(NumberFormatException e) {
					System.out.println("Enter a valid integer.");
				}
				
			}
		}
		else {
			while(true) {
				id = random.nextInt((int)Math.floor(id_set.size()*1.25));
				if(!id_set.contains(id)) {

					break;
				}
			}
		}
		
		id_set.add(id);
		return id;
	}
	
	
	public LocalDateTime readTime(LocalDate date, Scanner scan) {
		String parse = "";
		while (true) {
			parse = scan.nextLine();

			if (Pattern.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", parse)) {
				break;
			} else {
				System.out.println("Please enter valid HH:MM");
			}

		}
		String[] hoursMinutes = parse.split(":");
		LocalDateTime dt = date.atStartOfDay().plusHours(Integer.parseInt(hoursMinutes[0]))
				.plusMinutes(Integer.parseInt(hoursMinutes[1]));
		return dt;
	} 
	
	public boolean confirmAdd() {
		while(true) {
			if("y".equals(scan.nextLine()) || "Y".equals(scan.nextLine())) {
				return true;
			}
			else if("n".equals(scan.nextLine()) || "N".equals(scan.nextLine())) {
				return false;
			}
			else {
				System.out.println("Confirm Flight Addition. y/n");

			}
		}
	}
	
	
}



///* Choose a route */
//System.out.println("Add Flight(s)");
//System.out.println("Please pick an origin/departure airport");
//Airport start = pickAirport();
//System.out.println("Please pick a destination/arrival airport");
//Airport end = pickAirport();
//
//if (start.getAirportId().equals(end.getAirportId())) {
//	System.out.println("*****Please pick unique airports*****\n");
//	addFlight();
//	return;
//}
///* Attain the route Id using Service */
//Route route = admin.findRouteByAirports(start, end);
//index = 0; // used for indexing bullet points
///* First pick departure dates/times. Each will be converted into a flight */
//while (true) {
//	System.out.println("Please pick one or more departure dates. " + Integer.toString(dates.size()) + "/"
//			+ maxAdditions + " picked.");
//	for (int i = startDate; i < endDate; i++) { // Allows for 10 flight additions 5 days ahead of today
//		System.out.println(Integer.toString(i - 4) + ") " + date.plusDays(i));
//	}
//	System.out.println(Integer.toString(endDate - startDate + 1) + ") Continue");
//	index = readInput(1, endDate - startDate + 1);
//	if (index == endDate - startDate + 1) {
//		if (dates.size() == 0) {
//			System.out.println("*****Must choose at least 1 date*****");
//			continue;
//		}
//		break;
//	}
//	/* Handles choosing a time, allow the user to also input a time. */
//	System.out.println("Please choose a time.");
//	for (int t = 0; t < 24; t += 3) {
//		System.out.println(t / 3 + 1 + ") " + (t < 10 ? "0" : "") + t + ":00:00"); // hard coded to 8 time slots
//																					// for now
//	}
//	System.out.println("9) Choose a departure time.");
//	int t = 0;
//
//	if ((t = readInput(1, 9)) == 9) {
//		System.out.println("Please enter HH:MM");
//		time = readTime(date);
//
//		dates.add(time);
//	} else {
//		dates.add(date.plusDays(index + startDate - 1).atStartOfDay().plusHours(t * 3));
//	}
//	if (dates.size() == maxAdditions)
//		break;
//}
//List<Airplane> pickedAirplanes = new ArrayList<>();
//List<AirplaneType> pickedAirplaneTypes = new ArrayList<>();
//List<AirplaneType> airplanetypes = admin.createAirplaneTypeList();
//List<Flight> flights = new ArrayList<>();
//uniqueAirplaneId.clear();
///* Fill in additional values for each flight on dates selected */
//for (int i = 0; i < dates.size(); i++) {
//
//	Airplane airplane = new Airplane();
//	AirplaneType airplaneType = new AirplaneType();
//	pickAirplaneType(airplanetypes, airplaneType, airplane, dates.get(i));
//	System.out.println("Flight Overview");
//	/* Print out all previous flights */
//	for (int j = 0; j < pickedAirplaneTypes.size(); j++) {
//		System.out.println(Integer.toString(j + 1) + ") " + "Airplane Id: "
//				+ pickedAirplanes.get(j).getAirplaneId() + "	Airplane Type: "
//				+ pickedAirplanes.get(j).getType().getAirplaneTypeId() + "	Max Capacity: "
//				+ pickedAirplaneTypes.get(j).getMaxCapacity() + "	Reserved Seats: " + reserved_seats
//				+ "   Seat Price: " + seat_price + "\n -----------------\n");
//		System.out.println("Confirm Flight Addition (y/n).");
//
//	}
//	/* Print out current flight values and prompt user for confirmation */
//	System.out.println(Integer.toString(pickedAirplaneTypes.size() + 1) + ") " + " From: "
//			+ route.getOriginAirport() + "   To: " + route.getDestinationAirport() + "\n" + "Airplane Id: "
//			+ airplane.getAirplaneId() + "	Airplane Type: " + airplaneType.getAirplaneTypeId()
//			+ "	Max Capacity: " + airplaneType.getMaxCapacity() + "	Reserved Seats: " + reserved_seats
//			+ "   Seat Price: $" + seat_price + "\n -----------------\n");
//	System.out.println("Confirm Flight Addition (y/n).");
//	/* create flight object and insert into Database one at a time. */
//	if (readYesNo()) {
//		pickedAirplaneTypes.add(airplaneType);
//		pickedAirplanes.add(airplane);
//		Flight flight = new Flight(null, route, airplane, reserved_seats, Timestamp.valueOf(dates.get(i)),
//				seat_price);
//		System.out.println(admin.addFlight(flight));
//	} else {
//		i--; // redo the previous date
//		uniqueAirplaneId.remove(airplane.getAirplaneId()); // remove the id from hashset
//		continue;
//	}
//
//}
///* Return to the beginning */
//runProgram();
//
//}
///* Read Flight calls the service which returns the flights in string format*/
//public void readFlight() {
//
//System.out.println(admin.readFlight());
//System.out.println("1) Go Back");
//if(readInput(1, 1) == 1) {
//	runProgram();
//}
//
//}
//
///* Prompts the user to select one of the flights displayed to be deleted*/
//public void deleteFlight() {
//System.out.println("Choose a flight to delete");
//List<Flight> flights = admin.createFlightList();
//int index = 1; // indexing bulletpoint
//for (Flight f : flights) {
//	prepareString(
//			new String[] { "id", "route_id", "airplane_id", "departure_time", "reserved_seats", "seat_price" },
//			new Object[] { f.getId(), f.getRoute().getRouteId(), f.getAirplane().getAirplaneId(),
//					f.getDepartureTime(), f.getReservedSeats(), f.getSeatPrice() },
//			index++);
//}
//System.out.println(flights.size() + 1 + ") Go Back"); // return to previous menu
//int choice = readInput(1, flights.size() + 1);
//if (choice == flights.size() + 1) {
//	chooseCategory(4); // Return to Select Delete Menu from Menu.java
//	return;
//}
//Flight flightToDelete = flights.get(choice-1); //save flight choice as flightToDelete
///* Call findRoute to display the airport names of the flight's route*/
//List<String> cities = findRoute(flightToDelete.getRoute());
//flightToDelete.getRoute().setOriginAirport(cities.get(0));
//flightToDelete.getRoute().setDestinationAirport(cities.get(1));
//System.out.println("---------Confirm Flight Deletion---------");
//System.out.println(
//		"Flight Id: " + flightToDelete.getId() + "   Route: " + flightToDelete.getRoute().getOriginAirport()
//				+ "-" + flightToDelete.getRoute().getDestinationAirport() + "   Airplane Id: "
//				+ flightToDelete.getAirplane().getAirplaneId() + "   Departure Time: "
//				+ flightToDelete.getDepartureTime() + "   Reserved Seats: "
//				+ flightToDelete.getReservedSeats() + "   Seat Price: " + flightToDelete.getSeatPrice());
//System.out.println("Delete? y/n");
///* Confirm intent to delete */
//if(readYesNo()) {
//	System.out.println(admin.deleteFlight(flightToDelete));
//	runProgram();
//}
//else {
//	/* User has selected no. Should take us back to the previous menu (flight selection)*/
//	deleteFlight();
//}
//
//
//}
//
//
///*
//* START OF UPDATE FLIGHT PROCESS CALLS METHODS FROM THIS CLASS findRoute() --
//* finds all routes and matches them to the route_id to provide airport names
//* flightUpdateMenu() -- handles most features of the updating process
//*/
//public void updateFlight() {
//Flight flightToUpdate;
//System.out.println("Which Flight would you like to update?");
///* Displays all flights using services for user to choose */
//List<Flight> flights = admin.createFlightList();
//int index = 1; // indexing bulletpoint
//for (Flight f : flights) {
//	prepareString(
//			new String[] { "id", "route_id", "airplane_id", "departure_time", "reserved_seats", "seat_price" },
//			new Object[] { f.getId(), f.getRoute().getRouteId(), f.getAirplane().getAirplaneId(),
//					f.getDepartureTime(), f.getReservedSeats(), f.getSeatPrice() },
//			index++);
//}
//System.out.println(flights.size() + 1 + ") Go Back"); // return to previous menu
//int choice = readInput(1, flights.size() + 1);
//if (choice == flights.size() + 1) {
//	chooseCategory(3); // Return to Select Update Menu from Menu.java
//	return;
//}
//flightToUpdate = flights.get(choice - 1);
///* Call findRoute to get city names, which are unavailable from FlightDAO */
//List<String> cities = findRoute(flightToUpdate.getRoute());
//flightToUpdate.getRoute().setOriginAirport(cities.get(0));
//flightToUpdate.getRoute().setDestinationAirport(cities.get(1));
///* Call the next step of update flight */
//flightUpdateMenu(flightToUpdate);
//
//}
//
///*
//* HANDLES DIFFERENT VALUES FOR UPDATE OF FLIGHT CALLS METHODS FROM THIS CLASS:
//* pickAirport() -- pick airports for new route pickAirplane(AirplaneType) --
//* pick airplane based on type pickDepartureDate() -- pick a date from a given
//* set of dates pickDepartureTime(LocalDate) -- pick or input a time on given
//* LocalDate --readPositiveFloat() -- parses float for seat pricing
//*/
//public void flightUpdateMenu(Flight flightToUpdate) {
//int choice = 0;
///* Lists all options to update */
//System.out.println("What would you like to update?");
//System.out.println("1) Routes\n2) Airplane\n3) Departure Time\n4) Reserved Seats\n5) Seat Price");
//System.out.println("6) Go Back");
//choice = readInput(1, 6);
///*
// * Reset to updateFlight and return if yes. Otherwise save flightToUpdate and
// * start from flight menu
// */
//if (choice == 6) {
//	System.out.println("Are you sure? All progress will be lost.");
//	if (readYesNo()) {
//		updateFlight();
//		return;
//	} else {
//		flightUpdateMenu(flightToUpdate);
//		return;
//	}
//}
//
//switch (choice) {
//case 1:
//	/* ROUTE */
//	Airport origin = null;
//	Airport destination = null;
//	/* Use null to see if user has chosen a 'GO BACK' statement */
//	while (origin == null || destination == null || origin.getAirportId().equals(destination.getAirportId())) {
//		System.out.println("Choose new origin airport");
//		origin = pickAirport();
//		System.out.println("Choose new destination airport");
//		destination = pickAirport();
//		/* origin will not be null if received from RouteDAO */
//		if (origin.getAirportId().equals(destination.getAirportId())) {
//			System.out.println("*****Please pick unique airports*****\n");
//		}
//	}
//	/* Receive the route id */
//	Route route = admin.findRouteByAirports(origin, destination); // find the route through adminservices
//	// save change
//	flightToUpdate.setRoute(route);
//	break;
//case 2:
//	/* AIRPLANE */
//	Airplane airplane = null; // use airplane null as indicator user has selected 'GO BACK'
//	boolean reset_seats = false; // if seats exceed max cap
//	while (airplane == null) {
//		System.out.println("Choose a new airplane type.");
//		AirplaneType airplanetype = pickAirplaneType();
//		if (airplanetype == null) {
//			flightUpdateMenu(flightToUpdate);
//			return;
//		}
//		/*
//		 * The user may change reserved seats first, then return and change airplane.
//		 * Check if the reserved seats have exceeded max capacity. Otherwise, allow the
//		 * user to make the change.
//		 */
//		if (flightToUpdate.getReservedSeats() > airplanetype.getMaxCapacity()) {
//			System.out.println("***Warning***");
//			System.out.println("Reserved seats set to over maximum capacity");
//			System.out
//					.println("Reserved Seats will be set from " + flightToUpdate.getReservedSeats() + " to 0");
//			System.out.println("Continue? y/n");
//			reset_seats = true;
//			if (!readYesNo()) {
//				flightUpdateMenu(flightToUpdate);
//				return;
//			}
//
//		}
//		/* Choose airplane */
//		System.out.println("Choose a new airplane");
//		if (reset_seats)
//			flightToUpdate.setReservedSeats(0);
//
//		airplane = pickAirplane(airplanetype);
//	}
//	// save change
//	flightToUpdate.setAirplane(airplane);
//	break;
//case 3:
//	/* DEPARTURE TIME */
//	LocalDateTime dt = null;
//	/*
//	 * If User chooses 'Go Back' while choosing time, we can reset to date. If User
//	 * chooses 'Go Back' while choosing date, we can reset to previous menu
//	 */
//	while (dt == null) {
//		System.out.println("Choose a new departure date.");
//		LocalDate d = pickDepartureDate();
//		if (d == null) {
//			flightUpdateMenu(flightToUpdate);
//			return;
//		}
//		System.out.println(d);
//		System.out.println("Choose a new departure time");
//		dt = pickDepartureTime(d);
//	}
//	flightToUpdate.setDepartureTime(Timestamp.valueOf(dt));
//	break;
//case 4:
//	/*
//	 * RESERVED SEATS Has to call services to find the max capacity of airplane to
//	 * ensure reserved seats does not exceed
//	 */
//	System.out.println("Current number of reserved seats: " + flightToUpdate.getReservedSeats()
//			+ " Enter the number of seats to reserve.");
//	AirplaneType at = pickAirplaneTypeByAirplane(flightToUpdate.getAirplane());
//	while ((reserved_seats = readPositiveInt()) > at.getMaxCapacity()) {
//		System.out.println("Cannot reserve more than " + at.getMaxCapacity() + " for this plane.");
//	}
//	flightToUpdate.setReservedSeats(reserved_seats); //save changes
//	break;
//case 5:
//	/* SEAT PRICE*/
//	System.out.println("Current Price: $" + flightToUpdate.getSeatPrice() + " Enter new seat price.");
//	flightToUpdate.setSeatPrice(readPositiveFloat());
//	break;
//}
//
///* USER HAS REACHED THE END OF ONE UPDATIG PROCESS. PROMPT THE USER FOR MORE CHANGES*/
//System.out.println("1) Update another value.");
//System.out.println("2) Continue.");
//if (readInput(1, 2) == 1) {
//	flightUpdateMenu(flightToUpdate); //Values stored in flightToUpdate
//	return;
//} else {
//	/* One more confirmation step */
//	System.out.println("---------Review Flight Addition---------");
//	System.out.println(
//			"Flight Id: " + flightToUpdate.getId() + "   Route: " + flightToUpdate.getRoute().getOriginAirport()
//					+ "-" + flightToUpdate.getRoute().getDestinationAirport() + "   Airplane Id: "
//					+ flightToUpdate.getAirplane().getAirplaneId() + "   Departure Time: "
//					+ flightToUpdate.getDepartureTime() + "   Reserved Seats: "
//					+ flightToUpdate.getReservedSeats() + "   Seat Price: " + flightToUpdate.getSeatPrice());
//	System.out.println("Confirm: y/n");
//	if (readYesNo()) {
//		//Update the flight and printout the result
//		System.out.println(admin.updateFlight(flightToUpdate));
//	} else {
//		/* If the user enters NO, all progress will be lost.*/
//		updateFlight();
//	}
//}
//
//}
//
///* Two-Step pick airplanetype then pick airplane of type process. */
//public void pickAirplaneType(List<AirplaneType> airplanetypes, AirplaneType airplaneType, Airplane airplane,
//	LocalDateTime date) {
//int index = 0;
//System.out.println("Pick an airplane type for flight on " + date);
//for (int k = 0; k < airplanetypes.size(); k++) {
//	System.out.println(Integer.toString(k + 1) + ") " + "Type Id: " + airplanetypes.get(k).getAirplaneTypeId()
//			+ " Max capacity: " + airplanetypes.get(k).getMaxCapacity());
//}
//index = readInput(1, airplanetypes.size());
//airplaneType.setAirplaneTypeId(airplanetypes.get(index - 1).getAirplaneTypeId());
//airplaneType.setMaxCapacity(airplanetypes.get(index - 1).getMaxCapacity());
//
//pickAirplane(airplanetypes, airplaneType, airplane, date);
//
//}
//
//public LocalDate pickDepartureDate() {
//for (int i = startDate; i < endDate; i++) { // Allows for 10 flight additions 5 days ahead of today
//	System.out.println(Integer.toString(i - 4) + ") " + date.plusDays(i));
//}
//System.out.println(endDate - startDate + 1 + ") Go Back");
//int choice = readInput(1, endDate - startDate + 1);
//if (choice == endDate - startDate + 1) {
//	return null;
//}
//return date.plusDays(choice + startDate - 1);
//
//}
//
//public LocalDateTime pickDepartureTime(LocalDate d) {
//for (int t = 0; t < 24; t += 3) {
//	System.out.println(t / 3 + 1 + ") " + (t < 10 ? "0" : "") + t + ":00:00"); // hard coded to 8 time slots
//																				// for now
//}
//System.out.println("9) Choose a departure time.");
//System.out.println("10) Go Back");
//int t = readInput(1, 10);
//if (t == 10) {
//	return null;
//} else if (t == 9) {
//	System.out.println("Please enter HH:MM");
//	time = readTime(date);
//	return time;
//} else {
//	return d.atStartOfDay().plusHours(t * 3);
//}
//}
///* Coupled with pickAirplaneType. The former calls this with the AirplaneType. 
//* Takes in user input values for reserved seats and seat pricing. Handles menus also.*/
//public void pickAirplane(List<AirplaneType> airplanetypes, AirplaneType airplaneType, Airplane airplane,
//	LocalDateTime date) {
//List<Airplane> airplanes = admin.createAirplaneListByType(airplaneType);
//int index = 0;
//while (true) {
//	System.out.println("Pick an airplane for flight on " + date);
//	for (int j = 0; j < airplanes.size(); j++) {
//		System.out.println(Integer.toString(j + 1) + ") " + "Airplane Id: " + airplanes.get(j).getAirplaneId()
//				+ " Airplane type: " + airplanes.get(j).getType().getAirplaneTypeId());
//	}
//	System.out.println(Integer.toString(airplanes.size() + 1) + ") Go Back");
//	index = readInput(1, airplanes.size() + 1);
//	if (index == airplanes.size() + 1) {
//		pickAirplaneType(airplanetypes, airplaneType, airplane, date);
//		return;
//	}
//	if (uniqueAirplaneId.contains(airplanes.get(index - 1).getAirplaneId())) {
//		System.out.println("Cannot choose the same airplane more than once"); // TODO check dates to see if
//																				// airplane can fly two flights
//		continue;
//	}
//	airplane.setAirplaneId(airplanes.get(index - 1).getAirplaneId());
//	airplane.setType(airplaneType);
//	System.out.println("Enter number of seats to reserve.");
//
//	while ((reserved_seats = readPositiveInt()) > airplaneType.getMaxCapacity()) {
//		System.out.println("Cannot reserve more than " + airplaneType.getMaxCapacity() + " for this plane");
//	}
//	System.out.println("Enter ticket price.");
//	seat_price = readPositiveFloat();
//	seat_price = (float) (Math.round(seat_price * 100.0) / 100.0);
//
//	uniqueAirplaneId.add(airplane.getAirplaneId());
//	break;
//}
//
//};
//
///* Take in a route, and finds the airports of that route's id*/
//public List<String> findRoute(Route route) {
//List<String> cities = new ArrayList<>();
//for (Route r : admin.createRouteList()) {
//	if (r.getRouteId() == r.getRouteId()) {
//		cities.add(r.getOriginAirport());
//		cities.add(r.getDestinationAirport());
//	}
//}
//return cities;
//}
//
///* Takes in an airplane and finds the airplanetype. Useful for finding max capacity.*/
//public AirplaneType pickAirplaneTypeByAirplane(Airplane airplane) {
//List<AirplaneType> airplanetypes = admin.createAirplaneTypeList();
//for (AirplaneType at : airplanetypes) {
//	if (at.getAirplaneTypeId() == airplane.getAirplaneId()) {
//		return at;
//	}
//}
//return null;
//
//}
///* Displays all airplanetypes and returns the one the user has selected.*/
//public AirplaneType pickAirplaneType() {
//List<AirplaneType> airplanetypes = admin.createAirplaneTypeList();
//int index = 1;
//for (AirplaneType a : airplanetypes) {
//	System.out.println(Integer.toString(index++) + ") Id" + a.getAirplaneTypeId() + "   Max Capacity: "
//			+ a.getMaxCapacity());
//}
//System.out.println(airplanetypes.size() + 1 + ") Go Back");
//int choice = readInput(1, airplanetypes.size() + 1);
//if (choice == airplanetypes.size() + 1) {
//	return null;
//}
//return airplanetypes.get(choice - 1);
//}
//
///* displays all airplanes based on a type and returns the airplane the user selects*/
//public Airplane pickAirplane(AirplaneType airplanetype) {
//List<Airplane> airplanes = admin.createAirplaneListByType(airplanetype);
//int index = 1;
//for (Airplane a : airplanes) {
//	System.out.println(Integer.toString(index++) + ") Id: " + a.getAirplaneId() + "   Airplane type: "
//			+ a.getType().getAirplaneTypeId());
//}
//System.out.println(index + ") Go Back");
//int choice = readInput(1, airplanes.size() + 1);
//if (choice == airplanes.size() + 1) {
//	return null;
//}
//return airplanes.get(choice - 1);
//}
//
///* Displays all airports and returns the one the user selects*/
//public Airport pickAirport() {
//List<Airport> airports = admin.createAirportList();
//int index = 1;
//for (Airport a : airports) {
//	System.out.println(Integer.toString(index++) + ") " + a.getAirportId());
//}
//int choice = readInput(1, airports.size());
//return airports.get(choice - 1);
//}
//
////public BookingAgent pickBookingAgent() {
////List<BookingAgent> = 
////
////}
////
////public void printOneBookingAgent(BookingAgent ba) {
////
////}
//
///* Helper method to print out menu options*/
//public void prepareString(String[] strings, Object[] obj, int index) {
//System.out.print(index + ") ");
//for (int i = 0; i < strings.length; i++) {
//	System.out.print(strings[i] + ": " + obj[i] + "	");
//}
//System.out.println("\n-----------------------------------------");
//
//}
