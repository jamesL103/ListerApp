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

public class CreateEntryPanel extends VBox {


    private final List<Node> NODE_LIST;

    private final TextField NAME;
    private final TextArea DESCRIPTION;

    private final TextField DAY;
    private final ComboBox<String> MONTH;
    private final TextField YEAR;

    public CreateEntryPanel() {
        this(ListEntry.DEFAULT_ENTRY);
    }

    public CreateEntryPanel(ListEntry entry) {
        super();
        setSpacing(10);
        NODE_LIST = getChildren();

        NAME = new TextField(entry.getName());
        NODE_LIST.add(NAME);

        Label desc = new Label("Description: ");
        NODE_LIST.add(desc);

        DESCRIPTION = new TextArea(entry.getDescription());
        DESCRIPTION.setPromptText("enter description");
        DESCRIPTION.setPrefColumnCount(60);
        DESCRIPTION.setPrefRowCount(5);
        NODE_LIST.add(DESCRIPTION);

        Label date = new Label("Date: ");
        NODE_LIST.add(date);


        DAY = new TextField("0");
        DAY.setPrefColumnCount(2);
        MONTH = new ComboBox<>();
        MONTH.setItems(Months.LIST);
        MONTH.setValue(Months.LIST.get(0));
        YEAR = new TextField("0");
        YEAR.setPrefColumnCount(4);

        HBox dateEdit = new HBox(DAY, MONTH, YEAR);
        dateEdit.setPadding(new Insets(10, 0, 10, 0));
        dateEdit.setSpacing(10);
        dateEdit.setAlignment(Pos.BASELINE_CENTER);
        NODE_LIST.add(dateEdit);
        setDate(entry.getDate());

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        HBox buttons = new HBox(save, cancel);
        buttons.setSpacing(20);
        buttons.setPadding(new Insets(10, 0, 10, 0));
        buttons.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
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

}
