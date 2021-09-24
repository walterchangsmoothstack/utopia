/**
 * 
 */
package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.RouteDAO;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang
 *
 */
public class AdminAirport{
	
	ConnectionUtil connUtil = new ConnectionUtil();

	public String addAirport(Airport airport){
		AdminServices admin = new AdminServices();
		return admin.add(airport, Service.AIRPORT, connUtil);
	}
	
	public String readAirports() {
		
		AdminServices admin = new AdminServices();
		return admin.read(Service.AIRPORT, connUtil);
	}
	
	public String updateAirport(Airport airport) {
		AdminServices admin = new AdminServices();
		return admin.update(airport, Service.AIRPORT, connUtil);
	}
	
	public String deleteAirport(String airportId) {
		AdminServices admin = new AdminServices();
		return admin.delete(airportId, Service.AIRPORT, connUtil);
		
	}
	
	public String addRoute(Route route) {
		AdminServices admin = new AdminServices();
		return admin.add(route, Service.ROUTE, connUtil);
	}
	
//	public String addFlight(Airport airport, Route route) {
//		Connection conn = null;
//		try {
//			conn = connection.getConnection();
//			RouteDAO rdao = new RouteDAO(conn);
//			AirportDAO adao = new AirportDAO(conn);
//
//			conn.commit();
//			return "Flight added successfully";
//		} catch (Exception e) {
//			if (conn != null) {
//				try {
//					conn.rollback();
//				} catch (Exception e_2) {
//
//				}
//
//			}
//			return "Flight addition failed";
//		} finally {
//			if (conn != null) {
//				try {
//					conn.rollback();
//				} catch (Exception e) {
//
//				}
//			}
//		}
//
//	}
//
//	public String addAirport(Airport airport) {
//		Connection conn = null;
//
//		try {
//			conn = connection.getConnection();
//			AirportDAO adao = new AirportDAO(conn);
//			adao.addAirport(airport);
//			conn.commit();
//			return "New airport " + airport.getAirportId() + "added at " + airport.getCity();
//		} catch (ClassNotFoundException | SQLException e) {
//
//			if (conn != null) {
//				try {
//					conn.rollback();
//				} catch (Exception e_2) {
//
//				}
//			}
//			e.printStackTrace();
//			return "Airport addition failed";
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e) {
//
//				}
//			}
//
//		}
//
//	}
//	
	public void readAirportExample() {
		Connection conn = null;
		List<Airport> airports;
		try {
			conn = connUtil.getConnection();
			AirportDAO adao = new AirportDAO(conn);
			airports = adao.readAirports();
			
			for(Airport a : airports) {
				System.out.println("Name: " + a.getAirportId());
				System.out.println("City: " + a.getCity());
				System.out.println("------------------");
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e_2) {

				}
			}
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {

				}
			}

		}

	}

}
