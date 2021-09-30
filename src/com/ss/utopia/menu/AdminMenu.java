package com.ss.utopia.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminMenu extends StartMenu{

	private String operation = "Add/Update/Delete/Read ";
	private String cancellation_string = "Over-ride Trip Cancellation for a ticket.";
	private String [] category = new String[] {"Flights", "Seats","Tickets and Passengers", "Airports", "Travelers","Employees"};
	
	
	private List<String> formatted_menu = new ArrayList<>();
	
	public void displayMenu() {
		
		for(String s : category) {
			formatted_menu.add(operation+s);
		}
		formatted_menu.add(cancellation_string);
		
		
		Integer choice = getPrintMenu().printMenu(formatted_menu, Boolean.TRUE);
		if(choice == -1) {
			getStartMenu().displayMenu();
			return;
		}
		
		switch(choice) {
		case 0:
			FlightMenu fmenu = new FlightMenu();
			fmenu.displayMenu();
			break;
	
		
		
		}
		
		
	}
	
	

	
}
