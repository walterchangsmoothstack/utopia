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

/**
 * @author Walter Chang
 *
 */
public class AirplaneDAO extends BaseDAO<Airplane> {
	public AirplaneDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void addAirplane(Airplane airplane) throws SQLException, ClassNotFoundException {
		save("INSERT INTO airplane VALUES(?,?)", 
				new Object[] {airplane.getAirplaneId(), airplane.getType().getAirplaneTypeId()});
	
	}
	public void updateAirplane(Airplane airplane) throws SQLException, ClassNotFoundException {
		save("UPDATE airplane SET type_id = ? WHERE id = ?", 
				new Object[] {airplane.getType().getAirplaneTypeId(), airplane.getAirplaneId()});
	}
	
	public void deleteAirplane(Airplane airplane) throws SQLException, ClassNotFoundException {
		save("DELETE FROM airplane WHERE id = ?",
				new Object[] {airplane.getAirplaneId()});
	}
	
	public List<Airplane> readAirplane() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane", null);
	}
	public List<Airplane> readAirplanesByType(AirplaneType type) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane WHERE type_id = ?", new Object[] {type.getAirplaneTypeId()});
	}
	
	protected List<Airplane> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Airplane> airplaneList = new ArrayList<>();
		while(rs.next()) { 
			Airplane airplane = new Airplane();
			airplane.setAirplaneId(rs.getInt("id")); 
			airplane.setType(new AirplaneType(rs.getInt("type_id"), null));
			airplaneList.add(airplane);
	}
		return airplaneList;
	}
}
