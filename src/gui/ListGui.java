package gui;

import javax.swing.*;
import java.awt.*;

public class ListGui {

    //Parent container of the gui
    private final JFrame PARENT;

    private GridBagConstraints gbc;

    private GridBagLayout layout;

    //manages the data stored in all the lists
    private final ListManager MANAGER;

    //default colors
    private final Color BACKGROUND = new Color(24, 32, 54);
    private final Color TEXT = new Color(255, 255, 255);

    //create the gui for the list app
    public ListGui() {
        PARENT = new JFrame("List App");
        PARENT.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PARENT.setSize(new Dimension(800, 500));
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();

        PARENT.setLayout(layout);
        PARENT.getContentPane().setBackground(BACKGROUND);

        MANAGER = new ListManager();

        addLists();
        addButtons();

        PARENT.setVisible(true);
    }

    private void addLists() {

        //title for first list
        JLabel listLabel1 = createLabel("To-Do");
        listLabel1.setHorizontalAlignment(JLabel.CENTER);

        JPanel labelPanel1 = newPanel(listLabel1, new BorderLayout(),BorderLayout.CENTER);

        labelPanel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelPanel1.setPreferredSize(new Dimension(100,20));


        //constraints for title 1
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addTo(labelPanel1, 0, 0);

        //title for second list

        JLabel listLabel2 = createLabel("Completed");
        listLabel2.setHorizontalAlignment(JLabel.CENTER);

        JPanel labelPanel2 = newPanel(listLabel2, new BorderLayout(), BorderLayout.CENTER);
        labelPanel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        labelPanel2.setPreferredSize(new Dimension(100,20));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        addTo(labelPanel2, 1, 0);


        //list component constraints
        gbc.gridwidth = 1;
        gbc.weighty = 1.0;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;

        //to do list
        ScrollTaskList todo = createScroll();
        MANAGER.registerList(todo.LIST, "todo");


        JPanel todoPanel = newPanel(todo, new BorderLayout(), BorderLayout.CENTER);
        todoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        addTo(todoPanel, 0, 1);

        //list 2

        ScrollTaskList completedTasks = createScroll();
        MANAGER.registerList(completedTasks.LIST, "completed");

        JPanel completedTaskPanel = newPanel(completedTasks, new BorderLayout(), BorderLayout.CENTER);
        completedTaskPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //update constraints for list 2
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        addTo(completedTaskPanel, 1, 1);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        loadLists();

    }

    public void loadLists() {
        MANAGER.initAll();
    }

    private void addButtons() {
        EntryControlBar bar = new EntryControlBar(MANAGER);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth= GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        addTo(bar, 0, 2);
        int x = 0;
    }

    //adds component to grid at index x and y
    //resets grid x and grid y back to original values
    //does NOT reset GridBagLayout constraints
    private void addTo(Component c, int x, int y) {
        int origx = gbc.gridx;
        int origy = gbc.gridy;
        gbc.gridx = x;
        gbc.gridy = y;
        layout.setConstraints(c, gbc);
        PARENT.add(c);
        gbc.gridx = origx;
        gbc.gridy = origy;
    }

    //creates and returns a new Panel with the specified component added
    //uses specified LayoutManager and adds with specified constraints
    //if null, adds with default settings
    private JPanel newPanel(Component c, LayoutManager layout, Object layoutRules) {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);
        panel.setForeground(TEXT);
        if (layout != null) {
            panel.setLayout(layout);
        }
        if (layoutRules != null) {
            panel.add(c, layoutRules);
        } else {
            panel.add(c);
        }
        return panel;
    }

    //helper to create new label with correct colors
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBackground(BACKGROUND);
        label.setForeground(TEXT);
        return label;
    }

    //helper to create Scroll task list with correct colors
    private ScrollTaskList createScroll() {
        ScrollTaskList scroll = new ScrollTaskList();
        scroll.setBg(BACKGROUND);
        scroll.setFg(TEXT);
        return scroll;
    }
}
