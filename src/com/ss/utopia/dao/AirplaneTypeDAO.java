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
import com.ss.utopia.entity.Airport;

/**
 * @author Walter Chang
 *
 */
public class AirplaneTypeDAO extends BaseDAO<AirplaneType>{
	
	public AirplaneTypeDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void addAirplaneType(AirplaneType airplaneType) throws SQLException, ClassNotFoundException {
		save("INSERT INTO airplane_type VALUES(?,?)", 
				new Object[] {airplaneType.getAirplaneTypeId(), airplaneType.getMaxCapacity()});
	
	}
	public void updateAirplaneType(AirplaneType airplaneType) throws SQLException, ClassNotFoundException {
		save("UPDATE airplane_type SET max_capacity = ? WHERE id = ?", 
				new Object[] {airplaneType.getMaxCapacity(), airplaneType.getAirplaneTypeId()});
	}
	
	public void deleteAirplaneType(AirplaneType airplaneType) throws SQLException, ClassNotFoundException {
		save("DELETE FROM airplane_type WHERE id = ?",
				new Object[] {airplaneType.getAirplaneTypeId()});
	}
	
	public List<AirplaneType> readAirplaneType() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane_type", null);
	}
	public List<AirplaneType> readAirportsInCity(Integer maxCapacity) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane_type WHERE max_capacity = ?", new Object[] {maxCapacity});
	}
	
	protected List<AirplaneType> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<AirplaneType> airplaneTypeList = new ArrayList<>();
		while(rs.next()) { 
			AirplaneType airplaneType = new AirplaneType();
			airplaneType.setAirplaneTypeId(rs.getInt("id")); 
			airplaneType.setMaxCapacity(rs.getInt("max_capacity"));
			airplaneTypeList.add(airplaneType);
	}
		return airplaneTypeList;
	}
}
