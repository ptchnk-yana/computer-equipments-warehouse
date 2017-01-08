package onpu.diplom.mironov.cew.actions;

import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.MainFrame;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

public class SaveAction extends AbstractCewAction {

    public SaveAction(User user, MainFrame view, MainTableModel tableModel,
            Map<ActionEnum, AbstractCewAction> actions, Properties text) {
        super(user, view, tableModel, actions, text);
    }

    @Override
    protected ActionEnum getType() {
        return ActionEnum.SAVE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TSV File", "tsv");
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            String[] header = new String[tableModel.getColumnCount()];
            for (int i = 0; i < header.length; i++) {
                header[i] = tableModel.getColumnName(i);
            }
            ICsvMapWriter mapWriter = null;
            try {
                mapWriter = new CsvMapWriter(new FileWriter(chooser.getSelectedFile().getAbsoluteFile()),
                        CsvPreference.STANDARD_PREFERENCE);
                mapWriter.writeHeader(header);
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Map<String, Object> data = new HashMap<String, Object>();
                    for (int j = 0; j < header.length; j++) {
                        data.put(header[j], tableModel.getValueAt(i, j));
                    }
                    mapWriter.write(data, header);
                }
            } catch (Exception ex) {
                showException(ex);
            } finally {
                if (mapWriter != null) {
                    try {
                        mapWriter.close();
                    } catch (IOException ex) {
                        showException(ex);
                    }
                }
            }
        }
    }

}
