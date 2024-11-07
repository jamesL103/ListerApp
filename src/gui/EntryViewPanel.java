package gui;

import listItemStorage.ListEntry;
import util.Util;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryViewPanel extends EntryAccessPanel {

    private ListGui.ViewPanelObserver observer;


    public EntryViewPanel(ListEntry entry) {
        super(entry);
    }



    @Override
    public void initialize() {
        addNameAccessor(makeName());
        addDescAccessor(makeDescription());
        addDateAccessor(makeDate());
        addButtons(makeButtons());
        addExitButton(makeExitButton());

        updateFields();
    }

    private void addExitButton(JButton button) {
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;

        Util.addToGb(this, button, gbc, 1, 0);

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

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

    private JButton makeExitButton() {
        JButton exit = new JButton("x");
        exit.addActionListener(makeExitListener());
        exit.setFont(smallFont);
        return exit;
    }

    private ActionListener makeExitListener() {
        ActionListener exit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                observer.notifyClose();
            }
        };
        return exit;
    }

    /**Sets this panel's observer to the specified observer
     * @param observer the observer for this panel
     */
    public void addObserver(ListGui.ViewPanelObserver observer) {
        this.observer = observer;
    }

    @Override
    public void updateFields() {
        ((JLabel)nameDisplay).setText(toDisplay.getName());
        ((JTextComponent)descDisplay).setText(toDisplay.getDescription());
        ((JLabel)dateDisplay).setText("Due: " + toDisplay.getStringDate());
    }
}
