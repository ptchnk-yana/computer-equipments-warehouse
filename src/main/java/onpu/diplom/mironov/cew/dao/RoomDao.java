package onpu.diplom.mironov.cew.dao;

import java.util.List;
import onpu.diplom.mironov.cew.bean.Building;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;

public interface RoomDao extends AbstractDao<Room>{

    List<Room> findByUserAndBuilding(User user, Building building);
    
}
