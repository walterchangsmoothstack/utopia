package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.BookingGuest;
import com.ss.utopia.entity.BookingPayment;

public class BookingPaymentDAO extends BaseDAO<BookingPayment> {
	public BookingPaymentDAO(Connection conn) {
		super(conn);
	}

	public Integer addBookingPayment(BookingPayment payment) throws SQLException, ClassNotFoundException {
		return savePK("INSERT INTO booking_payment VALUES(?,?,?)",
				new Object[] { payment.getId(), payment.getStripe_id(), payment.isRefunded() });
	}

	public void updateBookingPayment(BookingPayment payment) throws SQLException, ClassNotFoundException {
		save("UPDATE booking_payment SET stripe_id = ?, refunded=? WHERE booking_id = ?",
				new Object[] { payment.getStripe_id(), payment.isRefunded(), payment.getId() });
	}

	public List<BookingPayment> readBookingPayment() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM booking_payment  ", null);
	}

	@Override
	protected List<BookingPayment> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<BookingPayment> list = new ArrayList<>();
		while(rs.next()) { 
			BookingPayment user = new BookingPayment();
			user.setId(rs.getInt("booking_id")); 
			user.setStripe_id(rs.getString("stripe_id"));
			user.setRefunded(rs.getBoolean("refunded"));
			list.add(user);
	}
		return list;
	}
}
