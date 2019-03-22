package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import transit.*;

public class searchTest {
	
	Routes routes;
	Trips trips;
	StopTimes stopTimes;
	Stops stops;

	@Before
	public void setUp() throws Exception {
		routes = new Routes(new ArrayList<Route>());
		trips = new Trips(new ArrayList<Trip>());
		stopTimes = new StopTimes(new ArrayList<StopTime>());
		stops = new Stops(new ArrayList<Stop>());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRoutesWithStopValid() {
		File routesFile = new File("GTFS_EauClaire/EC_RT.txt");
		File tripsFile = new File("GTFS_EauClaire/EC_T.txt");
		File stopTimesFile = new File("GTFS_EauClaire/EC_ST.txt");
		File stopsFile = new File("GTFS_EauClaire/EC_S.txt");
		
		//import the files into the data structure
		stopTimes.importFile(stopTimesFile);
		stops.importFile(stopsFile);
		trips = new Trips(stopTimes, new ArrayList<Trip>());
		trips.importFile(tripsFile);
		routes = new Routes(new ArrayList<Route>(),stops,trips,stopTimes);
		routes.importFile(routesFile);
		routes.updateRoutes();
		
		System.out.println(((Route)routes.getColOfRoutes().toArray()[0]).getStopsInRoute());
		System.out.println(routes.searchRoutesForStop("3F56F87E65").getColOfRoutes());
		
		//check for routes containing valid stop that exists
		assertTrue(routes.searchRoutesForStop("3F56F87E65").getColOfRoutes().size()>0);
	}
		
	@Test
	public void testRoutesWithStopStopDoesntExist(){
		File routesFile = new File("GTFS_EauClaire/EC_RT.txt");
		File tripsFile = new File("GTFS_EauClaire/EC_T.txt");
		File stopTimesFile = new File("GTFS_EauClaire/EC_ST.txt");
		File stopsFile = new File("GTFS_EauClaire/EC_S.txt");
		
		//import the files into the data structure
		stopTimes.importFile(stopTimesFile);
		stops.importFile(stopsFile);
		trips = new Trips(stopTimes, new ArrayList<Trip>());
		trips.importFile(tripsFile);
		routes = new Routes(new ArrayList<Route>(),stops,trips,stopTimes);
		routes.importFile(routesFile);
		routes.updateRoutes();
		
		System.out.println(((Route)routes.getColOfRoutes().toArray()[0]).getStopsInRoute());
		System.out.println(routes.searchRoutesForStop("3F56F87E65").getColOfRoutes());
		
		//check for routes containing valid stop that exists
		assertTrue(routes.searchRoutesForStop("THIS DOESNT EXIST").getColOfRoutes().size()==0);
	}
	
	
	@Test
	public void testNextTripOnRouteValid(){
		File routesFile = new File("GTFS_EauClaire/EC_RT.txt");
		File tripsFile = new File("GTFS_EauClaire/EC_T.txt");
		File stopTimesFile = new File("GTFS_EauClaire/EC_ST.txt");
		File stopsFile = new File("GTFS_EauClaire/EC_S.txt");
		Controller controller = new Controller();
		controller.initialize();
		
		//import the files into the data structure
		controller.importStopTimes(stopTimesFile);
		controller.importTrips(tripsFile);
		controller.importStops(stopsFile);
		controller.importRoutes(routesFile);
		controller.update();
	
		//check for next trip on route
		assertTrue(controller.searchForNextTrip("D837EE7A8")!=null);
	}
	
	@Test
	public void testNextTripOnRouteInvalidRoute(){
		File routesFile = new File("GTFS_EauClaire/EC_RT.txt");
		File tripsFile = new File("GTFS_EauClaire/EC_T.txt");
		File stopTimesFile = new File("GTFS_EauClaire/EC_ST.txt");
		File stopsFile = new File("GTFS_EauClaire/EC_S.txt");
		Controller controller = new Controller();
		controller.initialize();
		
		//import the files into the data structure
		controller.importStopTimes(stopTimesFile);
		controller.importTrips(tripsFile);
		controller.importStops(stopsFile);
		controller.importRoutes(routesFile);
		controller.update();
	
		//check for next trip on route
		assertTrue(controller.searchForNextTrip("THIS SHOULD NOT WORK")==null);
	}
}
