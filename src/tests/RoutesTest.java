package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import transit.*;

public class RoutesTest {

	Routes routes;
	
	@Before
	public void setUp() throws Exception {
		routes = new Routes(new ArrayList<Route>());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidInput() {
		File file = new File("src/tests/routesValid.txt");
		assertTrue(routes.importFile(file));
		assertTrue(routes.searchRouteId("2F52F87E6A")!=null);
		assertTrue(routes.searchRouteId("D837EE7A8")!=null);
	}
	
	@Test
	public void testInvalidHeader(){
		File file = new File("src/tests/routesInvalidHeader.txt");
		assertFalse(routes.importFile(file));
		assertEquals(routes.searchRouteId("2F52F87E6A"),null);
	}
	
	@Test
	public void testMissingHeader(){
		File file = new File("src/tests/routesMissingHeader.txt");
		assertFalse(routes.importFile(file));
		assertEquals(routes.searchRouteId("2F52F87E6A"),null);
		assertEquals(routes.searchRouteId("A7C1CB80"),null);
	}
	
	@Test
	public void testMissingRouteID(){
		File file = new File("src/tests/routesMissingRouteID.txt");
		assertFalse(routes.importFile(file));
		assertEquals(routes.getColOfRoutes().size(),0);
	}
	
	@Test
	public void testMissingRouteLongName(){
		File file = new File("src/tests/routesMissingRouteLongName.txt");
		assertFalse(routes.importFile(file));
		assertEquals(routes.getColOfRoutes().size(),0);
	}

	@Test
	public void testMissingRouteShortName(){
		File file = new File("src/tests/routesMissingShortName.txt");
		assertFalse(routes.importFile(file));
		assertEquals(routes.getColOfRoutes().size(),0);
	}
	
	@Test
	public void testMissingType(){
		File file = new File("src/tests/routesMissingType.txt");
		assertFalse(routes.importFile(file));
		assertEquals(routes.getColOfRoutes().size(),0);
	}
	
	@Test
	public void testNotEnoughParameters(){
		File file = new File("src/tests/routesNotEnoughParameters.txt");
		assertFalse(routes.importFile(file));
		assertEquals(routes.getColOfRoutes().size(),0);
	}
	
	@Test
	public void testValidExport(){
		//File in = new File("src/tests/routesValid.txt");
		//assertTrue(routes.importFile(in));
		File out = new File("src/tests/routesExport.txt");
		routes.export(out);
		assertTrue(routes.importFile(out));
	}
	
	@Test
	public void testWithEauClaire(){
		Controller controller = new Controller();
		controller.initialize();
		assertTrue(controller.importStopTimes(new File("GTFS_EauClaire/EC_ST.txt")));
		assertTrue(controller.importStops(new File("GTFS_EauClaire/EC_S.txt")));
		assertTrue(controller.importTrips(new File("GTFS_EauClaire/EC_T.txt")));
		assertTrue(controller.importRoutes(new File("GTFS_EauClaire/EC_RT.txt")));
	}
	
}
