package View.Panels;

import Model.Player;
import Model.Units.Unit;
import View.Network;
import enums.RequestActions;

public class UnitsPanel {


    public static String showPanel() {
        int i = 0;
        String result = "";
        result += printRow("id", "Type", "Location");
        Player player = ((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null));
        for (Unit unit : player.getUnits()) {
            i++;
            result += printRow(i + "  ",
                    unit.getUnitType().name,
                    unit.getRow() + " " + unit.getColumn()
            );
        }
        return result;
    }

    private static String printRow(String s1, String s2, String s3) {
        String format = "|%1$-3s|%2$-15s|%3$-15s|";
        String result = "";
        result += String.format(format, s1, s2, s3) + "\n";
        return result;
    }
}
