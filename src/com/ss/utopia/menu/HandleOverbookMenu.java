package com.ss.utopia.menu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ss.utopia.entity.Passenger;
import com.ss.utopia.service.AdminPassenger;
import com.ss.utopia.service.HandleOverbook;

public class HandleOverbookMenu {

	PrintMenu pmenu = new PrintMenu();
	Menu menu = new Menu();
	AdminPassenger admin_p = new AdminPassenger();

	List<List<String>> overbooked = new ArrayList<>();
	Set<String> unique_flights = new HashSet<>();

	private final String overbookedflights = "Below are flights that are overbooked";
	
	public void handleOverbook() {

		HandleOverbook handleoverbook = new HandleOverbook();
		overbooked = handleoverbook.callStatement();

		
		if (overbooked == null || overbooked.size() == 0) {
			System.out.println("No Overbooked Flights");
			return;
		}

		for (List<String> list : overbooked) {
			if (!unique_flights.contains(list.get(0))) { // flight index
				unique_flights.add(list.get(0));
			}
		}
		

		flightMenu();

	}

	public void flightMenu() {

		List<String> formatted_flights = new ArrayList<>();
		List<String> flight_id_order = new ArrayList<>();

		System.out.println(overbookedflights);
		
		/* Go through unique flight id's and acquire reserved seats and max capacity */
		for (String flight_id : unique_flights) {
			for (List<String> list : overbooked) {
				if (list.get(0).equals(flight_id)) {
					formatted_flights.add("Flight id: " + flight_id + " Reserved Seats: " + list.get(1)
							+ " Max Capacity: " + list.get(2));
					flight_id_order.add(flight_id);
					break;
				}
			}

		}

		Integer choice = pmenu.printMenu(formatted_flights, true);
		if (choice == -1) {
			menu.runProgram();
			return;
		}
		passengerMenu(flight_id_order.get(choice));

	}

	public void passengerMenu(String flight_id) {

		List<String> formatted_passengers = new ArrayList<>();
		List<String> passenger_id_order = new ArrayList<>();

		for (List<String> list : overbooked) {
			if(list.get(0).equals(flight_id)) {
			formatted_passengers.add("Passenger Id: " + list.get(3) + " First Name: "
					+ list.get(4) + " Last Name: " + list.get(5));
			
			passenger_id_order.add(list.get(3));
			}
			
		}
		
		System.out.println("Passenger(s) in flight no. "+flight_id);
		Integer choice = pmenu.printMenu(formatted_passengers, true);
		if (choice == -1) {
			flightMenu();
			return;
		}
		
		Passenger passenger_to_delete = new Passenger();
		passenger_to_delete.setId(Integer.parseInt(passenger_id_order.get(choice)));
		System.out.println(admin_p.deletePassengerId(passenger_to_delete));
		handleOverbook();
	}

}
