package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ss.utopia.dao.AirplaneDAO;
import com.ss.utopia.dao.AirplaneTypeDAO;
import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.BookDAO;
import com.ss.utopia.dao.BookingAgentDAO;
import com.ss.utopia.dao.BookingGuestDAO;
import com.ss.utopia.dao.BookingPaymentDAO;
import com.ss.utopia.dao.BookingUserDAO;
import com.ss.utopia.dao.FlightBookingDAO;
import com.ss.utopia.dao.FlightDAO;
import com.ss.utopia.dao.PassengerDAO;
import com.ss.utopia.dao.RouteDAO;
import com.ss.utopia.dao.UserDAO;
import com.ss.utopia.dao.UserRoleDAO;
import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.BookingGuest;
import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.entity.BookingUser;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.entity.Route;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;

public class AdminServices<T> {

	public enum Service {
		AIRPORT, ROUTE, FLIGHT, BOOKING, PASSENGER, EMPLOYEE, USER, AIRPLANE, AIRPLANETYPE, ROLE, BOOKING_USER,
		BOOKING_GUEST, BOOKING_PAYMENT, FLIGHT_BOOKING
	}

	Random random = new Random();

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
				List<Integer> keys = new ArrayList<>();
				for (Flight f : fdao.readFlight()) {
					keys.add(f.getId());
				}
				int key = 1;
				while (keys.contains(key)) {
					key = random.nextInt(100);
				}
				Flight flight = (Flight) obj;
				flight.setId(key);
				fdao.addFlight(flight);

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
				BookingAgentDAO badao = new BookingAgentDAO(conn);
				badao.addBookingAgent((BookingAgent) obj);
				break;
			case USER:
				UserDAO udao = new UserDAO(conn);
				udao.addUser((User) obj);
				break;
			case BOOKING_USER:
				BookingUserDAO budao = new BookingUserDAO(conn);
				budao.addBookingUser((BookingUser)obj);
				break;
			case BOOKING_GUEST:
				BookingGuestDAO bgdao = new BookingGuestDAO(conn);
				bgdao.addBookingGuest((BookingGuest)obj);
				break;
			case BOOKING_PAYMENT:
				BookingPaymentDAO bydao = new BookingPaymentDAO(conn);
				bydao.addBookingPayment((BookingPayment) obj);
				break;
			case FLIGHT_BOOKING:
				FlightBookingDAO fbdao = new FlightBookingDAO(conn);
				fbdao.addFlightBooking((FlightBooking)obj);
				break;
			default:
				break;
			}
			conn.commit();
			return "Added " + serv + " successfully";
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
			e.printStackTrace();
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
					for (User u : udao.readUserByRole((UserRole) optional)) {
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
				if(obj instanceof String) {
					String s = (String)obj;
					adao.updateAirportCode(s.substring(0, s.indexOf(".")), s.substring(s.indexOf(".")+1));
				}
				else {
				adao.updateAirport((Airport) obj);
				}
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
			case BOOKING_PAYMENT:
				BookingPaymentDAO payment = new BookingPaymentDAO(conn);
				payment.updateBookingPayment((BookingPayment)obj);
				break;
			case AIRPLANETYPE:
				AirplaneTypeDAO apdao = new AirplaneTypeDAO(conn);
				apdao.updateAirplaneType((AirplaneType)obj);
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
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
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

	public List<T> create(Object[] optional, Service serv, ConnectionUtil connUtil) {
		Connection conn = null;
		List list = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			switch (serv) {
			case AIRPORT:
				AirportDAO adao = new AirportDAO(conn);
				list = adao.readAirports();
				break;
			case AIRPLANE:
				AirplaneDAO apdao = new AirplaneDAO(conn);
				if(optional == null) {
				list = apdao.readAirplane();
				}
				else {
				list = apdao.readAirplanesByType((AirplaneType) optional[0]);
				}
				break;
			case AIRPLANETYPE:
				AirplaneTypeDAO aptdao = new AirplaneTypeDAO(conn);
				list = aptdao.readAirplaneType();
				break;
			case ROUTE:
				RouteDAO rdao = new RouteDAO(conn);
				if (optional == null) {
					list = rdao.readRoutes();
				} else {
					list = rdao.readRouteByAirports((Airport) optional[0], (Airport) optional[1]);
				}
				break;
			case FLIGHT:
				FlightDAO fdao = new FlightDAO(conn);
				list = fdao.readFlight();
				break;
			case USER:
				UserDAO udao = new UserDAO(conn);
				if(optional != null) {
					list = udao.readUserByRole((UserRole)optional[0]);
				}
				else {
				list = udao.readUsers();
				}
				break;
			case BOOKING:
				BookDAO bdao = new BookDAO(conn);

				if(optional == null) {
				list = bdao.readBookings();
				}
				else {
				Integer key = bdao.addBook((Book)optional[0]);
				list.add(key);
				}
				break;
			case ROLE:
				UserRoleDAO urdao = new UserRoleDAO(conn);
				list = urdao.readUserRole();
				break;
			case PASSENGER:
				PassengerDAO pdao = new PassengerDAO(conn);
				list = pdao.readPassengers();
				break;
			case BOOKING_USER:
				BookingUserDAO budao = new BookingUserDAO(conn);
				list = budao.readBookingUser();
				break;
			
			case BOOKING_PAYMENT:
				BookingPaymentDAO payment = new BookingPaymentDAO(conn);
				list =payment.readBookingPayment();
				break;
			case FLIGHT_BOOKING:
				FlightBookingDAO fbdao = new FlightBookingDAO(conn);
				list = fbdao.readFlightBooking();
				break;
			}
			conn.commit();

			return list;
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
			e.printStackTrace();
			System.out.println("Something went wrong");
			return null;
		}
	}	

}
