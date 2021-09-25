package com.ss.utopia.adminTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.service.AdminBookingAgent;

public class bookingAgentTest {

	BookingAgent agent = new BookingAgent(new Book(7, null, null), 1);
	AdminBookingAgent admin = new AdminBookingAgent();
	@Test
	public void testAddBookingAgentTrue() {
		assertEquals("Added EMPLOYEE successfully", admin.addAgent(agent));
		assertEquals("Deleted EMPLOYEE successfully", admin.deleteAgent(agent.getBook()));
	}
	@Test
	public void testAddBookingAgentFalse() {
		//System.out.println(admin.addAgent(agent));
		agent.getBook().setId(999);;
		assertEquals("Unable to add EMPLOYEE", admin.addAgent(agent));
	}
	@Test
	public void testReadBookingAgents() {
		System.out.println(admin.readAgent());
	}
	@Test
	public void testReadBookingAgentByAgent() {
		agent.setId(1);
		System.out.println(admin.readAgentById(agent));
	}
	@Test
	public void testUpdateAgent() {
		agent.setBook(new Book(3, null, null));
		agent.setId(2);
		assertEquals("Updated EMPLOYEE successfully", admin.updateAgent(agent));
	}
	@Test
	public void testDeleteAgent() {
		assertEquals("Deleted EMPLOYEE successfully", admin.deleteAgent(new Book(7, null, null)));
	}
	
}
