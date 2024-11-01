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
        addName();
        addDescription();
        addDate();

        updateFields();
    }

    private void addName() {
        JLabel name = new JLabel();
        name.setFont(titleFont);
        name.setText("default");
        name.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addNameAccessor(name);
    }

    private void addDescription() {
        JTextArea desc = new JTextArea();
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setText("default");

        addDescAccessor(desc);
    }

    private void addDate() {
        JLabel date = new JLabel();
        date.setText("Due: " + toDisplay.getStringDate());

        addDateAccessor(date);

    }

    @Override
    public void updateFields() {
        ((JLabel)nameDisplay).setText(toDisplay.getName());
        ((JTextComponent)descDisplay).setText(toDisplay.getDescription());
        ((JLabel)dateDisplay).setText("Due: " + toDisplay.getStringDate());
    }
}
