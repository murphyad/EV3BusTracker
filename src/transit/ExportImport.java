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

/**
 * All classes that implement this can export and import to a File
 *
 * @author gnabasikat
 * @version 1.0
 * @created 05-Oct-2017 12:10:45 PM
 */
public interface ExportImport {


    /**
     * Exports objects to a type of .GTFS file.
     *
     * @param file file to be written to.
     * @return The file that was written to.
     * @author Alexander Gnabasik
     */
    public File export(File file);

    /**
     * Reads a specified type of .GTFS file and places the data into respective objects
     *
     * @param file The .GTFS file
     * @return if the file was successfully read.
     * @author Alexander Gnabasik
     */
    public boolean importFile(File file);

}