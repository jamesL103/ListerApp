import gui.ListGui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater( ()-> {
            ListGui gui = new ListGui();
        });

    }
}