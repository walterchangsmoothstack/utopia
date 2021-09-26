/**
 * 
 */
package com.ss.utopia.menu;

import java.sql.SQLException;
import java.util.Scanner;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.service.AdminAirport;
import com.ss.utopia.service.AdminFlight;

/**
 * @author Walter Chang
 *
 */
public class CommandLineMenu {
	boolean quit;
	private final String [] operations = new String[]{"Add", "Read", "Update", "Delete"};
	private final String [] categories = new String[] {"Flights", "Seats", "Tickets and Passengers", "Airports",
			"Travelers", "Employees", "Over-ride Trip Cancellation", "Quit"};

	public void openingPrompt() {
		System.out.println("What would you like to do?");
		for(int i =0; i<operations.length; i++) {
			System.out.println(Integer.toString(i+1)+") "+ operations[i]);
		}
	}
	public void categoriesPrompt(int op) {
		System.out.println("What would you like to "+ operations[op-1]+"?");
		for(int i =0; i<categories.length; i++) {
			System.out.println(Integer.toString(i+1)+") "+ categories[i]);
		}
	}
	
	public void chooseCategory(int op) {
		//while(!quit) {
			categoriesPrompt(op);
			int cat = readInput(1, categories.length);
			if(cat == categories.length) {
				runProgram();
				return;
			}
			switch(op) {
			case 1:
			add(cat);
			break;
			default:
			break;
			}
		//}
		
		
	}
	public void runProgram() {
		//while(!quit) {
			openingPrompt();
			int input = readInput(1, operations.length);
			chooseCategory(input);
		//}
	}
	
	public int readInput(Integer min, Integer max) {
		Scanner scan = new Scanner(System.in);
		int input =0;
		while(input < min || input > max) {
			try {
				input = Integer.parseInt(scan.nextLine());
			}catch (NumberFormatException e) {
				
			}
			finally {
				if(input < min || input > max) {
					System.out.println("Please pick a number between "+Integer.toString(min)
					+" and "+Integer.toString(max));
				}
			}
			
			
		}
		return input;
	}
	
	public void add(int category) {
		switch(category) {
		case 1:
			addFlight();
		
		}
			
	}
	
	public void addFlight() {
		AdminFlight admin = new AdminFlight();
		System.out.println("Add Flights");
		System.out.println(admin.readFlight());
	
	}
	
	
	public static void main(String[] args){
		CommandLineMenu menu = new CommandLineMenu();
		menu.runProgram();
	}

}
