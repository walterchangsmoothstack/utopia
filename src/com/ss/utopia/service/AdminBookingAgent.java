/**
 * 
 */
package com.ss.utopia.service;

import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang Class Function: handles administration commands that
 *         have to do with booking_agent data manipulation Calls the
 *         AdminService which organizes the commands by Create, Read, Update,
 *         and Delete
 *
 */
public class AdminBookingAgent {

	ConnectionUtil conn = new ConnectionUtil();

	/* Adds a booking by an agent*/
	public String addAgent(BookingAgent agent) {
		AdminServices admin = new AdminServices();
		return admin.add(agent, Service.EMPLOYEE, conn);
	}

	/* Reads all bookings by agents with their respective agents' ids*/
	public String readAgent() {
		AdminServices admin = new AdminServices();
		return admin.read(null, Service.EMPLOYEE, conn);
	}

	/* Reads all bookings by a specific agent*/
	public String readAgentById(BookingAgent agent) {
		AdminServices admin = new AdminServices();
		return admin.read(agent, Service.EMPLOYEE, conn);
	}

	/* Updates a booking's agent*/
	public String updateAgent(BookingAgent agent) {
		if (agent == null || agent.getBook() == null || agent.getBook().getId() == null) {
			return "Failed to update EMPLOYEE";
		}
		AdminServices admin = new AdminServices();
		return admin.update(agent, Service.EMPLOYEE, conn);
	}
	
	/* Deletes a booking by an agent*/
	public String deleteAgent(Book book) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] { book }, Service.EMPLOYEE, conn);
	}
}
