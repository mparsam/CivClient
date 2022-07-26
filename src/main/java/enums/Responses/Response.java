package enums.Responses;

public class Response {
    // enums with $ have dynamic parts
    public enum GameMenu {
        GAME_CREATED("game created"),

        // SELECT UNIT
        UNIT_SELECTED("Unit selected successfully"),
        NO_UNIT_IN_TILE("no unit was found on \"$\""),

        // SELECT TROOP
        TROOP_SELECTED("Troop selected successfully"),
        NO_TROOP_IN_TILE("no troop was found on \"$\""),

        // SELECT CITY
        CITY_SELECTED("City selected successfully"),
        NO_CITY_IN_TILE("no city was found on this \"$\""),

        // PASSING TURN
        TURN_PASSED("turn passed successfully"),

        // CURRENT MENU
        CURRENT_MENU("Game menu"),

        // MOVE MAP
        AMOUNT_IS_NOT_POSITIVE("map movement amount must be positive"),
        SUCCESSFUL_MOVE("map moved successfully"),
        MOVEMENT_OUT_OF_RANGE("can't move outside of map"),

        // CHEAT CODES
        NO_UNIT_SELECTED("you haven't selected a unit"),
        NO_CITY_SELECTED("you haven't selected a city"),
        INVALID_POSITION("invalid coordinates"),
        CHEAT_SUCCESSFUL("your cheat code was activated successfully (you sneaky bastard!)");

        private final String message;

        GameMenu(String message) {
            this.message = message;
        }

        // can get a string and adds it to corresponding location in the response message
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }


    public enum LoginMenu {
        // LOGIN
        USERNAME_PASSWORD_DONT_MATCH("Username and password don't match"), // for nonexistence username and mismatch
        LOGIN_SUCCESSFUL("Logged in successfully"),

        // REGISTER
        INVALID_USERNAME_FORMAT("Username format is invalid"),
        INVALID_PASSWORD_FORMAT("Password format is invalid"),
        INVALID_NICKNAME_FORMAT("Nickname format is invalid"),
        WEAK_PASSWORD("Password is weak"),
        USERNAME_EXISTS("Username \"$\" already exists"),
        NICKNAME_EXISTS("Nickname \"$\" already exists"),
        REGISTER_SUCCESSFUL("User registered successfully"),

        // CURRENT MENU
        CURRENT_MENU("Login menu");

        private final String message;

        LoginMenu(String message) {
            this.message = message;
        }

        /**
         * can get a string and adds it to corresponding location in the response message
         */
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }

    public enum ProfileMenu {
        // MAIN MENU RETURN
        ENTERED_MAIN_MENU("Entered main menu"),

        // CHANGE PASSWORD
        INVALID_NEW_PASSWORD_FORMAT("New password format is invalid"),
        WRONG_OLD_PASSWORD("Old password is wrong"),
        WEAK_NEW_PASSWORD("New password is weak"),
        SUCCESSFUL_PASSWORD_CHANGE("Password changed successfully"),

        // CHANGE NICKNAME
        INVALID_NICKNAME_FORMAT("Nickname format is invalid"),
        NICKNAME_EXISTS("Nickname \"$\" already exists"),
        SUCCESSFUL_NICKNAME_CHANGE("Nickname changed successfully"),

        // CHANGE PICTURE
        INVALID_FILE_FORMAT("Invalid file format"),
        SUCCESSFUL_PICTURE_CHANGE("Profile Picture changed successfully"),

        // DELETE ACCOUNT
        WRONG_PASSWORD("Password is wrong"),
        ACCOUNT_DELETED_SUCCESSFULLY("Account deleted successfully"),

        // SHOW SCOREBOARD
        // no response

        // CURRENT MENU
        CURRENT_MENU("Profile menu"), SAME_PASSWORD("new password is identical to the old one");

        private final String message;

        ProfileMenu(String message) {
            this.message = message;
        }

        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }

    public enum MainMenu {
        // MENU NAVIGATION
        MENU_NAVIGATION_NOT_POSSIBLE("Menu navigation not possible"),
        SUCCESSFULLY_ENTERED_GAME("Entered Game menu"),
        SUCCESSFULLY_ENTERED_PROFILE("Entered Profile menu"),

        // LOGOUT
        SUCCESSFUL_LOGOUT("Logged out successfully "),

        // CURRENT MENU
        CURRENT_MENU("Main menu"),

        // NEW GAME
        NEW_GAME_STARTED("new game created"),
        NONEXISTENCE_USERS("user(s) \"$\" not found");

        private final String message;

        MainMenu(String message) {
            this.message = message;
        }

        // can get a string and adds it to corresponding location in the response message
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }
}
