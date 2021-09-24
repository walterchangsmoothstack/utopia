/**
 * 
 */
package com.ss.utopia.menu;

import java.sql.SQLException;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.service.AdminAirport;

/**
 * @author Walter Chang
 *
 */
public class CommandLineMenu {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
		AdminAirport admin = new AdminAirport();
		Airport airport = new Airport();
		airport.setAirportId("FTY");
		airport.setCity("Atlanta");
		admin.addAirport(airport);
		admin.readAirports();
	}

}
