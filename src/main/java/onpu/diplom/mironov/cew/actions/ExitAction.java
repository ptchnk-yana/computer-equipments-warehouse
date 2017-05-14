package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class ExitAction extends AbstractCewAction {

    public ExitAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text) {
        super(user, view, tableModel, actions, text);
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        if (JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(view, 
                getText("exit.msg"), 
                getText("exit.title"), 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                new Object[]{getText("yes"), getText("no")}, 
                null)) {
            System.exit(0);
        }
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.EXIT;
    }
    
}
