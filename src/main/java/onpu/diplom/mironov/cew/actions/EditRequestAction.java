package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.bean.Request;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.RequestDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import onpu.diplom.mironov.cew.view.NewRequestPanel;

public class EditRequestAction extends AbstractCewAction {
    private final RequestDao requestDao;

    public EditRequestAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text, 
            RequestDao requestDao) {
        super(user, view, tableModel, actions, text);
        this.requestDao = requestDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.EDIT_REQUEST;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        final int selectedRow = view.getMainTable().getSelectedRow();
        if (selectedRow >= 0 && tableModel.getDataType() == Request.class) {
            final Request request = tableModel.getObjectAt(selectedRow);
            final NewRequestPanel panel = new NewRequestPanel(request);
            if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
                    view, 
                    panel, 
                    getText("edit_request.title"), 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.PLAIN_MESSAGE, 
                    null, 
                    new Object[]{getText("create"), getText("cancel")}, 
                    null)) {
                if (panel.getTitle() == null || panel.getTitle().length() < 3) {
                    showErrorMessage(getText("new_request.invalid.title"), getText("edit_request.title"));
                    return;
                }

                this.requestDao.update(new Request(request.getId(), request.getUserId(), request.getUserName(),
                        panel.getTitle(), panel.getDescription(), panel.getStatus()));
                ((RequestListAction) actions.get(ActionEnum.REQUEST_LIST)).actionPerformedImpl(e, false);
            }
        } else {
            JOptionPane.showMessageDialog(view, getText("edit_request.invalid.selection"), getText("edit_request.title"),
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
}
