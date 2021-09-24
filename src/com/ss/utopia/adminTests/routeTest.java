/**
 * 
 */
package com.ss.utopia.adminTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;
import com.ss.utopia.service.AdminAirport;
import com.ss.utopia.service.AdminRoute;

/**
 * @author Walter Chang
 * NOTES: Delete is always successful (?) 
 * 		  Watch out for null values
 * 		  Keep in mind what to do with Generated Keys
 */
public class routeTest {
	AdminRoute admin = new AdminRoute();
	Route route = new Route();

	Airport airport2 = new Airport();
	
	@Test
	public void testAddRoute() {
		
//		route.setOriginAirport("LGA");
//		route.setDestinationAirport("MIA");
//		assertEquals("Added ROUTE successfully", admin.addRoute(route));
//		route.setOriginAirport("LGA");
//		route.setDestinationAirport("MIA");
//		assertNotEquals("Added ROUTE successfully", admin.addRoute(route));
		

	}
	
	@Test
	public void testAddRouteNull() {
		route.setOriginAirport(null);
		route.setDestinationAirport(null);
		assertNotEquals("Added ROUTE successfully", admin.addRoute(route));
	}
	
	@Test
	public void testAddSameDest() {
		
		route.setDestinationAirport("DEN");
		route.setOriginAirport("DEN");
		assertEquals("Unable to add ROUTE", admin.addRoute(route));
	}
	
	@Test
	public void testDeleteRouteFK() {
		assertEquals("Deleted ROUTE successfully", admin.deleteRouteFK("DEN", "JFK"));
	}
	
	@Test
	public void testDeleteRouteNullFK() {
		System.out.println(admin.deleteRouteFK(null, null));
		assertEquals("Deleted ROUTE successfully", admin.deleteRouteFK(null, null));
	}
	
	@Test
	public void testDeleteRouteId() {
		route.setRouteId(25);
		//assertEquals("Deleted ROUTE successfully", admin.deleteRouteId(route.getRouteId()));
	}
	
	@Test
	public void testReadRoutes() {
	//	System.out.println(admin.readRoutes());
		System.out.println(admin.readRoutesByAirportId(null));
	}
	
	@Test
	public void testUpdateRouteFalse() {
	//	System.out.println(admin.readRoutes());
		route.setOriginAirport("JFK");
		route.setRouteId(51);
		route.setDestinationAirport("JFK");
		System.out.println(admin.updateRoute(route));
		assertEquals("Failed to update ROUTE", admin.updateRoute(route));
		System.out.println(admin.updateRoute(null));

	}

}
