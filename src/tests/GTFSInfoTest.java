///**
// * 
// */
//package tests;
//
//import static org.junit.Assert.*;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import gtfs.GTFSInfo;
//
///**
// * @author murphyad
// *
// */
//public class GTFSInfoTest {
//	
//	GTFSInfo info;
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		info = new GTFSInfo();
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	/**
//	 * Test method for {@link GTFSInfo#importFiles(java.util.List)}.
//	 */
//	@Test
//	public void testImportFiles() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link GTFSInfo#testHeader(java.lang.String, int)}.
//	 */
//	@Test
//	public void testTestHeader() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link GTFSInfo#importRoutes(java.lang.String)}.
//	 */
//	@Test
//	public void testImportRoutes() {
//		String valid1 = "2F52F87E6A,ECT,1,\"MargaretndMall\",\"\",3,http://www.eauclairewi.gov/index.aspx?page=769,008345,FFFFFF";
//		String valid2 = "D837EE7A8,ECT,2,\"Mt. Washington\",\"\",3,http://www.eauclairewi.gov/index.aspx?page=770,005BAB,FFFFFF";
//		String missingRouteID = ",ECT,1,\"MargaretndMall\",\"\",3,http://www.eauclairewi.gov/index.aspx?page=769,008345,FFFFFF";
//		String missingShortName = "2F52F87E6A,ECT,,\"MargaretndMall\",\"\",3,http://www.eauclairewi.gov/index.aspx?page=769,008345,FFFFFF";
//		String missingLongName = "2F52F87E6A,ECT,1,,\"\",3,http://www.eauclairewi.gov/index.aspx?page=769,008345,FFFFFF";
//		String missingType = "2F52F87E6A,ECT,1,\"MargaretndMall\",\"\",,http://www.eauclairewi.gov/index.aspx?page=769,008345,FFFFFF";
//		String empty = "";
//		
//		info.importRoutes(valid1);
//		assertTrue(info.routes.containsKey("2F52F87E6A"));
//		info.importRoutes(valid2);
//		assertEquals("http://www.eauclairewi.gov/index.aspx?page=770",info.routes.get("2F52F87E6A").getRoute_url());
//		
//		info.routes.clear();
//		
//		info.importRoutes(missingRouteID);
//		assertEquals(0,info.routes.size());
//		info.importRoutes(missingShortName);
//		assertEquals(0,info.routes.size());
//		info.importRoutes(missingLongName);
//		assertEquals(0,info.routes.size());
//		info.importRoutes(missingType);
//		assertEquals(0,info.routes.size());
//		info.importRoutes(empty);
//		assertEquals(0,info.routes.size());
//	}
//
//	/**
//	 * Test method for {@link GTFSInfo#importStops(java.lang.String)}.
//	 */
//	@Test
//	public void testImportStops() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link GTFSInfo#importStopTimes(java.lang.String, java.util.HashSet)}.
//	 */
//	@Test
//	public void testImportStopTimes() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link GTFSInfo#importTrips(java.lang.String)}.
//	 */
//	@Test
//	public void testImportTrips() {
//		fail("Not yet implemented");
//	}
//
//
//
//}
