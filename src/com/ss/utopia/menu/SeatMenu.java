package com.ss.utopia.menu;

import java.util.List;

import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.service.AdminFlight;

public class SeatMenu extends Menu{
	AdminFlight admin = new AdminFlight();
	
	/* Largely similar to update seats. Call helper method to return airplanetype to change*/
	public void addSeats() {
		AirplaneType airplaneType = addOrUpdate();
		System.out.println("Enter how many seats to add: ");
		int choice = readInput(1, 1000);
		airplaneType.setMaxCapacity(airplaneType.getMaxCapacity()+choice);
		System.out.println(admin.updateAirplaneType(airplaneType));
		System.out.println("1) Okay");
		if(readInput(1, 1) == 1) {
			chooseCategory(1);
		}
		
	}
	/* Difference is update as opposed to add (trivial)*/
	public void updateSeats() {
		AirplaneType airplaneType = addOrUpdate();

		System.out.println("Enter new number of seats: ");
		int choice = readInput(1, 1000);
		airplaneType.setMaxCapacity(choice);
		System.out.println(admin.updateAirplaneType(airplaneType));
		System.out.println("1) Okay");
		if(readInput(1, 1) == 1) {
			chooseCategory(1);
		}
	}
	
	/* Helps select an airplanetype from possible airplanetypes*/
	public AirplaneType addOrUpdate() {
		List<AirplaneType> airplaneTypes = admin.createAirplaneTypeList();
		
		int index = 1;
		for(AirplaneType apt : airplaneTypes) {
		System.out.println((index++)+") Airplane Type: " +apt.getAirplaneTypeId());
		System.out.println("Max Capacity: "+ apt.getMaxCapacity());
		}
		System.out.println(index+") Go Back");
		int choice = readInput(1, index+1);
		if(choice == 1) {
			chooseCategory(1);
		}
		AirplaneType airplaneType = airplaneTypes.get(choice-1);
		return airplaneType;
	}
	
	/* Not the same as delete passengers/tickets(?)*/
	public void deleteSeats() {
		
		
	}
	
	public void read() {
		//List<AirplaneType> airplaneTypes = admin.createAirplaneTypeList();
		//List<Flight>
		
	}
	
	
	
}
