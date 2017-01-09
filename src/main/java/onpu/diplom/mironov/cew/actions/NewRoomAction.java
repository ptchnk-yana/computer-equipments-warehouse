package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.RoomDao;
import onpu.diplom.mironov.cew.dao.UserDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import onpu.diplom.mironov.cew.view.NewRoomPanel;

public class NewRoomAction extends AbstractCewAction {
    private final RoomDao roomDao;
    private final UserDao userDao;

    public NewRoomAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text, 
            RoomDao roomDao, UserDao userDao) {
        super(user, view, tableModel, actions, text);
        this.roomDao = roomDao;
        this.userDao = userDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.NEW_ROOM;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        List<Room> rooms = this.roomDao.list();
        List<User> users = this.userDao.list();
        NewRoomPanel panel = new NewRoomPanel(users.toArray(new User[users.size()]));
        if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
                view, 
                panel, 
                getText("new_room.title"), 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                new Object[]{getText("create"), getText("cancel")}, 
                null)) {
            Long id;
            try {
                id = panel.getSelectedUser().getId();
            } catch (NullPointerException ex) {
                showErrorMessage(getText("new_room.nouser"), getText("new_room.title"));
                return;
            }

            int number;
            try {
                number = Integer.parseInt(panel.getNumberText());
            } catch (NumberFormatException numberFormatException) {
                showErrorMessage(getText("new_room.invalid.number"), getText("new_room.title"));
                return;
            }

            if(panel.getTitleText() == null || panel.getTitleText().length() < 3) {
                showErrorMessage(getText("new_room.invalid.title"), getText("new_room.title"));
                return;
            }

            Room room = new Room(id, number, panel.getTitleText());
            for (Room r : rooms) {
                if (r.getNumber() == room.getNumber()) {
                    showErrorMessage(getText("new_room.invalid.number.exist"), getText("new_room.title"));
                    return;
                }
            }

            roomDao.add(room);
            ((RoomListAction)actions.get(ActionEnum.ROOM_LIST)).actionPerformedImpl(false);
        }
    }
    
}
