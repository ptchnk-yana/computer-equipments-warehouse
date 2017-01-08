package onpu.diplom.mironov.cew.actions;

import java.util.Map;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import static javax.swing.Action.LARGE_ICON_KEY;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;

public abstract class AbstractCewAction extends AbstractAction {
    protected final MainFrame view;
    protected final Map<ActionEnum, AbstractCewAction> actions;
    protected final Properties text;
    protected final User currentUser;
    protected final MainTableModel tableModel;

    public AbstractCewAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text) {
        this.view = view;
        this.actions = actions;
        this.text = text;
        this.currentUser = user;
        this.tableModel = tableModel;
        ActionEnum type = getType();
        String name = type.name().toLowerCase();
        putValue(NAME, getText(name));
        putValue(SMALL_ICON, getImageIcon(name + "_small"));
        putValue(LARGE_ICON_KEY, getImageIcon(name + "_large"));
        setEnabled(user.getPrivilege().getPrivilageValue() <= type.getPrivilege().getPrivilageValue());
    }

    protected ImageIcon getImageIcon(String name) {
        try {
            return new ImageIcon(getClass().getResource("/images/" + name + ".png"));
        } catch (Exception e) {
            return null;
        }
    }

    protected String getText(String key) {
        return text.getProperty(key, "${" + key + "}");
    }

    protected void showException(Exception ex) {
        JTextArea textArea = new JTextArea(ex.getMessage());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        showErrorMessage(textArea, null);
    }

    protected void showErrorMessage(Object msg, String title) {
        if (title == null) {
            title = getText("error.title");
        }
        JOptionPane.showMessageDialog(view, msg, getText("error.title"), 
                JOptionPane.ERROR_MESSAGE);
    }


    protected abstract ActionEnum getType();
}
