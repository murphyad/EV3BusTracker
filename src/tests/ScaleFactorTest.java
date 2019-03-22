package tests;


import java.io.File;

import org.junit.*;

import transit.Controller;

public class ScaleFactorTest {
	
	final double TRIP_LENGTH = 100.0;
	final double SCALE_FACTOR = 50.0; //some ratio
	final double EXPECTED =  TRIP_LENGTH/SCALE_FACTOR;
	

	final String TEST_TRIP = "1116B94971_R01_WKDY"; // God I hope this is right
	final double DISTANCE = 1502.59; // feet, 457.99 meters
	
	private Controller cont = new Controller();
	
	@Before // TODO: change to provided test files
	public void preload() {
		cont.initialize();
		System.out.println(cont.importStops(new File("stops.txt")));
		System.out.println(cont.importStopTimes(new File("stop_times.txt")));
		System.out.println(cont.importTrips(new File("trips.txt")));
		System.out.println(cont.importRoutes(new File("route.txt")));
		System.out.println(cont.importStops(new File("EC_S.txt")));
		System.out.println(cont.importStopTimes(new File("EC_ST.txt")));
		System.out.println(cont.importTrips(new File("EC_T.txt")));
		System.out.println(cont.importRoutes(new File("EC_RT.txt")));
	}
	
	@Test
	public void testScaleTrip() {
		cont.setScale(SCALE_FACTOR);
		double actual =  cont.scaleTrip(TRIP_LENGTH);
		assert(actual == EXPECTED);
	}
	
	@Test
	public void testDistanceOfTrip() {
		double actual = cont.distanceOfTrip(TEST_TRIP);
//		System.out.print(actual);
		assert(actual == DISTANCE);
	}
}
