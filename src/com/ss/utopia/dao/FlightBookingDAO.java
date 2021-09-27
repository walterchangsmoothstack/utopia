/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.entity.BookingUser;
import com.ss.utopia.entity.FlightBooking;

/**
 * @author Walter Chang
 *
 */
public class FlightBookingDAO extends BaseDAO<FlightBooking>{
	public FlightBookingDAO(Connection conn) {
		super(conn);
	}

	public Integer addFlightBooking(FlightBooking booking) throws SQLException, ClassNotFoundException {
		return savePK("INSERT INTO flight_bookings VALUES(?,?)", new Object[] { booking.getFlight_id(),
				booking.getBooking_id()});
	}
	public List<FlightBooking> readFlightBooking() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM flight_bookings", null);
	}

	@Override
	protected List<FlightBooking> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<FlightBooking> list = new ArrayList<>();
		while(rs.next()) { 
			FlightBooking user = new FlightBooking();
			user.setFlight_id(rs.getInt("flight_id")); 
			user.setBooking_id(rs.getInt("booking_id"));
			list.add(user);
	}
		return list;
	}
}
