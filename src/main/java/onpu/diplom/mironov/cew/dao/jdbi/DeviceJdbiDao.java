package onpu.diplom.mironov.cew.dao.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import onpu.diplom.mironov.cew.bean.Building;
import onpu.diplom.mironov.cew.bean.Device;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.DeviceDao;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class DeviceJdbiDao extends AbstractJdbiDao<Device> implements DeviceDao {
    public static final String DEVICE = "device";

    public static final String ROOM_ID = "room_id";
    public static final String ROOM_NUMBER = "room_number";

    public static final String DEVICE_TYPE_ID = "device_type_id";
    public static final String DEVICE_TITLE = "device_title";
    public static final String DEVICE_IMAGE_URL = "device_image_url";

    public static final String HASH = "hash";
    public static final String DESCRIPTION = "description";

    public DeviceJdbiDao(DBI dbi) {
        super(dbi, Device.class, DeviceJdbiDaoResource.class);
    }

    @Override
    public void add(Device t) {
        if (t != null) {
            DeviceJdbiDaoResource connection = null;
            try {
                (connection = open()).insert(t.getId(), t.getRoomId(),
                        t.getDeviceTypeId(), t.getHash(), t.getDescription());
            } finally {
                close(connection);
            }
        }
    }

    @Override
    public List<Device> findByTypeAndUserAndRoomAndBuilding(DeviceType deviceType, 
            User user, Room room, Building building) {
        DeviceJdbiDaoResource connection = null;
        try {
            return (connection = open()).findByTypeAndUserAndRoomAndBuilding(
                    deviceType != null ? deviceType.getId() : null,
                    user != null ? user.getId() : null,
                    room != null ? room.getId() : null,
                    building != null ? building.getId() : null);
        } finally {
            close(connection);
        }
    }

    @RegisterMapper(DeviceJdbiMapper.class)
    public static interface DeviceJdbiDaoResource extends SqlObjectType<Device> {
        public static final String BASE_QUERY = 
                "SELECT d." + ID + " as " + ID + "," +
                        " d." + ROOM_ID + " as " + ROOM_ID + "," +
                        " r." + RoomJdbiDao.NUMBER + " as " + ROOM_NUMBER + "," +
                        " d." + DEVICE_TYPE_ID + " as " + DEVICE_TYPE_ID + "," +
                        " t." + DeviceTypeJdbiDao.TITLE + " as " + DEVICE_TITLE + "," +
                        " t." + DeviceTypeJdbiDao.IMAGE_URL + " as " + DEVICE_IMAGE_URL + "," +
                        " d." + HASH + " as " + HASH + "," +
                        " d." + DESCRIPTION + " as " + DESCRIPTION +
                " FROM " + 
                    DEVICE + " d " + 
                    " LEFT JOIN " + RoomJdbiDao.ROOM + " r ON d." + ROOM_ID + " = r." + ID +
                    " LEFT JOIN " + DeviceTypeJdbiDao.DEVICE_TYPE + " t ON d." + DEVICE_TYPE_ID + " = t." + ID;

        @SqlUpdate("INSERT INTO " + DEVICE + " (" +
                    ID + ", " + 
                    ROOM_ID + ", " + 
                    DEVICE_TYPE_ID + ", " + 
                    HASH + ", " + 
                    DESCRIPTION +
                ") VALUES (" +
                    ":" + ID + ", " +
                    ":" + ROOM_ID + ", " +
                    ":" + DEVICE_TYPE_ID + ", " +
                    ":" + HASH + ", " +
                    ":" + DESCRIPTION + ")")
        int insert(@Bind(ID) long id, @Bind(ROOM_ID) long roomId, 
                @Bind(DEVICE_TYPE_ID) long deviceTypeId, @Bind(HASH) String title,
                @Bind(DESCRIPTION) String description);

        @Override
        @SqlUpdate("DELETE FROM " + DEVICE + " WHERE " + ID + " = :" + ID)
        int delete(@Bind(ID) long id);

        @Override
        @SqlQuery(BASE_QUERY +
                " WHERE " + ID + " = :" + ID)
        Device findById(@Bind(ID) long id);

        @Override
        @SqlQuery(BASE_QUERY)
        List<Device> findAll();
        
        @SqlQuery(BASE_QUERY +
                    " LEFT JOIN " + UserJdbiDao.USER + " u ON r." + RoomJdbiDao.USER_ID + " = u." + ID +
                    " LEFT JOIN " + BuildingJdbiDao.BUILDING + " b ON r." + RoomJdbiDao.BUILDING_ID + " = b." + ID +
                " WHERE " +
                    " (:typeId is NULL OR :typeId = t." + ID + ") " +
                    " AND (:userId is NULL OR :userId = u." + ID + ") " +
                    " AND (:roomId is NULL OR :roomId = r." + ID + ") " +
                    " AND (:buildingId is NULL OR :buildingId = b." + ID + ") "
        )
        List<Device> findByTypeAndUserAndRoomAndBuilding(@Bind("typeId") Long typeId,
                @Bind("userId") Long userId,
                @Bind("roomId") Long roomId,
                @Bind("buildingId") Long buildingId);
    }

    public static class DeviceJdbiMapper implements ResultSetMapper<Device> {

        @Override
        public Device map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new Device(r.getLong(ID),
                    extractLongValue(r, ROOM_ID, null),
                    extractIntValue(r, ROOM_NUMBER, 0),
                    extractLongValue(r, DEVICE_TYPE_ID, null),
                    r.getString(DEVICE_TITLE),
                    r.getString(DEVICE_IMAGE_URL),
                    r.getString(HASH),
                    r.getString(DESCRIPTION));
        }
    }
}
