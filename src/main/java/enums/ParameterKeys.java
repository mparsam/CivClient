package enums;

public enum ParameterKeys {
    USERNAME("username"),
    PASSWORD("password"),
    NICKNAME("nickname"),
    ENUM("enum"), OLD_PASSWORD("oldPassword"), NEW_PASSWORD("newPassword");

    public final String code;

    ParameterKeys(String code) {
        this.code = code;
    }
}
