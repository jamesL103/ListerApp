package javafxGui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ApplicationRootNode extends VBox { //vertical box

    private final ListPanel list1 = new ListPanel("To-do");
    private final ListPanel list2 = new ListPanel("Resolved");

    private final ListManager MANAGER = new ListManager();

    public ApplicationRootNode() {
        super();

        addLists();
        addButtons();
    }

    private void addLists() {
        HBox listBox = new HBox();
        listBox.setAlignment(Pos.BASELINE_CENTER);

        List<Node> listsChildren = listBox.getChildren();

        listsChildren.add(list1);
        listsChildren.add(list2);

        MANAGER.addList(list1);
        MANAGER.addList(list2);
        MANAGER.loadAll();


        getChildren().add(listBox);
    }

    private void addButtons() {
        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);

        List<Node> children = buttonBox.getChildren();

        Button create = new Button("Create New Entry");
        Button delete = new Button("Delete Entry");
        Button complete = new Button("Mark as Complete");

        children.add(create);
        children.add(delete);
        children.add(complete);

        getChildren().add(buttonBox);
    }

}
