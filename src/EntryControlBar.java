import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryControlBar extends JPanel {

    private final ListManager MANAGER;

    public EntryControlBar(ListManager manager) {
        super();
        MANAGER = manager;

        FlowLayout layout = new FlowLayout();
        layout.setHgap(20);
        setLayout(layout);
        addButtons();

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void addButtons() {
        JButton add = new JButton();
        add.setText("New Entry");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(add);

        JButton complete = new JButton();
        complete.setText("Complete");
        add(complete);

        JButton delete = new JButton();
        delete.setText("Delete Entry");
        add(delete);
    }

}
