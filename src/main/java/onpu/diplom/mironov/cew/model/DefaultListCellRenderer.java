package onpu.diplom.mironov.cew.model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class DefaultListCellRenderer<T> extends JLabel implements ListCellRenderer<T> {

    public DefaultListCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends T> list,
            T value, int index, boolean isSelected, boolean cellHasFocus) {

        setText(getText(value));
        setIcon(getIcon(value));
        setToolTipText(getToolTipText(value));

        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;

            // check if this cell is selected
        } else if (isSelected) {
            background = Color.RED;
            foreground = Color.WHITE;

            // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        }

        setBackground(background);
        setForeground(foreground);

        return this;
    }

    protected String getText(T value) {
        return value.toString();
    }

    protected Icon getIcon(T value) {
        return null;
    }

    protected String getToolTipText(T value) {
        return getToolTipText();
    }
}
