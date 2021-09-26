package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.BookDAO;
import com.ss.utopia.dao.BookingAgentDAO;
import com.ss.utopia.dao.FlightDAO;
import com.ss.utopia.dao.PassengerDAO;
import com.ss.utopia.dao.RouteDAO;
import com.ss.utopia.dao.UserDAO;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.entity.Route;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;

public class AdminServices<T> {

	public enum Service {
		AIRPORT, ROUTE, FLIGHT, BOOKING, PASSENGER, EMPLOYEE, USER
	}

	public String add(T obj, Service serv, ConnectionUtil connUtil) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			switch (serv) {
			case AIRPORT:
				AirportDAO adao = new AirportDAO(conn);
				adao.addAirport((Airport) obj);
				break;
			case ROUTE:
				RouteDAO rdao = new RouteDAO(conn);
				Integer pk = rdao.addRoute((Route) obj);
				break;
			case FLIGHT:
				FlightDAO fdao = new FlightDAO(conn);
				fdao.addFlight((Flight) obj);
				break;
			case BOOKING:
				BookDAO bdao = new BookDAO(conn);
				bdao.addBook((Book) obj);
				break;
			case PASSENGER:
				PassengerDAO pdao = new PassengerDAO(conn);
				pdao.addPassenger((Passenger) obj);
				break;
			case EMPLOYEE:
				BookingAgentDAO bgdao = new BookingAgentDAO(conn);
				bgdao.addBookingAgent((BookingAgent) obj);
				break;
			case USER:
				UserDAO udao = new UserDAO(conn);
				udao.addUser((User) obj);
				break;

			default:
				break;
			}
			conn.commit();
			return "Added " + serv + " successfully";
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
			// e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return "Unable to add " + serv;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String read(T optional, Service serv, ConnectionUtil connUtil) {
		Connection conn = null;
		StringBuilder information = new StringBuilder();
		try {
			conn = connUtil.getConnection();
			switch (serv) {
			case AIRPORT:
				AirportDAO adao = new AirportDAO(conn);
				if (optional != null) {
					for (Airport a : adao.readAirportsInCity((String) optional)) {
						information.append(
								"Name: " + a.getAirportId() + " City: " + a.getCity() + "\n -----------------\n");
					}
				} else {
					for (Airport a : adao.readAirports()) {
						information.append(
								"Name: " + a.getAirportId() + " City: " + a.getCity() + "\n -----------------\n");
					}
				}
				break;
			case ROUTE:
				RouteDAO rdao = new RouteDAO(conn);
				if (optional != null) {
					for (Route r : rdao.readRoutesByAirportId((String) optional)) {
						information.append("Id: " + r.getRouteId() + " Origin: " + r.getOriginAirport()
								+ " Destination: " + r.getDestinationAirport() + "\n -----------------\n");

					}
				} else {
					for (Route r : rdao.readRoutes()) {
						information.append("Id: " + r.getRouteId() + " Origin: " + r.getOriginAirport()
								+ " Destination: " + r.getDestinationAirport() + "\n -----------------\n");
					}
				}
				break;

			case FLIGHT:
				FlightDAO fdao = new FlightDAO(conn);
				if (optional != null) {
					for (Flight f : fdao.readFlightByRoute((Route) optional)) {
						information.append("Id: " + f.getId() + " Route ID: " + f.getRoute().getRouteId()
								+ " Airplane ID: " + f.getAirplane().getAirplaneId() + " Departure Time: "
								+ f.getDepartureTime() + " Reserved Seats: " + f.getReservedSeats() + " Seat Price: "
								+ f.getSeatPrice() + "\n -----------------\n");
					}

				} else {
					for (Flight f : fdao.readFlight()) {
						information.append("Id: " + f.getId() + " Route ID: " + f.getRoute().getRouteId()
								+ " Airplane ID: " + f.getAirplane().getAirplaneId() + " Departure Time: "
								+ f.getDepartureTime() + " Reserved Seats: " + f.getReservedSeats() + " Seat Price: "
								+ f.getSeatPrice() + "\n -----------------\n");
					}
				}
				break;
			case BOOKING:
				BookDAO bdao = new BookDAO(conn);
				if (optional != null) {
					for (Book b : bdao.readActiveBookings((Boolean) optional)) {
						information.append(
								"Booking Id: " + b.getId() + " Status: " + (b.getIsActive() ? "Active." : "Cancelled.")
										+ " Confirmation Code: " + b.getConfirmationCode() + "\n -----------------\n");
					}
				} else {
					for (Book b : bdao.readBookings()) {
						information.append(
								"Booking Id: " + b.getId() + " Status: " + (b.getIsActive() ? "Active." : "Cancelled.")
										+ " Confirmation Code: " + b.getConfirmationCode() + "\n -----------------\n");
					}
				}
				break;
			case PASSENGER:
				PassengerDAO pdao = new PassengerDAO(conn);
				if (optional != null) {
					for (Passenger p : pdao.readPassengerName((Passenger) optional)) {
						information
								.append("Id: " + p.getId() + " Booking Id: " + p.getBookId().getId() + "\n First Name: "
										+ p.getGivenName() + " Last Name: " + p.getFamilyName() + " Gender: "
										+ p.getGender() + " Addres: " + p.getAddress() + "\n -----------------\n");
					}
				} else {
					for (Passenger p : pdao.readPassengers()) {
						information
								.append("Id: " + p.getId() + " Booking Id: " + p.getBookId().getId() + "\n First Name: "
										+ p.getGivenName() + " Last Name: " + p.getFamilyName() + " Gender: "
										+ p.getGender() + " Addres: " + p.getAddress() + "\n -----------------\n");
					}
				}
				break;
			case EMPLOYEE:
				BookingAgentDAO bgdao = new BookingAgentDAO(conn);
				if (optional != null) {
					for (BookingAgent ba : bgdao.readBookingByAgent((BookingAgent) optional)) {
						information.append("Booking Id: " + ba.getBook().getId() + " Agent Id: " + ba.getId()
								+ "\n -----------------\n");
					}
				} else {
					for (BookingAgent ba : bgdao.readBookings()) {
						information.append("Booking Id: " + ba.getBook().getId() + " Agent Id: " + ba.getId()
								+ "\n -----------------\n");
					}
				}
				break;
			case USER:
				UserDAO udao = new UserDAO(conn);
				if (optional != null) {
					for (User u : udao.readUserByRole((UserRole)optional)) {
						information.append("Id: " + u.getId());
						information.append("\nRole Id: " + u.getRole().getId());
						information.append("\nFirst Name:" + u.getGivenName());
						information.append("\nLast Name:" + u.getFamilyName());
						information.append("\nUsername:" + u.getUsername());
						information.append("\nemail:" + u.getEmail());
						information.append("\nPhone Number:" + u.getPhone());
						information.append("\n -----------------\n");
					}
				} else {
					for (User u : udao.readUsers()) {
						information.append("Id: " + u.getId());
						information.append("\nRole Id: " + u.getRole().getId());
						information.append("\nFirst Name:" + u.getGivenName());
						information.append("\nLast Name:" + u.getFamilyName());
						information.append("\nUsername:" + u.getUsername());
						information.append("\nemail:" + u.getEmail());
						information.append("\nPhone Number:" + u.getPhone());
						information.append("\n -----------------\n");
					}
				}
				break;
			default:
				break;
			}
			conn.commit();
			return (information.length() == 0) ? "No " + serv + " found" : information.toString();
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
			 e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return "Unable to read " + serv;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String update(T obj, Service serv, ConnectionUtil connUtil) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			switch (serv) {
			case AIRPORT:
				AirportDAO adao = new AirportDAO(conn);
				adao.updateAirport((Airport) obj);
				break;
			case ROUTE:
				RouteDAO rdao = new RouteDAO(conn);
				rdao.updateRoute((Route) obj);
				break;
			case FLIGHT:
				FlightDAO fdao = new FlightDAO(conn);
				fdao.updateFlight((Flight) obj);
				break;
			case BOOKING:
				BookDAO bdao = new BookDAO(conn);
				bdao.updateBook((Book) obj);
				break;
			case PASSENGER:
				PassengerDAO pdao = new PassengerDAO(conn);
				pdao.updatePassenger((Passenger) obj);
				break;
			case EMPLOYEE:
				BookingAgentDAO bgdao = new BookingAgentDAO(conn);
				bgdao.updateBookingAgent((BookingAgent) obj);
				break;
			case USER:
				UserDAO udao = new UserDAO(conn);
				udao.updateUser((User) obj);
				break;
			default:
				break;
			}
			conn.commit();
			return "Updated " + serv + " successfully";
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return "Unable to update " + serv;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String delete(Object[] objects, Service serv, ConnectionUtil connUtil) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			switch (serv) {
			case AIRPORT:
				AirportDAO adao = new AirportDAO(conn);
				adao.deleteAirport((String) objects[0]);
				break;
			case ROUTE:
				RouteDAO rdao = new RouteDAO(conn);
				if (objects.length == 2) {
					rdao.deleteRouteFK((String) objects[0], (String) objects[1]);
				} else {
					rdao.deleteRoute((Integer) objects[0]);
				}
				break;
			case FLIGHT:
				FlightDAO fdao = new FlightDAO(conn);
				Flight flight = (Flight) objects[0];
				fdao.deleteFlight(flight.getId());
				break;
			case BOOKING:
				BookDAO bdao = new BookDAO(conn);
				Book book = (Book) objects[0];
				bdao.deleteBook(book.getId());
				break;
			case PASSENGER:
				PassengerDAO pdao = new PassengerDAO(conn);
				if (objects[0] instanceof Passenger) {
					pdao.deletePassengerId((Passenger) objects[0]);
				} else {
					pdao.deletePassengerBookingId((Book) objects[0]);
				}
				break;
			case EMPLOYEE:
				BookingAgentDAO bgdao = new BookingAgentDAO(conn);
				bgdao.deleteBookingAgent((Book) objects[0]);
				break;
			case USER:
				UserDAO udao = new UserDAO(conn);
				udao.deleteUser((User) objects[0]);
			default:
				break;
			}
			conn.commit();
			return "Deleted " + serv + " successfully";
		} catch (ClassNotFoundException | SQLException | ClassCastException | NullPointerException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return "Unable to delete " + serv;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
