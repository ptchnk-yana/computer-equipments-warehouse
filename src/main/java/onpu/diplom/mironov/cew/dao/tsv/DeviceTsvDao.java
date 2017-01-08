package onpu.diplom.mironov.cew.dao.tsv;

import onpu.diplom.mironov.cew.dao.DeviceDao;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import onpu.diplom.mironov.cew.CewUtil;
import onpu.diplom.mironov.cew.bean.Device;
import onpu.diplom.mironov.cew.bean.DeviceType;
import onpu.diplom.mironov.cew.bean.Room;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.dao.DeviceTypeDao;
import onpu.diplom.mironov.cew.dao.RoomDao;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class DeviceTsvDao extends AbstractTsvDao<Device> implements DeviceDao {
    private final DeviceTypeDao deviceTypeDao;
    private final RoomDao roomDao;

    public DeviceTsvDao(File file, DeviceTypeDao deviceTypeDao, RoomDao roomDao) {
        super(file);
        this.deviceTypeDao = deviceTypeDao;
        this.roomDao = roomDao;
    }

    @Override
    public List<Device> list() {
        List<Device> devices = super.list();
        for (Device device : devices) {
            initTypeAndRoomFields(device, 
                    deviceTypeDao.get(device.getDeviceTypeId()), 
                    roomDao.get(device.getRoomId()));
        }
        return devices;
    }

    @Override
    public Device get(long id) {
        Device device = super.get(id);
        if (device != null) {
            initTypeAndRoomFields(device, 
                    deviceTypeDao.get(device.getDeviceTypeId()), 
                    roomDao.get(device.getRoomId()));
        }
        return device;
    }

    @Override
    public List<Device> findByTypeAndUserAndRoom(DeviceType deviceType, User user, Room room) {
        Map<Long, DeviceType> deviceTypes = CewUtil.toMap(deviceType != null 
                ? Collections.singletonList(deviceType) 
                : deviceTypeDao.list());
        Map<Long, Room> rooms = CewUtil.toMap(room != null 
                ? Collections.singletonList(room) 
                : roomDao.findByUser(user));
        
        List<Device> devices = list();
        for (Iterator<Device> deviceIterator = devices.iterator(); deviceIterator.hasNext();) {
            Device device = deviceIterator.next();
            DeviceType currentType = deviceTypes.get(device.getDeviceTypeId());
            Room currentRoom = rooms.get(device.getRoomId());
            if (currentType == null || currentRoom == null) {
                deviceIterator.remove();
            }
        }
        
        return devices;
    }

    @Override
    protected CellProcessor[] getProcessors() {
        return new CellProcessor[]{
            new NotNull(new ParseLong()), 
            new NotNull(new ParseLong()), 
            new NotNull(new ParseLong()), 
            new NotNull(),
            new NotNull()
        };
    }

    @Override
    protected String[] getHeader() {
        return new String[]{"id", "deviceTypeId", "roomId", "hash", "description"};
    }

    @Override
    public Class<Device> getBeanClass() {
        return Device.class;
    }

    public void initTypeAndRoomFields(Device device, DeviceType dt, Room r) {
        if (dt != null) {
            device.setDeviceImageUrl(dt.getImageUrl());
            device.setDeviceTitle(dt.getTitle());
        }
        if (r != null) {
            device.setRoomNumber(r.getNumber());
        }
    }

}
