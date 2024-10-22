import listItemStorage.ListEntry;

import javax.swing.*;
import java.awt.*;

public class ScrollTaskList extends JPanel {

    private final JScrollPane ROOT;

    private final JList<ListEntry> LIST;

    public final DefaultListModel<ListEntry> MODEL;



    public ScrollTaskList() {
        super();

        setLayout(new BorderLayout());

        //create the Jlist
        LIST = new JList<>();
        MODEL = new DefaultListModel<>();
        LIST.setModel(MODEL);
        LIST.setFixedCellHeight(24);


        //create ScrollPane
        ROOT = new JScrollPane();
        ROOT.getViewport().setView(LIST);

        add(ROOT, BorderLayout.CENTER);
    }

}
