package onpu.diplom.mironov.cew.dao.tsv;

import java.io.File;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.bean.UserPrivilege;
import onpu.diplom.mironov.cew.dao.UserDao;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class UserTsvDao extends AbstractTsvDao<User> implements UserDao {

    public UserTsvDao(File file) {
        super(file);
    }

    @Override
    protected CellProcessor[] getProcessors() {
        return new CellProcessor[]{
            new NotNull(new ParseLong()), new NotNull(), new NotNull(),
            new NotNull(new ParseEnum(UserPrivilege.class))
        };
    }

    @Override
    protected String[] getHeader() {
        return new String[]{"id", "name", "password", "privilege"};
    }

    @Override
    public Class<User> getBeanClass() {
        return User.class;
    }

}
