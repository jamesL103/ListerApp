package gui;

import javax.swing.*;
import java.awt.*;

public class EntryViewPanel extends JPanel {

    private final GridBagLayout layout;
    private final GridBagConstraints gbc;

    private static final Font titleFont = new Font("arial", Font.PLAIN, 36);
    private static final Font subTitleFont = new Font("arial", Font.PLAIN, 24);

    public EntryViewPanel() {
        super();
        layout = new GridBagLayout();
        setLayout(layout);
        gbc = new GridBagConstraints();

        addFields();
    }

    private void addFields() {
        JLabel name = new JLabel();
        name.setFont(titleFont);


    }

}
