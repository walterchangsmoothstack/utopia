/**
 * 
 */
package com.ss.utopia.menu;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Walter Chang
 *
 */
public class Menu {

	boolean quit;
	private final String[] operations = new String[] { "Add", "Read", "Update", "Delete" };
	private final String[] categories = new String[] { "Flights", "Seats", "Tickets and Passengers", "Airports",
			"Travelers", "Employees", "Over-ride Trip Cancellation", "Quit" };

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
			break;
		}
		// }

	}

	public void runProgram() {
		// while(!quit) {
		openingPrompt();
		int input = readInput(1, operations.length);
		chooseCategory(input);
		// }
	}

	public void add(int category) {
		switch (category) {
		case 1:
			FlightMenu fmenu = new FlightMenu();
			fmenu.addFlight();
			break;
		case 4:
			AirportMenu amenu = new AirportMenu();
			amenu.addAirport();
			break;

		}

	}

	public void update(int category) {
		switch (category) {
		case 1:
			FlightMenu fmenu = new FlightMenu();
			fmenu.updateFlight();
			break;
		case 4:
			AirportMenu amenu = new AirportMenu();
			amenu.updateAirport();
			break;
		}

	}

	public void read(int category) {
		switch (category) {
		case 1:
			FlightMenu fmenu = new FlightMenu();
			fmenu.readFlight();
			break;
		case 4:
			AirportMenu amenu = new AirportMenu();
			amenu.readAirport();
		}
	}

	public void delete(int category) {
		switch (category) {
		case 1:
			FlightMenu fmenu = new FlightMenu();
			fmenu.deleteFlight();
		case 4:
			AirportMenu amenu = new AirportMenu();
			amenu.deleteAirport();
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

	public LocalDateTime readTime(LocalDate date) {
		String parse = "";
		Scanner scan = new Scanner(System.in);
		while (true) {
			parse = scan.nextLine();

			if (Pattern.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", parse)) {
				break;
			} else {
				System.out.println("Please valid enter HH:MM");
			}

		}
		String[] hoursMinutes = parse.split(":");
		LocalDateTime dt = date.atStartOfDay().plusHours(Integer.parseInt(hoursMinutes[0]))
				.plusMinutes(Integer.parseInt(hoursMinutes[1]));
		return dt;
	}

}
