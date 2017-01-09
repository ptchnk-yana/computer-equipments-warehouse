package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.UserDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class UserListAction extends AbstractCewAction {
    private final UserDao userDao;

    public UserListAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text, UserDao userDao) {
        super(user, view, tableModel, actions, text);
        this.userDao = userDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.USER_LIST;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        // We don't check privileges here, because only admin can see this action.
        actions.get(ActionEnum.DELETE).setEnabled(false);
        tableModel.init(userList());
        view.getStatusLabel().setText(getText("user_list.status"));
    }

    public List<User> userList() {
        return userDao.list();
    }
    
}
