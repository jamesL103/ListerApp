package javafxGui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

public class SyncPanel extends Group {

    private final Pane ROOT = new HBox();

    private final Text STATUS = new Text("Disabled");


    public SyncPanel() {
        super();

        List<Node> NODES = ROOT.getChildren();
        Text sync = new Text("Sync: ");
        NODES.add(sync);
        NODES.add(STATUS);

        getChildren().add(ROOT);
    }

}
