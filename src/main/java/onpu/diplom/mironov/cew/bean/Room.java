package onpu.diplom.mironov.cew.bean;

public class Room extends AbstractBean {
    private long userId;
    private String userName;
    private UserPrivilege userPrivilege;

    private int number;
    private String title;

    private long buildingId;

    public Room() {
    }

    public Room(long id, long userId, String userName, UserPrivilege userPrivilege, 
            int number, String title, long building_id) {
        super(id);
        this.userId = userId;
        this.userName = userName;
        this.userPrivilege = userPrivilege;
        this.number = number;
        this.title = title;
        this.buildingId = building_id;
    }

    public Room(long userId, int number, String title) {
        this.userId = userId;
        this.number = number;
        this.title = title;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserPrivilege getUserPrivilege() {
        return userPrivilege;
    }

    public void setUserPrivilege(UserPrivilege userPrivilege) {
        this.userPrivilege = userPrivilege;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String toString() {
        return "Room{" + "userId=" + userId + ", userName=" + userName + 
                ", userPrivilege=" + userPrivilege + ", number=" + number + 
                ", title=" + title + ", buildingId" + buildingId + '}';
    }

}
