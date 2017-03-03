package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.util.Map;
import java.util.Properties;
import javax.swing.JTable;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class PrintAction extends AbstractCewAction {

    public PrintAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text) {
        super(user, view, tableModel, actions, text);
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.PRINT;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) throws PrinterException {
        view.getMainTable().print(JTable.PrintMode.NORMAL);
    }
    
}
