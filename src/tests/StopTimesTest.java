package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import transit.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StopTimesTest {
	
	StopTimes stopTimes;

	@Before
	public void setUp() throws Exception {
		stopTimes = new StopTimes(new ArrayList<StopTime>());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidInput(){
		File file = new File("src/tests/stopTimesValid.txt");
		assertTrue(stopTimes.importFile(file));
		assertFalse(stopTimes.searchStopIdTimes("3F56F87E65")==null);
	}
	
	@Test
	public void testInvalidHeader(){
		File file = new File("src/tests/stopTimesInvalidHeader.txt");
		assertFalse(stopTimes.importFile(file));
		assertEquals(stopTimes.getColOfStopTimes().size(),0);
	}
	
	@Test
	public void testMissingHeader(){
		File file = new File("src/tests/stopTimesMissingHeader.txt");
		assertFalse(stopTimes.importFile(file));
		assertEquals(stopTimes.getColOfStopTimes().size(),0);
	}
	
	@Test
	public void testMissingTripID(){
		File file = new File("src/tests/stopTimesMissingTripID.txt");
		assertFalse(stopTimes.importFile(file));
		assertEquals(stopTimes.getColOfStopTimes().size(),0);
	}
	
	@Test
	public void testMissingArrivalTime(){
		File file = new File("src/tests/stopTimesMissingArrivalTime.txt");
		assertFalse(stopTimes.importFile(file));
		assertEquals(stopTimes.getColOfStopTimes().size(),0);
	}
	
	@Test
	public void testMissingDepartureTime(){
		File file = new File("src/tests/stopTimesMissingDepartureTime.txt");
		assertFalse(stopTimes.importFile(file));
		assertEquals(stopTimes.getColOfStopTimes().size(),0);
	}
	
	@Test
	public void testMissingStopID(){
		File file = new File("src/tests/stopTimesMissingStopID.txt");
		assertFalse(stopTimes.importFile(file));
		assertEquals(stopTimes.getColOfStopTimes().size(),0);
	}
	
	
	@Test
	public void testInvalidArrivalTime(){
		File file = new File("src/tests/stopTimesInvalidArrivalTime.txt");
		assertFalse(stopTimes.importFile(file));
		assertEquals(stopTimes.getColOfStopTimes().size(),0);
	}
	
	@Test
	public void testInvalidDepartureTime(){
		File file = new File("src/tests/stopTimesInvalidDepartureTime.txt");
		assertFalse(stopTimes.importFile(file));
		assertEquals(stopTimes.getColOfStopTimes().size(),0);
	}
	
	@Test
	public void testValidExport(){
		File in = new File("src/tests/stopTimesValid.txt");
		assertTrue(stopTimes.importFile(in));
		File out = new File("src/tests/stopTimesExport.txt");
		stopTimes.export(out);
		assertTrue(stopTimes.importFile(out));
	}
}
