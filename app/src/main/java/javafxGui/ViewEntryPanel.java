package javafxGui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import listItemStorage.ListEntry;
import util.Months;

import java.util.Calendar;
import java.util.List;

public class ViewEntryPanel extends EntryDisplay {

    private ListEntry entry;

    private final List<Node> NODE_LIST;

    private final Label NAME;
    private final TextArea DESCRIPTION;
    private final Label DATE;

    private final Button CLOSE;

    private Runnable closeCallback;
    private ApplicationRootNode.EditEntryObserver editEntry;

    public ViewEntryPanel(ListEntry entry) {
        super();
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.entry = entry;
        setSpacing(VERTICAL_SPACING);
        setPadding(PANEL_INSETS);
        NODE_LIST = getChildren();

        NAME = new Label(entry.getName());
        NAME.setFont(TITLE);
        CLOSE = new Button("x");
        CLOSE.setOnAction((e) -> close());


        HBox topBar = new HBox(NAME, CLOSE);
        topBar.setAlignment(Pos.CENTER);
        topBar.setSpacing(TOP_BAR_SPACING);
        NODE_LIST.add(topBar);

        Label desc = new Label("Description: ");
        NODE_LIST.add(desc);

        DESCRIPTION = new TextArea(entry.getDescription());
        DESCRIPTION.setEditable(false);
        DESCRIPTION.setFocusTraversable(false);
        DESCRIPTION.setPrefColumnCount(DESC_COLS);
        DESCRIPTION.setPrefRowCount(DESC_ROWS);
        NODE_LIST.add(DESCRIPTION);

        DATE = new Label("Due: ");
        NODE_LIST.add(DATE);
        setDate(entry.getDate());


        Button edit = new Button("Edit Entry");
        edit.setOnAction((e) -> {
            if (editEntry == null) {
                System.err.println("Error: Edit Button observer not set");
                return;
            }
            editEntry.editEntry(entry);
        });

        HBox buttons = new HBox(edit);
        buttons.setSpacing(BUTTON_SPACING);
        buttons.setPadding(BUTTON_PADDING);
        buttons.setBorder(BUTTON_BORDER);
        buttons.setAlignment(Pos.BASELINE_CENTER);
        NODE_LIST.add(buttons);

    }

    private void setDate(Calendar date) {
        int day = date.get(Calendar.DATE);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);

        DATE.setText("Due: " + Months.LIST.get(month) + " " + day + ", " + year);
    }

    private void close() {
        if (closeCallback != null){
            closeCallback.run();
        }
    }

    @Override
    public void setCloseButtonCallback(Runnable callback) {
        closeCallback = callback;
    }

    public void setEditButtonObserver(ApplicationRootNode.EditEntryObserver observer) {
        editEntry = observer;
    }

    @Override
    public ListEntry getEntry() {
        return entry;
    }

    @Override
    public void setEntry(ListEntry entry) {
        this.entry = entry;
        NAME.setText(entry.getName());
        DESCRIPTION.setText(entry.getDescription());
        setDate(entry.getDate());
    }

}
