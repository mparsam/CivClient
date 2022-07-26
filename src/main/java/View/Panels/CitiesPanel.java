package View.Panels;

import Model.City;
import Model.Player;
import View.Network;
import enums.RequestActions;

public class CitiesPanel {


    public static String showPanel() {
        int i = 0;
        String result = "";
        result += printRow("#", "Name", "Owner");
        Player player = ((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null));
        for (City city : player.getCities()) {
            i++;
            result += printRow(i + "  ",
                    city.getName(),
                    city.getOwner().getName()
            );
        }
        return result;
    }


    private static String printRow(String s1, String s2, String s3) {
        String format = "|%1$-5s|%2$-15s|%3$-15s|";
        String result = "";
        result += String.format(format, s1, s2, s3) + "\n";
        return result;
    }
}
