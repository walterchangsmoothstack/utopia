/**
 * 
 */
package com.ss.utopia.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
import com.ss.utopia.service.AdminPassenger;

/**
 * @author Walter Chang
 *
 */
public class EmployeeMenu extends StartMenu{

	private String [] options = new String[] {"Enter Flights You Manage."};
	
	
	public void displayMenu() {
		
		Integer choice = pmenu.printMenu(Arrays.asList(options), Boolean.TRUE);
		if(choice == -1) {
			smenu.displayMenu();
		}
		else {
			
		}
		
	}
	
	public Flight findFlight(Integer choice) {
		String s = "";
		while(true) {
			
		}
		
	}

}
