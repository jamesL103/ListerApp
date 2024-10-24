package listItemStorage;

import java.util.Calendar;
import java.util.Locale;

/**Class to store data for an entry in the list.
 * An entry stores a string name, string description, and a date object.
 *
 */
public class ListEntry {

    //count for creating default names
    private static int entryCount = 1;

    //data for the entry
    private String name, desc;
    private Calendar date;

    public ListEntry() {
        name = "item" + entryCount++;
        desc = "";
        date = Calendar.getInstance();
    }

    public ListEntry(String name) {
        this.name = name;
        desc = "";
        date = Calendar.getInstance();
    }

    public void setName(String n) {
        name = n;
    }

    public void setDescription(String d) {
        desc = d;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return desc;
    }

    public void setDate(Calendar calendar) {
        date = calendar;
    }


    public String getStringDate() {
        String display = date.getDisplayName(Calendar.DATE, Calendar.SHORT, Locale.ENGLISH);
        display = display + "/" + date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
        display = display + "/" + date.getDisplayName(Calendar.YEAR, Calendar.SHORT, Locale.ENGLISH);
        return display;
    }

    /**Returns the Entry as a String.
     * The String representation of an Entry is its name.
     *
     * @return the name of the Entry as a String.
     */
    @Override
    public String toString() {
        return name;
    }
}
