import listItemStorage.ListEntry;

import javax.swing.*;
import java.awt.*;

public class ScrollTaskList extends JPanel {

    private static final int DEF_WIDTH = 500;
    private static final int DEF_HEIGHT = 200;


    public final JScrollPane ROOT;

    private final JList<ListEntry> LIST;

    public final DefaultListModel<ListEntry> MODEL;



    public ScrollTaskList() {
        super();

        //create the Jlist
        LIST = new JList<>();
        MODEL = new DefaultListModel<>();
        LIST.setModel(MODEL);
        LIST.setFixedCellHeight(24);


        //create ScrollPane
        ROOT = new JScrollPane();
        ROOT.getViewport().setView(LIST);
        ROOT.setSize(DEF_WIDTH, DEF_HEIGHT);


        //set size to default
        add(ROOT);
    }

}
