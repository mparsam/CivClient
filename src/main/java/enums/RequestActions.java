package enums;

public enum RequestActions {
    LOGIN("login"),
    REGISTER("register"),
    REMOVE_USER("remove_user"),
    CHANGE_PROFILE_PICTURE("change_profile_pic"),
    GET_THIS_USER("get_this_user"),
    CHANGE_NICKNAME("change_nickname"),
    CHANGE_PASSWORD("change_password"),
    GET_GAME_MAP("get_game_map"),
    GET_INVITATIONS("get_invitations"),
    SEND_INVITATIONS("send_invitaions"),
    ARE_INVITATIONS_ACCEPTED("are_reqs_accepted"),
    ACCEPT_INVITAION("Accept_request"),
    GET_USER_BY_USERNAME("get_user_by_username"),
    PANEL_COMMAND("panel_command"),
    IS_MY_TURN("is_my_turn?"),
    NEW_GAME("new_game"),
    PASS_TURN("pass_turn"),
    GET_THIS_PLAYER("get_this_player"),
    GET_GAME("get_game"),
    GET_USERS("get_all_users"),
    GET_SELECTED_CITY("get-selected-city"),
    GET_SELECTED_UNIT("get-selected-unit"),
    SELECT_CITY("select-city"),
    SELECT_UNIT("select-unit"),
    SELECT_TROOP("select_troop"),
    GET_THIS_PLAYERS_MAP("get-this_players_map"),
    GET_USERS_PROFILE_PIC("getusersprofilepic"),
    GET_ONLINES("geto Onlines"),
    UPDATE_FIELD_OF_VIEW("update_fieldOfView"),
    IS_GAME_ENDED("isGameEnded"),
    IS_PLAYER_DEAD("isPlayerDead"),
    LOGOUT("logout"),
    END_GAME("endGame"),
    AM_I_LOST("amILost?"),
    AM_I_WON("amIWon??");


    public final String code;

    RequestActions(String actionStr) {
        this.code = actionStr;
    }

}
