package javafxGui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import listItemStorage.ListEntry;

import java.util.ArrayList;
import java.util.List;

//panel with label and list
public class ListPanel extends VBox {

    private final List<Node> NODE_LIST;

    //displays number of entries
    private final Label COUNT;

    //unique name for identifying lists
    public final String NAME;

    private final ObservableList<ListEntry> LIST;
    private final ListView<ListEntry> VIEW;

    public ListPanel(String name) {
        super();
        NAME = name;
        NODE_LIST = getChildren();

        Label title = new Label(name);
        title.setFont(Fonts.TITLE);
        title.setAlignment(Pos.BASELINE_LEFT);
        NODE_LIST.add(title);

        COUNT = new Label("0 Entries");
        NODE_LIST.add(COUNT);

        ScrollPane scroll = new ScrollPane();
        VIEW = new ListView<>();
        scroll.setContent(VIEW);
        ArrayList<ListEntry> items = new ArrayList<>();
        VIEW.setItems(FXCollections.observableList(items));

        LIST = VIEW.getItems();
        LIST.addListener((ListChangeListener<? super ListEntry>) (change) -> { //add listener to the contents of the ListView
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    COUNT.setText(LIST.size() + " Entries");
                }
            }
        });

        NODE_LIST.add(scroll);
    }

    public ObservableList<ListEntry> getList() {
        return LIST;
    }

    public void setSelectionListener() {

    }




}
