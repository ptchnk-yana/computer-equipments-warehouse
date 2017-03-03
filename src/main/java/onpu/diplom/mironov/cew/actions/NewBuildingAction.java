package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import onpu.diplom.mironov.cew.ConfigurationService;
import onpu.diplom.mironov.cew.bean.Building;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.BuildingDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import onpu.diplom.mironov.cew.view.NewBuildingPanel;

public class NewBuildingAction extends BuildingListRefreshAction {
    
    public NewBuildingAction(User user, MainFrame view, MainTableModel tableModel, 
            Map<ActionEnum, AbstractCewAction> actions, Properties text, 
            ConfigurationService configService, BuildingDao buildingDao) {
        super(user, view, tableModel, actions, text, configService, buildingDao);
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.NEW_BUILDING;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) throws Exception {
        Building building = createBuilding();
        if(building != null) {
            buildingDao.add(building);
            super.actionPerformedImpl(e);
        }
    }

    public Building createBuilding() {
        NewBuildingPanel panel = new NewBuildingPanel();
        if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
                view, 
                panel, 
                getText("new_building.title"), 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                new Object[]{getText("create"), getText("cancel")}, 
                null)) {
            Building building = new Building(panel.getNameText(), panel.getCommentText());
            if (building.getName() == null || building.getName().length() < 3) {
                showErrorMessage(getText("new_building.invalid.name"),
                        getText("new_building.title"));
                return null;
            }
            for(Building u : buildingDao.list()) {
                if(u.getName().equals(building.getName())) {
                    showErrorMessage(getText("new_building.buildingexist"),
                            getText("new_building.title"));
                    return null;
                }
            }
            return building;
        }
        return null;
    }
}
