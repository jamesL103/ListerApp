package javafxGui;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
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

    private final List<Node> CHILDREN;

    //displays number of entries
    private final Label COUNT;

    private final String NAME;

    public final String ID;

    private final ObservableList<ListEntry> LIST;
    private final ListView<ListEntry> VIEW;

    private final ReadOnlyIntegerProperty selectedIndex;
    private final ReadOnlyObjectProperty<ListEntry> selectedItem;
    private ApplicationRootNode.ListSelectionObserver selection;


    public ListPanel(String name, String id) {
        super();
        NAME = name;
        ID = id;
        CHILDREN = getChildren();

        Label title = new Label(name);
        title.getStyleClass().add("header");
        title.getStyleClass().add("bordered");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.BASELINE_LEFT);
        CHILDREN.add(title);

        COUNT = new Label("0 Entries");
        COUNT.getStyleClass().add("bordered");
        COUNT.setMaxWidth(Double.MAX_VALUE);
        CHILDREN.add(COUNT);

        ScrollPane scroll = new ScrollPane();
        VIEW = new ListView<>();
        selectedIndex = VIEW.getSelectionModel().selectedIndexProperty();
        selectedItem = VIEW.getSelectionModel().selectedItemProperty();
        selectedIndex.addListener((observable, oldVal, newVal) -> {
            if (newVal.intValue() >= 0) {
                selection.selectionChanged(this);
            }
        });
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

        CHILDREN.add(scroll);
    }

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public ReadOnlyIntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }

    public ListEntry getSelectedItem() {
        return selectedItem.get();
    }

    public ReadOnlyObjectProperty<ListEntry> selectedItemProperty() {
        return selectedItem;
    }

    public ObservableList<ListEntry> getList() {
        return LIST;
    }

    public void setChangeListener(ApplicationRootNode.ListSelectionObserver observer) {
        selection = observer;
    }

    public ListView<ListEntry> getListView() {
        return VIEW;
    }
}
