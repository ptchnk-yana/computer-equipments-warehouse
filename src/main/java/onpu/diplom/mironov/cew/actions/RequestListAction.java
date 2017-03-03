package onpu.diplom.mironov.cew.actions;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.Request;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.bean.UserPrivilege;
import onpu.diplom.mironov.cew.dao.RequestDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class RequestListAction extends AbstractCewAction {
    private final RequestDao requestDao;

    public RequestListAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text, RequestDao requestDao) {
        super(user, view, tableModel, actions, text);
        this.requestDao = requestDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.REQUEST_LIST;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        actionPerformedImpl(e, true);
    }

    public void actionPerformedImpl(ActionEvent e, boolean showNotification) throws HeadlessException {
        List<Request> list = new ArrayList<>();
        User user = fillRequestList(list, showNotification);
        view.getStatusLabel().setText(user != null 
                ? String.format(getText("request_list.status.user"), user.getName())
                : getText("request_list.status"));
        actions.get(ActionEnum.DELETE).setEnabled(false);
        tableModel.init(Request.class, list);

        actions.get(ActionEnum.REQUEST_LIST_REFRESH).actionPerformed(e);
    }

    public User fillRequestList(List<Request> list, boolean showNotification) throws HeadlessException {
        User user = null;

        if (currentUser.getPrivilege() == UserPrivilege.ADMIN) {
            int selectedRow;
            if (tableModel.getDataType() == User.class 
                    && (selectedRow = view.getMainTable().getSelectedRow()) >= 0) {
                user = tableModel.getObjectAt(selectedRow);
            } else if (showNotification) {
                JOptionPane.showMessageDialog(view, getText("request_list.nouser.msg"), 
                        getText("request_list.nouser.title"), JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            user = currentUser;
        }

        list.addAll(requestDao.findByUser(user));
        return user;
    }
    
    
}
