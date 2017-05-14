package onpu.diplom.mironov.cew.actions;

import java.util.Map;
import java.util.Properties;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.RequestDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class RequestListAllAction extends RequestListAction{
    
    public RequestListAllAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text, RequestDao requestDao) {
        super(user, view, tableModel, actions, text, requestDao);
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.REQUEST_LIST_ALL;
    }
}
