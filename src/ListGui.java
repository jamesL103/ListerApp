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
        gbc.ipadx = 300;
        gbc.ipady = 150;

        PARENT.setLayout(layout);

        addLists();

        PARENT.setVisible(true);
    }

    private void addLists() {
        //title for first list
        JPanel labelPanel1 = new JPanel();

        labelPanel1.setLayout(new BorderLayout());
        labelPanel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Label listLabel1 = new Label("To-Do");
        listLabel1.setAlignment(Label.CENTER);
        labelPanel1.add(listLabel1, BorderLayout.CENTER);
        labelPanel1.setPreferredSize(new Dimension(100,20));

        addTo(labelPanel1, 0, 0);

        //title for second list
        JPanel labelPanel2 = new JPanel();

        Label listLabel2 = new Label("Completed");
        listLabel2.setAlignment(Label.CENTER);

        labelPanel2.setLayout(new BorderLayout());
        labelPanel2.add((listLabel2), BorderLayout.CENTER);
        labelPanel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        labelPanel2.setPreferredSize(new Dimension(100,20));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        addTo(labelPanel2, 1, 0);

        //list 1
        String[] arr = {"a","b","c"};
        JList<String> list1 = new JList<>(arr);
        gbc.gridwidth = 1;
        addTo(list1, 0, 1);

        //list 2
        JList<String> list2 = new JList<>(arr);
        addTo(list2, 1, 1);
    }

    //adds component to grid at index x and y
    //resets gridx and gridy back to original values
    //does NOT reset GridBagLayout constraints
    private void addTo(Component c, int x, int y) {
        int orgx = gbc.gridx;
        int orgy = gbc.gridy;
        gbc.gridx = x;
        gbc.gridy = y;
        layout.setConstraints(c, gbc);
        PARENT.add(c);
        gbc.gridx = orgx;
        gbc.gridy = orgy;
    }

}
