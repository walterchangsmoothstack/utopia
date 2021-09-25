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
		assertEquals("Added BOOKING successfully", admin.addBooking(book));
	}
	
	@Test
	public void testReadBook() {
		System.out.println(admin.readBooking());
	}
	@Test
	public void testReadBookActive() {
		System.out.println(admin.readBookingActive(true));
	}
	@Test
	public void testReadBookCancelled() {
		System.out.println(admin.readBookingActive(false));

	}
	@Test
	public void testUpdateBookTrue() {
		book.setId(1);
		book.setIsActive(false);
		assertEquals("Updated BOOKING successfully", admin.updateBooking(book));
	}
//	@Test  TODO check if id exists for book in tbl_booking
//	public void testUpdateBookFalse() {
//		book.setId(999);
//		assertEquals("Failed to update BOOKING", admin.updateBooking(book));
//	}
	@Test
	public void testUpdateNull() {
		assertEquals("Failed to update BOOKING", admin.updateBooking(null));
		book.setConfirmationCode(null);
		assertEquals("Failed to update BOOKING", admin.updateBooking(book));
	}
	@Test
	public void testUpdateNullId() {
		book.setId(null);
		assertEquals("Failed to update BOOKING", admin.updateBooking(book));
	}
	@Test 
	public void testDeleteBook() {
		book.setId(35);
		assertEquals("Deleted BOOKING successfully", admin.deleteBooking(book)); //only test once
	}
	
}
