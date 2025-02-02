package gui;

import listItemStorage.ListEntry;

import javax.swing.*;
import java.awt.*;

public class EntryCellRenderer extends JLabel implements ListCellRenderer<ListEntry> {


    private final static int CELL_HEIGHT = 30;
    private final JLabel NAME = new JLabel();
    private final JLabel DATE = new JLabel();

    public EntryCellRenderer() {
        super();
        setPreferredSize(new Dimension(60, CELL_HEIGHT));
        setLayout(new BorderLayout());
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ListEntry> list, ListEntry value, int index, boolean isSelected, boolean cellHasFocus) {
        setSize(list.getWidth(), getHeight());
        String entry = value.toString();
        //cut off entry name if too long
        //note: this is bad
        int length = getWidth()/12;
        if (length > 0 && entry.length() > length) {
            entry = entry.substring(0, length - 3) + "...";
        }

        //create the label displaying the name of the entry
        NAME.setText(entry);
        NAME.setFont(list.getFont());
        NAME.setForeground(ListGui.COLOR_TEXT);
        NAME.setHorizontalAlignment(SwingConstants.LEFT);

        //create the label displaying the date of the entry
        DATE.setText("Due: " + value.getStringDate());
        DATE.setFont(list.getFont());
        DATE.setForeground(ListGui.COLOR_TEXT_ACCENT);
        DATE.setHorizontalAlignment(SwingConstants.RIGHT);

        add(NAME, BorderLayout.WEST);
        add(DATE, BorderLayout.EAST);

        setBorder(BorderFactory.createLineBorder(ListGui.COLOR_BORDER));
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(ListGui.COLOR_BG_ACCENT);
            setForeground(list.getForeground());
        }
        setOpaque(true);
        return this;
    }
}
