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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * A collection of stops
 *
 * @author Trey Gallun
 * @version 1.0
 * @created 05-Oct-2017 12:10:56 PM
 */

public class Stops implements ExportImport {

    private Collection<Stop> colOfStops;
    /**
     * default constructor
     */
    public Stops() {
        this(new ArrayList<Stop>());
    }

    /**
     * main constructor
     *
     * @param colOfStops: stop collection
     */
    public Stops(Collection<Stop> colOfStops) {
        this.colOfStops = colOfStops;
    }

    public Collection<Stop> getColOfStops() {
        return colOfStops;
    }

    /**
     * adds a stop to the collection
     *
     * @param stop: stop
     * @author Trey Gallun
     */
    public void add(Stop stop) {
        colOfStops.add(stop);
    }

    /**
     * removes a stop from the collection using given stopID.
     *
     * @param stopId: stop_ID
     * @return the stop removed
     * @author Trey Gallun
     */
    public Stop remove(String stopId) {
        Stop stop = searchStopId(stopId);
        colOfStops.remove(stop);
        return stop;
    }

    /**
     * Searches for a stop using given stopID
     *
     * @param stopId: stop_ID
     * @return the stop with the stopID
     * @author Trey Gallun
     */
    public Stop searchStopId(String stopId) {
        for (Stop stop : colOfStops) {
            if (stop.getStopId().equals(stopId))
                return stop;
        }
        return null;
    }

    /**
     * exports stop collection to given file
     *
     * @param file this is the file that will be written to
     * @return return the file written
     * @author Trey Gallun
     */
    //TODO throw exceptions
    @Override
    public File export(File file) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write("stop_id,stop_name,stop_desc,stop_lat,stop_lon\n");
            for (Stop stop : colOfStops) {
                stop.export(printWriter);
            }
            printWriter.close();
            //EV3.display("Stops exported");
        } catch (Exception e) {
            //EV3.display("File: " + file.getName() + " could not be found!");
        	return null;
        }
        return file;
    }

    /**
     * Imports the file to individual Stop objects
     *
     * @param file The .GTFS file
     * @return If the import was successful.
     * @author Trey Gallun
     */
    //TODO throw exceptions to controller
    public boolean importFile(File file){
        try {	
            Scanner in = new Scanner(file);
            String line = in.nextLine();
            if (!checkLine(line)) {
            	in.close();
            	return false;
            }
            while (in.hasNextLine()) {
            	line = in.nextLine();
                ArrayList<String> data = splitAttributes(line);
                if(data.size() != 5 || data.get(0).equals("") || data.get(1).equals("") || data.get(3).equals("") || data.get(4).equals("")) {
                	in.close();
                	return false;
                }
                if (checkLat(Double.parseDouble(data.get(3))) && checkLon(Double.parseDouble(data.get(4)))) {
                    colOfStops.add(new Stop(data.get(0), data.get(1), data.get(2), Double.parseDouble(data.get(3)), Double.parseDouble(data.get(4))));
                }else {
                    //EV3.display("INVALID LAT OR LON");
                	in.close();
                	return false;
                }
            }
            //EV3.display("Stops imported");
            in.close();
            return true;
        } catch (FileNotFoundException e) {
            //EV3.display("File: " + file.getName() + " could not be found!");
        	
            return false;
        } catch (Exception e) {
        	return false;
        }
    }
    
    
    /**
     * Checks to make sure the first line has the correct headers
     * S
     * @param line first line of the routes file
     * @return true if the headers are correct
     * @author terrybc & galluntf
     */
    private boolean checkLine(String line) {
            String[] a = line.split(",");
            return contains(a, "stop_id") && contains(a, "stop_name") && contains(a, "stop_desc")
            		&& contains(a, "stop_lat") && contains(a, "stop_lon");
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
     * Checks latitude for proper value
     *
     * @param latitude: the latitude
     * @return boolean if latitude is valid
     * @author Trey Gallun
     */
    public boolean checkLat(double latitude) {
        return latitude <= 90 && latitude >= -90;
    }

    /**
     * Checks longitude for proper value
     *
     * @param longitude: the longitude
     * @return boolean if longitude is valid
     * @author Trey Gallun
     */
    public boolean checkLon(double longitude) {
        return longitude <= 180 && longitude >= -180;
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
        return data;
    }
}