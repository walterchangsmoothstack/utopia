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
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;

/**
 * @author Walter Chang
 * TO-DO: Encrypt user password
 */
public class UserDAO extends BaseDAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public Integer addUser(User user) throws SQLException, ClassNotFoundException {
		return savePK(
				"INSERT INTO user (role_id, given_name, family_name, username, email, password, phone) VALUES(?,?,?,?,?,?,?)",
				new Object[] { user.getRole().getId(), user.getGivenName(), user.getFamilyName(), user.getUsername(),
						user.getEmail(), user.getPassword(), user.getPhone() });

	}

	public void updateUser(User user) throws SQLException, ClassNotFoundException {
		save("UPDATE user SET role_id=?, given_name=?, family_name=?, username=?, email=?, password=?, phone=? WHERE id = ?",
				new Object[] { user.getRole().getId(), user.getGivenName(), user.getFamilyName(), user.getUsername(),
						user.getEmail(), user.getPassword(), user.getPhone(), user.getId() });
	}

	public void deleteUser(User user) throws SQLException, ClassNotFoundException {
		save("DELETE FROM user WHERE id = ?", new Object[] { user.getId() });
	}

	public List<User> readUsers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user", null);
	}

	public List<User> readUserByRole(UserRole role) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user WHERE role_id=?",
				new Object[] { role.getId() });
	}
//	public List<Passenger> readPassengerBooking(Passenger passenger) throws ClassNotFoundException, SQLException {
//		return read("SELECT * FROM passenger WHERE booking_id= ?", 
//				new Object[] {passenger.getBookId()});
//	}

	protected List<User> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<User> userList = new ArrayList<>();
		while (rs.next()) {
			User user = new User(rs.getInt("id"), new UserRole(rs.getInt("role_id"), null),
					rs.getString("given_name"), rs.getString("family_name"), rs.getString("username"), rs.getString("email"),
					rs.getString("password"), rs.getString("phone"));
			userList.add(user);
		}
		return userList;
	}
}
