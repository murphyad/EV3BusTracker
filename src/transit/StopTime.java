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


/**
 * @author Alexander Gnabask
 * @version 1.0
 * @created 05-Oct-2017 12:10:57 PM
 * <p>
 * Represents a StopTime
 */
public class StopTime {

    private String arrivalTime;
    private String departureTime;
    private String stopId;
    private int sequence;
    private String tripId;
    private String stopHeadsign;
    private String pickUpType;
    private String dropOffType;


    /**
     * Constructs the StopTime
     *
     * @param arrivalTime   the time the bus arrives at the stop
     * @param departureTime the time the bus departs from the stop
     * @param stopId        the stopId of the specified stop
     * @param sequence      the stop number in the trip
     * @param tripId        the tripId
     * @author Alexander Gnabasik
     */
    public StopTime(String arrivalTime, String departureTime, String stopId, int sequence, String tripId,
                    String stopHeadsign, String pickUpType, String dropOffType) {
        setArrivalTime(arrivalTime);
        setDepartureTime(departureTime);
        setStopId(stopId);
        setSequenceNumber(sequence);
        setTripId(tripId);
        setStopHeadsign(stopHeadsign);
        setPickUpType(pickUpType);
        setDropOffType(dropOffType);
    }

    /**
     * Writes to a .GTFS file
     *
     * @param printWriter the printWriter of the .GTFS file.
     * @return The printWriter of the .GTFS file.
     * @author Alexander Gnabasik
     */
    public PrintWriter export(PrintWriter printWriter) {
        printWriter.print(stopId + "," + arrivalTime + "," + departureTime + "," + stopId + "," +
                sequence + "," + stopHeadsign + "," + pickUpType + "," + dropOffType + "\r\n");
        return printWriter;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getStopHeadsign() {
        return stopHeadsign;
    }

    public void setStopHeadsign(String stopHeadsign) {
        this.stopHeadsign = stopHeadsign;
    }

    public String getPickUpType() {
        return pickUpType;
    }

    public void setPickUpType(String pickUpType) {
        this.pickUpType = pickUpType;
    }

    public String getDropOffType() {
        return dropOffType;
    }

    public void setDropOffType(String dropOffType) {
        this.dropOffType = dropOffType;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getSequenceNumber() {
        return sequence;
    }

    public String getStopId() {
        return stopId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setArrivalTime(String arriveTime) {
        this.arrivalTime = arriveTime;
    }

    public void setDepartureTime(String departTime) {
        this.departureTime = departTime;
    }

    public void setSequenceNumber(int sequence) {
        this.sequence = sequence;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    /**
     * Checks to see if the StopTime is equal to another StopTime.
     *
     * @param test the StopTime to see if it is equal to it.
     * @return whether the two StopTimes are equal.
     * @author Alexander Gnabasik
     */
    public boolean isEqual(StopTime test) {
        return test.getArrivalTime().equals(arrivalTime) && test.getDepartureTime().equals(departureTime)
                && test.getStopId().equals(stopId) && test.getSequence() == sequence
                && test.getTripId().equals(tripId) && test.getStopHeadsign().equals(stopHeadsign)
                && test.getPickUpType().equals(pickUpType) && test.getDropOffType().equals(dropOffType);
    }

    /**
     * gets the arrival time in seconds
     *
     * @author Trey Gallun
     */
    public int getArrivalTimeInSeconds() {
        String[] times = getArrivalTime().split(":");
        return Integer.parseInt(times[0]) * 3600 + Integer.parseInt(times[1]) * 60 + Integer.parseInt(times[2]);
    }
    
    public int getDepartureTimeInSeconds() {
    	String[] times = getDepartureTime().split(":");
    	return Integer.parseInt(times[0]) * 3600 + Integer.parseInt(times[1]) * 60 + Integer.parseInt(times[2]);
    }

}//end StopTime