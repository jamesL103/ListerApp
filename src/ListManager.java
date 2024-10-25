import listItemStorage.ListEntry;

import javax.swing.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**Class to allow manipulation of the data stored within Jlists displayed in the GUI.
 * The class provides methods to manipulate their ListModels when registered.
 */
public class ListManager {

    private final Map<JList<ListEntry>, DefaultListModel<ListEntry>> LIST_MODELS;

    public ListManager() {
        LIST_MODELS = new HashMap<>();
    }

    /**Registers the specified JList to the list of managed lists.
     * All JLists must be registered in an instance for their contents to be managed.
     *
     * @param list the JList to register
     */
    public void registerList(JList<ListEntry> list) {
        LIST_MODELS.put(list, new DefaultListModel<>());
    }

    /**Registers all lists in the specified collection.
     *
     * @param lists the lists to register.
     */
    public void registerAll(Collection<JList<ListEntry>> lists) {
        for (JList<ListEntry> l: lists) {
            registerList(l);
        }
    }

    /**Adds the specified entry to the end of the specified list.
     *
     * @param list the list to add to
     * @param entry the entry to add
     */
    public void addTo(JList<ListEntry> list, ListEntry entry) {
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list);
        model.addElement(entry);
    }

    /**Inserts the specified entry at the specified index of the specified list.
     *
     * @param list the list to add to
     * @param entry the entry to add
     * @param index the index to insert at
     */
    public void addTo(JList<ListEntry> list, ListEntry entry, int index) {
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list);
        model.add(index, entry);
    }

    /**Removes an entry in a list at specified index.
     *
     * @param list list to remove from
     * @param index index of element to remove
     */
    public void removeAt(JList<ListEntry> list, int index) {
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list);
        model.remove(index);
    }

    /**Removes a specified entry from a list.
     *
     * @param list list to remove from
     * @param entry element to remove
     */
    public void removeEntry(JList<ListEntry> list,ListEntry entry) {
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list);
        model.removeElement(entry);
    }

    public void initList(JList<ListEntry> list) {

    }

    public void initAll() {

    }


}
