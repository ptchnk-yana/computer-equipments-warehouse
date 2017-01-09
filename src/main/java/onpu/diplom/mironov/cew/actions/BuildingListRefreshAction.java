package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import onpu.diplom.mironov.cew.ConfigurationService;
import onpu.diplom.mironov.cew.bean.Building;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.BuildingDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;

public class BuildingListRefreshAction extends AbstractCewAction {
    protected final ConfigurationService configService;
    protected final BuildingDao buildingDao;

    public BuildingListRefreshAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text,
            ConfigurationService configService, BuildingDao buildingDao) {
        super(user, view, tableModel, actions, text);
        this.configService = configService;
        this.buildingDao = buildingDao;
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.BUILDING_LIST_REFRESH;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) throws Exception {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(configService.getOrganization());
        for(Building building : buildingDao.list()) {
            root.add(new DefaultMutableTreeNode(building));
        }
        ((DefaultTreeModel)view.getRoomsTree().getModel()).setRoot(root);
    }
    
}
