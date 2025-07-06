package javafxGui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import listItemStorage.ListEntry;

import java.util.List;

public class ApplicationRootNode extends VBox { //vertical box

    private final List<Node> NODE_LIST;

    private final ListPanel list1 = new ListPanel("To-do");
    private final ListPanel list2 = new ListPanel("Resolved");
    private final HBox LISTS = new HBox();

    private final Runnable CLOSE_EDIT;

    private final ListManager MANAGER = new ListManager();

    private final EntryCreateObserver entryCreate = new EntryCreateObserver();

    public ApplicationRootNode() {
        super();
        NODE_LIST = getChildren();
        CLOSE_EDIT = () -> {
            LISTS.getChildren().remove(2);
        };
        addLists();
        addButtons();
    }

    private void addLists() {
        LISTS.setAlignment(Pos.CENTER);

        List<Node> listsChildren = LISTS.getChildren();

        listsChildren.add(list1);
        listsChildren.add(list2);


        MANAGER.addList(list1);
        MANAGER.addList(list2);
        MANAGER.loadAll();


        getChildren().add(LISTS);
    }

    private void addButtons() {
        HBox buttonBox = new HBox(5);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));
        buttonBox.setAlignment(Pos.BASELINE_CENTER);

        List<Node> children = buttonBox.getChildren();

        Button create = new Button("Create New Entry");
        create.setOnAction((event) -> {
            ListEntry entry = new ListEntry();
            CreateEntryPanel createNew = new CreateEntryPanel(entry, true);
            createNew.setCloseButtonCallback(CLOSE_EDIT);
            createNew.setObserver(entryCreate);
            LISTS.getChildren().add(createNew);
        });


        Button delete = new Button("Delete Entry");
        Button complete = new Button("Mark as Complete");

        children.add(create);
        children.add(delete);
        children.add(complete);

        getChildren().add(buttonBox);
    }

    public class EntryCreateObserver {
        public void saveEntry(ListEntry entry, boolean newEntry) {
            if (newEntry) {
                MANAGER.addEntrySorted(entry, list1.NAME);
                System.out.println("Adding entry '" + entry + "'");
            }
            System.out.println("Saving list 1");
            MANAGER.saveList(list1.NAME);
        }
    }


}
