/**
 * 
 */
package com.ss.utopia.menu;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.entity.Route;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
import com.ss.utopia.service.AdminFlight;
import com.ss.utopia.service.AdminPassenger;

/**
 * @author Walter Chang
 *
 */
public class Menu {

	boolean quit;
	private final String[] operations = new String[] { "Add", "Read", "Update", "Delete",  "Over-ride Trip Cancellation", "Quit" };
	private final String[] categories = new String[] { "Flights", "Seats", "Tickets and Passengers", "Airports",
			"Travelers", "Employees", "Go Back" };

	public void openingPrompt() {
		System.out.println("What would you like to do?");
		for (int i = 0; i < operations.length; i++) {
			System.out.println(Integer.toString(i + 1) + ") " + operations[i]);
		}
	}

	public void categoriesPrompt(int op) {
		System.out.println("What would you like to " + operations[op - 1] + "?");
		for (int i = 0; i < categories.length; i++) {
			System.out.println(Integer.toString(i + 1) + ") " + categories[i]);
		}
	}

	public void chooseCategory(int op) {
		// while(!quit) {
		categoriesPrompt(op);
		int cat = readInput(1, categories.length);
		if (cat == categories.length) {
			runProgram();
			return;
		}
		switch (op) {
		case 1:
			add(cat);
			break;
		case 2:
			read(cat);
			break;
		case 3:
			update(cat);
		case 4:
			delete(cat);
		default:
			;
		}
		// }

	}

	public void runProgram() {
		// while(!quit) {
		openingPrompt();
		int input = readInput(1, operations.length);
		if(input == operations.length) return;
		if(input == 5) {
			OverRideCancellation overide = new OverRideCancellation();
			overide.checkForOveride();
			runProgram();
			return;
		}
		chooseCategory(input);
		// }
	}

	public void add(int category) {
		switch (category) {
		case 1:
			FlightMenu fmenu = new FlightMenu();
			fmenu.addFlight();
			break;
		case 3:
			PassengerMenu pmenu = new PassengerMenu();
			pmenu.addPassengers();
			break;
		case 4:
			AirportMenu amenu = new AirportMenu();
			amenu.addAirport();
			break;
		case 6:
			EmployeeMenu emenu = new EmployeeMenu();
			emenu.addEmployee();
			break;
		}

	}

	public void update(int category) {
		switch (category) {
		case 1:
			FlightMenu fmenu = new FlightMenu();
			fmenu.updateFlight();
			break;
		case 3:
			PassengerMenu pmenu = new PassengerMenu();
			pmenu.updateTicketsAndPassengers();
			break;
		case 4:
			AirportMenu amenu = new AirportMenu();
			amenu.updateAirport();
			break;
		case 6:
			EmployeeMenu emenu = new EmployeeMenu();
			emenu.updateEmployee();
		}

	}

	public void read(int category) {
		switch (category) {
		case 1:
			FlightMenu fmenu = new FlightMenu();
			fmenu.readFlight();
			break;
		case 3:
			PassengerMenu pmenu = new PassengerMenu();
			pmenu.readTicketsAndPassengers();
			break;
		case 4:
			AirportMenu amenu = new AirportMenu();
			amenu.readAirport();
			break;
		case 6:
			EmployeeMenu emenu = new EmployeeMenu();
			emenu.readEmployee();
			break;
		}
	}

	public void delete(int category) {
		switch (category) {
		case 1:
			FlightMenu fmenu = new FlightMenu();
			fmenu.deleteFlight();
		case 3:
			PassengerMenu pmenu = new PassengerMenu();
			pmenu.deleteTickets();
		case 4:
			AirportMenu amenu = new AirportMenu();
			amenu.deleteAirport();
		case 6:
			EmployeeMenu emenu = new EmployeeMenu();
			emenu.deleteEmployee();
			break;
		}
	}

