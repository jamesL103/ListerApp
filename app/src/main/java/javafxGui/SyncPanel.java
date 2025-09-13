package javafxGui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import network.SyncManager;

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

    private Mode mode = Mode.NO_SYNC;

    private int id = -1;

    private enum Mode {
        NO_SYNC,
        SYNC_ENABLED,
        SYNCING
    }

    public SyncPanel() {
        super();

        List<Node> NODES = ROOT.getChildren();
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
        }
    }

    private Button activateSync() {
        Button button = new Button("Activate Sync");
        button.setOnAction((e) -> {
            Dialog<String> dialog = new TextInputDialog();
            dialog.setContentText("Enter your generated ID from the Lister Web App.");

            try {
                int id = 0;
                Optional<String> result = dialog.showAndWait();
                if (result.isEmpty()) {
                    return;
                }
                id = Integer.parseInt(result.get());
                try {
                    HttpResponse<Path> res = MANAGER.getDataFromServer(id).get();
                    System.out.println(res);
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

}
