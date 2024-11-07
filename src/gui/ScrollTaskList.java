package gui;

import listItemStorage.ListEntry;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ScrollTaskList extends JPanel {

    private final JScrollPane SCROLLER;

    public final JList<ListEntry> LIST;

    private final JLabel TITLE;

    public final DefaultListModel<ListEntry> MODEL;

    private final ListGui.ListSelectionObserver OBSERVER;

    private final GridBagLayout LAYOUT;
    private final GridBagConstraints GBC;



    public ScrollTaskList(ListGui.ListSelectionObserver observer) {
        super();

        OBSERVER = observer;

        //set layout
        LAYOUT = new GridBagLayout();
        GBC = new GridBagConstraints();

        //add title to graph
        TITLE = new JLabel();

        //create the Jlist
        LIST = new JList<>();
        LIST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        LIST.addListSelectionListener(makeAccessListener());
        LIST.setCellRenderer(new EntryCellRenderer());
        MODEL = new DefaultListModel<>();
        LIST.setModel(MODEL);
        LIST.setFixedCellHeight(24);


        //create ScrollPane
        SCROLLER = new JScrollPane();
        SCROLLER.getViewport().setView(LIST);

        //add scrollpane to panel

        add(SCROLLER, BorderLayout.CENTER);
    }

    public void addTitle() {
        JLabel title = new JLabel();
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
