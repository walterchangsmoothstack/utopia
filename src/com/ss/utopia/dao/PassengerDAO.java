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
import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.Book;
import com.ss.utopia.entity.Passenger;

/**
 * @author Walter Chang
 *
 */
public class PassengerDAO extends BaseDAO<Passenger>{

	public PassengerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public Integer addPassenger(Passenger passenger) throws SQLException, ClassNotFoundException {
		return savePK("INSERT INTO passenger (booking_id, given_name, family_name, dob, gender, address) VALUES(?,?,?,?,?,?)", 
				new Object[] {passenger.getBookId().getId(), passenger.getGivenName(), passenger.getFamilyName(),
						passenger.getDob(), passenger.getGender(), passenger.getAddress()});
	
	}
	public void updatePassenger(Passenger passenger) throws SQLException, ClassNotFoundException {
		save("UPDATE passenger SET booking_id = ?, given_name = ?, family_name =?, "
				+ "dob =?, gender =?, address =? WHERE id = ?", 
				new Object[] {passenger.getBookId().getId(), passenger.getGivenName(), passenger.getFamilyName(),
						passenger.getDob(), passenger.getGender(), passenger.getAddress(), passenger.getId()});
	}
	
	public void deletePassengerId(Passenger passenger) throws SQLException, ClassNotFoundException {
		save("DELETE FROM passenger WHERE id = ?",
				new Object[] {passenger.getId()});
	}
	public void deletePassengerBookingId(Book book) throws SQLException, ClassNotFoundException {
		save("DELETE FROM passenger WHERE booking_id = ?",
				new Object[] {book.getId()});
	}

	
	public List<Passenger> readPassengers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM passenger", null);
	}
	public List<Passenger> readPassengerName(Passenger passenger) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM passenger WHERE given_name= ? AND family_name =?", 
				new Object[] {passenger.getGivenName(), passenger.getFamilyName()});
	}
//	public List<Passenger> readPassengerBooking(Passenger passenger) throws ClassNotFoundException, SQLException {
//		return read("SELECT * FROM passenger WHERE booking_id= ?", 
//				new Object[] {passenger.getBookId()});
//	}
	
	protected List<Passenger> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Passenger> passengerList = new ArrayList<>();
		while(rs.next()) { 
			Passenger passenger = new Passenger(rs.getInt("id"), new Book(rs.getInt("booking_id"), null, null), rs.getString("given_name"),
					rs.getString("family_name"), rs.getDate("dob").toLocalDate(), rs.getString("gender"), rs.getString("address"));
			passengerList.add(passenger);
	}
		return passengerList;
	}
}
