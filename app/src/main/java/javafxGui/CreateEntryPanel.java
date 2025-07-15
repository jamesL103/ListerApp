package javafxGui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import listItemStorage.ListEntry;
import util.Months;

import java.util.Calendar;
import java.util.List;

public class CreateEntryPanel extends EntryDisplay {

    private ListEntry entry;
    private boolean isNewEntry;

    private final List<Node> NODE_LIST;

    private final TextField NAME;
    private final TextArea DESCRIPTION;

    private final TextField DAY;
    private final ComboBox<String> MONTH;
    private final TextField YEAR;

    private final Button CANCEL;
    private final Button SAVE;

    private Runnable closeCallback;

    private ApplicationRootNode.EntryCreateObserver observer;

    public CreateEntryPanel(ListEntry entry, boolean newEntry) {
        super();
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.entry = entry;
        isNewEntry = newEntry;
        setSpacing(VERTICAL_SPACING);
        setPadding(PANEL_INSETS);
        NODE_LIST = getChildren();

        NAME = new TextField(entry.getName());
        NAME.setFont(TITLE);

        HBox topBar = new HBox(NAME, CLOSE);
        topBar.setAlignment(Pos.CENTER);
        topBar.setSpacing(TOP_BAR_SPACING);
        NODE_LIST.add(topBar);

        Label desc = new Label("Description: ");
        NODE_LIST.add(desc);

        DESCRIPTION = new TextArea(entry.getDescription());
        DESCRIPTION.setPromptText("enter description");
        DESCRIPTION.setPrefColumnCount(DESC_COLS);
        DESCRIPTION.setPrefRowCount(DESC_ROWS);
        NODE_LIST.add(DESCRIPTION);

        Label date = new Label("Date: ");
        NODE_LIST.add(date);


        DAY = new TextField("0");
        DAY.setPromptText("dd");
        DAY.setPrefColumnCount(2);
        MONTH = new ComboBox<>();
        MONTH.setItems(Months.LIST);
        MONTH.setValue(Months.LIST.get(0));
        YEAR = new TextField("0");
        YEAR.setPromptText("yyyy");
        YEAR.setPrefColumnCount(4);

        HBox dateEdit = new HBox(DAY, MONTH, YEAR);
        dateEdit.setPadding(DATE_PADDING);
        dateEdit.setSpacing(10);
        dateEdit.setAlignment(Pos.BASELINE_CENTER);
        NODE_LIST.add(dateEdit);
        setDate(entry.getDate());

        SAVE = new Button("Save");
        CANCEL = new Button("Cancel");
        CANCEL.setOnAction((e) -> close());

        HBox buttons = new HBox(SAVE, CANCEL);
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

        DAY.setText(String.valueOf(day));
        MONTH.setValue(Months.LIST.get(month));
        YEAR.setText(String.valueOf(year));
    }

    protected void close() {
        if (closeCallback != null){
            closeCallback.run();
        }
    }

    public void setNewEntry(boolean newEntry) {
        isNewEntry = newEntry;
    }

    public boolean isNewEntry() {
        return isNewEntry;
    }

    @Override
    public void setCloseButtonCallback(Runnable callback) {
        closeCallback = callback;
    }


    @Override
    public void setEntry(ListEntry entry) {
        this.entry = entry;
        NAME.setText(entry.getName());
        DESCRIPTION.setText(entry.getDescription());
        setDate(entry.getDate());
    }

    @Override
    public ListEntry getEntry() {
        return entry;
    }

    public void setObserver(ApplicationRootNode.EntryCreateObserver observer) {
        this.observer = observer;
        SAVE.setOnAction((e) -> {
            entry.setName(NAME.getText());
            entry.setDescription(DESCRIPTION.getText());

            Calendar cal = entry.getDate();
            cal.set(Calendar.DATE, Integer.parseInt(DAY.getText()));
            cal.set(Calendar.MONTH, MONTH.getSelectionModel().getSelectedIndex());
            cal.set(Calendar.YEAR, Integer.parseInt(YEAR.getText()));


            this.observer.saveEntry(entry, isNewEntry);
        });
    }



}
