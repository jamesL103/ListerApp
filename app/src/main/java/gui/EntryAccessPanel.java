package gui;

import listItemStorage.ListEntry;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**Abstract class to create a gui that accesses the fields of a ListEntry.
 * Will automatically organize the components based on the layout and
 * give them access to the information.
 * All components will be sized and placed correctly in the gui,
 * but their properties will have to be set by any implementing subclass.
 *
 */
public abstract class EntryAccessPanel extends JPanel {

    protected ListEntry toDisplay;

    protected JComponent nameDisplay;
    protected JComponent descDisplay;
    protected JComponent dateDisplay;
    protected JComponent buttonDisplay;

    //exit button
    //ActionListener automatically added on init using makeExitListener()
    private final JButton EXIT_BUTTON = Util.newButton("x");

    //component GridBagConstraints
    private final static GridBagConstraints CONSTRAINTS_NAME = makeNameConstraints();
    private final static GridBagConstraints CONSTRAINTS_EXIT = makeExitButtonConstraints();
    private final static GridBagConstraints CONSTRAINTS_DESC = makeDescConstraints();
    private final static GridBagConstraints CONSTRAINTS_DATE = makeDateConstraints();
    private final static GridBagConstraints CONSTRAINTS_BUTTONS = makeButtonPanelConstraints();

    public static final Font subTitleFont = new Font("arial", Font.PLAIN, 24);
    public static final Font smallFont = new Font("arial", Font.PLAIN, 12);

    public EntryAccessPanel(ListEntry entry) {
        super();
        toDisplay = entry;
        GridBagLayout layout = new GridBagLayout();

        setBackground(ListGui.COLOR_BACKGROUND);

        setLayout(layout);
        addExitButton();
        initialize();
    }

    /**Method to be implemented by any subclasses.
     * Initializes all elements in the gui by setting all necessary
     * fields for the components
     * This method will be called on creating the gui.
     * Failing to set the name plate or description plate will result in errors.
     */
    public abstract void initialize();

    /**Add the specified JTextComponent to the top of the gui.
     * This component will display the name of the selected entry.
     *
     * @param comp the component to display the name.
     */
    protected void addNameAccessor(JComponent comp) {
        nameDisplay = comp;
        comp.setFont(ListGui.FONT_TITLE);

        comp.setBackground(ListGui.COLOR_BG_ACCENT);
        comp.setForeground(ListGui.COLOR_TEXT);

        add(comp, CONSTRAINTS_NAME);
    }

    protected void addDescAccessor(JComponent comp) {
        JPanel descPanel = new JPanel();
        descPanel.setLayout(new BoxLayout(descPanel, BoxLayout.Y_AXIS));

        JLabel descTitle = Util.newLabel("Description:");

        descTitle.setFont(subTitleFont);

        descPanel.add(descTitle);

        descDisplay = comp;
        comp.setBackground(ListGui.COLOR_BG_ACCENT);
        comp.setForeground(ListGui.COLOR_TEXT);

        JScrollPane descPane = new JScrollPane();
        descPane.setViewportView(comp);
        descPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        descPane.setPreferredSize(new Dimension(30, 50));

        descPanel.add(descPane);

        add(descPanel, CONSTRAINTS_DESC);
    }

    protected void addDateAccessor(JComponent comp) {
        dateDisplay = comp;
        comp.setFont(smallFont);

        comp.setBackground(ListGui.COLOR_BG_ACCENT);
        comp.setForeground(ListGui.COLOR_TEXT);

        add(dateDisplay, CONSTRAINTS_DATE);

    }

    protected void addButtons(JComponent comp) {
        buttonDisplay = comp;
        comp.setFont(smallFont);

        add(buttonDisplay, CONSTRAINTS_BUTTONS);
    }

    //create and add the exit button
    private void addExitButton() {
        EXIT_BUTTON.addActionListener(makeExitListener());

        add(EXIT_BUTTON, CONSTRAINTS_EXIT);
    }

    /**Creates and returns an ActionListener for when the close button is pressed.
     *
     * @return a Listener for the close button
     */
   public abstract ActionListener makeExitListener();

    /**Updates the gui to the current entry's fields.
     * Assumes the entry is not null.
     *
     */
    public abstract void updateFields();

    /**Sets the entry to be displayed and updates the gui based
     * on the new entry's fields.
     *
     * @param entry the new entry to display.
     */
    public void setEntry(ListEntry entry) {
        toDisplay = entry;
        updateFields();
    }

    public ListEntry getEntry() {
        return toDisplay;
    }


    private static GridBagConstraints makeNameConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private static GridBagConstraints makeExitButtonConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.NONE;
        return c;
    }

    private static GridBagConstraints makeDescConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.BOTH;
        return c;
    }

    private static GridBagConstraints makeDateConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1.0;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private static GridBagConstraints makeButtonPanelConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1.0;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

}
