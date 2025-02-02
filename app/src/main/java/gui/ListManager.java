package gui;

import listItemStorage.ListEntry;
import listItemStorage.ListFileReader;
import listItemStorage.ListFileWriter;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        public String name;
        public final int ID;

        public List_t(String n) {
            name = n;
            ID = count++;
        }

        public List_t() {
            ID = count++;
            name = "list" + ID;
        }
    }

    private final Map<ScrollListPanel, List_t> LIST_MODELS;

    private final String DIR_LIST;

    public ListManager() {
        DIR_LIST = System.getProperty("user.dir") + "/lists";
        LIST_MODELS = new LinkedHashMap<>();
        Path listDirPath = Path.of(DIR_LIST);
        if (!Files.exists(listDirPath)) {
            try {
                Files.createDirectory(listDirPath);
            } catch (IOException e) {
                System.out.println("ERROR: Couldn't create directory for storing lists.");
            }
        }
    }

    /**Registers the specified JList to the list of managed lists.
     * All JLists must be registered in an instance for their contents to be managed.
     * Will give the list's file the default name.
     *
     * @param list the JList to register
     */
    public void registerList(ScrollListPanel list) {
        LIST_MODELS.put(list, new List_t());
    }

    /**Registers the specified JList to the list of managed lists.
     * All JLists must be registered in an instance for their contents to be managed.
     * Will give the list's file the specified name.
     *
     * @param list the JList to register
     * @param name the name of the list
     */
    public void registerList(ScrollListPanel list, String name) {
        LIST_MODELS.put(list, new List_t(name));
    }


    /**Updates the entry count display for the specified list.
     * Called automatically when changes to a list are made.
     * @param list the list to update the count of.
     */
    public void updateListCount(ScrollListPanel list) {
        list.updateCount();
    }

    /**Adds the specified entry into a list sorted in ascending order of the entries' due dates.
     * The entry will be inserted at the appropriate index.
     *
     * @param lstPanel the list to insert into
     * @param entry the entry to insert
     */
    public void addSorted(ScrollListPanel lstPanel, ListEntry entry) {
        DefaultListModel<ListEntry> model = lstPanel.MODEL;
        int middle_index = model.getSize()/2;
        //check if the inserted entry has an earlier date than the entry in the middle of the list
        //from the start of the list, find the first element greater than the entry
        if (entry.getDate().compareTo(model.get(middle_index).getDate()) < 0) {
            for (int i = 0; i < model.getSize(); i ++) {
                if (entry.getDate().compareTo(model.get(i).getDate()) <= 0) {
                    model.insertElementAt(entry, i);
                    return;
                }
            }
        } else { //otherwise start from the end of the list and work backwards
            for (int i = model.getSize() - 1; i >= 0; i --) {
                if (entry.getDate().compareTo(model.get(i).getDate()) >= 0) {
                    model.insertElementAt(entry, i + 1);
                    return;
                }
            }
        }
    }

    /**Adds the specified entry to the end of the specified list.
     *
     * @param lstPanel the list to add to
     * @param entry the entry to add
     */
    public void addTo(ScrollListPanel lstPanel, ListEntry entry) {
       addTo(lstPanel, entry, lstPanel.MODEL.getSize());
    }

    /**Inserts the specified item at the specified index of the specified list.
     *
     * @param lstPanel the list to add to
     * @param entry the item to add
     * @param index the index to insert at
     */
    public void addTo(ScrollListPanel lstPanel, ListEntry entry, int index) {
        DefaultListModel<ListEntry> model = lstPanel.MODEL;
        model.add(index, entry);
        updateListCount(lstPanel);
        autosave(lstPanel);
    }

    /**Removes an item in a list at specified index.
     *
     * @param lstPanel list to remove from
     * @param index index of element to remove
     */
    public void removeAt(ScrollListPanel lstPanel, int index) {
        DefaultListModel<ListEntry> model = lstPanel.MODEL;
        model.remove(index);
        updateListCount(lstPanel);
        autosave(lstPanel);
    }

    /**Removes a specified item from a list.
     *
     * @param lstPanel list to remove from
     * @param entry element to remove
     */
    public void removeEntry(ScrollListPanel lstPanel, ListEntry entry) {
        DefaultListModel<ListEntry> model = lstPanel.MODEL;
        model.removeElement(entry);
        updateListCount(lstPanel);
        autosave(lstPanel);
    }

    /** Moves and sorts the specified entry to the correct spot in the list.
     * The list must contain the entry when calling this method.
     *
     * @param lstPanel the list containing the entry
     * @param entry the entry to sort
     */
    public void sortEntry(ScrollListPanel lstPanel, ListEntry entry) {
         lstPanel.MODEL.removeElement(entry);
         addSorted(lstPanel, entry);
    }

    /**Moves an entry from one list to another.
     *
     * @param source the original list
     * @param target the list to move the entry to
     * @param entry the entry to move
     */
    public void moveEntry(ScrollListPanel source, ScrollListPanel target, ListEntry entry) {
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
    public void loadListFromFile(ScrollListPanel list) {
        String filePath = DIR_LIST + "/" + LIST_MODELS.get(list).name;
        try {
            ListFileReader read = new ListFileReader(new File(filePath));
            List<ListEntry> lst = read.loadFromFile();
            DefaultListModel<ListEntry> model = list.MODEL;
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
        for (ScrollListPanel list : LIST_MODELS.keySet()) {
            loadListFromFile(list);
        }
    }

    //if AUTOSAVE is true, will write the list that is updated to its file
    private void autosave(ScrollListPanel list) {
        if (AUTOSAVE) {
            save(list);
        }
    }

    /**Saves the specified list to its file.
     *
     * @param list the list to save
     */
    public void save(ScrollListPanel list) {
        String pathname = DIR_LIST + "/" + LIST_MODELS.get(list).name;
        File fout = new File(pathname);
        DefaultListModel<ListEntry> model = list.MODEL;
        try {
            ListFileWriter write = new ListFileWriter(fout, model);
            write.writeList();
        } catch (IOException e) {
            System.out.println("ERROR: Cannot open file " + pathname);
            System.out.println(e.getMessage());
        }
    }


}
