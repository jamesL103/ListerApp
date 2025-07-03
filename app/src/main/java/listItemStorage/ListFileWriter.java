package listItemStorage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

/**Class to write a list model to a file.
 *The Writer is instantiated to write the entries from a single list
 * to a single specified file.
 *Once instantiated, the source list and target file cannot be changed.
 * All information in the target file will be overwritten.
 *
 */
public class ListFileWriter {

    private static final DecimalFormat TWO_DIGIT = new DecimalFormat("00");

    private final File FIN;

    private final DefaultListModel<ListEntry> SOURCE;

    private final List<ListEntry> LIST;


    public ListFileWriter(File fin, DefaultListModel<ListEntry> list)  throws IOException {
        this.FIN = fin;
        SOURCE = list;
        LIST = null;

        if (fin.isDirectory()) {
            throw new IOException(fin.getName() +" is a directory");
        }
        if (!fin.exists()) {
            if (!fin.createNewFile()) {
                throw new IOException("Cannot create file " + fin.getName());
            }
        }
    }

    public ListFileWriter(File fin, List<ListEntry> list)  throws IOException {
        this.FIN = fin;
        SOURCE = null;
        LIST = list;

        if (fin.isDirectory()) {
            throw new IOException(fin.getName() +" is a directory");
        }
        if (!fin.exists()) {
            if (!fin.createNewFile()) {
                throw new IOException("Cannot create file " + fin.getName());
            }
        }
    }

    /**Writes the entirety of the source list to the target file.
     * This will replace the previous contents of the file.
     */
    public void writeList() throws IOException  {
        FileWriter writer = new FileWriter(FIN);
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
        String date = TWO_DIGIT.format(entry.getDate().get(Calendar.DATE)) + "/"
                + TWO_DIGIT.format(entry.getDate().get(Calendar.MONTH) + 1) + "/" +
                entry.getDate().get(Calendar.YEAR);
        writer.write(date + "\n");
    }

}
