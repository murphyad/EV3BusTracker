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
import java.util.Collection;
import java.util.Iterator;

/**
 * Contains a sequence of stops
 *
 * @author Trey Gallun
 * @version 1.0
 * @created 05-Oct-2017 12:10:52 PM
 */

public class Route {

    private String routeColor;
    private String routeDesc;
    private String routeId;
    private String routeLongName;
    private String routeShortName;
    private Stops stopsInRoute;
    private Trips tripsInRoute;
    private String routeTextColor;
    private String agencyID;
    private String routeType;
    private String routeURL;
    private Stops allStops;
    private Trips allTrips;
    private StopTimes allStopTimes;

    /**
     * Constructs the Route Object.
     *
     * @param routeId        The ID of the Route.
     * @param agencyID       The agency ID of the Route.
     * @param routeShortName The Route's short name.
     * @param routeLongName  The Routes's Long Name.
     * @param routeDesc      The description of the Route.
     * @param routeType      The Type of Route.
     * @param routeURL       The URL of the Route.
     * @param routeColor     The Route color of the Route.
     * @param routeTextColor The Route Text Color.
     * @param allStops       The Stops object of all the Stops.
     * @param allTrips       The Trips object of all the Trips.
     * @param allStopTimes   The StopTimes object of all the StopTimes.
     * @author Trey Gallun
     */
    public Route(String routeId, String agencyID, String routeShortName, String routeLongName, String routeDesc,
                 String routeType, String routeURL, String routeColor, String routeTextColor, Stops allStops,
                 Trips allTrips, StopTimes allStopTimes) {
        this(routeId, agencyID, routeShortName, routeLongName, routeDesc, routeType,
                routeURL, routeColor, routeTextColor);
        this.allStops = allStops;
        this.allTrips = allTrips;
        this.allStopTimes = allStopTimes;
    }

    /**
     * Constructs the Route.
     *
     * @param routeId        The ID of the Route.
     * @param agencyID       The agency ID of the Route.
     * @param routeShortName The Route's short name.
     * @param routeLongName  The Routes's Long Name.
     * @param routeDesc      The description of the Route.
     * @param routeType      The Type of Route.
     * @param routeURL       The URL of the Route.
     * @param routeColor     The Route color of the Route.
     * @param routeTextColor The Route Text Color.
     * @author Trey Gallun
     */
    public Route(String routeId, String agencyID, String routeShortName, String routeLongName, String routeDesc,
                 String routeType, String routeURL, String routeColor, String routeTextColor) {
        this.routeId = routeId;
        this.agencyID = agencyID;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
        this.routeDesc = routeDesc;
        this.routeType = routeType;
        this.routeURL = routeURL;
        this.routeColor = routeColor;
        this.routeTextColor = routeTextColor;
    }

    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }

    public void setRouteTextColor(String routeTextColor) {
        this.routeTextColor = routeTextColor;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public void setRouteURL(String routeURL) {
        this.routeURL = routeURL;
    }

    public void setRouteColor(String color) {
        routeColor = color;
    }

    public void setRouteDesc(String description) {
        routeDesc = description;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setRouteLongName(String longName) {
        this.routeLongName = longName;
    }

    public void setRouteShortName(String shortName) {
        routeShortName = shortName;
    }

    public void setStopsInRoute(Stops stops) {
        stopsInRoute = stops;
    }

    public void setTripsInRoute(Trips trips) {
        tripsInRoute = trips;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public String getRouteType() {
        return routeType;
    }

    public String getRouteURL() {
        return routeURL;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public String getRouteTextColor() {
        return routeTextColor;
    }

    public Trips getTripsInRoute() {
        return tripsInRoute;
    }

    public String getId() {
        return routeId;
    }

    public String getLongName() {
        return routeLongName;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public String getShortName() {
        return routeShortName;
    }

    public Stops getStopsInRoute() {
        return stopsInRoute;
    }

    /**
     * returns a collection of tripIds that are in the route
     *
     * @return The collection of tripIds in the route
     * @author Trey Gallun
     */
    public Collection<String> getTripIdsInRoute() {
        Collection<String> tripIds = new ArrayList<>();
        for (Trip trip : tripsInRoute.getColOfTrips()) {
            tripIds.add(trip.getTripId());
        }
        return tripIds;
    }

    /**
     * Writes the single Trip object to a file
     *
     * @param printWriter the printWriter of the .GTFS file
     * @return The printWriter of the .GTFS file.
     * @author Trey Gallun
     */
    public PrintWriter export(PrintWriter printWriter) {
        printWriter.write(routeId + "," + agencyID + "," + routeShortName + "," + routeLongName + "," + routeDesc +
                "," + routeType + "," + routeURL + "," + routeColor + "," + routeTextColor + "\n");
        return printWriter;
    }

    /**
     * updates the stopsInRoute and the tripsInRoute from the master objects.
     *
     * @author Trey Gallun
     */
    public void updateRoute() {
        Collection<Stop> colStop = new ArrayList<>();
        Collection<String> colStopIds = new ArrayList<>();
        Collection<StopTime> colSt;
        Collection<Trip> colTrip;
        Trips tripsWithRouteId;
        StopTimes st;
        Trip tripOnRoute;
        Iterator<Trip> i;
        Stop stopOnRoute;
        if (allTrips != null && allStopTimes != null && allStops != null && allTrips.getColOfTrips().size() > 0 && allStopTimes.getColOfStopTimes().size() > 0
                && allStops.getColOfStops().size() > 0) {
            tripsWithRouteId = allTrips.searchByRouteId(routeId);
            tripsInRoute = tripsWithRouteId;
            colTrip = tripsWithRouteId.getColOfTrips();
            i = colTrip.iterator();
            tripOnRoute = i.next();
            st = tripOnRoute.getStopTimes();
            colSt = st.getColOfStopTimes();
            for (StopTime s : colSt) {
                colStopIds.add(s.getStopId());
            }
            for (String stopId : colStopIds) {
                stopOnRoute = allStops.searchStopId(stopId);
                colStop.add(stopOnRoute);
            }
            stopsInRoute = new Stops(colStop);
        }
    }
}