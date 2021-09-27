package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.BookingGuest;
import com.ss.utopia.entity.BookingUser;

public class BookingGuestDAO extends BaseDAO<BookingGuest>{
	public BookingGuestDAO(Connection conn) {
		super(conn);
	}

	public Integer addBookingGuest(BookingGuest guest) throws SQLException, ClassNotFoundException {

		return savePK("INSERT INTO booking_guest VALUES(?,?,?)", new Object[] { guest.getId(),
				guest.getEmail(), guest.getPhone()});
	}
	
	public List<BookingGuest> readBookingGuest()  throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM booking_guest", null);
	}
	@Override
	protected List<BookingGuest> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<BookingGuest> list = new ArrayList<>();
		while(rs.next()) { 
			BookingGuest user = new BookingGuest();
			user.setId(rs.getInt("booking_id")); 
			user.setEmail(rs.getString("contact_email"));
			user.setPhone(rs.getString("contact_phone"));
			list.add(user);
	}
		return list;
	
	}
}
