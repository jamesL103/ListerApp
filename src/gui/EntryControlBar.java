package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryControlBar extends JPanel {

    private final ListManager MANAGER;

    private final int BAR_HEIGHT = 50;

    public EntryControlBar(ListManager manager) {
        super();
        MANAGER = manager;

        FlowLayout layout = new FlowLayout();
        layout.setHgap(20);
        setLayout(layout);
        setPreferredSize(new Dimension(getWidth(), BAR_HEIGHT));
        addButtons();

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void addButtons() {
        JButton add = new JButton();
        add.setText("Create New Entry");
        add.addActionListener(ADD_EVENT);
        add(add);

        JButton complete = new JButton();
        complete.setText("Complete");
        add(complete);

        JButton delete = new JButton();
        delete.setText("Delete Entry");
        add(delete);
    }

    private final ActionListener ADD_EVENT = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

}
