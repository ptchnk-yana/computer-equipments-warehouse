package onpu.diplom.mironov.cew.bean;

public class User extends AbstractBean {
    private String name;
    private String password;
    private UserPrivilege privilege;

    public User() {
    }

    public User(long id, String name, String password, UserPrivilege privilege) {
        super(id);
        this.name = name;
        this.password = password;
        this.privilege = privilege;
    }

    public User(String name, String password, UserPrivilege privilege) {
        this.name = name;
        this.password = password;
        this.privilege = privilege;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name=" + name + ", password=" + password + 
                ", privilege=" + privilege + '}';
    }

}
