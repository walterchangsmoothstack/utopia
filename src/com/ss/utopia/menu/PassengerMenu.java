/**
 * 
 */
package com.ss.utopia.menu;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.BookingGuest;
import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.entity.BookingUser;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
import com.ss.utopia.service.AdminBooking;
import com.ss.utopia.service.AdminFlight;
import com.ss.utopia.service.AdminPassenger;

/**
 * @author Walter Chang
 *
 */
public class PassengerMenu extends Menu {
	final private static AdminPassenger admin = new AdminPassenger();
	final private static int AGENT = 3;
	final private static int USER = 2;

	public void addPassengers() {
		List<Passenger> passengers = new ArrayList<>();
		System.out.println("Add a Passenger");
		passengers.add(addOnePassenger());
		System.out.println("Passenger added!");
		addPassengerSave(passengers);

	}

	public void addPassengerSave(List<Passenger> passengers) {

		System.out.println("1) Add another passenger.");
		System.out.println("2) Continue to booking.");
		System.out.println("3) Manage passengers.");
		System.out.println("4) Go Back");
		int choice = readInput(1, 4);
		switch (choice) {
		case 1:
			passengers.add(addOnePassenger());
			addPassengerSave(passengers);
			break;
		case 2:
			bookingFlight(passengers);
			break;
		case 3:
			managePassengers(passengers);
			addPassengerSave(passengers);
			break;
		case 4:
			System.out.println("Are you sure? All progress will be lost. y/n");
			if (readYesNo()) { // reset to add menu
				chooseCategory(1);
			} else {
				addPassengerSave(passengers); // save passengers
				return;
			}

		}
	}

	public void bookingFlight(List<Passenger> passengers) {
		System.out.println("Choose a flight to book.");
		Flight flight = pickFlight();
		if (flight == null) { // return to previous step
			addPassengerSave(passengers);
		}
		System.out.println("Choose booking method");
		System.out.println("1) Booking Agent");
		System.out.println("2) Booking User");
		System.out.println("3) Booking Guest");
		System.out.println("4) Go Back");
		int choice = readInput(1, 4);
		User user = new User();
		Object obj = null;
		switch (choice) {
		case 1:
			System.out.println("Choose an agent to book with.");
			obj = pickUser(new UserRole(AGENT, "Agent"));
			if (user == null) {
				bookingFlight(passengers);
				return;
			}
			break;
		case 2:
			System.out.println("Choose a user to book with.");
			obj = pickUser(new UserRole(USER, "User"));
			if (user == null) {
				bookingFlight(passengers);
				return;
			}
			break;
		case 3:
			obj = bookingGuest();
			break;
		case 4:
			bookingFlight(passengers);
			return;
		}
		/*
		 * ===================FINAL REVIEW BEFORE COMMITING TO
		 * BOOKING===================
		 */
		System.out.println("================================================================");
		System.out.println("===================Review Booking Addition(s)===================");
		System.out.println("================================================================");
		System.out.println("Number of Tickets: " + passengers.size() + "       Total Cost: "
				+ flight.getSeatPrice() * passengers.size());
		int index = 1;
		BookingGuest guest = new BookingGuest();
		for (Passenger p : passengers) {
			System.out.print((index++) + ")");
			printOnePassenger(p); // call helper method to print one passenger
		}
		if (obj instanceof User) {
			user = (User) obj;
			if (user.getRole().getId() == AGENT) {
				System.out.print("Booking done by agent: ");

			} else {
				System.out.print("Booking done by user: ");
			}
			System.out.println(user.getGivenName() + " " + user.getFamilyName());
			System.out.println("Phone: " + user.getPhone() + "    Email: " + user.getEmail());

		} else {
			guest = (BookingGuest) obj;
			System.out.println("Booking done by guest. Contact Information:");
			System.out.println("Phone: " + guest.getPhone() + "    Email: " + guest.getEmail());

		}
		System.out.println("Confirm booking? y/n");
		if (readYesNo()) {
			Book book = createBook();
			for (Passenger p : passengers) {

				p.setBookId(book);
				admin.addPassenger(p);
			}

			if (obj instanceof User) { // Use instanceof to separate from guest
				user = (User) obj; // Cast again
				if (user.getId().equals(AGENT)) { /* Set booking agent and insert it into db */
					BookingAgent agent = new BookingAgent();

					agent.setBook(book);
					agent.setId(user.getId());
					admin.addAgent(agent);

				} else { /* Set booking user and insert it into db */
					BookingUser b_user = new BookingUser();
					b_user.setBookingId(book.getId());
					b_user.setId(user.getId());
					admin.addBookingUser(b_user);
				}
			} else {

				guest.setId(book.getId());/* Set booking guest and insert it into db */
				admin.addBookingGuest(guest);
			}

			insertBookingPayment(book); // insert a booking payment using booking_id

			insertFlightBooking(book, flight); // insert a flight booking using booking_id
			flight.setReservedSeats(flight.getReservedSeats() + passengers.size()); // update reserved seats on flight
			System.out.println("Booking has been submitted!");
			System.out.println("Here is your confirmation code: " + book.getConfirmationCode());
			runProgram();
		} else {
			/* take user back but save the data in passengers */
			bookingFlight(passengers);

		}
	}

