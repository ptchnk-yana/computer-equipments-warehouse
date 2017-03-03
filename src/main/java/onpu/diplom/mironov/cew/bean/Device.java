package onpu.diplom.mironov.cew.bean;

/**
 *
 * @author yura
 */
public class Device extends AbstractBean {
    private Long roomId;
    private int roomNumber;

    private Long deviceTypeId;
    private String deviceTitle;
    private String deviceImageUrl;

    private String hash;
    private String description;

    public Device() {
    }

    public Device(long id, Long roomId, int roomNumber, Long deviceTypeId, String deviceTitle, String deviceImageUrl, String hash, String description) {
        super(id);
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.deviceTypeId = deviceTypeId;
        this.deviceTitle = deviceTitle;
        this.deviceImageUrl = deviceImageUrl;
        this.hash = hash;
        this.description = description;
    }

    public Device(Long deviceTypeId, Long roomId, String hash, String description) {
        this.deviceTypeId = deviceTypeId;
        this.roomId = roomId;
        this.hash = hash;
        this.description = description;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceTitle() {
        return deviceTitle;
    }

    public void setDeviceTitle(String deviceTitle) {
        this.deviceTitle = deviceTitle;
    }

    public String getDeviceImageUrl() {
        return deviceImageUrl;
    }

    public void setDeviceImageUrl(String deviceImageUrl) {
        this.deviceImageUrl = deviceImageUrl;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Device{" + "deviceTypeId=" + deviceTypeId + ", deviceTitle=" 
                + deviceTitle + ", deviceImageUrl=" + deviceImageUrl 
                + ", roomId=" + roomId + ", roomNumber=" + roomNumber 
                + ", hash=" + hash + ", description=" + description + '}';
    }

    
    
}
