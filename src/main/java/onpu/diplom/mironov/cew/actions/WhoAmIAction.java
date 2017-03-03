package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class WhoAmIAction extends AbstractCewAction {

    public WhoAmIAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text) {
        super(user, view, tableModel, actions, text);
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.WHO_AM_I;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        JOptionPane.showMessageDialog(view, new StringBuilder()
                .append(getText("column.id")).append(": ")
                .append(currentUser.getId()).append('\n')
                .append(getText("column.name")).append(": ")
                .append(currentUser.getName()).append('\n')
                .append(getText("column.password")).append(": ")
                .append(currentUser.getPassword()).append('\n')
                .append(getText("column.privilege")).append(": ")
                .append(currentUser.getPrivilege()).append('\n').toString(), 
            getText("who_am_i.title"), JOptionPane.INFORMATION_MESSAGE);
    }
    
}
