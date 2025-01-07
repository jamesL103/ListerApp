package gui;

import listItemStorage.ListEntry;
import util.Util;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;

public class EntryViewPanel extends EntryAccessPanel {

    private ListGui.EntryPanelObserver observer;


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

    private JScrollPane makeName() {
        JScrollPane title = new JScrollPane(Util.newLabel());
        title.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        title.getViewport().setSize(1, 50);
        title.setBorder(null);

        JLabel name = new JLabel();
        name.setFont(ListGui.FONT_TITLE);
        name.setText("default");


        name.setForeground(ListGui.COLOR_TEXT);

        title.getViewport().setBackground(ListGui.COLOR_BG_ACCENT);
        title.setViewportView(name);
        title.setPreferredSize(new Dimension(name.getWidth(), name.getHeight() + 40));

        return title;
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
        date.setFont(ListGui.FONT_SMALL_TEXT);
        date.setText("Due: " + toDisplay.getStringDate());
        return date;
    }

    private JPanel makeButtons() {
        JPanel panel = new JPanel();
        panel.setBackground(ListGui.COLOR_BACKGROUND);

        JButton edit = Util.newButton("Edit");
        edit.addActionListener(makeEditListener());
        edit.setFont(smallFont);
        panel.add(edit);

        JButton delete = Util.newButton("Delete Entry");
        delete.addActionListener(makeDeleteListener());
        delete.setFont(smallFont);
        panel.add(delete);

        return panel;

    }

    @Override
    public ActionListener makeExitListener() {
        return (e) -> {
            observer.notifyClose();
        };
    }

    private ActionListener makeEditListener() {
        return (e) -> {
            observer.notifyEdit(toDisplay);
        };
    }

    private ActionListener makeDeleteListener() {
        return (e) -> {
            int delete = JOptionPane.showOptionDialog(null, "Delete this entry? This action cannot be undone!", "Delete Entry", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE, null, null, null);
            if (delete == JOptionPane.OK_OPTION) {
                observer.notifyDelete(toDisplay);
            }
        };
    }

    /**Sets this panel's observer to the specified observer
     * @param observer the observer for this panel
     */
    public void addObserver(ListGui.EntryPanelObserver observer) {
        this.observer = observer;
    }

    @Override
    public void updateFields() {
        ((JLabel)((JScrollPane)nameDisplay).getViewport().getView()).setText(toDisplay.getName());
        ((JTextComponent)descDisplay).setText(toDisplay.getDescription());
        ((JLabel)dateDisplay).setText("Due: " + toDisplay.getStringDate());
    }
}
