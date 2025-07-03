package javafxGui;

import com.sun.javafx.fxml.builder.JavaFXFontBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import listItemStorage.ListEntry;

import java.util.List;

//panel with label and list
public class ListPanel extends VBox {

    private final List<Node> NODE_LIST;

    private final Label COUNT;

    public final String NAME;

    private final ObservableList<ListEntry> LIST;

    public ListPanel(String name) {
        super();
        NAME = name;
        NODE_LIST = getChildren();

        Label title = new Label(name);
        title.setFont(new Font(30));
        title.setAlignment(Pos.BASELINE_LEFT);
        NODE_LIST.add(title);

        COUNT = new Label("0 Entries");
        NODE_LIST.add(COUNT);

        ScrollPane scroll = new ScrollPane();
        ListView<ListEntry> list = new ListView<>();
        scroll.setContent(list);
        list.setItems(FXCollections.observableList(List.of(ListEntry.DEFAULT_ENTRY)));

        LIST = list.getItems();

        NODE_LIST.add(scroll);
    }

    public ObservableList<ListEntry> getList() {
        return LIST;
    }



}
