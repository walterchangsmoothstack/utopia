/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.BookingGuest;
import com.ss.utopia.entity.BookingUser;

/**
 * @author Walter Chang
 *
 */
public class BookingUserDAO extends BaseDAO<BookingUser> {
	public BookingUserDAO(Connection conn) {
		super(conn);
	}

	public Integer addBookingUser(BookingUser user) throws SQLException, ClassNotFoundException {
		return savePK("INSERT INTO booking_user VALUES(?,?)", new Object[] { user.getBookingId(),
				user.getId()});
	}

	public List<BookingUser> readBookingUser()  throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM booking_user", null);
	}
	@Override
	protected List<BookingUser> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<BookingUser> list = new ArrayList<>();
		while(rs.next()) { 
			BookingUser user = new BookingUser();
			user.setBookingId(rs.getInt("booking_id")); 
			user.setId(rs.getInt("user_id"));
			list.add(user);
	}
		return list;
	}
}
