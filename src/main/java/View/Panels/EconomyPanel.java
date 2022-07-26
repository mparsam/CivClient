package View.Panels;


import Model.City;
import Model.Player;
import View.Network;
import enums.RequestActions;

import java.util.ArrayList;

public class EconomyPanel {
    private static final ArrayList<City> cities = new ArrayList<>();

    public static void run(String command) {
        cities.addAll(((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null)).getCities()); // this is what used every where, for same indexing ...
        if (command.startsWith("show panel")) {
            printPanel();
        }
    }

    public static String printPanel() {
        StringBuilder result = new StringBuilder();
        int i = 0;
        result.append(printRow("#", "Name", "Population", "Food", "Gold", "Science", "Production", "Building"));
        for (City city : cities) {
            i++;
            result.append(printRow(i + "",
                    city.getName(),
                    city.getPopulation() + "",
                    city.getFoodIncome() + "",
                    city.getGoldIncome() + "",
                    city.getScienceIncome() + "",
                    city.getProductionIncome() + "",
                    (((city.getBuildingInProgress() == null) ? "-" : city.getBuildingInProgress().getName()))
            ));

        }
        return result.toString();
    }

    private static String printRow(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8) {
        String result = "";
        String format = "|%1$-4s|%2$-15s|%3$-12s|%4$-12s|%5$-12s|%6$-12s|%6$-12s|%6$-12s|";
        result += String.format(format, s1, s2, s3, s4, s5, s6, s7, s8) + "\n";
        return result;
    }

}
