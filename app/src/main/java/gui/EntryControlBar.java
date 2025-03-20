package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**JPanel storing all the buttons in the main ListGui to
 * edit and move list entries.
 */
public class EntryControlBar extends JPanel {

    private ListGui.ControlBarObserver OBSERVER;

    private final int BAR_HEIGHT = 50;

    public final JButton BUTTON_COMPLETE = new JButton();

    public EntryControlBar(ListGui.ControlBarObserver observer) {
        super();

        OBSERVER = observer;
        FlowLayout layout = new FlowLayout();
        layout.setHgap(20);
        setLayout(layout);
        setPreferredSize(new Dimension(getWidth(), BAR_HEIGHT));
        setBackground(ListGui.COLOR_BG_ACCENT);
        addButtons();
    }

    private void addButtons() {
        JButton add = new JButton();
        add.setBackground(ListGui.COLOR_BUTTON);
        add.setForeground(ListGui.COLOR_TEXT);
        add.setText("Create New Entry");
        add.addActionListener(EVENT_ADD);
        add(add);

        BUTTON_COMPLETE.setBackground(ListGui.COLOR_BUTTON);
        BUTTON_COMPLETE.setForeground(ListGui.COLOR_TEXT);
        BUTTON_COMPLETE.setText("Complete");
        BUTTON_COMPLETE.addActionListener(EVENT_COMPLETE);
        BUTTON_COMPLETE.setEnabled(false);
        add(BUTTON_COMPLETE);
    }

    //event for creating a new entry
    private final ActionListener EVENT_ADD = (e) -> OBSERVER.notifyAdd();

    //event for completing and entry and moving it to the completed list
    private final ActionListener EVENT_COMPLETE = (e) -> OBSERVER.notifyComplete();

}
