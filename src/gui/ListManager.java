package gui;

import listItemStorage.ListEntry;
import listItemStorage.ListFileReader;
import listItemStorage.ListFileWriter;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**Class to allow manipulation of the data stored within Jlists displayed in the GUI.
 * The class provides methods to manipulate their ListModels when registered, and to
 * have that data stored in a file.
 * Any list in the gui must be registered in the manager before it's data can be changed
 * and stored.
 * A file will be created for every list if one does not already exist.
 *
 */
public class ListManager {

    //determines if lists are saved to file after every change
    private final boolean AUTOSAVE = true;

    //private class to store a DefaultListModel, a string name of a list, and an ID number
    private static class List_t {
        private static int count = 0;
        public DefaultListModel<ListEntry> model;
        public String name;
        public final int ID;

        public List_t(DefaultListModel<ListEntry> m, String n) {
            model = m;
            name = n;
            ID = count++;
        }

        public List_t(DefaultListModel<ListEntry> m) {
            model = m;
            ID = count++;
            name = "list" + ID;
        }
    }

    private final Map<JList<ListEntry>, List_t> LIST_MODELS;

    private final String DIR_LIST;

    public ListManager() {
        DIR_LIST = System.getProperty("user.dir") + "/lists";
        LIST_MODELS = new LinkedHashMap<>();
    }

    /**Registers the specified JList to the list of managed lists.
     * All JLists must be registered in an instance for their contents to be managed.
     * Will give the list's file the default name.
     *
     * @param list the JList to register
     */
    public void registerList(JList<ListEntry> list) {
        LIST_MODELS.put(list, new List_t((DefaultListModel<ListEntry>) list.getModel()));
    }

    /**Registers the specified JList to the list of managed lists.
     * All JLists must be registered in an instance for their contents to be managed.
     * Will give the list's file the specified name.
     *
     * @param list the JList to register
     * @param name the name of the list
     */
    public void registerList(JList<ListEntry> list, String name) {
        LIST_MODELS.put(list, new List_t((DefaultListModel<ListEntry>) list.getModel(), name));
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

    /**Updates the entry count display for the specified list.
     * Called automatically when changes to a list are made.
     * @param list the list to update the count of.
     */
    public void updateListCount(ScrollTaskList list) {
        list.updateCount();
    }

    /**Adds the specified entry to the end of the specified list.
     *
     * @param lstPanel the list to add to
     * @param entry the entry to add
     */
    public void addTo(ScrollTaskList lstPanel, ListEntry entry) {
        JList<ListEntry> list = lstPanel.LIST;
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
        model.addElement(entry);
        updateListCount(lstPanel);
        autosave(list);
    }

    /**Inserts the specified item at the specified index of the specified list.
     *
     * @param lstPanel the list to add to
     * @param entry the item to add
     * @param index the index to insert at
     */
    public void addTo(ScrollTaskList lstPanel, ListEntry entry, int index) {
        JList<ListEntry> list = lstPanel.LIST;
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
        model.add(index, entry);
        updateListCount(lstPanel);
        autosave(list);
    }

    /**Removes an item in a list at specified index.
     *
     * @param lstPanel list to remove from
     * @param index index of element to remove
     */
    public void removeAt(ScrollTaskList lstPanel, int index) {
        JList<ListEntry> list = lstPanel.LIST;
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
        model.remove(index);
        updateListCount(lstPanel);
        autosave(list);
    }

    /**Removes a specified item from a list.
     *
     * @param lstPanel list to remove from
     * @param entry element to remove
     */
    public void removeEntry(ScrollTaskList lstPanel,ListEntry entry) {
        JList<ListEntry> list = lstPanel.LIST;
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
        model.removeElement(entry);
        updateListCount(lstPanel);
        autosave(list);
    }

    /**Moves an entry from one list to another.
     *
     * @param source the original list
     * @param target the list to move the entry to
     * @param entry the entry to move
     */
    public void moveEntry(ScrollTaskList source, ScrollTaskList target, ListEntry entry) {
        removeEntry(source, entry);
        addTo(target, entry);
    }

    /**Initializes the elements in the specified gui JList to be displayed from a file.
     * Will read from the file that was registered alongside the list.
     * If the specified file does not exist, a blank file will be created and
     * the list will be left empty.
     *
     * @param list the list to initialize
     */
    public void loadListFromFile(JList<ListEntry> list) {
        String filePath = DIR_LIST + "/" + LIST_MODELS.get(list).name;
        try {
            ListFileReader read = new ListFileReader(new File(filePath));
            List<ListEntry> lst = read.loadFromFile();
            DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
            for (ListEntry entry: lst) {
                model.addElement(entry);
            }
        } catch (FileNotFoundException e) {
            File fin = new File(filePath);
            try {
                if (!(fin.createNewFile())) {
                    System.out.println("Couldn't create file " + filePath);
                }
            } catch (IOException fail) {
                System.out.println("Error initializing list '" + LIST_MODELS.get(list).name + "'");
            }
        }
    }

    /**Initializes every registered list from their files.
     * If a list's file does not exist, a new file will be created
     * and the list will be left empty.
     *
     */
    public void loadAllLists() {
        for (JList<ListEntry> list : LIST_MODELS.keySet()) {
            loadListFromFile(list);
        }
    }

    //if AUTOSAVE is true, will write the list that is updated to its file
    private void autosave(JList<ListEntry> list) {
        if (AUTOSAVE) {
            save(list);
        }
    }

    /**Saves the specified list to its file.
     *
     * @param lstPanel the list to save
     */
    public void save(ScrollTaskList lstPanel) {
        save(lstPanel.LIST);
    }

    /**Saves the specified list to its file.
     * Internal helper that takes a JList as parameter
     *
     * @param list the list to save
     */
    private void save(JList<ListEntry> list) {
        String pathname = DIR_LIST + "/" + LIST_MODELS.get(list).name;
        File fout = new File(pathname);
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
        try {
            ListFileWriter write = new ListFileWriter(fout, model);
            write.writeList();
        } catch (IOException e) {
            System.out.println("Cannot open file " + pathname);
            System.out.println(e.getMessage());
        }
    }


}