	public void readTicketsAndPassengers() {
		System.out.println(admin.readPassengers());
		System.out.println("1) Go Back");
		if (readInput(1, 1) == 1) {
			chooseCategory(2); // return to read menu
		}
	}
	
	public void deleteTickets() {
		AdminFlight adminF = new AdminFlight();
		AdminBooking adminB = new AdminBooking();
		List<Book> books = admin.createBookList();
		List<Passenger> passengers = admin.createPassengerList();
		int index = 1;
		for(Book b : books) {
			System.out.println((index++)+") Book Id: "+ b.getId());
			System.out.println("Passengers tied to this booking:");
			for(Passenger p : passengers) {
				if(p.getBookId().getId().equals(b.getId())) {
				System.out.println("Passenger: "+ p.getGivenName()+" "+p.getFamilyName());
				}
			}
			System.out.println("--------------------------------------------------------");
		}
		System.out.println(index+") Go Back");
		int choice = readInput(1, index+1);
		if(choice == index+1) {
			chooseCategory(4);
			return;
		}
		System.out.println("Are you sure you want to delete this booking? y/n");
		if(!readYesNo()) {
			deleteTickets();
			return;
		}
		System.out.println(adminB.deleteBooking(books.get(choice-1)));
		runProgram();
		
		
		
	}

	public void updateTicketsAndPassengers() {
		System.out.println("Update Ticket.");
		System.out.println("1) Cancel/Refund");
		System.out.println("2) Change Flight");
		System.out.println("3) Go Back");
		int choice = readInput(1, 3);
		if (choice == 3) {
			chooseCategory(3);
			return;
		}
		Passenger passenger = pickPassenger();
		if (passenger == null) {
			updateTicketsAndPassengers();
			return;
		}
		switch (choice) {
		case 1:
			cancelTicket(passenger);
			break;
		case 2:
			changeFlight(passenger);
			break;
		case 3:

		}

	}

	/*
	 * Take in Book and Flight object, creates flightbooking object using id's and
	 * inserts to db
	 */
	public void insertFlightBooking(Book book, Flight flight) {
		FlightBooking fb = new FlightBooking();
		fb.setBooking_id(book.getId());
		fb.setFlight_id(flight.getId());
		admin.addFlightBooking(fb);

	}

	/*
	 * Adds a booking object into the DB and returns a book with a key and
	 * confirmation code
	 */
	public Book createBook() {
		StringBuilder code = new StringBuilder();
		Random rand = new Random(); // Generate a random confirmation code;
		for (int i = 0; i < 50; i++) {
			if (Math.random() > .5) {
				code.append(rand.nextInt(10));
			} else {
				code.append(Math.random() > 0.5 ? Character.toUpperCase((char) (rand.nextInt(26) + 97))
						: (char) (rand.nextInt(26) + 97));
			}
		}

		Book book = new Book(null, true, code.toString());
		Integer key = admin.createBookingKey(book).get(0);
		book.setId(key);
		return book;

	}

