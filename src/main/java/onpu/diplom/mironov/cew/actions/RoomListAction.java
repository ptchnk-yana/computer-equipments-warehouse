package onpu.diplom.mironov.cew.actions;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.bean.UserPrivilege;
import onpu.diplom.mironov.cew.dao.RoomDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class RoomListAction extends AbstractCewAction {
    private final RoomDao roomDao;

    public RoomListAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text, RoomDao roomDao) {
        super(user, view, tableModel, actions, text);
        this.roomDao = roomDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.ROOM_LIST;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        actionPerformedImpl(true);
    }

    public void actionPerformedImpl(boolean showNotification) throws HeadlessException {
        List<Room> list = new ArrayList<>();
        User user = fillRoomList(list, showNotification);
        view.getStatusLabel().setText(user != null 
                ? String.format(getText("room_list.status.user"), user.getName())
                : getText("room_list.status"));
        actions.get(ActionEnum.DELETE).setEnabled(false);
        tableModel.init(Room.class, list);
    }

    public User fillRoomList(List<Room> list, boolean showNotification) throws HeadlessException {
        User user = null;

        if (currentUser.getPrivilege() == UserPrivilege.ADMIN) {
            int selectedRow;
            if (tableModel.getDataType() == User.class 
                    && (selectedRow = view.getMainTable().getSelectedRow()) >= 0) {
                user = tableModel.getObjectAt(selectedRow);
            } else if (showNotification) {
                JOptionPane.showMessageDialog(view, getText("room_list.nouser.msg"), 
                        getText("room_list.nouser.title"), JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            user = currentUser;
        }

        list.addAll(roomDao.findByUserAndBuilding(user, getSelectedBuilding()));
        return user;
    }
    
    
}
