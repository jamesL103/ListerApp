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
        addName();
        addDescription();
        addButton();
    }

    @Override
    public void updateFields() {
        ((JTextComponent)nameDisplay).setText(toDisplay.getName());
        ((JTextComponent)descDisplay).setText(toDisplay.getName());
    }

    private void addName() {
        JTextField name = new JTextField();
        name.setText("default");
        name.setFont(titleFont);
        name.setHorizontalAlignment(JLabel.LEFT);
        name.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addNameAccessor(name);

    }

    private void addDescription() {
        JTextArea desc = new JTextArea();
        desc.setLineWrap(true);
        desc.setText("default");

        addDescAccessor(desc);
    }

    private void addButton() {
        JButton save = new JButton("Save");

        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0.1;

        addComponent(save, 0, 3);
    }

}
