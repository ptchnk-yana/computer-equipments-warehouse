package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.DeviceTypeDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class DeviceTypeListAction extends AbstractCewAction {
    private final DeviceTypeDao deviceTypeDao;

    public DeviceTypeListAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text, DeviceTypeDao deviceTypeDao) {
        super(user, view, tableModel, actions, text);
        this.deviceTypeDao = deviceTypeDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.DEVICE_TYPE_LIST;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        actions.get(ActionEnum.DELETE).setEnabled(false);
        tableModel.init(listDeviceTypes());
        view.getStatusLabel().setText(getText("device_type_list.status"));
    }

    public List<DeviceType> listDeviceTypes() {
        return deviceTypeDao.list();
    }
    
}
