package gui;

import listItemStorage.ListEntry;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class EntryViewPanel extends EntryAccessPanel {


    public EntryViewPanel(ListEntry entry) {
        super(entry);
    }



    @Override
    public void initialize() {
        addNameAccessor(makeName());
        addDescAccessor(makeDescription());
        addDateAccessor(makeDate());

        updateFields();
    }

    private JLabel makeName() {
        JLabel name = new JLabel();
        name.setFont(titleFont);
        name.setText("default");
        name.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return name;
    }

    private JTextArea makeDescription() {
        JTextArea desc = new JTextArea();
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setText("default");

        return desc;
    }

    private JLabel makeDate() {
        JLabel date = new JLabel();
        date.setText("Due: " + toDisplay.getStringDate());

        return date;
    }

    @Override
    public void updateFields() {
        ((JLabel)nameDisplay).setText(toDisplay.getName());
        ((JTextComponent)descDisplay).setText(toDisplay.getDescription());
        ((JLabel)dateDisplay).setText("Due: " + toDisplay.getStringDate());
    }
}
