package javafxGui;

import javafx.collections.ObservableList;
import listItemStorage.ListEntry;
import listItemStorage.ListFileReader;
import listItemStorage.ListFileWriter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ListManager {

    //track all lists and their elements
    private final Map<String, ObservableList<ListEntry>> LISTS;
    public final String LIST_DIR;

    public ListManager() {
        LISTS = new HashMap<>();

        LIST_DIR = System.getProperty("user.dir") + "/lists/";
    }

    public void addList(ListPanel list) {
        LISTS.put(list.NAME, list.getList());
    }

    //load all lists from file
    public void loadAll() {
        for (String name: LISTS.keySet()) {
            ObservableList<ListEntry> list = LISTS.get(name);
            File lstFile = new File(LIST_DIR + name);
            try {
                ListFileReader read = new ListFileReader(lstFile);
                list.addAll(read.loadFromFile());
            } catch (IOException e) {
                System.err.println("Error: couldn't load list " + name + " from file: " + e.getMessage());
                try {
                    lstFile.createNewFile();
                } catch (IOException ioException){
                    System.err.println("Error: couldn't create list file + " + name + ": " + e.getMessage());
                }
            }
        }
    }

    public void addEntrySorted(ListEntry entry, String name) {

    }


    public void saveList(String name) {
        File file = new File(LIST_DIR + name);
        try {
            ListFileWriter writer = new ListFileWriter(file, LISTS.get(name));
            writer.writeList();
        } catch (IOException e) {
            System.err.println("Error saving list " + name + ": " + e.getMessage());
        }
    }

}
