package gui;

import listItemStorage.ListEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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

    private final GridBagLayout layout;
    protected final GridBagConstraints gbc;

    protected static final Font subTitleFont = new Font("arial", Font.PLAIN, 24);
    protected static final Font smallFont = new Font("arial", Font.PLAIN, 12);

    public EntryAccessPanel(ListEntry entry) {
        super();
        toDisplay = entry;
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(layout);
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
        comp.setFont(ListGui.TITLE);
        comp.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //gbc constraints
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(comp, 0, 0);
    }

    protected void addDescAccessor(JComponent comp) {
        JLabel descTitle = new JLabel("Description:");

        descTitle.setFont(subTitleFont);

        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(descTitle, 0, 1);

        descDisplay = comp;

        JScrollPane descPane = new JScrollPane();
        descPane.setViewportView(comp);
        descPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        gbc.weighty = 0.5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        addComponent(descPane, 0, 2);
    }

    protected void addDateAccessor(JComponent comp) {
        dateDisplay = comp;
        comp.setFont(smallFont);

        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;

        addComponent(comp, 0, 3);

    }

    protected void addButtons(JComponent comp) {
        buttonDisplay = comp;
        comp.setFont(smallFont);

        gbc.weighty = 0.1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(comp, 0, 4);
    }

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

    //adds components to specified grid in layout
    protected void addComponent(Component c, int x, int y) {
        int origx = gbc.gridx;
        int origy = gbc.gridy;
        gbc.gridx = x;
        gbc.gridy = y;
        layout.setConstraints(c, gbc);
        add(c);
        gbc.gridx = origx;
        gbc.gridy = origy;
    }

}
