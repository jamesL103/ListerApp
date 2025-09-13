package javafxGui;

import javafx.collections.ObservableList;
import listItemStorage.ListEntry;
import listItemStorage.ListFileReader;
import listItemStorage.ListFileWriter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListManager {

    //track all lists and their elements
    private final Map<String, ObservableList<ListEntry>> LISTS;
    public static final String LIST_DIR = System.getProperty("user.dir") + "/lists/";

    public ListManager() {
        LISTS = new HashMap<>();
    }

    public void addList(ListPanel list) {
        LISTS.put(list.ID, list.getList());
    }

    //load all lists from file
    //clears currently loaded lists
    public void loadAll() {
        for (String id: LISTS.keySet()) {
            ObservableList<ListEntry> list = LISTS.get(id);
            list.clear();
            File lstFile = new File(LIST_DIR + id);
            try {
                ListFileReader read = new ListFileReader(lstFile);
                list.addAll(read.loadFromFile());
            } catch (IOException e) {
                System.err.println("Error: couldn't load list " + id + " from file: " + e.getMessage());
                try {
                    System.out.println("Creating missing file...");
                    if (!lstFile.createNewFile()) {
                        // this should be impossible bc file should not already exist
                        System.out.println("Note: file " + lstFile.getName() + " already exists");
                    }
                } catch (IOException ioException){
                    System.err.println("Error: couldn't create list file + " + id + ": " + e.getMessage());
                }
            }
        }
    }

    public void addEntrySorted(ListEntry entry, String id) {
        List<ListEntry> list = LISTS.get(id);
        if (list == null) {
            return;
        }
        int i;
        int start = 0, end = list.size();
        while (start < end) {
            i = ((end - start) / 2 + start);
            if (entry.compareTo(list.get(i)) > 0) { //entry is later than the current element
                start = i + 1;
            } else if (entry.compareTo(list.get(i)) < 0) { //entry earlier than current element
                end = i - 1;
            } else { //entry is at same date
                list.add(i + 1, entry);
                System.out.println("Adding entry '" + entry.getName() + "' at index " + (i + 1) + " in '" + id + "'");
                saveList(id);
                return;
            }
        }

        if (start == list.size() || entry.compareTo(list.get(start)) < 0) {
            list.add(start, entry);
            System.out.println("Adding entry '" + entry.getName() + "' at index" + start + "in '" + id + "'");
        } else {
            System.out.println("Adding entry '" + entry.getName() + "' at index" + (start + 1) + "in '" + id + "'");
            list.add(start + 1, entry);
        }
        saveList(id);
    }

    public void deleteEntry(int index, String id) {
        System.out.println("Deleting entry at index " + index + " in '" + id + "'");
        List<ListEntry> list = LISTS.get(id);
        list.remove(index);
        saveList(id);
    }


    public void saveList(String id) {
        File file = new File(LIST_DIR + id);
        try {
            System.out.println("Saving list '" + id + "'");
            ListFileWriter writer = new ListFileWriter(file, LISTS.get(id));
            writer.writeList();
            System.out.println("List saved");
        } catch (IOException e) {
            System.err.println("Error saving list " + id + ": " + e.getMessage());
        }
    }

    //moves entry from list to the end of the target list
    public void moveEntry(int index, String fromList, String targetList) {
        List<ListEntry> from = LISTS.get(fromList);
        ListEntry entry = from.remove(index);
        System.out.printf("Moving entry '%s' from '%s' to '%s'%n", entry.getName(), fromList, targetList);
        List<ListEntry> target = LISTS.get(targetList);
        target.add(entry);
        saveList(fromList);
        saveList(targetList);
    }

}
