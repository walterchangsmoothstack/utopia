/**
 * 
 */
package com.ss.utopia.menu;

import java.util.Arrays;

/**
 * @author Walter Chang
 *
 */
public class StartMenu {

	private final String prompt = "Welcome to the Utopia Airlines Management System. Which category of a user are you?";
	private String[] user_category = new String[] { "Employee/Agent", "Administrator", "Traveler" };
	


	public void displayMenu() {

		System.out.println(prompt);
		Integer choice = getPrintMenu().printMenu(Arrays.asList(user_category), Boolean.FALSE);
		
		if(choice == null) {
			System.out.println("GoodBye");
			return;
		}
		
		switch (choice) {

		case 0:
			break;
		case 1:
			AdminMenu amenu = new AdminMenu();
			amenu.displayMenu();
			break;
		case 2:
			break;

		}

	}
	
	
	public StartMenu getStartMenu() {
		return new StartMenu();
	}
	
	public PrintMenu getPrintMenu() {
		return new PrintMenu();
	}

	public static void main(String[] args) {

		StartMenu smenu = new StartMenu();
		
		smenu.displayMenu();
	}

}
