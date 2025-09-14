package javafxGui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import listItemStorage.FileSync;
import network.SyncManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class SyncPanel extends Group {

    private final Pane ROOT = new HBox();

    private final Text STATUS = new Text("Disabled");
    private final Pane INTERACTION = new HBox();

    private final SyncManager MANAGER = new SyncManager();
    private final FileSync SYNC;

    private Mode mode = Mode.NO_SYNC;

    private int id = -1;

    private enum Mode {
        NO_SYNC,
        SYNC_ENABLED,
        SYNCING
    }

    public SyncPanel(FileSync synchronizer) {
        super();

        List<Node> NODES = ROOT.getChildren();
        SYNC = synchronizer;
        Text sync = new Text("Sync: ");
        NODES.add(sync);
        NODES.add(STATUS);
        NODES.add(INTERACTION);
        getChildren().add(ROOT);
        setDisplay();
    }

    private void updateMode(Mode mode) {
        this.mode = mode;
        setDisplay();
    }

    //set status message, buttons
    private void setDisplay() {
        if (mode == Mode.NO_SYNC) {
            STATUS.setText("Disabled");
            List<Node> children = INTERACTION.getChildren();
            children.clear();
            children.add(activateSync());
        } else if (mode == Mode.SYNC_ENABLED) {
            STATUS.setText("Enabled");
            List<Node> children = INTERACTION.getChildren();
            children.clear();
            children.add(resync());
        } else if (mode == Mode.SYNCING) {
            STATUS.setText("fetching data");
            List<Node> children = INTERACTION.getChildren();
            children.clear();
        }
    }

    private Button activateSync() {
        Button button = new Button("Activate Sync");
        button.setOnAction((e) -> {
            Dialog<String> dialog = new TextInputDialog();
            dialog.setContentText("Enter your generated ID from the Lister Web App.");
            try { //check if string input is a valid int
                Optional<String> result = dialog.showAndWait();
                if (result.isEmpty()) {
                    return;
                }
                this.id = Integer.parseInt(result.get());
                try { //id is valid
                    //request list data from server
                    updateMode(Mode.SYNCING);
                    HttpResponse<Path> res = MANAGER.getDataFromServer(id).get();
                    System.out.println(res);
                    if (res.statusCode() == SyncManager.NO_LIST_FOUND) {
                        return;
                    }
                    SYNC.updateLists();
                    updateMode(Mode.SYNC_ENABLED);
                } catch (ExecutionException ex) {
                    System.err.println("Error retrieving server data result: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (NumberFormatException err) {
                System.err.println("Error parsing input: " + err.getMessage());
            }
        });


        return button;
    }

    private Button resync() {
        Button button = new Button("Save changes to server");
        button.setOnAction(e -> {
            File todo = new File(ListManager.LIST_DIR + "todo");
            File resolved = new File(ListManager.LIST_DIR + "resolved");
            JSONArray listTodo, listResolved;
            try {
                listTodo = SYNC.toJson(todo);
                listResolved = SYNC.toJson(resolved);
            } catch (IOException err) {
                System.err.println("Couldn't load list files: " + err.getMessage());
                return;
            }

            JSONObject body = new JSONObject();
            body
                    .put("id", id)
                    .put("operation", "sendData")
                    .put("todo", listTodo)
                    .put("resolved", listResolved);

            try {
                HttpResponse<String> res = MANAGER.sendData(body).get();
                System.out.println(res);
                System.out.println(res.body());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (ExecutionException ex) {
                System.err.println("Error sending list data: " + ex.getMessage());
            }
        });

        return button;
    }


}
