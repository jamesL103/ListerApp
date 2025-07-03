package javafxGui;

import com.sun.javafx.fxml.builder.JavaFXFontBuilder;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

//panel with label and list
public class ListPanel extends VBox {

    private final List<Node> NODE_LIST;

    public ListPanel(String name) {
        super();

        NODE_LIST = getChildren();

        Label title = new Label(name);
        title.setFont(new Font(30));
        title.setAlignment(Pos.BASELINE_LEFT);

        NODE_LIST.add(title);

        ScrollPane scroll = new ScrollPane();
        ListView<String> list = new ListView<>();
        scroll.setContent(list);
        list.setItems(FXCollections.observableList(List.of("default")));

        NODE_LIST.add(scroll);
    }

}
