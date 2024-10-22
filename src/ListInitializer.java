import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**Class to initialize the Task lists in the app.
 * Provides methods that load the elements of a list into
 * ScrollTaskLists from files.
 *
 */
public class ListInitializer {



    public ListInitializer() {

    }


    //initializes a specified list by loading from a specified file path
    public static void initLists(ScrollTaskList list, String path) {
        File listFile = new File(path);
        try {
            Scanner read = new Scanner(listFile);
        } catch (FileNotFoundException e) {
            //try to create a blank file if cannot read from specified path
            try {
                if (!listFile.createNewFile()) {
                    //error message
                    JDialog err = new JDialog();
                    err.setSize(new Dimension(100, 50));
                    err.setVisible(true);
                }
                //if can't create a file, raise error
            } catch (IOException createE) {
                //error message
            }
        }
    }

    public static void initLists() {

    }

}
