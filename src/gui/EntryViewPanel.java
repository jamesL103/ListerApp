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
        addButtons(makeButtons());

        updateFields();
    }

    private JLabel makeName() {
        JLabel name = new JLabel();
        name.setFont(ListGui.TITLE);
        name.setText("default");
        name.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return name;
    }

    private JTextArea makeDescription() {
        JTextArea desc = new JTextArea();
        desc.setEditable(false);
        desc.setFocusable(false);
        desc.setLineWrap(true);
        desc.setText("default");

        return desc;
    }

    private JLabel makeDate() {
        JLabel date = new JLabel();
        date.setText("Due: " + toDisplay.getStringDate());

        return date;
    }

    private JPanel makeButtons() {
        JPanel panel = new JPanel();
        JButton edit = new JButton("Edit");
        edit.setFont(smallFont);
        panel.add(edit);

        return panel;

    }

    @Override
    public void updateFields() {
        ((JLabel)nameDisplay).setText(toDisplay.getName());
        ((JTextComponent)descDisplay).setText(toDisplay.getDescription());
        ((JLabel)dateDisplay).setText("Due: " + toDisplay.getStringDate());
    }
}
