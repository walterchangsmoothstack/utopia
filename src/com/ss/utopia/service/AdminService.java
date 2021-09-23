/**
 * 
 */
package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.RouteDAO;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;

/**
 * @author Walter Chang
 *
 */
public class AdminService {
	ConnectionUtil connection = new ConnectionUtil();

	public String addFlight(Airport airport, Route route) {
		Connection conn = null;
		try {
			conn = connection.getConnection();
			RouteDAO rdao = new RouteDAO(conn);
			AirportDAO adao = new AirportDAO(conn);
			
			
			conn.commit();
			return "Flight added successfully";
		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e_2) {

				}

			}
			return "Flight addition failed";
		} finally {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e) {

				}
			}
		}

	}
	
	public String addAirport(Airport airport) {
		Connection conn = null;
		
		try {
			conn = connection.getConnection();
			AirportDAO adao = new AirportDAO(conn);
			adao.addAirport(airport);
			conn.commit();
			return "New airport " + airport.getAirportId() +
					"added at " +airport.getCity();
		} catch (ClassNotFoundException | SQLException e) {
			
			if(conn != null) {
				try {
					conn.rollback();
				} catch(Exception e_2) {
					
				}
			}
			e.printStackTrace();
			return "Airport addition failed";
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch(Exception e) {
					
				}
			}
			
		}
		
		
	}
	
	
	
}
