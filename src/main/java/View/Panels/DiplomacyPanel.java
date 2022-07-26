package View.Panels;

import Model.Player;
import View.Network;
import enums.RequestActions;

public class DiplomacyPanel  {
    public static void run(String command) {

    }

    public static String showPanel() {
        Player player = ((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null));
        String result = "Currently in War with:\n";
        for (Player inWarPlayer : player.getInWarPlayers()) {
            result += inWarPlayer.getName() + "\n";
        }
        return result;
    }

}
