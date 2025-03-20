package gui.panels;

import gui.ListGui;
import listItemStorage.ListEntry;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import util.Util;

public class EntryEditPanel extends AbstractEntryPanel {

    private final DatePanel DATE_EDITOR;

    private ListGui.EntryPanelObserver observer;

    //determines if the panel is creating a new entry or editing a preexisting one
    private boolean isCreatingNewEntry;
    //original date of the entry before being edited
    private Calendar originalDate;


    public EntryEditPanel(ListEntry entry) {
        super(entry);
        DATE_EDITOR = new DatePanel();

        addDateAccessor(DATE_EDITOR);
        setPanelDate();
    }

    @Override
    public void initialize() {
        addNameAccessor(makeName());
        addDescAccessor(makeDescription());
        addButtons(makeButtons());

        updateFields();
    }

    @Override
    public void updateFields() {
        ((JTextComponent)nameDisplay).setText(toDisplay.getName());
        ((JTextComponent)descDisplay).setText(toDisplay.getDescription());
    }

    @Override
    public ActionListener makeExitListener() {
        return (e) -> {
            observer.notifyClose();
        };
    }

    //sets the fields of the panel and records the original date
    @Override
    public void setEntry(ListEntry entry) {
        super.setEntry(entry);
        setPanelDate();
        originalDate = (Calendar) (entry.getDate().clone());
    }

    //sets the panel's observer
    public void setObserver(ListGui.EntryPanelObserver ob) {
        observer = ob;
    }

    //set all fields in the date panel based on the entry
    private void setPanelDate() {
        Calendar cal = toDisplay.getDate();
        DATE_EDITOR.MONTH.setSelectedIndex(cal.get(Calendar.MONTH));
        DATE_EDITOR.DATE.setText(String.valueOf(cal.get(Calendar.DATE)));
        DATE_EDITOR.YEAR.setText(String.valueOf(cal.get(Calendar.YEAR)));
    }

    private JTextField makeName() {
        JTextField name = new JTextField();
        name.setBorder(BorderFactory.createLineBorder(ListGui.COLOR_BORDER));
        name.setCaretColor(ListGui.COLOR_TEXT);
        name.setText("default");
        name.setFont(ListGui.FONT_TITLE);
        name.setHorizontalAlignment(JLabel.LEFT);
        return name;
    }

    private JTextArea makeDescription() {
        JTextArea desc = new JTextArea();
        desc.setLineWrap(true);
        desc.setCaretColor(Color.WHITE);
        desc.setText("default");

        return desc;
    }

    //subclass of Jpanel to display all the elements for editing the date
    //and accessing the data
    private static class DatePanel extends JPanel {

        public final JTextField DATE;
        public final JComboBox<Util.Months> MONTH;
        public final JTextField YEAR;


        public DatePanel() {
            super();
            ((FlowLayout)getLayout()).setAlignment(FlowLayout.LEFT);
            Label title = new Label("Due:");
            title.setFont(ListGui.FONT_SMALL_TEXT);
            add(title);

            JTextField date = new JTextField("1");
            date.setColumns(2);
            DATE = date;
            add(date);

            JComboBox<Util.Months> month = makeMonthChooser();
            MONTH = month;
            add(month);


            JTextField year = new JTextField("1000");
            year.setColumns(4);
            YEAR = year;
            add(year);
        }

        //makes the combobox dropdown that allows selection of month
        private static JComboBox<Util.Months> makeMonthChooser() {
            JComboBox<Util.Months> toReturn = new JComboBox<>();
            Util.Months[] months = Util.Months.values();
            for (Util.Months month : months) {
                toReturn.addItem(month);
            }

            return toReturn;
        }
    }


    //makes the save button for the edit panel
    //updates the fields of the current entry
    private JPanel makeButtons() {
        JPanel panel = new JPanel();
        panel.setBackground(ListGui.COLOR_BACKGROUND);

        JButton save = Util.newButton("Save");
        save.addActionListener(makeSaveListener());
        panel.add(save);

        JButton cancel = Util.newButton("Cancel");
        cancel.addActionListener(makeCancelListener());
        panel.add(cancel);

        return panel;
    }

    //Listener for the save button
    private ActionListener makeSaveListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDisplay.setName(((JTextField) nameDisplay).getText());
                toDisplay.setDescription(((JTextArea) descDisplay).getText());
                setDate();

                if (isCreatingNewEntry) {
                    observer.notifySave(toDisplay, true, false);
                } else {
                    //check if the date was changed
                    boolean dateChanged = !originalDate.equals(toDisplay.getDate());
                    observer.notifySave(toDisplay, false, dateChanged);
                }
            }

            private void setDate() {
                Calendar cal = toDisplay.getDate();
                cal.set(Calendar.DATE, Integer.parseInt(DATE_EDITOR.DATE.getText()));
                cal.set(Calendar.MONTH, DATE_EDITOR.MONTH.getSelectedIndex());
                cal.set(Calendar.YEAR, Integer.parseInt(DATE_EDITOR.YEAR.getText()));
            }
        };

    }

    private ActionListener makeCancelListener() {
        return e -> {
            observer.notifyCancel(toDisplay);
        };
    }

    /**Sets whether the panel is creating a new entry or editing an
     * existing one.
     * true signifies a new entry, while false signifies editing.
     *
     * @param isNewEntry whether the panel is creating a new entry
     */
    public void setCreatingNewEntry(boolean isNewEntry) {
        isCreatingNewEntry = isNewEntry;
    }
}
