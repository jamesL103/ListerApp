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
 * The class provides methods to manipulate their ListModels when registered.
 *
 */
public class ListManager {

    //determines if lists are saved to file after every change
    private final boolean AUTOSAVE = true;

    //private class to store a DefaultListModel and a string name of a list
    private static class ListRec {
        public static int count = 0;
        public DefaultListModel<ListEntry> model;
        public String name;

        public ListRec(DefaultListModel<ListEntry> mod, String n) {
            model = mod;
            name = n;
        }
    }

    private final Map<JList<ListEntry>, ListRec> LIST_MODELS;

    private final String LIST_DIR = "lists";

    public ListManager() {
        LIST_MODELS = new LinkedHashMap<>();
    }

    /**Registers the specified JList to the list of managed lists.
     * All JLists must be registered in an instance for their contents to be managed.
     * Will give the list's file the default name.
     *
     * @param list the JList to register
     */
    public void registerList(JList<ListEntry> list) {
        LIST_MODELS.put(list, new ListRec((DefaultListModel<ListEntry>) list.getModel(), "list" + ListRec.count++));
    }

    /**Registers the specified JList to the list of managed lists.
     * All JLists must be registered in an instance for their contents to be managed.
     * Will give the list's file the specified name.
     *
     * @param list the JList to register
     * @param name the name of the list
     */
    public void registerList(JList<ListEntry> list, String name) {
        LIST_MODELS.put(list, new ListRec((DefaultListModel<ListEntry>) list.getModel(), name));
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
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
        model.addElement(entry);
        autosave(list);
    }

    /**Inserts the specified item at the specified index of the specified list.
     *
     * @param list the list to add to
     * @param entry the item to add
     * @param index the index to insert at
     */
    public void addTo(JList<ListEntry> list, ListEntry entry, int index) {
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
        model.add(index, entry);
        autosave(list);
    }

    /**Removes an item in a list at specified index.
     *
     * @param list list to remove from
     * @param index index of element to remove
     */
    public void removeAt(JList<ListEntry> list, int index) {
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
        model.remove(index);
        autosave(list);
    }

    /**Removes a specified item from a list.
     *
     * @param list list to remove from
     * @param entry element to remove
     */
    public void removeEntry(JList<ListEntry> list,ListEntry entry) {
        DefaultListModel<ListEntry> model = LIST_MODELS.get(list).model;
        model.removeElement(entry);
        autosave(list);
    }

    /**Initializes the elements in the specified gui JList to be displayed from a file.
     * Will read from the file that was registered alongside the list.
     * If the specified file does not exist, a blank file will be created and
     * the list will be left empty.
     *
     * @param list the list to initialize
     */
    public void initList(JList<ListEntry> list) {
        String filePath = LIST_DIR + "/" + LIST_MODELS.get(list).name;
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
    public void initAll() {
        for (JList<ListEntry> list : LIST_MODELS.keySet()) {
            initList(list);
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
     * @param list the list to save
     */
    public void save(JList<ListEntry> list) {
        String pathname = LIST_DIR + "/" + LIST_MODELS.get(list).name;
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
