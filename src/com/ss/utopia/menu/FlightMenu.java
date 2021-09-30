/**
 * 
 */
package com.ss.utopia.menu;

import java.util.Arrays;

import com.ss.utopia.service.CreateFlight;
import com.ss.utopia.service.DeleteFlight;

/**
 * @author Walter Chang
 *
 */
public class FlightMenu extends StartMenu{
	
	AdminMenu amenu = new AdminMenu();
	private String operation = "Add/Update/Delete/Read ";

	
	public void displayMenu() {
		
		
		Integer choice = getPrintMenu().printMenu(Arrays.asList(operation.split("/")), Boolean.TRUE);
		if(choice == -1) {
			amenu.displayMenu();
			return;
		}
		
		switch(choice) {
		
		case 0:
			CreateFlight create_flight = new CreateFlight();
			create_flight.createFlight();
			break;
		case 3:
			DeleteFlight delete_flight = new DeleteFlight();
			delete_flight.deleteFlight();
			
		
		
		}
		
	}
	
	
}
