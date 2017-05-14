package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.AbstractBean;
import onpu.diplom.mironov.cew.bean.Device;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.AbstractDao;
import onpu.diplom.mironov.cew.dao.DeviceDao;
import onpu.diplom.mironov.cew.dao.DeviceTypeDao;
import onpu.diplom.mironov.cew.dao.RequestDao;
import onpu.diplom.mironov.cew.dao.RoomDao;
import onpu.diplom.mironov.cew.dao.UserDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class DeleteAction extends AbstractCewAction {

    private final MainTalbeSelectionListener selectionListener;
    private final Map<Class<? extends AbstractBean>, DaoWithFunction> classToDaoMap;

    public DeleteAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text,
            MainTalbeSelectionListener selectionListener, UserDao userDao,
            RoomDao roomDao, DeviceTypeDao deviceTypeDao, DeviceDao deviceDao,
            RequestDao requestDao) {
        super(user, view, tableModel, actions, text);
        this.selectionListener = selectionListener;

        Map<Class<? extends AbstractBean>, DaoWithFunction> classToDaoMapBuilder = new HashMap<>();
        classToDaoMapBuilder.put(userDao.getBeanClass(),
                new DaoWithFunction(userDao, actions.get(ActionEnum.USER_LIST)) {
            @Override
            String getMessage(AbstractBean bean) {
                return String.format(getText("delete.msg.user"), ((User)bean).getName());
            }
        });
        classToDaoMapBuilder.put(roomDao.getBeanClass(),
                new DaoWithFunction(roomDao, actions.get(ActionEnum.ROOM_LIST)) {
            @Override
            String getMessage(AbstractBean bean) {
                return String.format(getText("delete.msg.room"), ((Room)bean).getNumber(), ((Room)bean).getTitle());
            }
        });
        classToDaoMapBuilder.put(deviceTypeDao.getBeanClass(),
                new DaoWithFunction(deviceTypeDao, actions.get(ActionEnum.DEVICE_TYPE_LIST)) {
            @Override
            String getMessage(AbstractBean bean) {
                return String.format(getText("delete.msg.deviceType"), ((DeviceType)bean).getTitle());
            }
        });
        classToDaoMapBuilder.put(deviceDao.getBeanClass(),
                new DaoWithFunction(deviceDao, actions.get(ActionEnum.DEVICE_LIST)) {
            @Override
            String getMessage(AbstractBean bean) {
                return String.format(getText("delete.msg.device"), ((Device)bean).getHash());
            }
        });
        classToDaoMapBuilder.put(requestDao.getBeanClass(),
                new DaoWithFunction(requestDao, actions.get(ActionEnum.REQUEST_LIST)) {
            @Override
            String getMessage(AbstractBean bean) {
                return String.format(getText("delete.msg.request"), ((Device)bean).getHash());
            }
        });
        this.classToDaoMap = Collections.unmodifiableMap(classToDaoMapBuilder);
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.DELETE;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        if (!selectionListener.isCurrentUserPrivilegedToDeleteCurrentSelection()) {
            showErrorMessage(getText("delete.norights"), getText("delete.title"));
            return;
        }
        AbstractBean object = tableModel.getObjectAt(view.getMainTable().getSelectedRow());
        DaoWithFunction get = classToDaoMap.get(object.getClass());

        if (get == null) {
            JOptionPane.showMessageDialog(view, 
                    "For technical reasons the " + object.getClass().getSimpleName() + " cannot be deleted", 
                    getText("delete.title"), 
                    JOptionPane.WARNING_MESSAGE);
        } else if (JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(view,
                get.getMessage(object),
                getText("delete.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{getText("yes"), getText("no")},
                null)) {
            get.dao.delete(object);
            get.action.actionPerformed(e);
        }
    }

    abstract class DaoWithFunction {

        final AbstractDao dao;
        final AbstractCewAction action;

        public DaoWithFunction(AbstractDao dao, AbstractCewAction action) {
            this.dao = dao;
            this.action = action;
        }

        abstract String getMessage(AbstractBean bean);
    }
}
