package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.DefaultListModel;
import onpu.diplom.mironov.cew.bean.Request;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.bean.UserPrivilege;
import onpu.diplom.mironov.cew.dao.RequestDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class RequestListRefreshAction extends AbstractCewAction {
    private final RequestDao requestDao;

    public RequestListRefreshAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text, RequestDao requestDao) {
        super(user, view, tableModel, actions, text);
        this.requestDao = requestDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.REQUEST_LIST_REFRESH;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) throws Exception {
        final User user = currentUser.getPrivilege().getPrivilageValue() >= UserPrivilege.ADMIN.getPrivilageValue() 
                ? null : currentUser;
        final List<Request> requests = requestDao.findByUser(user);
        final DefaultListModel<Request> model = (DefaultListModel<Request>) view.getRequestsList().getModel();
        model.clear();

        for(Request request : requests) {
            if (request.getStatus() == Request.Status.OPEN || request.getStatus() == Request.Status.IN_PROGRESS) {
                model.addElement(request);
            }
        }
    }
    
}
