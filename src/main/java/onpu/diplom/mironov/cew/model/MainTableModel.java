package onpu.diplom.mironov.cew.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import onpu.diplom.mironov.cew.CewUtil;
import onpu.diplom.mironov.cew.bean.AbstractBean;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.bean.UserPrivilege;

public class MainTableModel extends AbstractTableModel {

    private final Properties text;
    private final User currentUser;
    private final Map<String, UserPrivilege> specialPrivilagesForColumns;
    private List<? extends AbstractBean> data;
    private Class<? extends AbstractBean> dataType;
    private Field[] fields;
    private String[] header;

    public MainTableModel(Properties text, User currentUser,
            Map<String, UserPrivilege> specialPrivilagesForColumns) {
        this.text = text;
        this.currentUser = currentUser;
        this.specialPrivilagesForColumns = specialPrivilagesForColumns;
        init(AbstractBean.class, Collections.EMPTY_LIST);
    }

    public void init(Class<? extends AbstractBean> dataType, List<? extends AbstractBean> data) {
        this.data = data;
        if (data.isEmpty()) {
            this.dataType = dataType;
        } else {
            this.dataType = data.get(0).getClass();
        }
        List<Field> fieldList = new ArrayList<Field>();
        Class<?> clazz = this.dataType;
        while (clazz != null) {
            for(Field f : clazz.getDeclaredFields()) {
                UserPrivilege priv = specialPrivilagesForColumns.get(f.getName());
                if (priv != null && priv.getPrivilageValue() 
                        < currentUser.getPrivilege().getPrivilageValue()) {
                    continue;
                }
                fieldList.add(f);
            }
            clazz = clazz.getSuperclass();
        }
        this.fields = fieldList.toArray(new Field[fieldList.size()]);
        this.header = new String[fieldList.size()];
        for (int i = 0; i < this.header.length; i++) {
            this.fields[i].setAccessible(true);
            this.header[i] = this.text.getProperty("column." + this.fields[i].getName(),
                    super.getColumnName(i));
        }

        fireTableStructureChanged();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value;
        try {
            value = fields[columnIndex].get(data.get(rowIndex));
        } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException(ex);
        }
        return isImageUrlName(columnIndex)
                ? CewUtil.createScaledImageIcon(value.toString())
                : value;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getRowCount() > 0
                ? isImageUrlName(columnIndex)
                    ? ImageIcon.class
                    : getValueAt(0, columnIndex).getClass()
                : super.getColumnClass(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    public <T extends AbstractBean> T getObjectAt(int rowIndex) {
        return (T) data.get(rowIndex);
    }

    public Class<? extends AbstractBean> getDataType() {
        return dataType;
    }

    private boolean isImageUrlName(int columnIndex) {
        return fields[columnIndex].getName().equals("imageUrl") 
                || fields[columnIndex].getName().endsWith("ImageUrl");
    }
}
