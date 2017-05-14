package onpu.diplom.mironov.cew.bean;

public enum UserPrivilege {
    SYSTEM(0), ADMIN(1), OPERATOR(2);

    private final int privilageValue;

    private UserPrivilege(int privilageValue) {
        this.privilageValue = privilageValue;
    }

    public int getPrivilageValue() {
        return privilageValue;
    }
}
