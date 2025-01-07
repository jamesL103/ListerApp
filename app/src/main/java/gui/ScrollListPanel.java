package gui;

import listItemStorage.ListEntry;
import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ScrollListPanel extends JPanel {

    private final JScrollPane SCROLLER;

    public final JList<ListEntry> LIST;

    private final JPanel TOP_PANEL;

    private final JLabel TITLE;

    private final JLabel COUNTER;

    public final DefaultListModel<ListEntry> MODEL;

    private final ListGui.ListSelectionObserver OBSERVER;

    private final GridBagConstraints GBC;



    public ScrollListPanel(ListGui.ListSelectionObserver observer) {
        super();

        OBSERVER = observer;

        //set layout
        GBC = new GridBagConstraints();
        setLayout(new GridBagLayout());


        //set title
        TOP_PANEL = new JPanel();
        TITLE = Util.newLabel();
        TITLE.setFont(ListGui.FONT_TITLE);
        TITLE.setHorizontalAlignment(SwingConstants.CENTER);

        //add title to the top panel
        addTitle();

        //add the list length counter to the top panel
        COUNTER = new JLabel();
        addCounter();
        
        add(TOP_PANEL, makeTopPanelConstraints());

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

    private void addTitle() {
        //put title in a panel
        TOP_PANEL.setLayout(new BorderLayout());

        TOP_PANEL.add(TITLE, BorderLayout.NORTH);

        TOP_PANEL.setBorder(BorderFactory.createLineBorder(ListGui.COLOR_BORDER));
        TOP_PANEL.setBackground(ListGui.COLOR_BG_ACCENT);
    }

    //add the list length counter
    private void addCounter() {
        COUNTER.setHorizontalAlignment(SwingConstants.RIGHT);
        COUNTER.setText("0 entries");
        COUNTER.setForeground(ListGui.COLOR_TEXT);
        TOP_PANEL.add(COUNTER, BorderLayout.SOUTH);
    }
    
    //initializes the GridBagConstraints for the top panel
    private static GridBagConstraints makeTopPanelConstraints() {
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;

        return c;
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
                OBSERVER.notifySelection(LIST.getSelectedValue(), ScrollListPanel.this);
            }
        };
    }


}
