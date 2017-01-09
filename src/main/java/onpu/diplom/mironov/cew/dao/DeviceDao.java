package onpu.diplom.mironov.cew.dao;

import java.util.List;
import onpu.diplom.mironov.cew.bean.Building;
import onpu.diplom.mironov.cew.bean.Device;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;

public interface DeviceDao extends AbstractDao<Device> {

    List<Device> findByTypeAndUserAndRoomAndBuilding(DeviceType deviceType, 
            User user, Room room, Building building);
}
