/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import javax.swing.JOptionPane;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.utility.TextMenu;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
/**
 * The controller for the project and the FXML file
 *
 * @author Noe Gonzalez, Alex Gnabasik, and Trey Gallun
 * @version 1.3
 * @created 09-Oct-2017 12:10:42 PM
 */
public class Controller{
    private static Routes allRoutes;
    private static Stops allStops;
    private static StopTimes allStopTimes;
    private static Trips allTrips;
    private static double scaleFactor;
    static boolean stopsImported;
    static boolean routesImported;
    static boolean tripsImported;
    static boolean stopTimesImported;
    static boolean searchForRoutes;
    static boolean searchForStops;
    static boolean searchForTrips;
    static MoveInstructions mi = new MoveInstructions();
    

    public Controller() {
        stopsImported = false;
        routesImported = false;
        tripsImported = false;
        stopTimesImported = false;
        searchForRoutes = false;
        searchForStops = false;
        searchForTrips = false;
        scaleFactor = 500.0;
    }
    
    /**
     * Initalizes all of the required collections and variables
     */
    public static void initialize() {
        allStopTimes = new StopTimes(new ArrayList<StopTime>());
        allTrips = new Trips(allStopTimes, new ArrayList<Trip>());
        allStops = new Stops(new ArrayList<Stop>());
        allRoutes = new Routes(new ArrayList<Route>(), allStops, allTrips, allStopTimes);
    }
    
    /**
     * Imports the routes file and stores the data in the routes object
     * @author murphyad
     * @param routes the file containing routes information
     * @return true if the import is successful, false if not.
     */
    public boolean importRoutes(File routes){
    	return allRoutes.importFile(routes);
    }
    
    /**
     * Imports the stops file and stores the data in the stops object
     * @author murphyad
     * @param stops the file containing stops information
     * @return true if the import is successful, false if not.
     */
    public boolean importStops(File stops){
    	return allStops.importFile(stops);
    }
    
    /**
     * Imports the stopTimes file and stores the data in the stopTimes object
     * @author murphyad
     * @param stopTimes the file containing stops information
     * @return true if the import is successful, false if not.
     */
    public boolean importStopTimes(File stopTimes){
    	return allStopTimes.importFile(stopTimes);
    }
    
    /**
     * Imports the trips file and stores the data in the trips object
     * @author murphyad
     * @param trips the file containing stops information
     * @return true if the import is successful, false if not.
     */
    public boolean importTrips(File trips){
    	return allTrips.importFile(trips);
    }
    /**
     * Updates the data structure to the most recent state
     * This should only be called after everything has been imported
     * @author murphyad 
     */
    public void update(){
    	allRoutes.updateRoutes();
    }
    /**
     * Exports the routes in the data structure to the specified file
     * @author murphyad
     * @param routes the file to write to
     * @return true if the file is successfully exported
     */
    public boolean exportRoutes(File routes){
    	return allRoutes.export(routes)!=null;
    }
    
    /**
     * Exports the stops in the data structure to the specified file
     * @author murphyad
     * @param stops the file to write to
     * @return true if the file is successfully exported
     */
    public boolean exportStops(File stops){
    	return allStops.export(stops)!=null;
    }
    
   /**
    * Exports the stopTimes in the data structure to the specified file
    * @author murphyad
    * @param stopTimes the file to write to
    * @return true if the file is successfully exported
    */
    public boolean exportStopTimes(File stopTimes){
    	return allStopTimes.export(stopTimes)!=null;
    }
    
    /**
     * Exports the trips in the data structure to the specified file
     * @author murphyad 
     * @param trips the file to write to
     * @return true if the file is successfully exported
     */
    public boolean exportTrips(File trips){
    	return allTrips.export(trips)!=null;
    }
    
    public String[] getStopArray() {
    	String[] array = new String[allStops.getColOfStops().size()];
    	Stop stop;
    	for(int i=0; i<allStops.getColOfStops().size(); i++) {
    		stop = (Stop) allStops.getColOfStops().toArray()[i];
    		array[i] = stop.getStopId();
    	}
    	return array;
    }
    
    public String[] getRouteArray() {
    	String[] array = new String[allStops.getColOfStops().size()];
    	Route route;
    	for(int i=0; i<allRoutes.getColOfRoutes().size(); i++) {
    		route = (Route) allRoutes.getColOfRoutes().toArray()[i];
    		array[i] = route.getRouteId();
    	}
    	return array;
     }
    
    public String[] getTripArray() {
    	String[] array = new String[allTrips.getColOfTrips().size()];
    	Trip trip;
    	for(int i=0; i<array.length; i++){
    		trip = (Trip) allTrips.getColOfTrips().toArray()[i];
    		array[i] = trip.getTripId();
    	}
    	return array;
    }
    
