package onpu.diplom.mironov.cew.bean;

public class Building extends AbstractBean {
    private String name;
    private String comment;

    public Building() {
    }

    public Building(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public Building(long id, String name, String comment) {
        super(id);
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Building{" + "id=" + id + ", name=" + name + '}';
    }

    
}
