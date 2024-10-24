package listItemStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**Class to create a list of ListEntries from a file.
 * The FileReader is instantiated to read from a specified file, and the file cannot be changed after.
 *
 */
public class ListFileReader {

    private final Scanner FIN;

    private List<ListEntry> entries;

    public ListFileReader(File file) throws FileNotFoundException {
        FIN = new Scanner(file).useDelimiter("\\s*,\\s*|\n");
    }

    /**Loads all ListEntries from the reader's file.
     * Returns the entries as a List of ListEntries.
     * Should the reader encounter an invalid Entry in the file,
     * it will skip it.
     *An Entry must have an associated Name in order to be valid.
     *
     * @return a list of Entries in the file
     */
    public List<ListEntry> loadFromFile() {
        FIN.
    }


}
