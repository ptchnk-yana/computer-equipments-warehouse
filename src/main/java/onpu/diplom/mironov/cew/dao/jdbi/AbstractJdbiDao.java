package onpu.diplom.mironov.cew.dao.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import onpu.diplom.mironov.cew.bean.AbstractBean;
import onpu.diplom.mironov.cew.dao.AbstractDao;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.logging.FormattedLog;

public abstract class AbstractJdbiDao<T extends AbstractBean> implements AbstractDao<T> {

    public static final String ID = "id";
    protected final DBI dbi;
    protected final Class<? extends SqlObjectType> sqlObjectType;

    public AbstractJdbiDao(DBI dbi, Class<? extends SqlObjectType> sqlObjectType) {
        if (dbi == null) {
            throw new IllegalArgumentException("JDBI cannot be null");
        }
        if (sqlObjectType == null) {
            throw new IllegalArgumentException("sqlObjectType cannot be null");
        }
        this.dbi = dbi;
        this.sqlObjectType = sqlObjectType;
        dbi.setSQLLog(new FormattedLog() {
            @Override
            protected boolean isEnabled() {
                return true;
            }

            @Override
            protected void log(String msg) {
                System.out.println("JDBI: " + msg);
            }
        });
    }

    @Override
    public void delete(T t) {
        if (t != null) {
            SqlObjectType connection = null;
            try {
                (connection = open()).delete(t.getId());
            } finally {
                close(connection);
            }
        }
    }

    @Override
    public T get(long id) {
        SqlObjectType connection = null;
        try {
            return ((SqlObjectType<T>) (connection = open())).findById(id);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<T> list() {
        SqlObjectType connection = null;
        try {
            return (connection = open()).findAll();
        } finally {
            close(connection);
        }
    }

    protected <S extends SqlObjectType> S open() {
        return (S) dbi.open(sqlObjectType);
    }

    protected <S extends SqlObjectType> void close(S s) {
        dbi.close(s);
    }

    public interface SqlObjectType<T extends AbstractBean> {

        int delete(long id);

        T findById(long id);

        List<T> findAll();
    }

    public static Long extractLongValue(ResultSet r, String label, Long defaultValue)
            throws SQLException {
        Object userIdObject = r.getObject(label);
        if (!r.wasNull() && userIdObject != null) {
            return ((Number) userIdObject).longValue();
        }
        return defaultValue;
    }

    public static Integer extractIntValue(ResultSet r, String label, Integer defaultValue)
            throws SQLException {
        Object userIdObject = r.getObject(label);
        if (!r.wasNull() && userIdObject != null) {
            return ((Number) userIdObject).intValue();
        }
        return defaultValue;
    }
}
