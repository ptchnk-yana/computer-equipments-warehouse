package onpu.diplom.mironov.cew.actions;

import java.util.HashMap;
import java.util.Map;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import onpu.diplom.mironov.cew.bean.AbstractBean;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import onpu.diplom.mironov.cew.bean.Device;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.bean.Room;

import static onpu.diplom.mironov.cew.actions.ActionEnum.DELETE;
import static onpu.diplom.mironov.cew.actions.ActionEnum.NEW_DEVICE;
import static onpu.diplom.mironov.cew.actions.ActionEnum.NEW_DEVICE_TYPE;
import static onpu.diplom.mironov.cew.actions.ActionEnum.NEW_ROOM;
import static onpu.diplom.mironov.cew.actions.ActionEnum.NEW_USER;

public class MainTalbeSelectionListener implements ListSelectionListener {
    protected final MainFrame view;
    protected final Map<ActionEnum, AbstractCewAction> actions;
    protected final User currentUser;
    protected final MainTableModel tableModel;

    private final Map<Class<? extends AbstractBean>, Boolean> deletePrivileges;

    public MainTalbeSelectionListener(MainFrame view, Map<ActionEnum, AbstractCewAction> actions, 
            User user, MainTableModel tableModel) {
        this.view = view;
        this.actions = actions;
        this.currentUser = user;
        this.tableModel = tableModel;
        deletePrivileges = new HashMap<Class<? extends AbstractBean>, Boolean>();
        deletePrivileges.put(Device.class, defineDeletePrivilege(NEW_DEVICE, user));
        deletePrivileges.put(DeviceType.class, defineDeletePrivilege(NEW_DEVICE_TYPE, user));
        deletePrivileges.put(Room.class, defineDeletePrivilege(NEW_ROOM, user));
        deletePrivileges.put(User.class, defineDeletePrivilege(NEW_USER, user));
    }

    private static boolean defineDeletePrivilege(ActionEnum actionDef, User currentUser1) {
        return actionDef.getPrivilege().getPrivilageValue() 
                >= currentUser1.getPrivilege().getPrivilageValue();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        actions.get(DELETE).setEnabled(isCurrentUserPrivilegedToDeleteCurrentSelection());
    }

    public boolean isCurrentUserPrivilegedToDeleteCurrentSelection() {
        int selectedRow = view.getMainTable().getSelectedRow();
        return selectedRow >= 0 && deletePrivileges.get(tableModel.getObjectAt(selectedRow).getClass());
    }
    
}
