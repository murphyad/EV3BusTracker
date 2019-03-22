package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import transit.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StopsTest {
	
	Stops stops;

	@Before
	public void setUp() throws Exception {
		stops = new Stops(new ArrayList<Stop>());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValid(){
		File file = new File("src/tests/stopsValid.txt");
		assertTrue(stops.importFile(file));
		assertTrue(stops.searchStopId("3F56F87E65")!=null);
	}
	
	@Test
	public void testInvalidHeader(){
		File file = new File("src/tests/stopsInvalidHeader.txt");
		assertFalse(stops.importFile(file));
		assertEquals(stops.getColOfStops().size(),0);
	}
	
	@Test
	public void testMissingHeader(){
		File file = new File("src/tests/stopsMissingHeader.txt");
		assertFalse(stops.importFile(file));
		assertEquals(stops.getColOfStops().size(),0);
	}
	
	@Test
	public void testMissingStopID(){
		File file = new File("src/tests/stopsMissingStopID.txt");
		assertFalse(stops.importFile(file));
		assertEquals(stops.getColOfStops().size(),0);
	}
	
	@Test
	public void testMissingStopName(){
		File file = new File("src/tests/stopsMissingStopName.txt");
		assertFalse(stops.importFile(file));
		assertEquals(stops.getColOfStops().size(),0);
	}
	
	@Test
	public void testMissingLat(){
		File file = new File("src/tests/stopsMissingLat.txt");
		assertFalse(stops.importFile(file));
		assertEquals(stops.getColOfStops().size(),0);
	}
	
	@Test
	public void testMissingLon(){
		File file = new File("src/tests/stopsMissingLon.txt");
		assertFalse(stops.importFile(file));
		assertEquals(stops.getColOfStops().size(),0);
	}
	
	@Test
	public void testInvalidLat(){
		File file = new File("src/tests/stopsInvalidLat.txt");
		assertFalse(stops.importFile(file));
		assertEquals(stops.getColOfStops().size(),0);
	}
	
	@Test
	public void testInvalidLon(){
		File file = new File("src/tests/stopsInvalidLon.txt");
		assertFalse(stops.importFile(file));
		assertEquals(stops.getColOfStops().size(),0);
	}
	
	@Test
	public void testValidExport(){
		File in = new File("src/tests/stopsValid.txt");
		assertTrue(stops.importFile(in));
		File out = new File("src/tests/stopsExport.txt");
		stops.export(out);
		assertTrue(stops.importFile(out));
	}

}
