/**
 * 
 */
package com.ss.utopia.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.ss.utopia.dao.FlightBookingDAO;
import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.service.AdminBooking;
import com.ss.utopia.service.AdminFlight;
import com.ss.utopia.service.AdminPassenger;
import com.ss.utopia.service.AdminServices;
import com.ss.utopia.service.AdminServices.Service;
import com.ss.utopia.service.ConnectionUtil;

/**
 * @author Walter Chang
 *
 */
public class OverRideTripCancellation extends Menu {

	ConnectionUtil connUtil = new ConnectionUtil();
	Scanner scan = new Scanner(System.in);
	Menu menu = new Menu();

	public void overrideCancellation() {

		AdminServices admin = new AdminServices();
		List<Book> bookings = admin.create(null, Service.BOOKING, connUtil);

		Integer ans;

		for (Book b : bookings) {
			if (!b.getIsActive()) {
				System.out.println("id: " + b.getId()+"  Status: Active");
				if (readNextInt() == 1) {
					b.setIsActive(Boolean.TRUE);
					System.out.println(admin.update(b, Service.BOOKING, connUtil));
				}

			}
		}
		menu.runProgram();

	}

	private Integer readNextInt() {
		String s= null;
		try {
			System.out.println("Press (1) to override cancellation");
			 s = scan.nextLine();
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			if("q".equals(s) || "Q".equals(s)) {
				menu.exit();
			}
			return readNextInt();
		}
	}
}
