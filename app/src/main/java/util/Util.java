package util;

import gui.ListGui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Util {

    /**Enum representing all the months of the year.
     * The months are zero indexed.
     *
     */
    public enum Months {
        JANUARY("Jan"),
        FEBUARY("Feb"),
        MARCH("Mar"),
        APRIL("Apr"),
        MAY("May"),
        JUNE("June"),
        JULY("July"),
        AUGUST("Aug"),
        SEPTEMBER("Sep"),
        OCTOBER("Oct"),
        NOVEMBER("Nov"),
        DECEMBER("Dec");

        public final String name;

        /**Returns the String symbol for each month.
         *
         * @return the month's symbol
         */
        @Override
        public String toString() {
            return name;
        }

        Months(String symbol) {
            name = symbol;
        }

    }




    /**Adds the specified Jcomponent to the specified target JComponent assuming
     * the target is using a GridBagLayout.
     * The JComponent will be added at the specified coordinates in the layout.
     * All other GridBagConstraint fields should be set before calling this method.
     * The gridx and gridy fields of the specified constraints will be unchanged from before calling the method.
     * If the target Component is using a different layout, the method will no-op.
     *
     * @param target the component to add to
     * @param toAdd the component to add
     * @param contraints the GridBagConstraints to apply
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public static void addToGb(Container target, JComponent toAdd, GridBagConstraints contraints, int x, int y) {
        if (target.getLayout() instanceof GridBagLayout) {
            int tempx = contraints.gridx;
            int tempy = contraints.gridy;
            GridBagLayout layout = (GridBagLayout) (target.getLayout());
            contraints.gridx = x;
            contraints.gridy = y;
            layout.setConstraints(toAdd, contraints);
            target.add(toAdd);
            contraints.gridx = tempx;
            contraints.gridy = tempy;
        }
    }

    /**Creates a new JPanel with the specified component added according to the specified layout.
     *
     * @param c the component to add to the panel
     * @param layout the layout of the panel
     * @param layoutRules the constraints for adding the component
     * @return a JPanel containing the component.
     */
    public static JPanel newPanel(Component c, LayoutManager layout, Object layoutRules) {
        JPanel panel = new JPanel();
        panel.setBackground(ListGui.COLOR_BACKGROUND);
        panel.setForeground(ListGui.COLOR_TEXT);
        if (layout != null) {
            panel.setLayout(layout);
        }
        if (layoutRules != null) {
            panel.add(c, layoutRules);
        } else {
            panel.add(c);
        }
        return panel;
    }

    //helper to create new label with correct colors
    public static JLabel newLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(ListGui.COLOR_TEXT);
        return label;
    }

    //helper to create new label with correct colors
    public static JLabel newLabel() {
        JLabel label = new JLabel();
        label.setForeground(ListGui.COLOR_TEXT);
        return label;
    }

    //helper to make new button with app theme
    public static JButton newButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ListGui.COLOR_BUTTON);
        button.setForeground(ListGui.COLOR_TEXT);
        return button;
    }


}
