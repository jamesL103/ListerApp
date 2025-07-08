package javafxGui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import listItemStorage.ListEntry;

public abstract class EntryDisplay extends VBox {

    public static final int VERTICAL_SPACING = 10;
    public static final int DESC_COLS = 20;
    public static final int DESC_ROWS = 5;
    public static final int TOP_BAR_SPACING = 5;

    public static final Insets DATE_PADDING = new Insets(10, 0, 10, 0);
    public static final Insets PANEL_INSETS = new Insets(0, 10, 10, 10);
    public static final Font TITLE = new Font(Fonts.FONT_FAMILY, 20);

    public abstract void setCloseButtonCallback(Runnable callback);

    public abstract void setEntry(ListEntry entry);

    public abstract ListEntry getEntry();

}
