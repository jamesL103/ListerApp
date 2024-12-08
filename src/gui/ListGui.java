package gui;

import listItemStorage.ListEntry;

import javax.swing.*;
import java.awt.*;

public class ListGui {

    //Parent container of the gui
    private final JFrame PARENT;

    //GridBagLayout Constraints
    private final GridBagConstraints gbc;
    private final GridBagConstraints CONSTRAINTS_ENTRY = new GridBagConstraints();
    private final GridBagConstraints CONSTRAINTS_L2_DEFAULT = createDefList2Constraints();
    private final GridBagConstraints CONSTRAINTS_L2_SMALL = createSmallList2Constraints();

    private final GridBagLayout layout;

    /**manages the data stored in all the lists
     * 
     */
    private final ListManager MANAGER;

    //components of the gui
    private ScrollTaskList toDo;
    private ScrollTaskList completed;

    //the list with the current active selection
    private ScrollTaskList activeList;

    //panels for accessing and editing entry fields
    private EntryAccessPanel currentView;
    private EntryViewPanel viewPanel;
    private EntryEditPanel editPanel;

    //default colors
    public static final Color BACKGROUND = new Color(24, 32, 54);
    public static final Color COLOR_BG_ACCENT = new Color(54, 60, 81);
    public static final Color COLOR_BORDER = new Color(101, 101, 101);
    public static final Color TEXT = new Color(255, 255, 255);
    public static final Color COLOR_BUTTON = new Color(116, 160, 255);

    //fonts
    public static final Font TITLE = new Font("arial", Font.PLAIN, 24);

    //create the gui for the list app
    public ListGui() {
        PARENT = new JFrame("List App");
        PARENT.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PARENT.setSize(new Dimension(800, 500));
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();

        PARENT.setLayout(layout);
        PARENT.getContentPane().setBackground(BACKGROUND);

        initViewPanel();
        initEditPanel();
        currentView = viewPanel;

        MANAGER = new ListManager();

        addLists();
        addButtons();

        PARENT.setVisible(true);
    }

    private void addLists() {
        //list component constraints
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;

        //to do list
        toDo = createScroll();
        toDo.setTitle("To-Do");
        MANAGER.registerList(toDo.LIST, "todo");

        toDo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        activeList = toDo;

        addTo(toDo, 0, 0);

        //completed list

        completed = createScroll();
        completed.setTitle("Completed");
        MANAGER.registerList(completed.LIST, "completed");

        completed.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //update constraints for list 2
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        PARENT.add(completed, CONSTRAINTS_L2_DEFAULT);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        loadLists();

        //set the counters of the lists
        toDo.updateCount();
        completed.updateCount();
    }

    public void loadLists() {
        MANAGER.initAll();
    }

    //create and add button bar
    private void addButtons() {
        EntryControlBar bar = new EntryControlBar(new ControlBarObserver());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth= GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        addTo(bar, 0, 1);
    }

    //creates the EntryViewPanel and sets its constraints in the layout
    private void initViewPanel() {
        viewPanel = new EntryViewPanel(new ListEntry("null"));
        viewPanel.addObserver(new EntryPanelObserver());

        CONSTRAINTS_ENTRY.gridwidth = GridBagConstraints.REMAINDER;
        CONSTRAINTS_ENTRY.fill = GridBagConstraints.VERTICAL;
        CONSTRAINTS_ENTRY.gridx = 3;
        CONSTRAINTS_ENTRY.gridy = 0;
        CONSTRAINTS_ENTRY.weightx = 0.25;
        CONSTRAINTS_ENTRY.weighty = 0.5;
    }

    //creates the EntryEditPanel
    private void initEditPanel() {
        editPanel = new EntryEditPanel(new ListEntry("null"));
        editPanel.setObserver(new EntryPanelObserver());
    }

