package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.RouteDAO;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;

public class AdminServices<T> {

	public enum Service {
		AIRPORT, ROUTE, FLIGHT,
	}

	public String add(T obj, Service serv, ConnectionUtil connUtil) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			switch (serv) {
			case AIRPORT:
				AirportDAO adao = new AirportDAO(conn);
				adao.addAirport((Airport) obj);
				break;
			case ROUTE:
				RouteDAO rdao = new RouteDAO(conn);
				Integer pk = rdao.addRoute((Route) obj);
			case FLIGHT:
				break;
			default:
				break;
			}
			conn.commit();
			return "Added " + serv + " successfully";
		} catch (ClassNotFoundException | SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return "Unable to add " + serv;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String read(T optional, Service serv, ConnectionUtil connUtil) {
		Connection conn = null;
		StringBuilder information = new StringBuilder();
		try {
			conn = connUtil.getConnection();
			switch (serv) {
			case AIRPORT:
				AirportDAO adao = new AirportDAO(conn);
				if (optional != null) {
					for (Airport a : adao.readAirportsInCity((String) optional)) {
						information.append(
								"Name: " + a.getAirportId() + " City: " + a.getCity() + "\n -----------------\n");
					}
				} else {
					for (Airport a : adao.readAirports()) {
						information.append(
								"Name: " + a.getAirportId() + " City: " + a.getCity() + "\n -----------------\n");
					}
				}
				break;
			case ROUTE:
				RouteDAO rdao = new RouteDAO(conn);
				if (optional != null) {
					for (Route r : rdao.readRoutesByAirportId((String) optional)) {
						information.append("Id: " + r.getRouteId() + " Origin: " + r.getOriginAirport()
								+ " Destination: " + r.getDestinationAirport() + "\n -----------------\n");

					}
					break;
				}
				for (Route r : rdao.readRoutes()) {
					information.append("Id: " + r.getRouteId() + " Origin: " + r.getOriginAirport() + " Destination: "
							+ r.getDestinationAirport() + "\n -----------------\n");

				}
			case FLIGHT:
				break;
			default:
				break;
			}
			conn.commit();
			return (information.length() == 0) ? "No " +serv+ " found" : information.toString();
		} catch (ClassNotFoundException | SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return "Unable to read " + serv;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String update(T obj, Service serv, ConnectionUtil connUtil) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			switch (serv) {
			case AIRPORT:
				AirportDAO adao = new AirportDAO(conn);
				adao.updateAirport((Airport) obj);
				break;
			case ROUTE:
				RouteDAO rdao = new RouteDAO(conn);
				rdao.updateRoute((Route)obj);
				break;
			case FLIGHT:
				break;
			default:
				break;
			}
			conn.commit();
			return "Updated " + serv + " successfully";
		} catch (ClassNotFoundException | SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return "Unable to update " + serv;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String delete(Object[] objects, Service serv, ConnectionUtil connUtil) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			switch (serv) {
			case AIRPORT:
				AirportDAO adao = new AirportDAO(conn);
				adao.deleteAirport((String) objects[0]);
				break;
			case ROUTE:
				RouteDAO rdao = new RouteDAO(conn);
				if (objects.length == 2) {
					rdao.deleteRouteFK((String) objects[0], (String) objects[1]);
				} else {
					rdao.deleteRoute((Integer) objects[0]);
				}
				break;
			case FLIGHT:
				break;
			default:
				break;
			}
			conn.commit();
			return "Deleted " + serv + " successfully";
		} catch (ClassNotFoundException | SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return "Unable to delete " + serv;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
