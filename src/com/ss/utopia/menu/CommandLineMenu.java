/**
 * 
 */
package com.ss.utopia.menu;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminAirport;
import com.ss.utopia.service.AdminFlight;
import com.ss.utopia.service.AdminPassenger;
import com.ss.utopia.service.AdminRoute;

/**
 * @author Walter Chang
 *
 */
public class CommandLineMenu extends Menu {

	/* Start of program. Call the Menu super class' run method*/
	public static void main(String[] args) {
		CommandLineMenu menu = new CommandLineMenu();

		menu.runProgram();

	}

}
