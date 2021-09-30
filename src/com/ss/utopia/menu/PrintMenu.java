package com.ss.utopia.menu;

import java.util.List;
import java.util.Scanner;

public class PrintMenu {

	Menu menu = new Menu();
	Scanner scan = new Scanner(System.in);

	public Integer printMenu(List<String> list, Boolean hasPrev) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i+1 + ") " + list.get(i));
		}
		if (hasPrev) {
			System.out.println(list.size()+ 1 + ") Go Back");
		}
		
		
		Integer choice = readNextInt(1, list.size()+1);
		if(choice == list.size()+1) { //account for 1 indexed formatting
			return -1;
		}
		return choice-1;

	}

	private Integer readNextInt(Integer min, Integer max) {
		String s = null;
		Integer i;
		try {
					
			s = scan.nextLine();
			i = Integer.parseInt(s);
			if(i < min || i>max) {
				
				if(max != 1) {
					System.out.println("Choose a number between "+min+" and " +max);
					}
					else {
						System.out.println("Please press (1) to go back or 'q' to quit");
					}
				
				return readNextInt(min, max);
			}
			
			return i;
			
		} catch (NumberFormatException e) {
			if ("q".equals(s) || "Q".equals(s)) {
				System.exit(0);
				return null;
			}
			return readNextInt(min, max);
		}
	}
}
