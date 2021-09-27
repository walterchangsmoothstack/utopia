/**
 * 
 */
package com.ss.utopia.service;

import java.util.List;

import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.BookingGuest;
import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.entity.BookingUser;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
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
	
	public String addUser(User user) {
		AdminServices admin = new AdminServices();
		return admin.add(user, Service.USER, conn);
	}
	public String readUser() {
		AdminServices admin = new AdminServices();
		return admin.read(null, Service.USER, conn);
	}
	public String readUser(UserRole role) {
		AdminServices admin = new AdminServices();
		return admin.read(role, Service.USER, conn);
	}
	public String updateUser(User user) {
		if(user == null || user.getId() ==null) {
			return "Unable to update USER successfully";
		}
		AdminServices admin = new AdminServices();
		return admin.update(user, Service.USER, conn);
	}
	public String deleteUser(User user) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] {user}, Service.USER, conn);
	}
	
	public List<User> createUserList(){
		AdminServices admin = new AdminServices();
		return admin.create(null, Service.USER, conn);
	}
	public List<User> createUserList(UserRole role){
		AdminServices admin = new AdminServices();
		return admin.create(new Object[] {role}, Service.USER, conn);
	}
	public List<BookingAgent> createBookingAgentList(){
		AdminServices admin = new AdminServices();
		return admin.create(null, Service.EMPLOYEE, conn);
	}
	public List<UserRole> createUserRoleList(Integer id){
		AdminServices admin = new AdminServices();
		return admin.create(new Object[] {id}, Service.ROLE, conn);
	}
	public List<UserRole> createUserRoleList(){
		AdminServices admin = new AdminServices();
		return admin.create(null, Service.ROLE, conn);
	}
	public List<Integer> createBookingKey(Book book) {
		AdminServices admin = new AdminServices();
		return admin.create(new Object[] {book}, Service.BOOKING, conn);
	}
	
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
	public String addBookingUser(BookingUser user) {
		AdminServices admin = new AdminServices();
		return admin.add(user, Service.BOOKING_USER, conn);
	}
	public String addBookingGuest(BookingGuest guest) {
		AdminServices admin = new AdminServices();
		return admin.add(guest, Service.BOOKING_GUEST, conn);
	}
	public String addBookingPayment(BookingPayment payment) {
		AdminServices admin = new AdminServices();
		return admin.add(payment, Service.BOOKING_PAYMENT, conn);
	}
	public List<Passenger> createPassengerList() {
		AdminServices admin = new AdminServices();
		return admin.create(null, Service.PASSENGER, conn);
	}
	public List<BookingUser> createBookingUserList() {
		AdminServices admin = new AdminServices();
		return admin.create(null, Service.BOOKING_USER, conn);
	}
	public List<Book> createBookList() {
		AdminServices admin = new AdminServices();
		return admin.create(null, Service.BOOKING, conn);
	}
	public String addFlightBooking(FlightBooking booking) {
		AdminServices admin = new AdminServices();
		return admin.add(booking, Service.FLIGHT_BOOKING, conn);
	}
	public List<BookingPayment> createBookingPaymentList(){
		AdminServices admin = new AdminServices();
		return admin.create(null, Service.BOOKING_PAYMENT, conn);
	}
	public String updateBookingPayment(BookingPayment payment){
		AdminServices admin = new AdminServices();
		return admin.update(payment, Service.BOOKING_PAYMENT, conn);
	}
	/* Update a booking */
	public String updateBooking(Book book) {
		if (book == null || book.getId() == null) {
			return "Failed to update BOOKING";
		}
		AdminServices admin = new AdminServices();
		return admin.update(book, Service.BOOKING, conn);
	}

}
