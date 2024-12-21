package gui;

import listItemStorage.ListEntry;

import javax.swing.*;
import java.awt.*;

public class ListGui {

    //Parent container of the gui
    private final JFrame PARENT;

    //GridBagLayout Constraints
    private static final GridBagConstraints CONSTRAINTS_ENTRY = createEntryViewConstraints();
    private static final GridBagConstraints CONSTRAINTS_L1_DEFAULT = createList1DefConstraints();
    private static final GridBagConstraints CONSTRAINTS_L2_DEFAULT = createDefList2Constraints();
    private static final GridBagConstraints CONSTRAINTS_L2_SMALL = createSmallList2Constraints();
    private static final GridBagConstraints CONSTRAINTS_CONTROL_BAR = createControlBarConstraints();

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
    public static final Font SMALL_TEXT = new Font("arial", Font.PLAIN, 12);

    //create the gui for the list app
    public ListGui() {
        PARENT = new JFrame("List App");
        PARENT.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PARENT.setSize(new Dimension(800, 500));
        layout = new GridBagLayout();

        PARENT.setLayout(layout);
        PARENT.getContentPane().setBackground(BACKGROUND);

        initViewPanel();
        initEditPanel();
        currentView = viewPanel;

        MANAGER = new ListManager();

        addLists();
        addControlBar();

        PARENT.setVisible(true);
    }

    private void addLists() {

        //to do list
        toDo = createScroll();
        toDo.setTitle("To-Do");
        MANAGER.registerList(toDo.LIST, "todo");

        toDo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        activeList = toDo;

        PARENT.add(toDo, CONSTRAINTS_L1_DEFAULT);


        //completed list
        completed = createScroll();
        completed.setTitle("Completed");
        MANAGER.registerList(completed.LIST, "completed");

        completed.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        PARENT.add(completed, CONSTRAINTS_L2_DEFAULT);

        loadLists();

        //set the counters of the lists
        toDo.updateCount();
        completed.updateCount();
    }

    //loads all lists in the manager from their files
    public void loadLists() {
        MANAGER.loadAllLists();
    }

    //create and add control bar
    private void addControlBar() {
        EntryControlBar bar = new EntryControlBar(new ControlBarObserver());
        PARENT.add(bar, CONSTRAINTS_CONTROL_BAR);
    }

    //creates the EntryViewPanel and sets its constraints in the layout
    private void initViewPanel() {
        viewPanel = new EntryViewPanel(new ListEntry("null"));
        viewPanel.addObserver(new EntryPanelObserver());
    }

    //creates the EntryEditPanel
    private void initEditPanel() {
        editPanel = new EntryEditPanel(new ListEntry("null"));
        editPanel.setObserver(new EntryPanelObserver());
    }

    //create layout constraints for entry viewer
    private static GridBagConstraints createEntryViewConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 3;
        c.gridy = 0;
        c.weightx = 0.25;
        c.weighty = 0.5;

        return c;
    }
    
    //create the layout constraints for list 1
    private static GridBagConstraints createList1DefConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.gridheight = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.BOTH;
        return c;
    }

    //initialize the default layout constraints for the second list
    private static GridBagConstraints createDefList2Constraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = GridBagConstraints.RELATIVE;
        c.weightx = 0.5;
        c.weighty = 1.0;
        return c;
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

    //create the layout constraints for the control bar
    private static GridBagConstraints createControlBarConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth= GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 1.0;
        return c;
    }

    //displays the specified entry and its fields as a side panel of a specified type
    //replaces the current display panel if one is visible
    private void displayEntry(ListEntry entry, EntryAccessPanel panel) {

        PARENT.remove(currentView);
        currentView = panel;

        //change layout constraints for completed list
        layout.setConstraints(completed, CONSTRAINTS_L2_SMALL);

        layout.setConstraints(currentView, CONSTRAINTS_ENTRY);


        PARENT.add(panel, CONSTRAINTS_ENTRY);
        currentView.setEntry(entry);
//        PARENT.paintComponents(PARENT.getGraphics());
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
                displayEntry(entry, viewPanel);
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
            displayEntry(entry, editPanel);
            editPanel.setCreatingNewEntry(false);
            PARENT.paintComponents(PARENT.getGraphics());
        }

        //saves the data of the entry being edited
        public void notifySave(ListEntry entry, boolean newEntry) {
            MANAGER.save(toDo);
            displayEntry(entry, viewPanel);
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
            displayEntry(entry, editPanel);
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