	public int readInput(Integer min, Integer max) {
		Scanner scan = new Scanner(System.in);
		int input = 0;
		while (input < min || input > max) {
			try {
				input = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {

			} finally {
				if (input < min || input > max) {
					System.out.println(
							"Please pick a number between " + Integer.toString(min) + " and " + Integer.toString(max));
				}
			}

		}
		return input;
	}

	public int readPositiveInt() {
		int parse = -1;
		Scanner scan = new Scanner(System.in);
		while (parse < 0) {
			try {
				parse = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {

			} finally {
				if (parse < 0) {
					System.out.println("Please insert a valid integer");
				}
			}
		}
		// scan.close();
		return parse;
	}

	public String readLine() {
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}

	public float readPositiveFloat() {
		float parse = -1;
		Scanner scan = new Scanner(System.in);
		while (parse < 0) {
			try {
				parse = Float.parseFloat(scan.nextLine());
			} catch (Exception e) {

			} finally {
				if (parse < 0) {
					System.out.println("Please insert valid cost");
				}
			}
		}
		// scan.close();
		return parse;
	}

	public boolean readYesNo() {
		Scanner scan = new Scanner(System.in);
		boolean bool;
		String parse = "";
		while (!"y".equals(parse) && !"Y".equals(parse) && !"n".equals(parse) && !"N".equals(parse)) {

			parse = scan.nextLine();
			if (!"y".equals(parse) && !"Y".equals(parse) && !"n".equals(parse) && !"N".equals(parse)) {
				System.out.println("Please confirm y/n.");
			}
		}
		// scan.close();
		return "y".equals(parse) || "Y".equals(parse) ? true : false;
	}

	public String readInputRegex(String regex) {
		String parse = "";
		Scanner scan = new Scanner(System.in);
		while (true) {
			parse = scan.nextLine();
			if (Pattern.matches(regex, parse)) {
				break;
			} else {
				System.out.println("Please enter valid characters");
			}

		}
		return parse;

	}

	public LocalDateTime readTime(LocalDate date) {
		String parse = "";
		Scanner scan = new Scanner(System.in);
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
	
	public User pickUser(UserRole user) {
		AdminPassenger admin = new AdminPassenger();
		List<User> users = admin.createUserList(user);
		int index = 1;
		for(User u : users) {
			System.out.print((index++) +") ");
			printOneUser(u);
		}
		System.out.println(index+") Go Back");
		int choice = readInput(1, index);
		if(choice == index) return null;
		return users.get(choice-1);
		
	}
	/* Print the contents of one employee for review*/
	public void printOneUser(User user) {
		System.out.println("Role: " + user.getRole().getId());
		System.out.println("First Name: " + user.getGivenName() + "   Last Name: " + user.getFamilyName());
		System.out.println("Username: " + user.getUsername());
		System.out.println("Email: " + user.getEmail());
		System.out.println("Phone Number: " + user.getPhone()+"\n");
		System.out.println("----------------------------------------------------------------------------");
	}
	
	/* Helper method to print out flights and also handle picking one. Returns the flight picked
	 * or null if user decided to back out*/
	public Flight pickFlight(){
		AdminFlight admin = new AdminFlight();
		List<Flight> flights = admin.createFlightList();
		List<Route> routes = admin.createRouteList();
		int index = 1;
		for(Flight f : flights) {
			System.out.print((index++)+") ");
			printOneFlight(f, routes);
		}
		System.out.println(index+") Go Back");
		int choice = readInput(1, index);
		if(choice == index) return null;
		return flights.get(choice-1);
	}
	
	public Passenger pickPassenger() {
		AdminPassenger admin = new AdminPassenger();
		List<Passenger> passengers = admin.createPassengerList();
		int index = 1;
		for(Passenger p : passengers) {
			System.out.print((index++)+") ");
			printOnePassenger(p);
		}
		System.out.println(index+") Go Back");
		int choice = readInput(1, index);
		if(choice == index) return null;
		return passengers.get(choice-1);
	}
	
	/* Helper method. Prints the attributes of parameter passenger. */
	public void printOnePassenger(Passenger p) {
		System.out.println("First Name: " + p.getGivenName()+"   Last Name: "+ p.getFamilyName());
		System.out.println("Birthdate: "+ p.getDob()+"   Gender: "+ p.getGender());
		System.out.println("Address: "+ p.getAddress());
		System.out.println("-----------------------------------------------------------------");
	}
	
	
	/* Helper method to print out one flight and also get its routes*/
	public void printOneFlight(Flight flight, List<Route> routes) {
		String route = "";
		
		for(int i =0; i<routes.size(); i++) {
			if(flight.getRoute().getRouteId().equals(routes.get(i).getRouteId())) {
				route = routes.get(i).getOriginAirport()+"-"+routes.get(i).getDestinationAirport();
				flight.getRoute().setOriginAirport(routes.get(i).getOriginAirport());
				flight.getRoute().setDestinationAirport(routes.get(i).getDestinationAirport());
				break;
			}
		}
		System.out.println("Flight id: " +flight.getId()+"   "+ route);
		System.out.println("Departure Time: "+ flight.getDepartureTime());
		System.out.println("Reserved Seats: "+ flight.getReservedSeats()+"   Seat price: "+ flight.getSeatPrice());
		System.out.println("--------------------------------------------------------");
	}

	
///////////////////////////////////////////////////////////////////
	/* Validation Methods return null if the user selects Go Back */
///////////////////////////////////////////////////////////////////

	public LocalDate validateDate() {
		LocalDate date = null;
		LocalDate before = LocalDate.now().minusYears(100);
		LocalDate after = LocalDate.now().minusYears(18);
		while(true) {
		String dt = readInputRegex("([0-9]{2}-[0-9]{2}-[0-9]{4})|([0-9]{4}-[0-9]{2}-[0-9]{2})");
		try {
			String[] nums = dt.split("-");
			if(nums[0].length() == 2) { //MM-DD-YYYY
				date = LocalDate.of(Integer.parseInt(nums[2]), Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
			}
			else { //YYYY-MM-DD
				date = LocalDate.of(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));
			}
		}catch(Exception e) {
			System.out.println("Enter a valid date: ");
			continue;
		}
		if(date.isBefore(before)) {
			System.out.println("Enter a more recent year");
		}
		else if(date.isAfter(after)) {
			System.out.println("Must be 18 years or older");
		}
		else {
			break;
		}
		}
		return date;
	}

	/* Read until user provides valid name */
	public String validateName() {
		String name = readInputRegex("[a-zA-Z]{2,}");
		name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		return name;
	}

	/* Read until user provides valid email */
	public String validateEmail(List<String> emails) {
		String email = "";
		/* Check if email exists already */
		while (true) {
			email = readInputRegex("^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$");
			if (emails.contains(email)) {
				System.out.println("Email is already in use.");
				System.out.println("Continue? y/n"); // Prompt if the user wants to exit now
				if (!readYesNo()) {
					return null;
				}
				System.out.println("Enter an email: ");

			} else {
				break;
			}
		}
		return email;
	}

	/* Read until user provides valid username */
	public String validateUsername(List<String> usernames) {
		String username = "";
		while (true) {
			username = readInputRegex("[a-zA-Z0-9_]{4,25}");
			/* Check if username exists already */
			if (usernames.contains(username)) {
				System.out.println("Username is taken. Please enter a different one:");
			} else {
				break;
			}
		}
		return username;
	}

	/* Read until user provides valid phone */
	public String validatePhone(List<String> phones) {
		String phone = "";
		while (true) {
			/* Check if phone number exists already */
			phone = readInputRegex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
			phone = formatPhoneNumber(phone);
			if (phones.contains(phone)) {
				System.out.println("Phone Number is already in use.");
				System.out.println("Continue? y/n"); // Prompt if the user wants to exit now
				if (!readYesNo()) {
					return null;
				}
				System.out.println("Enter a phone number: ");
			} else {
				break;
			}
		}
		return phone;
	}

	/*
	 * Takes in a phone number, and checks if it has area code. Then formats it
	 * accordingly
	 */
	public String formatPhoneNumber(String phone) {
		phone = phone.replaceAll("[^0-9]", "");
		if (phone.length() == 10) {
			return "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + " " + phone.substring(6);
		} else {
			return phone.substring(0, 1) + "+(" + phone.substring(1, 4) + ") " + phone.substring(4, 7) + " "
					+ phone.substring(7);
		}

	}
	/* Reads user input using readLin() and only accepts letters*/
	public String readValidCity() {

		String city = readLine();
		/* Check for valid characters */
		while (true) {
			if (city == null || city.equals("") || !city.matches("[ a-zA-Z]+")) {
				System.out.println("Please enter valid characters");
				city = readLine();
				continue;
			}
			break;
		}
		/* Formatting to make the city consistent */
		city = city.trim();
		StringBuilder sb = new StringBuilder();
		sb.append(Character.toUpperCase(city.charAt(0)));
		for (int i = 1; i < city.length(); i++) {

			if (city.charAt(i - 1) == ' ') {
				if (city.charAt(i) == ' ')
					continue;
				sb.append(Character.toUpperCase(city.charAt(i)));
			} else {
				sb.append(Character.toLowerCase(city.charAt(i)));
			}
		}
		return sb.toString();
	}
	/* Reads user input using readLine() and only accepts 3 letter codes*/
	public String readValidAirportCode() {
		String airportCode = readLine();
		while (true) {
			if (airportCode == null || airportCode.length() != 3 || !airportCode.matches("[a-zA-Z]+")) {
				System.out.println("Please enter valid code");
				airportCode = readLine();
				continue;
			}
			break;

		}
		return airportCode.toUpperCase();
	}



}
