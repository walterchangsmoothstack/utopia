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
import com.ss.utopia.entity.UserRole;

/**
 * @author Walter Chang
 *
 */
public class UserRoleDAO extends BaseDAO<UserRole>{
	public UserRoleDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void addUserRole(UserRole role) throws SQLException, ClassNotFoundException {
		save("INSERT INTO user_role VALUES(?,?)", 
				new Object[] {role.getId(), role.getName()});
	
	}
	public void updateUserRole(UserRole role) throws SQLException, ClassNotFoundException {
		save("UPDATE user_role SET name = ? WHERE id = ?", 
				new Object[] {role.getName(), role.getId()});
	}
	
	public void deleteUserRole(UserRole role) throws SQLException, ClassNotFoundException {
		save("DELETE FROM user_role WHERE id = ?",
				new Object[] {role.getId()});
	}
	
	public List<UserRole> readUserRole() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user_role", null);
	}
	public List<UserRole> readUserRoleById(Integer id) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user_role WHERE id = ?", new Object[] {id});
	}
	
	protected List<UserRole> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<UserRole> userRoleList = new ArrayList<>();
		while(rs.next()) { 
			UserRole role = new UserRole();
			role.setId(rs.getInt("id")); 
			role.setName(rs.getString("name"));
			userRoleList.add(role);
	}
		return userRoleList;
	}
}
