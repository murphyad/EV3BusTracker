package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import transit.*;

public class TripsTest {
	
	Trips trips;

	@Before
	public void setUp() throws Exception {
		trips = new Trips(new ArrayList<Trip>());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidInput(){
		File file = new File("src/tests/tripsValid.txt");
		trips.importFile(file);
		assertTrue(trips.searchTripId("1116B94971_R01_WKDY")!=null);
		assertTrue(trips.searchTripId("20A546C35C_R01_SAT")!=null);
	}
	
	@Test
	public void testInvalidHeader(){
		File file = new File("src/tests/tripsInvalidHeader.txt");
		trips.importFile(file);
		assertEquals(trips.searchTripId("1116B94971_R01_WKDY"),null);
	}
	
	@Test
	public void testMissingHeader(){
		File file = new File("src/tests/tripsMissingHeader.txt");
		trips.importFile(file);
		assertEquals(trips.searchTripId("1116B94971_R01_WKDY"),null);
	}
	
	@Test
	public void testMissingRouteID(){
		File file = new File("src/tests/tripsMissingRouteID.txt");
		trips.importFile(file);
		assertEquals(trips.getColOfTrips().size(),0);
	}
	
	@Test
	public void testMissingServiceID(){
		File file = new File("src/tests/tripsMissingServiceID.txt");
		trips.importFile(file);
		assertEquals(trips.getColOfTrips().size(),0);
	}
	
	@Test
	public void testMissingTripID(){
		File file = new File("src/tests/tripsMissingTripID.txt");
		trips.importFile(file);
		assertEquals(trips.getColOfTrips().size(),0);
	}
	
	@Test
	public void testValidExport(){
		File in = new File("src/tests/tripsValid.txt");
		assertTrue(trips.importFile(in));
		File out = new File("src/tests/tripsExport.txt");
		trips.export(out);
		assertTrue(trips.importFile(out));
	}
	
}
