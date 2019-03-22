/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * A collection of routes and associated methods
 *
 * @author Trey Gallun
 * @version 1.0
 * @created 05-Oct-2017 12:10:53 PM
 */

public class Routes implements ExportImport {

    private Stops allStops;
    private Trips allTrips;
    private StopTimes allStopTimes;
    private Collection<Route> colOfRoutes;
    /**
     * Constructs the Routes object
     *
     * @param colOfRoutes  The collection of Route objects.
     * @param allStops     The Stops object containing all Stops.
     * @param allTrips     The Trips object containing all Trips.
     * @param allStopTimes The StopTimes object containing all StopTimes.
     * @author Trey Gallun
     */
    public Routes(Collection<Route> colOfRoutes, Stops allStops, Trips allTrips, StopTimes allStopTimes) {
        this(colOfRoutes);
        this.allStops = allStops;
        this.allTrips = allTrips;
        this.allStopTimes = allStopTimes;
    }

    /**
     * main constructor
     *
     * @param colOfRoutes: collection of routes
     * @author Trey Gallun
     */
    public Routes(Collection<Route> colOfRoutes) {
        this.colOfRoutes = colOfRoutes;
    }

    public Collection<Route> getColOfRoutes() {
        return colOfRoutes;
    }

    /**
     * adds a route to collection
     *
     * @param route: a route
     * @author Trey Gallun
     */
    public void add(Route route) {
        colOfRoutes.add(route);
        
    }

    /**
     * removes a route from collection
     *
     * @param routeId: route_ID
     * @return route removed from collection
     * @author Trey Gallun
     */
    public Route remove(String routeId) {
        Route route = searchRouteId(routeId);
        colOfRoutes.remove(route);
        
        return route;
    }

    /**
     * updates the routes
     *
     * @author Trey Gallun
     */
    public void updateRoutes() {
        int i = 0;
        for (Route route : colOfRoutes) {
            route.updateRoute();
        }
        //update(this);
    }

    /**
     * searches for a route with given id
     *
     * @param routeId: route_ID
     * @return route with id, null if not present
     * @author Trey Gallun
     */
    public Route searchRouteId(String routeId) {
        for (Route route : colOfRoutes) {
            if (route.getId().equals(routeId))
                return route;
        }
        return null;
    }

    /**
     * searches routes in collection for a stop with given stop id
     *
     * @param stopId: stop_ID
     * @return a collection of routes containing the stop given the stop id. Returns an empty routes object if stopId is not on any routes.
     * @author Trey Gallun
     */
    public Routes searchRoutesForStop(String stopId) {
        Routes routes = new Routes(new ArrayList<Route>());
        for (Route route : colOfRoutes) {
            if (route.getStopsInRoute().searchStopId(stopId) != null)
                routes.add(route);
        }
        return routes;
    }

    /**
     * exports route collection to given file
     *
     * @param file this is the file that will be written to
     * @return return the file written
     */
    @Override
    //TODO throw exceptions
    public File export(File file) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color\n");
            for (Route route : colOfRoutes) {
                route.export(printWriter);
            }
            printWriter.close();
            //TODO move this to controller
            //EV3.display("Routes exported");
        } catch (Exception e) {
        	//TODO move this to controller
            //EV3.display("File: " + file.getName() + " could not be found!");
        	return null;
        }
        return file;
    }


    /**
     * Imports the file to individual Route objects and populates the collection
     *
     * @param file The .GTFS file
     * @return boolean if the import was successful.
     * @author terrybc
     */
    //TODO throw exceptions to controller
    public boolean importFile(File file) {
        try {
            Scanner in = new Scanner(file);
            if(in.hasNextLine()) {
            	String line = in.nextLine();
            	if(checkLine(line)) {
            		while(in.hasNextLine()) {
            			String[] data = in.nextLine().split(",");
            			if (data.length != 9) {
            				return false;
            			}
            			String route_id = data[0];
            			String agency_id = data[1];
            			String route_short = data[2];
            			String route_long = data[3];
            			String route_desc = data[4];
            			String route_type = data[5];
            			String route_url = data[6];
            			String route_color = data[7];
            			String route_text_clr = data[8];
            			if(route_id.length() > 0 && agency_id.length() > 0 && route_short.length() > 0 &&
            					route_long.length() > 0 && route_desc.length() > 0 && route_type.length() > 0
            					&& route_url.length() > 0 && route_color.length() > 0 && route_text_clr.length() > 0) {
            				colOfRoutes.add(new Route(route_id, agency_id, route_short, route_long, route_desc, 
            						route_type, route_url, route_color, route_text_clr, allStops, allTrips, allStopTimes));
            			}else{
            				
            				return false;
            			}
            		}
            	}else {
            		
            		return false;
            	}
            }
//            in.nextLine();
//            while (in.hasNextLine()) {
//                ArrayList<String> data = splitAttributes(in.nextLine());
//                colOfRoutes.add(new Route(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5),
//                		data.get(6), data.get(7), data.get(8), allStops, allTrips, allStopTimes));
//            }
            //update(this);
            //updateRoutes();
            //TODO move this to where import is called!
            //EV3.display("Routes imported");
            return true;
        } catch (Exception e) {
        	//TODO move this to where import is called!
            //EV3.display("File: " + file.getName() + " could not be found!");
            return false;
        }
    }
    
    /**
     * Checks to make sure the first line has the correct headers
     * 
     * @param line first line of the routes file
     * @return true if the headers are correct
     * @author terrybc & galluntf
     */
    private boolean checkLine(String line) {
            String[] a = line.split(",");
            return contains(a, "route_id") && contains(a, "route_short_name") && contains(a, "route_long_name")
            		&& contains(a, "route_type") && contains(a, "agency_id") && contains(a, "route_desc")
            		&& contains(a, "route_url") && contains(a, "route_color") && contains(a, "route_text_color");
    }

    /**
     * Checks if string array contains a string
     * helper for line check
     * @param the string array
     * @param the string to see if the array contains
     * @return true if it is contained
     * @author galluntf
     */
	private boolean contains(String[] array, String contain) {
		for(String s:array) {
			if(s.equalsIgnoreCase(contain))
				return true;
		}
		return false;
	}

    /**
     * Splits the attributes into an ArrayList
     *
     * @param line: the line to split
     * @return the ArrayList of the split data
     * @author Trey Gallun
     */
    public ArrayList<String> splitAttributes(String line) {
        ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(",")));
        while (data.size() != 9) {
            data.set(4, data.get(4) + "," + data.get(5));
            data.remove(5);
        }
        return data;
    }
    
    public Stops getStops() {
    	return allStops;
    }
}