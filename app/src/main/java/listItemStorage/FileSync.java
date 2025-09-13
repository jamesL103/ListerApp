package listItemStorage;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafxGui.ListManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileSync implements Observable {

    public static final Path TEMP_FILE = Path.of(System.getProperty("user.dir") + "/temp/sync_res");

    private final List<InvalidationListener> LISTENERS = new ArrayList<>();

    //write temp file data to applicable list files
    public void updateLists() {
        File in = TEMP_FILE.toFile();
        try (FileReader reader = new FileReader(in)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject body = new JSONObject(tokener);
            JSONArray todo = body.getJSONObject("list").getJSONArray("data");
            JSONArray complete = body.getJSONObject("complete").getJSONArray("data");
            writeToListFile(todo, Path.of(ListManager.LIST_DIR + "todo"));
            writeToListFile(complete, Path.of(ListManager.LIST_DIR + "resolved"));

            for (InvalidationListener listener: LISTENERS) {
                listener.invalidated(this);
            }
        } catch (IOException e) {
            System.err.println("Error: cannot load temp server sync file: " + e.getMessage());
        }
    }

    public void writeToListFile(JSONArray listData, Path list) {
        System.out.println("Writing from temp to " + list.toString());
        byte[] buffer = JSONByteArray(listData);
        try (ListByteWriter write = new ListByteWriter(list.toFile(), buffer)){
            write.write();
            System.out.println(list + " updated successfully");
        } catch (IOException e) {
            System.err.println("Error writing to " + list + ": " + e.getMessage());
        }
    }

    public static byte[] JSONByteArray(JSONArray array) {
        byte[] buffer = new byte[array.length()];
        for (int i = 0; i < array.length(); i ++) {
            byte b = (byte)(array.getInt(i));
            buffer[i] = b;
        }
        return buffer;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        LISTENERS.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        LISTENERS.remove(invalidationListener);
    }
}
