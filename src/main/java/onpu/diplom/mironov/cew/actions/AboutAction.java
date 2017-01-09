package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class AboutAction extends AbstractCewAction {

    public AboutAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text) {
        super(user, view, tableModel, actions, text);
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.ABOUT;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        JOptionPane.showMessageDialog(view, getText("about.msg"), getText("about.title"), 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
}
