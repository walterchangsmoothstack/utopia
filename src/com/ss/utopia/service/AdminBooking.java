/**
 * 
 */
package com.ss.utopia.service;

import com.ss.utopia.entity.Book;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang
 *
 */
public class AdminBooking {

	ConnectionUtil conn = new ConnectionUtil();
	
	public String addBooking(Book book) {
		AdminServices admin = new AdminServices();
		return admin.add(book, Service.BOOKING, conn);
		
	}
	public String readBooking() {
		AdminServices admin = new AdminServices();
		return admin.read(null, Service.BOOKING, conn);
	}
	public String readBookingActive(Boolean isActive) {
		AdminServices admin = new AdminServices();
		return admin.read(isActive, Service.BOOKING, conn);
	}
	public String updateBooking(Book book) {
		if(book == null || book.getId() == null) {
			return "Failed to update BOOKING";
		}
		AdminServices admin = new AdminServices();
		return admin.update(book, Service.BOOKING, conn);
	}
	public String deleteBooking(Book book) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] {book}, Service.BOOKING, conn);
	}
}
