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
        setLayout(LAYOUT);


        //set title
        TITLE = new JLabel();
        TITLE.setHorizontalAlignment(JLabel.CENTER);
        TITLE.setFont(ListGui.TITLE);
        TITLE.setForeground(ListGui.TEXT);
        //add title
        addTitle();

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

        Util.addToGb(this, SCROLLER, GBC, 0, 1);
    }

    public void addTitle() {
        //put title in a panel
        JPanel titlePanel = Util.newPanel(TITLE, new BorderLayout(),BorderLayout.CENTER);

        titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        titlePanel.setPreferredSize(new Dimension(100,20));

        //add title to graph
        GBC.gridwidth = GridBagConstraints.REMAINDER;
        GBC.gridheight = GridBagConstraints.RELATIVE;
        GBC.weightx = 1.0;
        GBC.weighty = 0.1;
        GBC.fill = GridBagConstraints.HORIZONTAL;

        Util.addToGb(this, TITLE, GBC, 0, 0);
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
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!(e.getValueIsAdjusting())) {
                    OBSERVER.notifySelection(LIST.getSelectedValue(), ScrollTaskList.this);
                }
            }
        };
    }


}