    //initialize the default layout constraints for the second list
    private static GridBagConstraints createDefList2Constraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.RELATIVE;
        constraints.weightx = 0.5;
        constraints.weighty = 1.0;
        return constraints;
    }

    //initialize the minimized layout constraints for the second list
    private static GridBagConstraints createSmallList2Constraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.25;
        constraints.weighty = 1.0;
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.gridheight = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.BOTH;
        return constraints;
    }

    //displays the specified entry and its fields as a side panel of a specified type
    //replaces the current display panel if one is visible
    private void displayEntryAccess(ListEntry entry, EntryAccessPanel panel) {

        PARENT.remove(currentView);
        currentView = panel;

        //change layout constraints for completed list
        layout.setConstraints(completed, CONSTRAINTS_L2_SMALL);

        layout.setConstraints(currentView, CONSTRAINTS_ENTRY);


        PARENT.add(panel, CONSTRAINTS_ENTRY);
        currentView.setEntry(entry);
        PARENT.paintComponents(PARENT.getGraphics());
    }

    //close the panel currently displaying an entry
    private void closeEntryPanel() {
        PARENT.remove(currentView);
        PARENT.paintComponents(PARENT.getGraphics());
        layout.setConstraints(completed, CONSTRAINTS_L2_DEFAULT);
        toDo.clearSelection();
        completed.clearSelection();
    }

    //completes an entry, moving it from to-do to completed
    private void completeEntry(ListEntry entry) {
        MANAGER.removeEntry(toDo, entry);
        MANAGER.addTo(completed, entry);
    }

    //deletes an entry
    private void deleteEntry(ScrollTaskList list, ListEntry entry) {
        MANAGER.removeEntry(list, entry);
        closeEntryPanel();
    }

    //adds component to grid at index x and y
    //resets grid x and grid y back to original values
    //does NOT reset GridBagLayout constraints
    private void addTo(Component c, int x, int y) {
        int origx = gbc.gridx;
        int origy = gbc.gridy;
        gbc.gridx = x;
        gbc.gridy = y;
        PARENT.add(c, gbc);
        gbc.gridx = origx;
        gbc.gridy = origy;
    }


    //helper to create Scroll task list with correct colors
    private ScrollTaskList createScroll() {
        ScrollTaskList scroll = new ScrollTaskList(new ListSelectionObserver());
        scroll.setBg(BACKGROUND);
        scroll.setFg(TEXT);
        return scroll;
    }


    //observer that notifies gui when list entry is selected from list
    public class ListSelectionObserver {

        //called when list selection is updated
        public void notifySelection(ListEntry entry, ScrollTaskList caller) {
            if (entry != null) {
                displayEntryAccess(entry, viewPanel);
                activeList = caller;
            }
        }

    }

    //observer that handles events with any entry panel
    public class EntryPanelObserver {

        //closes the entryViewPanel
        public void notifyClose() {
            closeEntryPanel();
        }

        //opens editing of the currently viewed entry
        public void notifyEdit(ListEntry entry) {
            displayEntryAccess(entry, editPanel);
            editPanel.setCreatingNewEntry(false);
            PARENT.paintComponents(PARENT.getGraphics());
        }

        //saves the data of the entry being edited
        public void notifySave(ListEntry entry, boolean newEntry) {
            MANAGER.save(toDo);
            displayEntryAccess(entry, viewPanel);
            if (newEntry) {
                MANAGER.addTo(toDo, entry);
            }
        }

        public void notifyDelete(ListEntry entry) {
            deleteEntry(activeList, entry);
        }

    }

    //observer that tracks when buttons in the control bar are used
    public class ControlBarObserver {

        public void notifyAdd() {
            ListEntry entry = new ListEntry();
            displayEntryAccess(entry, editPanel);
            editPanel.setCreatingNewEntry(true);
            PARENT.paintComponents(PARENT.getGraphics());
        }

        public void notifyComplete() {
            ListEntry selection = toDo.LIST.getSelectedValue();
            if (selection != null) {
                completeEntry(selection);
            }
        }

    }

}
