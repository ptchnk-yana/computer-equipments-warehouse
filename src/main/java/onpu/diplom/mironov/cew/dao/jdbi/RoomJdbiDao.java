package onpu.diplom.mironov.cew.dao.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.RoomDao;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class RoomJdbiDao extends AbstractJdbiDao<Room> implements RoomDao {
    public static final String ROOM = "room";

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PRIVILEGE = "user_privilege";

    public static final String NUMBER = "number";
    public static final String TITLE = "title";

    public RoomJdbiDao(DBI dbi) {
        super(dbi, RoomJdbiDaoResource.class);
    }

    @Override
    public void add(Room t) {
        if (t != null) {
            RoomJdbiDaoResource connection = null;
            try {
                (connection = open()).insert(t.getId(), t.getUserId(),
                        t.getNumber(), t.getTitle());
            } finally {
                close(connection);
            }
        }
    }

    @Override
    public List<Room> findByUser(User user) {
        if (user == null) {
            return list();
        }
        RoomJdbiDaoResource connection = null;
        try {
            return (connection = open()).findByUserId(user.getId());
        } finally {
            close(connection);
        }
    }

    @Override
    public Class<Room> getBeanClass() {
        return Room.class;
    }

    @RegisterMapper(RoomJdbiMapper.class)
    public static interface RoomJdbiDaoResource extends SqlObjectType<Room> {

        @SqlUpdate("INSERT INTO " + ROOM + " (" +
                ID + ", " + USER_ID + ", " + NUMBER + ", " + TITLE +
                ") VALUES (" +
                ":" + ID + ", :" + USER_ID + ", :" + NUMBER + ", :" + TITLE + ")")
        int insert(@Bind(ID) long id, @Bind(USER_ID) long userId, 
                @Bind(NUMBER) int number,  @Bind(TITLE) String title);

        @Override
        @SqlUpdate("DELETE FROM " + ROOM + " WHERE " + ID + " = :" + ID)
        int delete(@Bind(ID) long id);

        @Override
        @SqlQuery("SELECT r." + ID + " as " + ID + "," + 
                        " r." + USER_ID + " as " + USER_ID + "," + 
                        " r." + NUMBER + " as " + NUMBER + "," + 
                        " r." + TITLE + " as " + TITLE + "," + 
                        " u." + UserJdbiDao.NAME + " as " + USER_NAME + "," + 
                        " u." + UserJdbiDao.PRIVILEGE + " as " + USER_PRIVILEGE + 
                " FROM " + 
                    ROOM + " r " + 
                    " LEFT JOIN " + UserJdbiDao.USER + " u " + 
                        " ON r." + USER_ID + " = u." + ID +
                " WHERE " + ID + " = :" + ID)
        Room findById(@Bind(ID) long id);

        @Override
        @SqlQuery("SELECT r." + ID + " as " + ID + "," + 
                        " r." + USER_ID + " as " + USER_ID + "," + 
                        " r." + NUMBER + " as " + NUMBER + "," + 
                        " r." + TITLE + " as " + TITLE + "," + 
                        " u." + UserJdbiDao.NAME + " as " + USER_NAME + "," + 
                        " u." + UserJdbiDao.PRIVILEGE + " as " + USER_PRIVILEGE + 
                " FROM " + 
                    ROOM + " r " + 
                    " LEFT JOIN " + UserJdbiDao.USER + " u " + 
                        " ON r." + USER_ID + " = u." + ID)
        List<Room> findAll();

        @SqlQuery("SELECT r." + ID + " as " + ID + "," + 
                        " r." + USER_ID + " as " + USER_ID + "," + 
                        " r." + NUMBER + " as " + NUMBER + "," + 
                        " r." + TITLE + " as " + TITLE + "," + 
                        " u." + UserJdbiDao.NAME + " as " + USER_NAME + "," + 
                        " u." + UserJdbiDao.PRIVILEGE + " as " + USER_PRIVILEGE + 
                " FROM " + 
                    ROOM + " r " + 
                    " LEFT JOIN " + UserJdbiDao.USER + " u " + 
                        " ON r." + USER_ID + " = u." + ID +
                " WHERE " + USER_ID + " = :" + USER_ID)
        List<Room> findByUserId(@Bind(USER_ID) long userId);
    }

    public static class RoomJdbiMapper implements ResultSetMapper<Room> {

        @Override
        public Room map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new Room(r.getLong(ID),
                    extractLongValue(r, USER_ID, 0L),
                    r.getString(USER_NAME),
                    UserJdbiDao.extractUserPrivilege(r, USER_PRIVILEGE),
                    r.getInt(NUMBER),
                    r.getString(TITLE));
        }
    }
}
