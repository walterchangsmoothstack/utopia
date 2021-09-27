/**
 * 
 */
package com.ss.utopia.menu;

import java.util.List;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.service.AdminFlight;

/**
 * @author Walter Chang
 *
 */
public class AirportMenu extends Menu {
	AdminFlight admin = new AdminFlight();

	/*
	 * Add an airport, takes in a 3 letter code and a string If failed, it is
	 * because there was a duplicate airport code, will return to menu after.
	 */
	public void addAirport() {
		System.out.println("Enter airport code (three letters).");
		String airportCode = readValidAirportCode(); // readLine from Menu.java

		System.out.println("Enter name of city.");
		String city = readValidCity();
		System.out.println("----------Review Airport Addition----------");
		System.out.println("Airport Code: " + airportCode);
		System.out.println("City: " + city);
		System.out.println("Confirm: y/n");
		if (readYesNo()) {
			/* Proceed to add airport then return to menu */
			Airport airportToAdd = new Airport();
			airportToAdd.setAirportId(airportCode);
			airportToAdd.setCity(city);
			System.out.println(admin.addAirport(airportToAdd));
		} else {
			/* Otherwise return to add selection */
			chooseCategory(1);
			return;
		}
		runProgram();
	}

	public void readAirport() {
		System.out.println(admin.readAirports());
		System.out.println("1) Go Back");
		if (readInput(1, 1) == 1) {
			runProgram();
		}

	}

	/* Update Airport allows for updates on both city and airport code 
	 * Finishes the updating process. Calls methods -- readValidAirportCode()
	 * which only returns a valid user input for airportCode readValidCity()
	 * which only returns letters with unnecessary whitespace trimmed
	 * */
	public void updateAirport() {
		System.out.println("Choose airport to update.");
		Airport airportToUpdate = displayAirports();
		/* use null value to check if user has selected 'Go Back' option */
		if (airportToUpdate == null) {
			chooseCategory(3);
			return;
		}
		/* Prompt user to select what to update */
		System.out.println("1) Update City");
		System.out.println("2) Update Aiport Code");
		System.out.println("3) Go Back");
		int choice = readInput(1, 3);
		/* Final query will differ based on updating city or code */
		boolean isCode = false;
		if (choice == 3) {
			updateAirport();
			return;
		}
		/* Call readValidCity or readValidAirportCode to receive proper user input*/
		if (choice == 1) {
			System.out.println("Enter name of city.");
			String city = readValidCity();
			airportToUpdate.setCity(city);
		} else {
			System.out.println("Enter airport code (three letters).");
			isCode = true;
			String airportCode = readValidAirportCode();
			/* Append String together for use in AirportDAO method*/
			airportToUpdate.setAirportId(airportToUpdate.getAirportId() + "." + airportCode);
		}
		System.out.println("----------Review Airport Update----------");
		System.out.println("Airport Code: " + airportToUpdate.getAirportId());
		System.out.println("City: " + airportToUpdate.getCity());

		System.out.println("Confirm y/n");
		if (readYesNo()) {
			if (isCode) { 
				System.out.println(admin.updateAirportCode(airportToUpdate.getAirportId()));
			} else {
				System.out.println(admin.updateAirport(airportToUpdate));
			}
			runProgram(); //restart program
		}
		updateAirport(); //restart update process
		//Above confrimation
	}

	/* Delete an airport. Simply displays airports for user to select in deletion.
	 * WARNING-- deleting airports may truncate tables drastically. Therefore add several
	 * confirmation checks on the way.*/
	public void deleteAirport() {
		System.out.println("Choose an airport to delete.");
		Airport airportToDelete = displayAirports();
		if (airportToDelete == null) {
			chooseCategory(4); // if airport is null, user has selected go back
			return;
		}
		System.out.println("----------Review Airport Deletion----------");
		System.out.println("Airport Code: " + airportToDelete.getAirportId());
		System.out.println("City: " + airportToDelete.getCity());

		/* Put more checks for deleting airport as many tables rely on airports */
		System.out.println("Delete? y/n");
		if (readYesNo()) {
			System.out.println("*********WARNING*********");
			System.out.println("DELETION OF AIRPORT WILL RESULT IN ALL DATA ASSOCIATED WITH AIRPORT TO BE DELETED");
			System.out.println("Still delete? y/n");
			if (readYesNo()) {

				System.out.println(admin.deleteAirport(airportToDelete.getAirportId()));
			}
		}
		deleteAirport();
		return;

	}



	/* Calls read Airport in the service which will display airports. Allows user to select
	 * airport and will return the selected airport or null if the user chooses to 'Go Back' */
	public Airport displayAirports() {
		List<Airport> airports = admin.createAirportList();
		int index = 1;
		for (Airport a : airports) {
			System.out.println((index++) + ") Airport Code: " + a.getAirportId() + "   City: " + a.getCity());
		}
		System.out.println(airports.size() + 1 + ") Go Back");
		int choice = readInput(1, airports.size() + 1);
		if (choice == airports.size() + 1)
			return null;
		return airports.get(choice - 1);

	}
}
