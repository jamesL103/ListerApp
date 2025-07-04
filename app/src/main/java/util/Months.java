package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Months {
    public static final ObservableList<String> LIST = FXCollections.observableList(
            List.of("Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec")
    );
}
