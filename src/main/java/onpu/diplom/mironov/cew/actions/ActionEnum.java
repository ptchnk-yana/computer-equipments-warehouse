package onpu.diplom.mironov.cew.actions;

import onpu.diplom.mironov.cew.bean.UserPrivilege;

public enum ActionEnum {
    SAVE,
    PRINT,
    EXIT,

    NEW_USER(UserPrivilege.ADMIN),
    NEW_ROOM(UserPrivilege.ADMIN),
    NEW_DEVICE_TYPE(UserPrivilege.ADMIN),
    NEW_DEVICE,
    NEW_BUILDING(UserPrivilege.ADMIN),
    NEW_REQUEST,
    EDIT_REQUEST(UserPrivilege.ADMIN),
    DELETE,
    DELETE_BUILDING(UserPrivilege.ADMIN),

    USER_LIST(UserPrivilege.ADMIN),
    ROOM_LIST,
    DEVICE_TYPE_LIST,
    DEVICE_LIST,
    REQUEST_LIST,
    REQUEST_LIST_ALL,
    REQUEST_LIST_REFRESH,
    BUILDING_LIST_REFRESH,

    WHO_AM_I,
    ABOUT;

    private final UserPrivilege privilege;

    private ActionEnum() {
        this(UserPrivilege.OPERATOR);
    }

    private ActionEnum(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }
}
