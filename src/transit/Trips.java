/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;


import javax.xml.crypto.dsig.Transform;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Alexander Gnabasik
 * @version 1.0
 * @created 05-Oct-2017 12:11:01 PM
 * <p>
 * Represents a Collection of Trip objects.
 */
public class Trips {

    private StopTimes allStopTimes;
    private Collection<Trip> colOfTrips;
    /**
     * Constructs a Trips object
     *
     * @param allStopTimes The StopTimes object that contains all the StopTimes.
     * @param colOfTrips   The collection of Trip objects to be contained in the Trips object.
     * @author Alexander Gnabasik
     */
    public Trips(StopTimes allStopTimes, Collection<Trip> colOfTrips) {
        this(colOfTrips);
        this.allStopTimes = allStopTimes;
    }

    /**
     * Constructs the Trips object
     *
     * @param colOfTrips a collection of trips wanted in the trips object
     * @author Alexander Gnabask
     */
    public Trips(Collection<Trip> colOfTrips) {
        this.colOfTrips = colOfTrips;
    }

    public Collection<Trip> getColOfTrips() {
        return colOfTrips;
    }

    /**
     * Adds a trip object to the collection of trips
     *
     * @param trip trip to be added to the collection of trips
     * @author Alexander Gnabask
     */
    public void add(Trip trip) {
        colOfTrips.add(trip);
    }

    /**
     * Removes a trip from the collection of trips by a trip ID.
     *
     * @param tripId the tripId of the trip to be removed
     * @return The Trip that is removed or null if not
     * @author Alexander Gnabask
     */
    public Trip remove(String tripId) {
        Trip tripToRemove = searchTripId(tripId);
        if (colOfTrips.remove(tripToRemove)) {
            return tripToRemove;
        }
        return null;
    }

    /**
     * Searches for a trip with a specified tripId
     *
     * @param tripId the tripId of the trip to be searched for
     * @return A Trip if found, or null if not.
     * @author Alexander Gnabask
     */
    public Trip searchTripId(String tripId) {
        Iterator iterator = colOfTrips.iterator();
        while (iterator.hasNext()) {
            Trip t = (Trip) iterator.next();
            if (t.getTripId().equals(tripId)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Gives all trips on a specified route.
     *
     * @param routeId The ID of a specified route
     * @return The Trips object containing a collection of Trip on a specified route. Returns null if route ID is not valid.
     * @author Alexander Gnabask
     */
    public Trips searchByRouteId(String routeId) {
        Collection<Trip> t = new ArrayList<>();
        Iterator i = colOfTrips.iterator();
        while (i.hasNext()) {
            Trip testTrip = (Trip) i.next();
            if (routeId.equals(testTrip.getRouteId())) {
                t.add(testTrip);
            }
        }
        if (t.size() > 0) {
            return new Trips(t);
        } else {
            return null;
        }
    }

    /**
     * Exports Trips object to a .GTFS file
     *
     * @param file .GTFS file to be written to.
     * @return The .GTFS file that was written to.
     * @author Alexander Gnabask
     */
    public File export(File file) {
        try {
            Writer writer = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.write("route_id,service_id,trip_id,trip_headsign," +
                    "direction_id,block_id,shape_id\r\n");
            Iterator i = colOfTrips.iterator();
            while (i.hasNext()) {
                Trip trip = (Trip) i.next();
                trip.export(printWriter);
            }
            printWriter.close();
            //EV3.display("Trips exported");
        } catch (Exception e) {
            //EV3.display("Could not export the Trips file; File Malformed.");
        	return null;
        }
        return file;
    }

    /**
     * Imports the file to individual Trip objects
     *
     * @param file The .GTFS file
     * @return If the import was successful.
     * @author Alexander Gnabask
     */
    public boolean importFile(File file) {
        try {
            Scanner in = new Scanner(file);
            LinkedHashSet<String> tripIds = new LinkedHashSet<>();
            if (in.hasNextLine()) {
                String attributes = in.nextLine();
                if (checkAttributes(attributes)) {
                    while (in.hasNextLine()) {
                        String shapeId = "";
                        String blockId = "";
                        String directionId = "";
                        String tripHeadsign = "";
                        String allData = in.nextLine();
                        String[] data = allData.split(",");
                        String routeId = data[0];
                        String serviceId = data[1];
                        String tripId = data[2];
                        if (data.length > 3) {
                            tripHeadsign = data[3];
                            if (data.length > 4) {
                                directionId = data[4];
                                if (data.length > 5) {
                                    blockId = data[5];
                                    if (data.length > 6) {
                                        shapeId = data[6];
                                    }
                                }
                            }
                        }
                        if (idChecker(routeId) && idChecker(serviceId) && idChecker(tripId)) {
                            Trip trip = new Trip(routeId, serviceId, tripId, tripHeadsign,
                                    directionId, blockId, shapeId, allStopTimes);
                            colOfTrips.add(trip);
                            tripIds.add(tripId);
                        } else {
                            return false;
                        }
                    }
                    //EV3.display("Trips imported");
                    return true;
                }
            }
        } catch (Exception e) {
            //EV3.display("Could not import Trips file: File Malformed");
        }
        return false;
    }

    /**
     * This updates all the trip objects within the Collection.
     */
    public void updateTrips() {
        //int i = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        Date dateBefore = new Date();
        //EV3.display("Before: " + dateFormat.format(dateBefore));
        for(Trip trip: colOfTrips) {
        	trip.updateTrip();
        }
        //colOfTrips.parallelStream().forEach(Trip::updateTrip);
        Date dateAfter = new Date();
        //EV3.display("After: " + dateFormat.format(dateAfter));
       /* for (Trip t: colOfTrips) {
            t.updateTrip();
            System.out.println("Updated Trip: " + i++);
        } */
        //EV3.display("Updating Trips complete");
    }

    public boolean contains(Trip trip) {
        return colOfTrips.contains(trip);
    }

    /**
     * Checks to see if the ID is blank or not.
     *
     * @param id The String id to check.
     * @return if the String is blank or not.
     */
    public boolean idChecker(String id) {
        return id.length() > 0;
    }

    /**
     * Checks to make sure the attributes are correct for a Trips GTFS file.
     *
     * @param attributes The String of the first line of a Trips GTFS file.
     * @return If the attributes are correct for a Trips GTFS file.
     */
    public boolean checkAttributes(String attributes) {
        if (attributes.contains(",")) {
            String[] a = attributes.split(",");
            if (a.length == 7) {
                if (a[0].equals("route_id") && a[1].equals("service_id") && a[2].equals("trip_id")
                        && a[3].equals("trip_headsign") && a[4].equals("direction_id") && a[5].equals("block_id")
                        && a[6].equals("shape_id")) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}//end Trips