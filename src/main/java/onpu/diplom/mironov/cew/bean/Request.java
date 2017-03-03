package onpu.diplom.mironov.cew.bean;

/**
 */
public class Request extends AbstractBean {

    public static enum Status {
        OPEN, IN_PROGRESS, DONE
    }

    private long userId;
    private String userName;

    private String title;
    private String description;
    private Status status;

    public Request() {
    }

    public Request(final long userId, final String userName, final String title, final String description, final Status status) {
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.description = description;
    }

    public Request(final long id, final long userId, final String userName, final String title, 
            final String description, final Status status) {
        super(id);
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" + "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