	/*
	 * Create a bookingpayment object and call add booking payment object using the
	 * book key
	 */
	public void insertBookingPayment(Book book) {
		BookingPayment payment = new BookingPayment();
		payment.setId(book.getId());
		payment.setRefunded(false);
		StringBuilder code = new StringBuilder();
		Random rand = new Random(); // Generate a random confirmation code;
		for (int i = 0; i < 50; i++) {
			if (Math.random() > .5) {
				code.append(rand.nextInt(10));
			} else {
				code.append(Math.random() > 0.5 ? Character.toUpperCase((char) (rand.nextInt(26) + 97))
						: (char) (rand.nextInt(26) + 97));
			}
		}
		payment.setStripe_id(code.toString());
		admin.addBookingPayment(payment);

	}

	/* Inserts all passengers created at the end of booking */
	public void insertPassengers(Book book, List<Passenger> passengers) {
		for (Passenger p : passengers) {

			p.setBookId(book);
			System.out.println(p.getBookId().getId());
			admin.addPassenger(p);
		}
		return;
	}

	/* Creates a booking guest object from user input */
	public BookingGuest bookingGuest() {
		BookingGuest guest = new BookingGuest();
		System.out.println("Please enter email: ");
		String email = readInputRegex("^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$");
		System.out.println("Please enter phone: ");
		String phone = readInputRegex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
		guest.setEmail(email);
		guest.setPhone(phone);
		return guest;
	}

	public Passenger addOnePassenger() {
		System.out.println("First Name: ");
		String fname = validateName();
		System.out.println("Last Name: ");
		String lname = validateName();
		System.out.println("Enter birthdate (MM-DD-YYYY): ");
		LocalDate date = validateDate();
		System.out.println("Enter gender: ");
		String gender = validateName();
		System.out.println("Enter street: ");
		String street = readInputRegex("[0-9]+[ a-zA-Z]+");
		System.out.println("Enter city: ");
		String city = readValidCity();
		System.out.println("Enter state abbreviation: ");
		String state = readInputRegex("[a-zA-z]{2}").toUpperCase();
		System.out.println("Enter zip code: ");
		String zip = readInputRegex("[0-9]{5}");
		String address = street + ", " + city + " " + state + ", " + zip;
		Passenger passenger = new Passenger(null, null, fname, lname, date, gender, address);
		return passenger;
	}

	/*
	 * Displays all passengers added in current booking session and calls itself
	 * until the user decides to leave.
	 */
	public List<Passenger> managePassengers(List<Passenger> passengers) {
		int index = 1;
		if (passengers.isEmpty()) { // base case if all have been deleted, then return
			System.out.println("No passengers available");
			return passengers;
		}
		System.out.println("Delete from your passengers");
		for (Passenger p : passengers) {
			System.out.print((index++) + ")");
			printOnePassenger(p); // call helper method to print one passenger
		}
		System.out.println(passengers.size() + 1 + " ) Go Back");
		int choice = readInput(1, passengers.size() + 1);
		if (choice == passengers.size() + 1)
			return passengers; // Make no changes
		passengers.remove(choice - 1); // remove passenger from list
		System.out.println("Deleted passenger number " + (choice - 1));
		return managePassengers(passengers);
	}

