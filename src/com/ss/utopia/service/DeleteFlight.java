package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.ss.utopia.dao.FlightDAO;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.menu.FlightMenu;
import com.ss.utopia.menu.PrintMenu;
import com.ss.utopia.service.AdminServices.Service;

public class DeleteFlight {
	
	Scanner scan = new Scanner(System.in);

	ConnectionUtil connUtil = new ConnectionUtil();
	
	AdminServices admin = new AdminServices();
	
	List<Flight> flights = admin.create(null, Service.FLIGHT, connUtil);
	List<String> formatter_menu = Arrays.asList("Enter a flight id to delete", "Choose a flight to delete");
	Set<Integer> id_set = new HashSet<>();
	
	PrintMenu pmenu = new PrintMenu();
	FlightMenu previous_menu = new FlightMenu();
	
	public void deleteFlight() {
		
		Integer choice = pmenu.printMenu(formatter_menu, Boolean.TRUE);
		if(choice == -1) {
			previous_menu.displayMenu();
			return;
		}
		
		Integer id;
		
		if(choice == 0) {
			id = deleteFromInput();
			
		}
		else {
			id = deleteFromList();
		}
		
		if(id == null) {
			previous_menu.displayMenu();
			return;
		}
		
		
		Connection conn = null;
		
		try {
			conn = connUtil.getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			fdao.deleteFlight(id);
			conn.commit();
			
			id_set.remove(id);
			
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		previous_menu.displayMenu();
		
	}
	
	public Integer deleteFromInput() {
			
		for(Flight f : flights) {
			id_set.add(f.getId());
		}
		
		System.out.println("Enter a flight no. to delete.");
		return getValidId(scan);	
		
	}
	
	public Integer deleteFromList() {
		List<String> formatter_flight = new ArrayList<>();
		System.out.println("Choose a flight to delete.");
		for(Flight f : flights) {
			formatter_flight.add(f.toString());
		}
		Integer choice = pmenu.printMenu(formatter_flight, Boolean.TRUE);
		
		if(choice == -1) {
			return null;
		}
		
		return flights.get(choice).getId();
			
		
	}
	
	
	
	
	
	public Integer getValidId(Scanner scan) {
		
		Integer id = -1;
		String s = "";
		while(true) {
			try {
				s = scan.nextLine();
				id = Integer.parseInt(s);
				if(!id_set.contains(id)) {
					System.out.println("Flight no. does not exist");
				}
				else {
					return id;
				}
				
				
			} catch(NumberFormatException e) {
				if("Q".equals(s.toUpperCase()) || "quit".equals(s)) {
					return null;
				}			
				System.out.println("Enter a valid integer OR \"q\" to quit.");
			}
			
		}
	}
	
	
	
}
