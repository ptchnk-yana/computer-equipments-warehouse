package onpu.diplom.mironov.cew.dao.tsv;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import onpu.diplom.mironov.cew.CewUtil;
import onpu.diplom.mironov.cew.bean.Building;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.RoomDao;
import onpu.diplom.mironov.cew.dao.UserDao;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class RoomTsvDao extends AbstractTsvDao<Room> implements RoomDao {
    private final UserDao userDao;

    public RoomTsvDao(File file, UserDao userDao) {
        super(file, Room.class);
        this.userDao = userDao;
    }

    @Override
    protected CellProcessor[] getProcessors() {
        return new CellProcessor[]{
            new NotNull(new ParseLong()), new NotNull(new ParseInt()), new NotNull(), 
            new NotNull(new ParseLong())
        };
    }

    @Override
    public List<Room> list() {
        Map<Long, User> usersMap = CewUtil.toMap(userDao.list());
        List<Room> rooms = super.list();
        for (Room room : rooms) {
            initUsersFields(usersMap.get(room.getUserId()), room);
        }
        return rooms;
    }

    @Override
    public Room get(long id) {
        Room room = super.get(id);
        if (room != null) {
            initUsersFields(userDao.get(id), room);
        }
        return room;
    }

    @Override
    public List<Room> findByUserAndBuilding(User user, Building building) {
        List<Room> rooms = list();
        if (user != null) {
            for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext();) {
                Room room = iterator.next();
                if(room.getUserId() != user.getId()) {
                    iterator.remove();
                }
                if (building != null && room.getBuildingId() != building.getId()) {
                    iterator.remove();
                }
            }
        }
        return rooms;
    }

    @Override
    protected String[] getHeader() {
        return new String[]{"id", "number", "title", "userId", "buildingId"};
    }

    public void initUsersFields(User u, Room room) {
        if (u != null) {
            room.setUserName(u.getName());
            room.setUserPrivilege(u.getPrivilege());
        }
    }

}
