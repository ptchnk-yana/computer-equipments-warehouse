package onpu.diplom.mironov.cew.model;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import onpu.diplom.mironov.cew.bean.Building;

public class RoomsTreeCellRenderer extends DefaultTreeCellRenderer {
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
            boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        Building building = getSelectedBuilding(value, leaf);
        if (building != null) {
            setText(building.getName());
            setToolTipText(building.getComment());
        } else {
            setToolTipText(null);
        }
        return this;
    }

    private Building getSelectedBuilding(Object value, boolean leaf) {
        if (leaf) {
            Object userObject = ((DefaultMutableTreeNode)value).getUserObject();
            if (userObject instanceof Building) {
                return (Building) userObject;
            }
        }
        return null;
    }
    
}
