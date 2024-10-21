package listItemStorage;

import java.util.Calendar;
import java.util.Locale;

/**Class to store data for an entry in the list.
 *
 */
public class ListEntry {

    //count for creating default names
    private static int entryCount = 1;

    //data for the entry
    private String name, desc;
    private Calendar dueDate;

    public ListEntry() {
        name = "item" + entryCount++;
        desc = "";
        dueDate = Calendar.getInstance();
    }

    public ListEntry(String name) {
        this.name = name;
        desc = "";
        dueDate = Calendar.getInstance();
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

    public String getStringDueDate() {
        String display = dueDate.getDisplayName(Calendar.DATE, Calendar.SHORT, Locale.ENGLISH);
        display = display + "/" + dueDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
        display = display + "/" + dueDate.getDisplayName(Calendar.YEAR, Calendar.SHORT, Locale.ENGLISH);
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
