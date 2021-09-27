package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO<T> {

	protected static Connection conn = null;

	public BaseDAO(Connection conn) {
		this.conn = conn;
	}
	

	protected void save(String statement, Object[] params) throws SQLException, ClassNotFoundException {
		PreparedStatement pstmt = conn.prepareStatement(statement);
		int c = 1;
		if (params != null) {
			for (Object o : params) {
				pstmt.setObject(c++, o);
			}
		}
		pstmt.execute();
	}
	
	protected Integer savePK(String statement, Object[] params) throws SQLException, ClassNotFoundException {
		PreparedStatement pstmt = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
		if (params != null) {
			int c = 1;
			for (Object o : params) {
				pstmt.setObject(c++, o);
			}
		}
		pstmt.execute();

		ResultSet rs = pstmt.getGeneratedKeys();
		while(rs.next()) {
			return rs.getInt(1); //check if this is 0 or 1;

		}

		return null;
	}
	protected List<T> read(String statement, Object[] params) throws SQLException, ClassNotFoundException{
		PreparedStatement pstmt = conn.prepareStatement(statement);
		int c = 1;
		if (params != null) {
			for (Object o : params) {
				pstmt.setObject(c++, o);
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
		
	}
	


	 abstract protected List<T> extractData(ResultSet rs) throws SQLException, ClassNotFoundException;
	
}
