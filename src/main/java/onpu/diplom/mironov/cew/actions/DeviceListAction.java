package onpu.diplom.mironov.cew.actions;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.CewUtil;
import onpu.diplom.mironov.cew.bean.Device;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.bean.UserPrivilege;
import onpu.diplom.mironov.cew.dao.DeviceDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class DeviceListAction extends AbstractCewAction {

    private final DeviceDao deviceDao;

    public DeviceListAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text,
            DeviceDao deviceDao) {
        super(user, view, tableModel, actions, text);
        this.deviceDao = deviceDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.DEVICE_LIST;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        actionPerformedImpl(view.getMainTable().getSelectedRow(), true);
    }

    public void actionPerformedImpl(int selectedRow, boolean showDefaultNotification) 
            throws HeadlessException {
        // user --> room -->  device  <-- deviceType
        User user = null;
        Room room = null;
        DeviceType deviceType = null;
        String status = null;
        if (selectedRow >= 0 && tableModel.getDataType() == User.class) {
            user = tableModel.getObjectAt(selectedRow);
            status = String.format(getText("device_list.for.user"), user.getName());
            JOptionPane.showMessageDialog(view, status, getText("device_list.title"),
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (selectedRow >= 0 && tableModel.getDataType() == Room.class) {
            room = tableModel.getObjectAt(selectedRow);
            status = String.format(getText("device_list.for.room"), room.getNumber(), room.getTitle());
            JOptionPane.showMessageDialog(view, status, getText("device_list.title"),
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (selectedRow >= 0 && tableModel.getDataType() == DeviceType.class) {
            deviceType = tableModel.getObjectAt(selectedRow);
            status = String.format(getText("device_list.for.deviceType"), deviceType.getTitle());
            JOptionPane.showMessageDialog(view, status, getText("device_list.title"),
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (currentUser.getPrivilege() == UserPrivilege.ADMIN) {
            status = getText("device_list.for.all");
            JOptionPane.showMessageDialog(view, status, getText("device_list.title"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
        if (currentUser.getPrivilege() != UserPrivilege.ADMIN && user == null) {
            user = currentUser;
        }
        if (status == null) {
            status = getText("device_list.for.privileged");
            if (showDefaultNotification) {
                JOptionPane.showMessageDialog(view, status, getText("device_list.title"),
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
//        Set<Long> roomIds = getApplicableRoomIds(user, room);
//        List<Device> listDevices = getApplicableDevices(deviceType, roomIds);
        List<Device> listDevices = deviceDao.findByTypeAndUserAndRoom(deviceType, user, room);
        view.getStatusLabel().setText(status);
        actions.get(ActionEnum.DELETE).setEnabled(false);
        tableModel.init(listDevices);
    }

    public List<Device> listDevices() {
        return deviceDao.list();
    }

//    private Set<Long> getApplicableRoomIds(User user, Room room) {
//        Set<Long> listRoomIds = new HashSet<Long>();
//        List<Room> listRoms = ((RoomListAction) actions.get(ActionEnum.ROOM_LIST)).listRoms();
//        for (Room actualRoom : listRoms) {
//            if ((user == null || user.getId() == actualRoom.getUserId())
//                    && (room == null || room.getId() == actualRoom.getId())) {
//                listRoomIds.add(actualRoom.getId());
//            }
//        }
//        return listRoomIds;
//    }
//
//    private List<Device> getApplicableDevices(DeviceType deviceType, Set<Long> roomIds) {
//        List<Device> listDevices = listDevices();
//        Map<Long, DeviceType> deviceTypes = CewUtil.toMap(((DeviceTypeListAction) 
//                super.actions.get(ActionEnum.DEVICE_TYPE_LIST)).listDeviceTypes());
//        Map<Long, Room> rooms = CewUtil.toMap(((RoomListAction) 
//                super.actions.get(ActionEnum.ROOM_LIST)).listRoms());
//
//        for (Iterator<Device> it = listDevices.iterator(); it.hasNext();) {
//            Device iteratedValue = it.next();
//            if (deviceType != null && deviceType.getId() != iteratedValue.getDeviceTypeId()) {
//                it.remove();
//            } else if (!roomIds.contains(iteratedValue.getRoomId())) {
//                it.remove();
//            } else {
//                DeviceType actualDeviceType = deviceTypes.get(iteratedValue.getDeviceTypeId());
//                if (actualDeviceType != null) {
//                    iteratedValue.setDeviceImageUrl(actualDeviceType.getImageUrl());
//                    iteratedValue.setDeviceTitle(actualDeviceType.getTitle());
//                }
//                Room actualRoom = rooms.get(iteratedValue.getRoomId());
//                if (actualRoom != null) {
//                    iteratedValue.setRoomNumber(actualRoom.getNumber());
//                }
//            }
//        }
//        return listDevices;
//    }

}
