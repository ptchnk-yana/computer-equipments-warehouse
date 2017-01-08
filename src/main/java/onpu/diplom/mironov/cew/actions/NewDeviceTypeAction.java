package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.DeviceTypeDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import onpu.diplom.mironov.cew.view.NewDeviceTypePanel;

public class NewDeviceTypeAction extends AbstractCewAction {
    private final DeviceTypeDao deviceTypeDao;

    public NewDeviceTypeAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text, 
            DeviceTypeDao deviceTypeDao) {
        super(user, view, tableModel, actions, text);
        this.deviceTypeDao = deviceTypeDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.NEW_DEVICE_TYPE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NewDeviceTypePanel panel = new NewDeviceTypePanel();
        if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
                view, 
                panel, 
                getText("new_device_type.title"), 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                new Object[]{getText("create"), getText("cancel")}, 
                null)) {
            DeviceType deviceType = new DeviceType(panel.getNameText(),
                    panel.getImageUrlText());
            if (deviceType.getTitle() == null || deviceType.getTitle().length() < 3) {
                showErrorMessage(getText("new_device_type.invalid.title"), 
                        getText("new_device_type.title"));
                return;
            }
            for (DeviceType dt : deviceTypeDao.list()) {
                if (dt.getTitle().equals(deviceType.getTitle())) {
                    showErrorMessage(getText("new_device_type.invalid.titleюexist"), 
                            getText("new_device_type.title"));
                    return;
                }
            }
            deviceTypeDao.add(deviceType);
            actions.get(ActionEnum.DEVICE_TYPE_LIST).actionPerformed(e);
        }
    }
    
}