	/*
	 * Go through the tables booking and booking_payments to cancel tickets
	 * associated with passenger. Also updates the number of reserved seats
	 * accordingly
	 */
	public void cancelTicket(Passenger p) {
		List<Passenger> passengers = getAllPassengersInBook(p);
		AdminFlight adminF = new AdminFlight();
		List<Book> books = admin.createBookList();
		List<BookingPayment> payments = admin.createBookingPaymentList();
		List<Flight> flights = adminF.createFlightList();
		List<FlightBooking> fbooks = adminF.createFlightBookingList();
		FlightBooking flightB = null; //flightBooking, Book, BookPayment, Flight
		Book book = null;
		BookingPayment payment = null; 
		Flight flight = null; 
		
		/* Find booking that equals PASSENGER book id*/
		for (Book b : books) {
			if (b.getId().equals(p.getBookId().getId())) {
				b.setIsActive(!b.getIsActive());
				book = b;
				break;
			}
		}
		
		//Set both to opposite 
		
		/* Find bookingPayment that equals same book id*/
		for (BookingPayment bp : payments) {
			if (bp.getId().equals(p.getBookId().getId())) {
				bp.setRefunded(!bp.isRefunded());
				payment = bp;
				break;
			}
		}
		/* Get the flight ID of the flight associated with PASSENGER's booking*/
		for (FlightBooking fb : fbooks) {
			if (fb.getBooking_id().equals(p.getBookId().getId())) {
				flightB = fb;
			}
		}
		for (Flight f : flights) {
			if (f.getId().equals(flightB.getFlight_id())) {
				flight = f;
				break;
			}
		}
		admin.updateBookingPayment(payment);
		admin.updateBooking(book);
		if (book.getIsActive()) {
			System.out.println("The ticket is now active again.");
			flight.setReservedSeats(flight.getReservedSeats() + passengers.size());
		} else {
			System.out.println("The ticket has been cancelled");
			flight.setReservedSeats(flight.getReservedSeats() - passengers.size());

		}
		adminF.updateFlight(flight);
		System.out.println("1) Okay");
		if (readInput(1, 1) == 1) {
			updateTicketsAndPassengers(); // return to update tickets menu
		}
	}

	/* Change Flight of booking. Update all flights and passengers*/
	public void changeFlight(Passenger p) {
		List<Passenger> passengers = getAllPassengersInBook(p);
		AdminFlight adminF = new AdminFlight();
		List<FlightBooking> fbooks = adminF.createFlightBookingList();
		List<Flight> flights = adminF.createFlightList();
		FlightBooking fbook = null;
		Flight flight = null;
		for (FlightBooking fb : fbooks) { //find connection between passenger and flight booking id
			if (fb.getBooking_id().equals(p.getBookId().getId())) {
				fbook = fb;
				break;
			}
		}		
		/* Set the flight associated with passengers reserved seats. Also save flight*/
		for (Flight f : flights) {
			if (fbook.getFlight_id().equals(f.getId())) {
				f.setReservedSeats(f.getReservedSeats() - passengers.size());
				flight = f;
				break;
			}
		}
		/* Make sure the user does not select the same flight from earlier*/
		while(true) {
		Flight f = pickFlight();
		if (f == null) {
			updateTicketsAndPassengers(); // return to update tickets menu
			return;
		}
		if(!f.getId().equals(flight.getId())) {
			break;
		}
		else {
			System.out.println("Must choose a different flight");
		}
		}

		/* Find the flight booking id of the new flight*/
		for (FlightBooking fb : fbooks) {
			if (fb.getFlight_id().equals(flight.getId())) {
				fbook = fb;
				break;
			}
		}
		/* Set passengers to new booking id from flight book of new flight*/
		for(Passenger pass : passengers) {
			pass.getBookId().setId(fbook.getBooking_id());
			admin.updatePassenger(pass); //update passengers
		}
		System.out.println("Flight Changed");
		printOneFlight(flight, adminF.createRouteList());
		System.out.println("1) Go Back");
		if (readInput(1, 1) == 1)
			updateTicketsAndPassengers(); // return to update tickets menu

	}

	public List<Passenger> getAllPassengersInBook(Passenger p) {
		List<Passenger> res = new ArrayList<>();
		for (Passenger pass : admin.createPassengerList()) {
			if (pass.getBookId().getId().equals(p.getBookId().getId())) {
				res.add(pass);
			}
		}
		return res;
	}

}
