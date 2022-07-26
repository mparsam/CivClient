package View.Panels;

import Model.Player;
import Model.Units.Troop;
import View.Network;
import enums.RequestActions;

import java.util.ArrayList;

public class MilitaryPanel {
    public static void run(String command) {
        if (command.startsWith("show panel")) {
            printPanel();
        }
    }

    public static String printPanel() {
        StringBuilder result = new StringBuilder();
        ArrayList<Troop> troops = new ArrayList<>(((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null)).getUnits().stream().
                filter(u -> u instanceof Troop).filter(u -> u.getRemainingCost() == 0).map(u -> (Troop) u).toList());
        result.append(printRow("Name", "Status", "HP", "Melee Strength", "Ranged Strength", "Destination"));
        for (Troop troop : troops) {
            result.append(printRow(troop.getUnitType().name,
                    troop.getOrderType().toString(),
                    troop.getHP() + "",
                    troop.getMeleeStrength() + "",
                    troop.getRangedStrength() + "",
                    (troop.getDestination() == troop.getTile()) ? "-" : (troop.getDestination().getRow() + ", " + troop.getDestination().getColumn())));
        }
        return result.toString();
    }

    private static String printRow(String s1, String s2, String s3, String s4, String s5, String s6) {
        String result = "";
        String format = "|%1$-13s|%2$-10s|%3$-8s|%4$-17s|%5$-17s|%6$-15s|";
        result += String.format(format, s1, s2, s3, s4, s5, s6);
        result += "\n";
        return result;
    }
}
