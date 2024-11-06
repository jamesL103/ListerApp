package gui;

import listItemStorage.ListEntry;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ScrollTaskList extends JPanel {

    private final JScrollPane ROOT;

    public final JList<ListEntry> LIST;

    public final DefaultListModel<ListEntry> MODEL;

    private final ListGui.ListSelectionObserver OBSERVER;



    public ScrollTaskList(ListGui.ListSelectionObserver observer) {
        super();

        OBSERVER = observer;

        setLayout(new BorderLayout());

        //create the Jlist
        LIST = new JList<>();
        LIST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        LIST.addListSelectionListener(makeAccessListener());
        LIST.setCellRenderer(new EntryCellRenderer());
        MODEL = new DefaultListModel<>();
        LIST.setModel(MODEL);
        LIST.setFixedCellHeight(24);


        //create ScrollPane
        ROOT = new JScrollPane();
        ROOT.getViewport().setView(LIST);

        add(ROOT, BorderLayout.CENTER);
    }

    /**Sets the background color of the scroll pane to the specified color
     *
     * @param color the new color
     */
    public void setBg(Color color) {
        super.setBackground(color);
        LIST.setBackground(color);
    }

    /**Sets the foreground color of the scroll pane to the specified color
     *
     * @param color the new color
     */
    public void setFg(Color color) {
        super.setForeground(color);
        LIST.setForeground(color);
    }

    //creates an access listener that sets the currently selected
    //ListEntry
    private ListSelectionListener makeAccessListener() {
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                OBSERVER.notifySelection(LIST.getSelectedValue());
            }
        };
    }


}
