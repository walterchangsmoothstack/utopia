/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.BookingAgent;

/**
 * @author Walter Chang
 *
 */
public class BookingAgentDAO extends BaseDAO<BookingAgent> {
	public BookingAgentDAO(Connection conn) {
		super(conn);
	}

	public Integer addBookingAgent(BookingAgent agent) throws SQLException, ClassNotFoundException {
		System.out.println(agent.getBook().getId());
		return savePK("INSERT INTO booking_agent VALUES(?,?)", new Object[] { agent.getBook().getId(),
				agent.getId() });
	}

	public void updateBookingAgent(BookingAgent agent) throws SQLException, ClassNotFoundException {
		save("UPDATE booking_agent SET agent_id = ? WHERE booking_id = ?",
				new Object[] {agent.getId(), agent.getBook().getId() });
	}

	public void deleteBookingAgent(Book book) throws SQLException, ClassNotFoundException {
		save("DELETE FROM booking_agent WHERE booking_id = ?", new Object[] { book.getId() });
	}

	public List<BookingAgent> readBookings() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_agent", null);
	}

	public List<BookingAgent> readBookingByAgent(BookingAgent agent) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_agent WHERE agent_id = ?", new Object[] { agent.getId() });
	}

	protected List<BookingAgent> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookingAgent> bookingAgentList = new ArrayList<>();
		while (rs.next()) {
			BookingAgent agent = new BookingAgent();
			agent.setId(rs.getInt("agent_id"));
			agent.setBook(new Book(rs.getInt("booking_id"), null, null));
			bookingAgentList.add(agent);
		}
		return bookingAgentList;
	}
}
