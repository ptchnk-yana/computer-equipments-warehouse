package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.Device;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.DeviceDao;
import onpu.diplom.mironov.cew.dao.DeviceTypeDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import onpu.diplom.mironov.cew.view.NewDevicePanel;

public class NewDeviceAction extends AbstractCewAction {

    private final DeviceDao deviceDao;
    private final DeviceTypeDao deviceTypeDao;

    public NewDeviceAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text,
            DeviceDao deviceDao, DeviceTypeDao deviceTypeDao) {
        super(user, view, tableModel, actions, text);
        this.deviceDao = deviceDao;
        this.deviceTypeDao = deviceTypeDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.NEW_DEVICE;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        List<Room> roomList = new ArrayList<Room>();
        // TODO: extract this method to some util or service
        ((RoomListAction) actions.get(ActionEnum.ROOM_LIST)).fillRoomList(roomList, false);
        List<DeviceType> listDeviceTypes = deviceTypeDao.list();

        NewDevicePanel panel = new NewDevicePanel(
                listDeviceTypes.toArray(new DeviceType[listDeviceTypes.size()]),
                roomList.toArray(new Room[roomList.size()]));
        if (JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
                view,
                panel,
                getText("new_device.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{getText("create"), getText("cancel")},
                null)) {

            long deviceTypeId;
            try {
                deviceTypeId = panel.getSelectedDeviceType().getId();
            } catch (Exception ex) {
                showErrorMessage(getText("new_device.invalid.deviceType"),
                        getText("new_device.title"));
                return;
            }

            long roomId;
            try {
                roomId = panel.getSelectedRoom().getId();
            } catch (Exception ex) {
                showErrorMessage(getText("new_device.invalid.room"),
                        getText("new_device.title"));
                return;
            }

            Device device = new Device(deviceTypeId, roomId, panel.getHashText(),
                    panel.getDescriptionText());
            if (device.getHash() == null || device.getHash().length() < 3) {
                showErrorMessage(getText("new_device.invalid.hash"),
                        getText("new_device.title"));
                return;
            }

            for (Device d : deviceDao.list()) {
                if (d.getHash().equals(device.getHash())) {
                    showErrorMessage(getText("new_device.duplicate.hash"),
                            getText("new_device.title"));
                    return;
                }
            }

            deviceDao.add(device);
            ((DeviceListAction) actions.get(ActionEnum.DEVICE_LIST)).actionPerformedImpl(-1, false);
        }
    }

}
