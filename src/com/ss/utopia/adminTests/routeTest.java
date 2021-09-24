/**
 * 
 */
package com.ss.utopia.adminTests;

import org.junit.Test;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminAirport;

/**
 * @author Walter Chang
 *
 */
public class routeTest {
	AdminAirport admin = new AdminAirport();
	Airport airport1 = new Airport();
	Airport airport2 = new Airport();
	
	@Test
	public void testAddRoute() {
		Route route = new Route();
		airport1.setAirportId("LAX");
		airport2.setAirportId("JFK");
		route.setDestinationAirport(airport1);
		route.setOriginAirport(airport2);
		admin.addRoute(route);
	}

}
