package onpu.diplom.mironov.cew.dao.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import onpu.diplom.mironov.cew.bean.Building;
import onpu.diplom.mironov.cew.dao.BuildingDao;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class BuildingJdbiDao extends AbstractJdbiDao<Building> implements BuildingDao{
    public static final String BUILDING = "building";

    public static final String NAME = "name";
    public static final String COMMENT = "comment";

    public BuildingJdbiDao(DBI dbi) {
        super(dbi, Building.class, RoomJdbiDaoResource.class);
    }

    @Override
    public void add(Building t) {
        RoomJdbiDaoResource connection = null;
        try {
            (connection = open()).insert(t.getId(), t.getName(), t.getComment());
        } finally {
            close(connection);
        }
    }

    @RegisterMapper(BuildingJdbiMapper.class)
    public static interface RoomJdbiDaoResource extends SqlObjectType<Building> {
        @SqlUpdate("INSERT INTO " + BUILDING + " (" +
                ID + ", " + NAME + ", " + COMMENT +
                ") VALUES (" +
                ":" + ID + ", :" + NAME + ", :" + COMMENT + ")")
        int insert(@Bind(ID) long id, @Bind(NAME) String name, @Bind(COMMENT) String comment);

        @Override
        @SqlUpdate("DELETE FROM " + BUILDING + " WHERE " + ID + " = :" + ID)
        int delete(@Bind(ID) long id);

        @Override
        @SqlQuery("SELECT " + ID + ", " + NAME + ", " + COMMENT +
                " FROM " + BUILDING +
                " WHERE " + ID + " = :" + ID)
        Building findById(@Bind(ID) long id);

        @Override
        @SqlQuery("SELECT " + ID + ", " + NAME + ", " + COMMENT +
                " FROM " + BUILDING)
        List<Building> findAll();
    }
    

    public static class BuildingJdbiMapper implements ResultSetMapper<Building> {

        @Override
        public Building map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new Building(r.getLong(ID), r.getString(NAME), r.getString(COMMENT));
        }
    }
}
