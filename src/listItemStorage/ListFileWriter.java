package listItemStorage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**Class to write a list model to a file.
 *The Writer is instantiated to write the entries from a single list
 * to a single specified file.
 *Once instantiated, the source list and target file cannot be changed.
 * All information in the target file will be overwritten.
 *
 */
public class ListFileWriter {


    private File fin;

    private final DefaultListModel<ListEntry> SOURCE;


    public ListFileWriter(File fin, DefaultListModel<ListEntry> list)  throws IOException{
        this.fin = fin;
        SOURCE = list;
        new FileWriter(fin);
    }

    /**Writes the entirety of the source list to the target file.
     * This will replace the previous contents of the file.
     */
    public void writeList() throws IOException  {
        FileWriter writer = new FileWriter(fin);
        for (int i = 0; i < SOURCE.getSize(); i ++) {
            writeEntry(SOURCE.get(i), writer);
            if (i % 128 == 0) {
                writer.flush();
            }
        }
        writer.close();
    }

    //helper method to write another line into the file
    //representing another list entry
    private void writeEntry(ListEntry entry, FileWriter writer) throws IOException{
        writer.write((entry.getName() +","));
        writer.write(entry.getDescription() + ",");
        String date = "" + entry.getDate().get(Calendar.DATE) + "/"
                + (entry.getDate().get(Calendar.MONTH) + 1) + "/" +
                entry.getDate().get(Calendar.YEAR);
        writer.write(date + "\n");
    }

}
