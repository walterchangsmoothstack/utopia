/**
 * 
 */
package com.ss.utopia.service;

import com.ss.utopia.entity.Book;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang--Class Function: handles administration commands that
 *         have to do with passenger data manipulation Calls the AdminService
 *         which organizes the commands by Create, Read, Update, and Delete
 *
 */
public class AdminBooking {

	ConnectionUtil conn = new ConnectionUtil();

	/* Add a booking */
	public String addBooking(Book book) {
		AdminServices admin = new AdminServices();
		return admin.add(book, Service.BOOKING, conn);

	}
	/* Read all bookings */
	public String readBooking() {
		AdminServices admin = new AdminServices();
		return admin.read(null, Service.BOOKING, conn);
	}
	/* Read all bookings based off of status */
	public String readBookingActive(Boolean isActive) {
		AdminServices admin = new AdminServices();
		return admin.read(isActive, Service.BOOKING, conn);
	}
	/* Update a booking */
	public String updateBooking(Book book) {
		if (book == null || book.getId() == null) {
			return "Failed to update BOOKING";
		}
		AdminServices admin = new AdminServices();
		return admin.update(book, Service.BOOKING, conn);
	}
	/* Delete a booking */
	public String deleteBooking(Book book) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] { book }, Service.BOOKING, conn);
	}
}
