package onpu.diplom.mironov.cew.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import javax.swing.ListCellRenderer;
import onpu.diplom.mironov.cew.bean.Request;

public class MainFrame extends JFrame {
    private final JMenuBar menuBar = new JMenuBar();
    private final JToolBar toolBar = new JToolBar();
    private final JLabel requestsLabel = new JLabel("");
    private final JList<Request> requestsList = new JList<>(new DefaultListModel<Request>());
    private final JTree roomsTree = new JTree(new DefaultMutableTreeNode());
    private final JTable mainTable = new JTable();
    private final JLabel statusLabel = new JLabel("");

    public MainFrame()  throws HeadlessException {
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(new ResizeCornerPanel(20), BorderLayout.EAST);

        JPanel requestsListPanel = new JPanel(new BorderLayout());
        requestsListPanel.add(requestsLabel, BorderLayout.PAGE_START);
        requestsListPanel.add(new JScrollPane(requestsList), BorderLayout.CENTER);
        JSplitPane mainSplitPane = new JSplitPane(HORIZONTAL_SPLIT,  
                new JSplitPane(VERTICAL_SPLIT, roomsTree, requestsListPanel),
                new JScrollPane(mainTable));
        mainSplitPane.setDividerLocation(.35);

        setLayout(new BorderLayout());
        Container contentPane = getContentPane();
        contentPane.add(toolBar, BorderLayout.PAGE_START);
        contentPane.add(statusPanel, BorderLayout.PAGE_END);
        contentPane.add(mainSplitPane, BorderLayout.CENTER);
        setJMenuBar(menuBar);
    }
 
    public void init(Properties text,
            List<? extends AbstractAction> toolBarActions, 
            Map<String, List<? extends AbstractAction>> menuActions,
            final AbstractAction exitAction,
            TableModel dataModel,
            ListSelectionListener mainTalbeSelectionListener,
            TreeCellRenderer roomsTreeCellRenderer,
            ListCellRenderer<Request> requestsListCellRenderer) {

        mainTable.setModel(dataModel);
        ListSelectionModel tableSelectionModel = mainTable.getSelectionModel();
        tableSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSelectionModel.addListSelectionListener(mainTalbeSelectionListener);

        requestsLabel.setText(text.getProperty("mainframe.requests.title", "Requests"));
        requestsList.setCellRenderer(requestsListCellRenderer);
        requestsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        roomsTree.setCellRenderer(roomsTreeCellRenderer);
        roomsTree.expandRow(0);
        TreeSelectionModel roomsTreeSelectionModel = roomsTree.getSelectionModel();
        roomsTreeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        for (AbstractAction action : toolBarActions) {
            if (action != null && action.isEnabled()) {
                JButton btn = new JButton(action);
                btn.setVerticalTextPosition(SwingConstants.BOTTOM);
                btn.setHorizontalTextPosition(SwingConstants.CENTER);
                toolBar.add(btn);
            }
        }

        for(Map.Entry<String, List<? extends AbstractAction>> entry : menuActions.entrySet()) {
            JMenu menu = menuBar.add(new JMenu(entry.getKey()));
            for (AbstractAction action : entry.getValue()) {
                if (action != null && action.isEnabled()) {
                    menu.add(action);
                }
            }
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(new ActionEvent(e.getSource(), 
                        e.getID(), e.paramString()));
            }
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle(text.getProperty("mainframe.title"));
        setSize(800, 640);
        setLocationRelativeTo(null);
    }

    public JTable getMainTable() {
        return mainTable;
    }

    public JList<Request> getRequestsList() {
        return requestsList;
    }

    public JTree getRoomsTree() {
        return roomsTree;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }
}