    public String[] getStopTimeArray(){
    	String[] array = new String[allStopTimes.getColOfStopTimes().size()];
    	StopTime stopTime;
    	for(int i=0; i<array.length; i++){
    		stopTime = (StopTime) allStopTimes.getColOfStopTimes().toArray()[i];
    		array[i] = stopTime.getTripId() + "::" + stopTime.getSequenceNumber();
    	}
    	return array;
    }
    
    public StopTime getStopTime(int index){
    	return (StopTime) allStopTimes.getColOfStopTimes().toArray()[index];
    }

    /**
     * filters the routes for the routes containing a specific stop
     *
     * @param stopId the ID of the stop to be searched for.
     * @author Alexander Gnabasik
     */
    public static Routes routesWithStop(String stopId) {
        return allRoutes.searchRoutesForStop(stopId);
    }

    /**
     * Verifies the route is in the allRoutes object
     *
     * @param routeId The route ID to be verified
     * @author Alexander Gnabasik
     */
    private static boolean routeVerify(String routeId) {
        return allRoutes.searchRouteId(routeId) != null;
    }


    /**
     * Searches allStops for a stop with stopId
     *
     * @param stopId
     * @author Alexander Gnabasik
     */
    public static Stop searchStopId(String stopId) {
        return allStops.searchStopId(stopId);
    }

    /**
     * Searches for the next trip by a stop id
     *
     * @author Trey Gallun
     */
    public static Trip searchForNextTrip(String routeID) {
    	try{
    	StopTime nextStopTime = null;
//    	ArrayList<Trip> nextTrips = new ArrayList<>();
    	int currentTime = (int) (System.nanoTime() * 1000000000);
    	Trips trips = allTrips.searchByRouteId(routeID);
    	StopTimes stoptimes = null;
    	for(Trip trip : trips.getColOfTrips()) {
    		stoptimes = allStopTimes.searchTripIdTimes(trip.getTripId());
    		//        System.out.println("Found stop times: " + stoptimes.getColOfStopTimes().size());
    		for (StopTime stopTime : stoptimes.getColOfStopTimes()) {
    			if (nextStopTime == null) {
    				nextStopTime = stopTime;
    			} else if (stopTime.getArrivalTimeInSeconds() > currentTime && nextStopTime.getArrivalTimeInSeconds() > stopTime.getArrivalTimeInSeconds()) {
    				nextStopTime = stopTime;
    			} else if (nextStopTime.getArrivalTimeInSeconds() < stopTime.getArrivalTimeInSeconds()) {
    				nextStopTime = stopTime;
    			}
    		}
//    		nextTrips.add(allTrips.searchTripId(nextStopTime.getTripId()));
    	}
    	
        return allTrips.searchTripId(nextStopTime.getTripId());
    	}catch(NullPointerException e){
    		return null;
    	}
    }

    /**
     * Verifies the Stop is in the program.
     *
     * @param stopId the ID to the stop
     * @return if the stop is found.
     * @author Alexander Gnabasik
     */
    private static boolean stopVerify(String stopId) {
        return searchStopId(stopId) != null;
    }

    /**
     * Verifies the trip with the tripId is in the program
     *
     * @param tripId the specified tripId
     * @return if the trip is found
     */
    private static boolean tripVerify(String tripId) {
        return allTrips.searchTripId(tripId) != null;
    }
    
    /**
     * This will take the trip in feet and the scale the user wants
     * and return how many feet the robot will move
     * 
     * @param userScale the amount of feet of a bus per one foot of a robot
     * @param tripLength the length of a trip in feet
     * @return robot friendly scale
     * @author Bailey Terry
     */
    public double scaleTrip(double tripLength) {
    	return tripLength/scaleFactor;
    }
    
    public void setScale(double userIn) {
    	scaleFactor = userIn;
    }
    
    public double getScale() {
    	return scaleFactor;
    }
    
    /**
     * This will get all the stops, in order, on a specified trip
     * 
     * @param tripID the id of the trip you want
     * @return an ordered list of stops
     * @author Bailey Terry
     */
    public ArrayList<Stop> getStopsOnTrip(String tripID) {
    	ArrayList<Stop> allStop = new ArrayList<>();
//    	int stopSeq1;
//    	int stopSeq2 = Integer.MAX_VALUE;
    	String stopID;
    	Stop stop;
    	if(allTrips.searchTripId(tripID) != null) {
    		Trip trip = allTrips.searchTripId(tripID);
    		Collection<StopTime> stopTimes = trip.getStopTimes().getColOfStopTimes();
    		for(StopTime st: stopTimes) {
//    			stopSeq1 = st.getSequenceNumber();
    			stopID = st.getStopId();
//    			if(stopSeq1 < stopSeq2) {
//    				stopSeq2 = stopSeq1;//I dont think this is necessary because stops are already in sequnce
    				stop = allStops.searchStopId(stopID);
    				allStop.add(stop);
//    			}
    		}
    	}
    	return allStop;
    }
    //Trey Gallun
    //Performs x stops
    public double perform(int stops) {
    	return mi.perform(stops);
    }
    
