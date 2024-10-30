import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListGui {

    //Parent container of the gui
    private final JFrame PARENT;

    private GridBagConstraints gbc;

    private GridBagLayout layout;

    //manages the data stored in all the lists
    private final ListManager MANAGER;


    //create the gui for the list app
    public ListGui() {
        PARENT = new JFrame("List App");
        PARENT.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PARENT.setSize(new Dimension(500, 300));
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();

        PARENT.setLayout(layout);

        MANAGER = new ListManager();

        addLists();
        addButtons();

        PARENT.setVisible(true);
    }

    private void addLists() {
        //label padding
        gbc.ipadx = 300;

        //title for first list
        Label listLabel1 = new Label("To-Do");
        listLabel1.setAlignment(Label.CENTER);

        JPanel labelPanel1 = newPanel(listLabel1, new BorderLayout(),BorderLayout.CENTER);

        labelPanel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelPanel1.setPreferredSize(new Dimension(100,20));

        addTo(labelPanel1, 0, 0);

        //title for second list

        Label listLabel2 = new Label("Completed");
        listLabel2.setAlignment(Label.CENTER);

        JPanel labelPanel2 = newPanel(listLabel2, new BorderLayout(), BorderLayout.CENTER);
        labelPanel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        labelPanel2.setPreferredSize(new Dimension(100,20));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        addTo(labelPanel2, 1, 0);
        gbc.gridwidth = 1;

        //list padding
        gbc.ipady = 400;

        //to do list
        ScrollTaskList todo = new ScrollTaskList();
        MANAGER.registerList(todo.LIST, "todo");


        JPanel todoPanel = newPanel(todo, new BorderLayout(), BorderLayout.CENTER);
        todoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        gbc.gridheight = GridBagConstraints.REMAINDER;
        addTo(todoPanel, 0, 1);

        //list 2
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        ScrollTaskList completedTasks = new ScrollTaskList();
        MANAGER.registerList(completedTasks.LIST, "completed");

        JPanel completedTaskPanel = newPanel(completedTasks, new BorderLayout(), BorderLayout.CENTER);
        completedTaskPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
}
