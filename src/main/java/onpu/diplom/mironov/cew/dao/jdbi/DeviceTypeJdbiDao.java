package onpu.diplom.mironov.cew.dao.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.dao.DeviceTypeDao;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class DeviceTypeJdbiDao extends AbstractJdbiDao<DeviceType> implements DeviceTypeDao {
    public static final String DEVICE_TYPE = "device_type";

    public static final String TITLE = "title";
    public static final String IMAGE_URL = "image_url";

    public DeviceTypeJdbiDao(DBI dbi) {
        super(dbi, DeviceType.class, DeviceTypeJdbiDaoResource.class);
    }

    @Override
    public void add(DeviceType t) {
        DeviceTypeJdbiDaoResource connection = null;
        try {
            (connection = open()).insert(t.getId(), t.getTitle(), t.getImageUrl());
        } finally {
            close(connection);
        }
    }

    @RegisterMapper(DeviceTypeJdbiMapper.class)
    public static interface DeviceTypeJdbiDaoResource extends SqlObjectType<DeviceType> {

        @SqlUpdate("INSERT INTO " + DEVICE_TYPE + " ("
                + ID + ", " + TITLE + ", " + IMAGE_URL
                + ") VALUES ("
                + ":" + ID + ", :" + TITLE + ", :" + IMAGE_URL + ")")
        int insert(@Bind(ID) long id, @Bind(TITLE) String title, @Bind(IMAGE_URL) String imageUrl);

        @Override
        @SqlUpdate("DELETE FROM " + DEVICE_TYPE + " WHERE " + ID + " = :" + ID)
        int delete(@Bind(ID) long id);

        @Override
        @SqlQuery("SELECT " + ID + ", " + TITLE + ", " + IMAGE_URL
                + " FROM " + DEVICE_TYPE
                + " WHERE " + ID + " = :" + ID)
        DeviceType findById(@Bind(ID) long id);

        @Override
        @SqlQuery("SELECT " + ID + ", " + TITLE + ", " + IMAGE_URL
                + " FROM " + DEVICE_TYPE)
        List<DeviceType> findAll();
    }

    public static class DeviceTypeJdbiMapper implements ResultSetMapper<DeviceType> {

        @Override
        public DeviceType map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new DeviceType(r.getLong(ID), r.getString(TITLE), r.getString(IMAGE_URL));
        }

    }
}
