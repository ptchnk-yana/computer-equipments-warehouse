package onpu.diplom.mironov.cew.bean;

public enum UserPrivilege {
    ADMIN(0), OPERATOR(1);

    private final int privilageValue;

    private UserPrivilege(int privilageValue) {
        this.privilageValue = privilageValue;
    }

    public int getPrivilageValue() {
        return privilageValue;
    }
}
