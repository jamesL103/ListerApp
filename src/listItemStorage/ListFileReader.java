package listItemStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Class to create a list of ListEntries from a file.
 * The FileReader is instantiated to read from a specified file, and the file cannot be changed after.
 *
 */
public class ListFileReader {

    private final Scanner READ;

    public final File FIN;

    private List<ListEntry> entries;

    //regex pattern to match with a valid date
    private final static Pattern DATE_REGEX = Pattern.compile("\\s*[0-9]{2}/[0-9]{2}/[0-9]{4}\\s*");

    public ListFileReader(File file) throws FileNotFoundException {
        READ = new Scanner(file);
        FIN = new File(file.getPath());
    }

    //private default
    private ListFileReader() {
        READ = null;
        FIN = null;
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
        List<ListEntry> toReturn = new LinkedList<>();

        //load each line from the file
        while (READ.hasNext()) {
            String line = READ.nextLine();
            //if first field is blank, skip the line
            if (!(line.isEmpty())) {
                ListEntry entry = makeEntry(line);
                toReturn.add(entry);
            }
        }
        return toReturn;
    }

    //helper method to create a ListEntry from a String line read from a file
    //assume the line forms a valid entry
    private ListEntry makeEntry(String line) {

        Scanner lineReader = new Scanner(line).useDelimiter("\\s*(,|$)");

        //parse line for fields
        ListEntry entry = new ListEntry(lineReader.next());

        //get desc (optional)
        if (lineReader.hasNext()) {
            line = lineReader.next();
            if (!(line.isEmpty())) {
                entry.setDescription(line);
            }

            //get date (optional)
            if (lineReader.hasNext()) {
                line = lineReader.next();
                if (!(line.isEmpty())) {
                    Calendar cal = makeCalendar(line.strip());
                    if (cal != null) {
                        entry.setDate(cal);
                    }
                }
            }
        }

        return entry;
    }

    //helper method to make a Calendar from string input
    //String must be of form dd/mm/yyyy, no spaces
    //invalid strings will result in null return
    private Calendar makeCalendar(String input) {
        Matcher dateMatch = DATE_REGEX.matcher(input);
        Calendar toReturn = null;
        if (dateMatch.matches()) {
            int date = Integer.parseInt(input.substring(0,2));
            int month = Integer.parseInt(input.substring(3,5));
            month = Months.MONTHS[month-1];
            int year = Integer.parseInt(input.substring(6));
            toReturn = new GregorianCalendar();
            toReturn.set(year,month,date);
        }
        return toReturn;
    }



    //inner class to hold an array of all calendar months
    private static class Months {
        private static final int[] MONTHS = {
            Calendar.JANUARY,
            Calendar.FEBRUARY,
            Calendar.MARCH,
            Calendar.APRIL,
            Calendar.MAY,
            Calendar.JUNE,
            Calendar.JULY,
            Calendar.AUGUST,
            Calendar.SEPTEMBER,
            Calendar.OCTOBER,
            Calendar.NOVEMBER,
            Calendar.DECEMBER
        };
    }


}
