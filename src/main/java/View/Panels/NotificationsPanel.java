package View.Panels;


import Model.Player;
import View.Network;
import enums.RequestActions;

public class NotificationsPanel {


    public static String showPanel() {
        StringBuilder panelContent = new StringBuilder();
        Player player = (Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null);
        for (String notification : player.getNotifications()) {
            panelContent.append(notification).append("\n");
        }
        if (player.getNotifications().size() == 0) {
            panelContent.append("no notifications!");
        }
        return panelContent.toString();
    }


    /////////////////////////////////////////////////////////

}
