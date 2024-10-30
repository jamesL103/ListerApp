package gui;

import javax.swing.*;
import java.awt.*;

public class EntryEditPanel extends JPanel {

    private final GridBagLayout layout;
    private final GridBagConstraints gbc;

    private Font titleFont = new Font("arial", Font.PLAIN, 36);

    public EntryEditPanel() {
        super();
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();

        addFields();
    }

    private void addFields() {
        JLabel name = new JLabel("default");
        name.setFont(titleFont);
        name.setHorizontalAlignment(JLabel.LEFT);
        name.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //gbc constraints for name
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(name, 0, 0);

    }

    private void addDescription() {

    }

    //helper to add component to specified position in layout
    private void addComponent(Component c, int x, int y) {
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
