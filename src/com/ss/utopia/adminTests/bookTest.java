package com.ss.utopia.adminTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ss.utopia.entity.Book;
import com.ss.utopia.service.AdminBooking;

public class bookTest {
	Book book = new Book(null, Boolean.FALSE, "12345");
	AdminBooking admin = new AdminBooking();
	@Test
	public void testAddBookFalse() {
		book.setConfirmationCode("4735");
		System.out.println(admin.addBooking(book));
		//assertEquals("Unable to add BOOKING", admin.addBooking(book) );
	}
	
	@Test
	public void testReadBook() {
		//System.out.println(admin.readBooking());
	}
	@Test
	public void testReadBookActive() {
		//System.out.println(admin.readBookingActive(true));
	}
	@Test
	public void testReadBookCancelled() {
		//System.out.println(admin.readBookingActive(false));

	}
	@Test
	public void testUpdateBook() {
		//book.setId(5);
		book.setId(12);
		book.setIsActive(false);
		System.out.println(admin.updateBooking(book));
	}
	@Test 
	public void testDeleteBook() {
		for(int i =33; i<=34; i++) {
			book.setId(i);
		System.out.println(admin.deleteBooking(book));
		}
	}
	
}
