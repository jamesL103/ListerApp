package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**JPanel storing all the buttons in the main ListGui to
 * edit and move list entries.
 */
public class EntryControlBar extends JPanel {

    private ListGui.ListEditObserver observer;

    private final int BAR_HEIGHT = 50;

    public EntryControlBar(ListGui.ListEditObserver observer) {
        super();

        this.observer = observer;
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
        add.addActionListener(EVENT_ADD);
        add(add);

        JButton complete = new JButton();
        complete.setText("Complete");
        add(complete);

        JButton delete = new JButton();
        delete.setText("Delete Entry");
        add(delete);
    }

    //event for creating a new entry
    private final ActionListener EVENT_ADD = (e) -> {
        observer.notifyAdd();
    };

    //event for completing and entry and moving it to the completed list
    private final ActionListener EVENT_COMPLETE = (e) -> {

    };

}
