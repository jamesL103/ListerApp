package listItemStorage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**Class to write a list to a file.
 *The Writer is instantiated to write the entries from a single list
 * to a single specified file.
 *Once instantiated, the source list and target file cannot be changed.
 * All information in the target file will be overwritten.
 *
 */
public class ListFileWriter {


    private File fin;

    private FileWriter writer;

    private final DefaultListModel<ListEntry> SOURCE;


    public ListFileWriter(File fin, DefaultListModel<ListEntry> list) throws IOException {
        this.fin = fin;
        SOURCE = list;
        writer = new FileWriter(fin);
    }

    /**Writes the entirety of the source list to the target file.
     * This method should only be called on a file that is empty.
     */
    public void writeList() throws IOException  {
        for (int i = 0; i < SOURCE.getSize(); i ++) {
            writeEntry(SOURCE.get(i));
        }
    }

    //helper method to write another line into the file
    //representing another list entry
    private void writeEntry(ListEntry entry) throws IOException{
        writer.write((entry.getName() +","));
        writer.append(entry.getDescription() + ",");
        String date = "" + entry.getDate().get(Calendar.DATE) + "/"
                + (entry.getDate().get(Calendar.MONTH) + 1) + "/" +
                entry.getDate().get(Calendar.YEAR);
        writer.append(date + "\n");
        writer.flush();
    }

}