    //Trey Gallun
    //performs full trip
    public double performFullTrip() {
    	return mi.perform();
    }
    
    //Trey Gallun
    //used to check if the trip is fully performed
    public boolean hasNextStopInInstructions() {
    	return mi.hasNext();
    }
    
    //Trey Gallun
    //used to clear instructions
    public void clearInstructions() {
    	mi = new MoveInstructions();
    }
    
    //Trey Gallun
    //used to create instructions for a trip starting and stopping at given stops
    public double limitTrip(String tripID, String startStop, int stops) {
    	clearInstructions();
    	double distance = 0;
    	ArrayList<Stop> allStop = getStopsOnTrip(tripID);
    	ArrayList<Stop> stopsToPerform = new ArrayList<>();
    	Iterator<Stop> itr = allStop.iterator();
    	Stop stop = itr.next();
    	while(itr.hasNext() && !stop.getStopId().equals(startStop)) {
    		stop = itr.next();
    	}
    	int t = 0;
    	while(itr.hasNext() && t < stops) {
    		stopsToPerform.add(stop);
    		stop = itr.next();
    		++t;
    	}
    	for(int i = 0; i < stopsToPerform.size() - 1; i++) {
    		distance += distanceBetweenTwoPoints(stopsToPerform.get(i), stopsToPerform.get(i+1));
    	}
    	return distance;
    }
    
    /**
     * This will take in a trip and return the total distance a bus would
     * travel on this trip in feet
     * 
     * @param tripID the id of the trip you want to know about
     * @return the distance in feet a bus would travel
     * @author Bailey Terry
     */
    public double distanceOfTrip(String tripID) {
    	mi = new MoveInstructions();
    	ArrayList<Stop> allStop = getStopsOnTrip(tripID);
    	double tempDist;
    	double dist = 0;
//    	System.out.println(allStops.getColOfStops().size());
    	for(int i = 0; i < allStop.size() - 1; i++) {
    		tempDist = distanceBetweenTwoPoints(allStop.get(i), allStop.get(i+1));
    		dist += tempDist;
    	}
    	return dist;
    }
    
    
public double distanceBetweenTwoPoints(Stop stop1, Stop stop2) {
	double tempDist = 0;
	//MoveInstructions mi = new MoveInstructions();
	int rOfEarth = 6371000;
	double angle;
//	double miles = 0.621371;
	int feet = 5280;
//	double radian = 0.01745329252;
	StopTime stopTime1 = allStopTimes.searchStopIdTimes(stop1.getStopId());
	StopTime stopTime2 = allStopTimes.searchStopIdTimes(stop2.getStopId());
	int departureTime1 = stopTime1.getDepartureTimeInSeconds();
	int arrivalTime2 = stopTime2.getArrivalTimeInSeconds();
	int scaledDiff = (arrivalTime2 - departureTime1)/60;//technically in minutes but we can treat it as seconds
	double lat1 = stop1.getStopLat();
	double lat2 = stop2.getStopLat();
	double long1 = stop1.getStopLon();
	double long2 = stop2.getStopLon();
	angle = getAngle(lat1, long1, lat2, long2);
	tempDist = Math.sqrt(((long2 - long1) * Math.cos(lat1)) * 
			((long2 - long1) * Math.cos(lat1)) + (lat2 - lat1) 
			* (lat2 - lat1)) * ((2 * Math.PI * 3961.3)/360);
	tempDist *= feet;
	mi.addInstruction(angle, tempDist/scaleFactor, scaledDiff+1);
	return tempDist;
}

private double getAngle(double lat1, double long1, double lat2, double long2) {
	double radian = 0.01745329252;
    double dLon = (long2 - long1)*radian;

    double y = Math.sin(dLon) * Math.cos(lat2*radian);
    double x = Math.cos(lat1*radian) * Math.sin(lat2*radian) - Math.sin(lat1*radian)
            * Math.cos(lat2*radian) * Math.cos(dLon);

    double brng = Math.atan2(y, x);

    brng = Math.toDegrees(brng);
    brng = (brng + 360) % 360;
//    brng = 360 - brng; // count degrees counter-clockwise - remove to make clockwise
    if(brng > 180){
    	return (brng - 360);
    }
    return brng;
}
    

}// end Controller