package onpu.diplom.mironov.cew.dao.tsv;

import java.io.File;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.dao.DeviceTypeDao;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class DeviceTypeTsvDao extends AbstractTsvDao<DeviceType> implements DeviceTypeDao{

    public DeviceTypeTsvDao(File file) {
        super(file);
    }

    @Override
    protected CellProcessor[] getProcessors() {
        return new CellProcessor[]{
            new NotNull(new ParseLong()), new NotNull(), new Optional()
        };
    }

    @Override
    protected String[] getHeader() {
        return new String[]{"id", "title", "imageUrl"};
    }

    @Override
    public Class<DeviceType> getBeanClass() {
        return DeviceType.class;
    }

}
