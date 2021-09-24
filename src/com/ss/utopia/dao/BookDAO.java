/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;

/**
 * @author Walter Chang
 *
 */
public class BookDAO extends BaseDAO<Book> {
	public BookDAO(Connection conn) {
		super(conn);
	}

	public Integer addBook(Book book) throws SQLException, ClassNotFoundException {

		return savePK("INSERT INTO booking (confirmation_code, is_active) VALUES(?,?)", new Object[] { book.getConfirmationCode(),
				book.getIsActive() });
	}

	public void updateBook(Book book) throws SQLException, ClassNotFoundException {
		save("UPDATE booking SET confirmation_code = ? , is_active = ? WHERE id = ?",
				new Object[] { book.getConfirmationCode(), book.getIsActive(), book.getId() });
	}

	public void deleteBook(Integer id) throws SQLException, ClassNotFoundException {
		save("DELETE FROM booking WHERE id = ?", new Object[] { id });
	}

	public List<Book> readBookings() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking", null);
	}

	public List<Book> readActiveBookings(Boolean bool) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking WHERE is_active = ?", new Object[] { bool });
	}

	protected List<Book> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Book> bookList = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setIsActive(rs.getBoolean("is_active"));
			book.setConfirmationCode(rs.getString("confirmation_code"));
			bookList.add(book);
		}
		return bookList;
	}
}
