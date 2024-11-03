package gui;

import listItemStorage.ListEntry;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class EntryEditPanel extends EntryAccessPanel {


    public EntryEditPanel(ListEntry entry) {
        super(entry);
    }

    @Override
    public void initialize() {
        addNameAccessor(makeName());
        addDescAccessor(makeDescription());
        addButtons(makeButton());
        updateFields();
    }

    @Override
    public void updateFields() {
        ((JTextComponent)nameDisplay).setText(toDisplay.getName());
        ((JTextComponent)descDisplay).setText(toDisplay.getName());
    }

    private JTextField makeName() {
        JTextField name = new JTextField();
        name.setText("default");
        name.setFont(titleFont);
        name.setHorizontalAlignment(JLabel.LEFT);
        name.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return name;
    }

    private JTextArea makeDescription() {
        JTextArea desc = new JTextArea();
        desc.setLineWrap(true);
        desc.setText("default");

        return desc;
    }

    private JButton makeButton() {
        JButton save = new JButton("Save");
        return save;
    }

}
