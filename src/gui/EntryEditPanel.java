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
        addDateAccessor(makeDateEditor());
        addButtons(makeButton());

        updateFields();
    }

    @Override
    public void updateFields() {
        ((JTextComponent)nameDisplay).setText(toDisplay.getName());
        ((JTextComponent)descDisplay).setText(toDisplay.getDescription());
        updateDate();
    }

    private void updateDate() {

    }

    private JTextField makeName() {
        JTextField name = new JTextField();
        name.setText("default");
        name.setFont(ListGui.TITLE);
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

    private JPanel makeDateEditor() {
        JPanel panel = new JPanel();
        dateDisplay = panel;
        Label title = new Label("Due: ");
        title.setFont(smallFont);
        panel.add(title);

        JTextField date = new JTextField("1");
        panel.add(date);

        JComboBox<String> month = new JComboBox<>();
        panel.add(month);


        JTextField year = new JTextField("1000");
        panel.add(year);

        return panel;
    }

    private JPanel makeButton() {
        JPanel panel = new JPanel();
        JButton save = new JButton("Save");
        panel.add(save);
        return panel;
    }

}
