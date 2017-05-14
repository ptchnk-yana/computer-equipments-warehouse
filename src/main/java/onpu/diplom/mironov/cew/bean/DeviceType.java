package onpu.diplom.mironov.cew.bean;

/**
 *
 */
public class DeviceType extends AbstractBean {
    private String title;
    private String imageUrl;

    public DeviceType() {
    }

    public DeviceType(long id, String title, String imageUrl) {
        super(id);
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public DeviceType(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "DeviceType{id=" + id + ", title=" + title + ", imageUrl=" + imageUrl + '}';
    }
    
}
