package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.Request;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.RequestDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import onpu.diplom.mironov.cew.view.NewRequestPanel;

public class NewRequestAction extends AbstractCewAction {
    private final RequestDao requestDao;

    public NewRequestAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text, 
            RequestDao requestDao) {
        super(user, view, tableModel, actions, text);
        this.requestDao = requestDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.NEW_REQUEST;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        NewRequestPanel panel = new NewRequestPanel(null);
        if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
                view, 
                panel, 
                getText("new_request.title"), 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                new Object[]{getText("create"), getText("cancel")}, 
                null)) {
            Long id = currentUser.getId();

            if (panel.getTitle() == null || panel.getTitle().length() < 3) {
                showErrorMessage(getText("new_request.invalid.title"), getText("new_request.title"));
                return;
            }

            this.requestDao.add(new Request(id, null, panel.getTitle(), panel.getDescription(), Request.Status.OPEN));
            ((RequestListAction) actions.get(ActionEnum.REQUEST_LIST)).actionPerformedImpl(e, false);
        }
    }
    
}
