import listItemStorage.ListEntry;

import javax.swing.*;
import java.awt.*;

public class ListGui {

    //Parent container of the gui
    private final JFrame PARENT;

    private GridBagConstraints gbc;

    private GridBagLayout layout;


    //create the gui for the list app
    public ListGui() {
        PARENT = new JFrame("List App");
        PARENT.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PARENT.setSize(new Dimension(500, 300));
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();

        PARENT.setLayout(layout);

        addLists();

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

        //list 1
        ListEntry[] lst = new ListEntry[3];
        for (int i = 0; i < 3; i ++) {
            lst[i] = new ListEntry();
        }
        String[] arr = {"a","b","c"};
        JList<ListEntry> list1 = new JList<>(lst);
        list1.setCellRenderer(new EntryCellRenderer());
//        list1.setBackground(Color.BLACK);

        JPanel listPanel1 = newPanel(list1, new BorderLayout(), BorderLayout.CENTER);
        listPanel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        gbc.gridheight = GridBagConstraints.REMAINDER;
        addTo(listPanel1, 0, 1);

        //list 2
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        JList<String> list2 = new JList<>(arr);

        JPanel listPanel2 = newPanel(list2, new BorderLayout(), BorderLayout.CENTER);
        listPanel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addTo(listPanel2, 1, 1);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
    }

    //adds component to grid at index x and y
    //resets gridx and gridy back to original values
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
