package gui;

import listItemStorage.ListEntry;
import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ScrollTaskList extends JPanel {

    private final JScrollPane SCROLLER;

    public final JList<ListEntry> LIST;

    private final JLabel TITLE;

    private final JLabel COUNTER;

    public final DefaultListModel<ListEntry> MODEL;

    private final ListGui.ListSelectionObserver OBSERVER;

    private final GridBagConstraints GBC;



    public ScrollTaskList(ListGui.ListSelectionObserver observer) {
        super();

        OBSERVER = observer;

        //set layout
        GBC = new GridBagConstraints();
        setLayout(new GridBagLayout());


        //set title
        TITLE = new JLabel();
        TITLE.setHorizontalAlignment(JLabel.CENTER);
        TITLE.setFont(ListGui.TITLE);
        TITLE.setForeground(ListGui.TEXT);
        //add title
        addTitle();

        //add the list length counter
        COUNTER = new JLabel();
        addCounter();

        //create the Jlist
        LIST = new JList<>();
//        LIST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        LIST.addListSelectionListener(makeAccessListener());
        LIST.setCellRenderer(new EntryCellRenderer());
        MODEL = new DefaultListModel<>();
        LIST.setModel(MODEL);
        LIST.setFixedCellHeight(24);


        //create ScrollPane
        SCROLLER = new JScrollPane();
        SCROLLER.getViewport().setView(LIST);

        //add scrollpane to panel

        GBC.gridheight = GridBagConstraints.REMAINDER;
        GBC.fill = GridBagConstraints.BOTH;
        GBC.weighty = 1.0;

        Util.addToGb(this, SCROLLER, GBC, 0, 2);
    }

    private void addTitle() {
        //put title in a panel
        JPanel titlePanel = Util.newPanel(TITLE, new BorderLayout(),BorderLayout.CENTER);

        titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //add title to graph
        GBC.gridwidth = GridBagConstraints.REMAINDER;
        GBC.gridheight = 1;
        GBC.weightx = 1.0;
        GBC.weighty = 0.1;
        GBC.fill = GridBagConstraints.HORIZONTAL;

        Util.addToGb(this, TITLE, GBC, 0, 0);
    }

    //add the list length counter
    private void addCounter() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        COUNTER.setHorizontalAlignment(SwingConstants.LEFT);
        COUNTER.setText("0 entries");
        COUNTER.setForeground(ListGui.TEXT);
        panel.add(COUNTER, BorderLayout.WEST);

        panel.setBackground(ListGui.BACKGROUND);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        GBC.weightx = 1.0;
        GBC.weighty = 0.1;
        GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.gridwidth = GridBagConstraints.REMAINDER;
        GBC.gridheight = 1;

        Util.addToGb(this, panel, GBC, 0, 1);
    }

    /**Updates the displayed entry count to the length of the list.
     *
     */
    public void updateCount() {
        COUNTER.setText(MODEL.getSize() + " entries");
    }

    //clears the selection of the list
    public void clearSelection() {
        LIST.clearSelection();
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

    /**Sets the title of the scroll list to the specified string.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        TITLE.setText(title);
    }

    //creates an access listener
    //notifies when a new entry has been selected
    private ListSelectionListener makeAccessListener() {
        return e -> {
            if (!(e.getValueIsAdjusting())) {
                OBSERVER.notifySelection(LIST.getSelectedValue(), ScrollTaskList.this);
            }
        };
    }


}
