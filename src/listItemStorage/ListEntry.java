package listItemStorage;

import java.util.Calendar;

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
    }

    public ListEntry(String name) {
        this.name = name;
        desc = "";
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

}
