/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;


import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * The sequence of stop_times
 *
 * @author Alexander Gnabasik
 * @version 1.0
 * @created 05-Oct-2017 12:11:00 PM
 */
public class Trip {

    private String tripHeadsign;
    private StopTimes stopTimesInTrip;
    private String serviceId;
    private String tripId;
    private String routeId;
    private String directionId;
    private String blockId;
    private String shapeId;
    private StopTimes allStopTimes;


    /**
     * Constructs the Trip Object.
     *
     * @param routeId      The Route ID of the Trip.
     * @param serviceId    The Service Id of the Trip.
     * @param tripId       The tripID of the Trip.
     * @param tripHeadsign The Trip Headsign of the Trip
     * @param directionId  The Direction ID of Trip.
     * @param blockId      The Block ID of the Trip.
     * @param shapeId      The Shape ID of the Trip.
     * @param allStopTimes The StopTimes object of all the StopTimes
     * @author Alexander Gnabasik
     */
    public Trip(String routeId, String serviceId, String tripId, String tripHeadsign,
                String directionId, String blockId, String shapeId, StopTimes allStopTimes) {
        this(routeId, serviceId, tripId, tripHeadsign, directionId, blockId, shapeId);
        this.allStopTimes = allStopTimes;
    }

    /**
     * Constructs the Trip Object.
     *
     * @param routeId      The route Id of the trip
     * @param serviceId    The service Id of the Trip.
     * @param tripId       The trip Id of the Trip
     * @param tripHeadsign The trip Headsign of the Trip.
     * @param directionId  The directionId of the Trip.
     * @param blockId      The block Id of the Trip.
     * @param shapeId      The Shape Id of Trip.
     * @author Alexander Gnabasik.
     */
    public Trip(String routeId, String serviceId, String tripId, String tripHeadsign,
                String directionId, String blockId, String shapeId) {
        this.routeId = routeId;
        this.serviceId = serviceId;
        this.tripId = tripId;
        this.tripHeadsign = tripHeadsign;
        this.directionId = directionId;
        this.blockId = blockId;
        this.shapeId = shapeId;
        stopTimesInTrip = new StopTimes(new ArrayList<StopTime>());
    }

    public String getTripId() {
        return tripId;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
    }

    /**
     * Updates the Trip before sending the StopTimes in a Trip.
     *
     * @return The stopTimes in a trip
     * @author Alexander Gnabasik
     */
    public StopTimes getStopTimesInTrip() {
        updateTrip();
        return stopTimesInTrip;
    }

    public void setStopTimesInTrip(StopTimes stopTimesInTrip) {
        this.stopTimesInTrip = stopTimesInTrip;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getDirectionId() {
        return directionId;
    }

    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getShapeId() {
        return shapeId;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    public StopTimes getStopTimes() {
        updateTrip();
        return stopTimesInTrip;
    }

    public void setStopTimes(StopTimes stopTimes) {
        stopTimesInTrip = stopTimes;
    }

    /**
     * Writes the single Trip object to a file
     *
     * @param printWriter the printWriter of the .GTFS file
     * @return The printWriter of the .GTFS file.
     * @author Alexander Gnabasik
     */
    public PrintWriter export(PrintWriter printWriter) {
        printWriter.print(routeId + "," + serviceId + "," + tripId + ","
                + tripHeadsign + "," + directionId + "," + blockId + "," + shapeId + "\r\n");
        return printWriter;
    }

    /**
     * Updates the trip and populates it with StopTimes.
     *
     * @author Alexander Gnabasik
     */
    public void updateTrip() {
        if (allStopTimes.getColOfStopTimes().size() > 0) {
            stopTimesInTrip = allStopTimes.searchTripIdTimes(tripId);
        }
    }

}//end Trip