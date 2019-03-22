/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu ,galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;

import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * A single stop containing geographical information on said stop.
 *
 * @author Trey Gallun
 * @version 1.0
 * @created 05-Oct-2017 12:10:55 PM
 */

public class Stop {

    DecimalFormat df = new DecimalFormat("#.000000");
    private String stopDesc;
    private String stopId;
    private double stopLat;
    private double stopLon;
    private String stopName;

    /**
     * main constructor
     *
     * @param stopDesc: description of the stop
     * @param stopId    : stop_ID
     * @param stopLat   : Latitude coordinate
     * @param stopLon   : Longitude coordinate
     * @author Trey Gallun
     */
    public Stop(String stopId, String stopName, String stopDesc, double stopLat, double stopLon) {
        setStopDesc(stopDesc);
        setStopName(stopName);
        setStopId(stopId);
        setStopLat(stopLat);
        setStopLon(stopLon);
    }

    public void setStopName(String name) {
        stopName = name;
    }

    public void setStopDesc(String description) {
        stopDesc = description;
    }

    public void setStopId(String id) {
        stopId = id;
    }

    /**
     * @param latitude: latitude coordinate, must be between -90 and 90
     */
    public void setStopLat(double latitude) {
        stopLat = latitude;
    }

    /**
     * @param longitude: longitude coordinate, must be between -180 and 180
     */
    public void setStopLon(double longitude) {
        stopLon = longitude;
    }

    public String getStopDesc() {
        return stopDesc;
    }

    public String getStopId() {
        return stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public double getStopLat() {
        return stopLat;
    }

    public double getStopLon() {
        return stopLon;
    }

    /**
     * Writes the single Stop object to a file
     *
     * @param printWriter the printWriter of the .GTFS file
     * @return The printWriter of the .GTFS file.
     * @author Trey Gallun
     */
    public PrintWriter export(PrintWriter printWriter) {
        printWriter.write(stopId + "," + stopName + "," + stopDesc + "," + df.format(stopLat) + "," + df.format(stopLon) + "\n");
        return printWriter;
    }
}