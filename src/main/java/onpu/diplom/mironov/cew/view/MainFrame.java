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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

public class MainFrame extends JFrame {
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JLabel statusLabel;
    private JTable mainTable;

    public MainFrame()  throws HeadlessException {
        menuBar = new JMenuBar();
        toolBar = new JToolBar();
        statusLabel = new JLabel("");
        mainTable = new JTable();
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(new ResizeCornerPanel(20), BorderLayout.EAST);

        setLayout(new BorderLayout());
        Container contentPane = getContentPane();
        contentPane.add(toolBar, BorderLayout.PAGE_START);
        contentPane.add(statusPanel, BorderLayout.PAGE_END);
        contentPane.add(new JScrollPane(mainTable), BorderLayout.CENTER);
        setJMenuBar(menuBar);
    }
 
    public void init(Properties text,
            List<? extends AbstractAction> toolBarActions, 
            Map<String, List<? extends AbstractAction>> menuActions,
            final AbstractAction exitAction,
            TableModel dataModel,
            ListSelectionListener mainTalbeSelectionListener) {
        mainTable.setModel(dataModel);
        ListSelectionModel tableSelectionModel = mainTable.getSelectionModel();
        tableSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSelectionModel.addListSelectionListener(mainTalbeSelectionListener);
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

    public JLabel getStatusLabel() {
        return statusLabel;
    }
}
