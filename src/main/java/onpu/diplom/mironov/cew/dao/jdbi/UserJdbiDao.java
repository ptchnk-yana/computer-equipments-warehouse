package onpu.diplom.mironov.cew.dao.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.bean.UserPrivilege;
import onpu.diplom.mironov.cew.dao.UserDao;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class UserJdbiDao extends AbstractJdbiDao<User> implements UserDao {
    public static final String USER = "user";

    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String PRIVILEGE = "privilege";

    public UserJdbiDao(DBI dbi) {
        super(dbi, UserJdbiDaoResource.class);
    }

    @Override
    public void add(User t) {
        if (t != null) {
            UserPrivilege privilege = t.getPrivilege() != null 
                    ? t.getPrivilege() 
                    : UserPrivilege.OPERATOR;
            UserJdbiDaoResource connection = null;
            try {
                (connection = open()).insert(
                        t.getId(), t.getName(), t.getPassword(), privilege.name());
            } finally {
                close(connection);
            }
        }
    }

    @Override
    public Class<User> getBeanClass() {
        return User.class;
    }

    @RegisterMapper(UserJdbiMapper.class)
    public static interface UserJdbiDaoResource extends SqlObjectType<User> {

        @SqlUpdate("INSERT INTO " + USER + " ("
                + ID + ", " + NAME + ", " + PASSWORD + ", " + PRIVILEGE
                + ") VALUES ("
                + ":" + ID + ", :" + NAME + ", :" + PASSWORD + ", :" + PRIVILEGE + ")")
        int insert(@Bind(ID) long id, @Bind(NAME) String name, 
                @Bind(PASSWORD) String password,  @Bind(PRIVILEGE) String privilege);

        @Override
        @SqlUpdate("DELETE FROM " + USER + " WHERE " + ID + " = :" + ID)
        int delete(@Bind(ID) long id);

        @Override
        @SqlQuery("SELECT " + ID + ", " + NAME + ", " + PASSWORD + ", " + PRIVILEGE
                + " FROM " + USER
                + " WHERE " + ID + " = :" + ID)
        User findById(@Bind(ID) long id);

        @Override
        @SqlQuery("SELECT " + ID + ", " + NAME + ", " + PASSWORD + ", " + PRIVILEGE
                + " FROM " + USER)
        List<User> findAll();
    }

    public static class UserJdbiMapper implements ResultSetMapper<User> {

        @Override
        public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new User(r.getLong(ID), r.getString(NAME), r.getString(PASSWORD),
                    extractUserPrivilege(r, PRIVILEGE));
        }
    }

    public static UserPrivilege extractUserPrivilege(ResultSet r, String label) throws SQLException {
        String privilegeStr = r.getString(label);
        return privilegeStr != null
                ? UserPrivilege.valueOf(privilegeStr.toUpperCase())
                : UserPrivilege.OPERATOR;
    }
}
