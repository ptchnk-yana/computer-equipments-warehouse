package onpu.diplom.mironov.cew.dao.jdbi;

import onpu.diplom.mironov.cew.bean.*;
import onpu.diplom.mironov.cew.dao.RequestDao;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author yuriy.dunko on 13.05.17.
 */
public class RequestJdbiDao extends AbstractJdbiDao<Request> implements RequestDao {
    public static final String REQUEST = "request";

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "status";

    public RequestJdbiDao(final DBI dbi) {
        super(dbi, Request.class, RequestJdbiDaoResource.class);
    }

    @Override
    public void add(final Request t) {
        if (t != null) {
            RequestJdbiDaoResource connection = null;
            try {
                final Request.Status status = t.getStatus();
                (connection = open()).insert(t.getId(), t.getUserId(), t.getTitle(), 
                        t.getDescription(), status != null ? status.name() : Request.Status.OPEN.name());
            } finally {
                close(connection);
            }
        }
    }

    @Override
    public void update(Request t) {
        if (t != null) {
            RequestJdbiDaoResource connection = null;
            try {
                final Request.Status status = t.getStatus();
                (connection = open()).update(t.getId(), t.getTitle(), t.getDescription(),
                        status != null ? status.name() : Request.Status.OPEN.name());
            } finally {
                close(connection);
            }
        }
    }

    @Override
    public List<Request> findByUser(final User user) {
        RequestJdbiDaoResource connection = null;
        try {
            return (connection = open()).findByUser(
                    user != null ? user.getId() : null);
        } finally {
            close(connection);
        }
    }

    @RegisterMapper(RequestJdbiMapper.class)
    public static interface RequestJdbiDaoResource extends SqlObjectType<Request> {
        public static final String BASE_QUERY =
                "SELECT r." + ID + " as " + ID + "," +
                        " r." + USER_ID + " as " + USER_ID + "," +
                        " r." + TITLE + " as " + TITLE + "," +
                        " r." + DESCRIPTION + " as " + DESCRIPTION + "," +
                        " r." + STATUS + " as " + STATUS + "," +
                        " u." + UserJdbiDao.NAME + " as " + USER_NAME +
                        " FROM " +
                        REQUEST + " r " +
                        " LEFT JOIN " + UserJdbiDao.USER + " u ON r." + USER_ID + " = u." + ID;


        @SqlUpdate("INSERT INTO " + REQUEST + " (" +
                    ID + ", " +
                    USER_ID + ", " +
                    TITLE + ", " +
                    DESCRIPTION + ", " +
                    STATUS +
                ") VALUES (" +
                    ":" + ID + ", " +
                    ":" + USER_ID + ", " +
                    ":" + TITLE + ", " +
                    ":" + DESCRIPTION + ", " +
                    ":" + STATUS +
                ")")
        int insert(@Bind(ID) long id, @Bind(USER_ID) long userId, @Bind(TITLE) String title, 
                @Bind(DESCRIPTION) String description, @Bind(STATUS) String status);

        @SqlUpdate("UPDATE " + REQUEST + " SET " +
                    TITLE + " = :" + TITLE + ", " +
                    DESCRIPTION + " = :" + DESCRIPTION + ", " +
                    STATUS + " = :" + STATUS +
                " WHERE " +
                    ID + " = :" + ID +
                "")
        int update(@Bind(ID) long id, @Bind(TITLE) String title, 
                @Bind(DESCRIPTION) String description, @Bind(STATUS) String status);

        @Override
        @SqlUpdate("DELETE FROM " + REQUEST + " WHERE " + ID + " = :" + ID)
        int delete(@Bind(ID) long id);

        @Override
        @SqlQuery(BASE_QUERY + " WHERE " + ID + " = :" + ID)
        Request findById(@Bind(ID) long id);

        @Override
        @SqlQuery(BASE_QUERY)
        List<Request> findAll();

        @SqlQuery(BASE_QUERY + " WHERE (:userId is NULL OR " + USER_ID + " = :userId)")
        List<Request> findByUser(@Bind("userId") Long userId);
    }

    public static class RequestJdbiMapper implements ResultSetMapper<Request> {

        @Override
        public Request map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new Request(r.getLong(ID),
                    extractLongValue(r, USER_ID, 0L),
                    r.getString(USER_NAME),
                    r.getString(TITLE),
                    r.getString(DESCRIPTION),
                    extractRequestStatus(r, STATUS));
        }
    }

    public static Request.Status extractRequestStatus(ResultSet r, String label) throws SQLException {
        String statusStr = r.getString(label);
        return statusStr != null
                ? Request.Status.valueOf(statusStr.toUpperCase())
                : Request.Status.OPEN;
    }
}
