package javafxGui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    private final EditEntryObserver editEntry = new EditEntryObserver();

    private EntryDisplay currentView = null;

    private final Button DELETE = new Button("Delete Entry");;
    private final Button COMPLETE = new Button("Mark as Complete");


    public ApplicationRootNode() {
        super();
        NODE_LIST = getChildren();
        CLOSE_EDIT = () -> {
            LISTS.getChildren().remove(2);
            currentView = null;
            list1.getListView().getSelectionModel().clearSelection();
            list2.getListView().getSelectionModel().clearSelection();
            DELETE.setDisable(true);
        };
        addLists();
        addButtons();
    }

    private void addLists() {
        LISTS.setAlignment(Pos.CENTER);

        List<Node> listsChildren = LISTS.getChildren();

        ListSelectionObserver selection = new ListSelectionObserver();
        listsChildren.add(list1);
        list1.setChangeListener(selection);
        listsChildren.add(list2);
        list2.setChangeListener(selection);


        MANAGER.addList(list1);
        MANAGER.addList(list2);
        MANAGER.loadAll();


        NODE_LIST.add(LISTS);
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
            createNew.setObserver(entryCreate);
            changeView(createNew);
        });

        DELETE.setDisable(true);
        DELETE.setOnAction((e)-> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Entry? This action is permanent!");
            alert.showAndWait().ifPresent(result -> {
                if (result != ButtonType.OK) {
                    return;
                }
                int selectedIndex;
                String selectedList;
                if (list1.getSelectedIndex() >= 0) {
                    selectedIndex = list1.getSelectedIndex();
                    selectedList = list1.NAME;
                } else {
                    selectedIndex = list2.getSelectedIndex();
                    selectedList = list2.NAME;
                }
                MANAGER.deleteEntry(selectedIndex, selectedList);
            });
        });
        COMPLETE.setDisable(true);
        COMPLETE.setOnAction((e) -> {
            if (list1.getSelectedIndex() >= 0) {
                MANAGER.moveEntry(list1.getSelectedIndex(), list1.NAME, list2.NAME);
            }
        });

        children.add(create);
        children.add(DELETE);
        children.add(COMPLETE);

        NODE_LIST.add(buttonBox);
    }

    private void changeView(EntryDisplay display) {
        if (currentView instanceof CreateEntryPanel && display instanceof CreateEntryPanel) {
            currentView.setEntry(display.getEntry());
            ((CreateEntryPanel)currentView).setNewEntry(((CreateEntryPanel)display).isNewEntry());
            return;
        } else if (currentView instanceof ViewEntryPanel && display instanceof ViewEntryPanel) {
            currentView.setEntry(display.getEntry());
            return;
        }
        if (currentView != null) {
            LISTS.getChildren().remove(2);
        }
        display.setCloseButtonCallback(CLOSE_EDIT);
        LISTS.getChildren().add(display);
        currentView = display;
    }

    public class EntryCreateObserver {
        public void saveEntry(ListEntry entry, boolean newEntry) {
            if (newEntry) {
                MANAGER.addEntrySorted(entry, list1.NAME);
                System.out.println("Added entry '" + entry + "'");
            }
            ViewEntryPanel display = new ViewEntryPanel(entry, editEntry);
            changeView(display);
        }

        public void cancelEdit(ListEntry entry) {
            ViewEntryPanel display = new ViewEntryPanel(entry, editEntry);
            changeView(display);
        }
    }

    public class ListSelectionObserver {

        public void selectionChanged(ListPanel caller) {
            DELETE.setDisable(false);
            if (caller == list1) {
                list2.getListView().getSelectionModel().clearSelection();
                COMPLETE.setDisable(false);
            } else {
                list1.getListView().getSelectionModel().clearSelection();
                COMPLETE.setDisable(true);
            }
            ViewEntryPanel display = new ViewEntryPanel(caller.getSelectedItem(), editEntry);
            changeView(display);
        }
    }

    public class EditEntryObserver {

        public void editEntry(ListEntry entry) {
            CreateEntryPanel create = new CreateEntryPanel(entry, false);
            create.setObserver(entryCreate);
            changeView(create);
        }

    }



}
