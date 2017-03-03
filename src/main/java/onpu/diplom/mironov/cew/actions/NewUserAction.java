package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.UserDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import onpu.diplom.mironov.cew.view.NewUserPanel;

public class NewUserAction extends AbstractCewAction {
    private final UserDao userDao;

    public NewUserAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text, UserDao userDao) {
        super(user, view, tableModel, actions, text);
        this.userDao = userDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.NEW_USER;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        List<User> userList = userDao.list();
        User user = createUser(userList);
        if(user != null) {
            userDao.add(user);
            actions.get(ActionEnum.USER_LIST).actionPerformed(e);
        }
    }

    public User createUser(List<User> userList) {
        NewUserPanel panel = new NewUserPanel();
        if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
                view, 
                panel, 
                getText("new_user.title"), 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                new Object[]{getText("create"), getText("cancel")}, 
                null)) {
            User user = new User(panel.getNameText(), panel.getPasswordText(), 
                    panel.getUserPrivilege());
            if (user.getName() == null || user.getName().length() < 3) {
                showErrorMessage(getText("new_user.invalid.name"), getText("new_user.title"));
                return null;
            }
            if (user.getPassword() == null || user.getPassword().length() < 3) {
                showErrorMessage(getText("new_user.invalid.pswd"), getText("new_user.title"));
                return null;
            }
            for(User u :userList) {
                if(u.getName().equals(user.getName())) {
                    showErrorMessage(getText("new_user.userexist"), getText("new_user.title"));
                    return null;
                }
            }
            return user;
        }
        return null;
    }
}
