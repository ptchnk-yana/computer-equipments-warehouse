package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.ConfigurationService;
import onpu.diplom.mironov.cew.bean.Building;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.BuildingDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class DeleteBuilding extends BuildingListRefreshAction {

    public DeleteBuilding(User user, MainFrame view, MainTableModel tableModel, Map<ActionEnum, AbstractCewAction> actions, Properties text, ConfigurationService configService, BuildingDao buildingDao) {
        super(user, view, tableModel, actions, text, configService, buildingDao);
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.DELETE_BUILDING;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) throws Exception {
        Building selectedBuilding = getSelectedBuilding();
        if (selectedBuilding != null) {
            if (JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(view,
                    String.format(getText("delete_building.msg"), selectedBuilding.getName()),
                    getText("delete_building.title"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{getText("yes"), getText("no")},
                    null)) {
                buildingDao.delete(selectedBuilding);
                super.actionPerformedImpl(e);
            }
        } else {
            showErrorMessage(getText("delete_building.noselection"),
                    getText("delete_building.title"));
        }
    }
}
