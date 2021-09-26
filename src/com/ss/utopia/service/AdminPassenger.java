/**
 * 
 */
package com.ss.utopia.service;

import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang --Class Function: handles administration commands that
 *         have to do with passenger data manipulation Calls the AdminService
 *         which organizes the commands by Create, Read, Update, and Delete
 */
public class AdminPassenger {

	ConnectionUtil conn = new ConnectionUtil();

	/* Adds a new passenger */
	public String addPassenger(Passenger passenger) {
		AdminServices admin = new AdminServices();
		return admin.add(passenger, Service.PASSENGER, conn);
	}
	/* Reads passengers by first and last name*/
	public String readPassengerName(Passenger passenger) {
		AdminServices admin = new AdminServices();
		return admin.read(passenger, Service.PASSENGER, conn);
	}
	/* Reads all passengers in DB*/
	public String readPassengers() {
		AdminServices admin = new AdminServices();
		return admin.read(null, Service.PASSENGER, conn);
	}
	/* Updates passenger based off passenger id*/
	public String updatePassenger(Passenger passenger) {
		if (passenger == null || passenger.getId() == null) {
			return "Failed to update PASSENGER";
		}
		AdminServices admin = new AdminServices();
		return admin.update(passenger, Service.PASSENGER, conn);
	}
	/* Deletes passenger based off passenger id*/
	public String deletePassengerId(Passenger passenger) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] { passenger }, Service.PASSENGER, conn);
	}
	/* Deletes passenger based off booking id*/
	public String deletePassengerBookingId(Book book) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] { book }, Service.PASSENGER, conn);
	}

}
