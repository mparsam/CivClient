package View.ClientPanels;

import Model.Player;
import Model.Technology;
import View.GameView;
import View.Network;
import enums.RequestActions;
import enums.Responses.InGameResponses;
import enums.Types.TechnologyType;

public class ClientResearchPanel extends GameView {

    public static void researchTech(TechnologyType techType) {
        InGameResponses.Technology response = ((InGameResponses.Technology) Network.getResponseObjOfPanelCommand("research -t " + techType.name));
        if(response != InGameResponses.Technology.TECH_RESEARCHED){
            showAlert(invalidAlert, response.getString());
        }
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        System.err.println(response.getString());
        show(stage);
    }

    public static String showCurrent() {
        Player player = ((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null));
        Technology technology = player.getTechnologyInProgress();
        StringBuilder result = new StringBuilder();
        if (technology == null) {
            result.append("no tech being researched");
            return result.toString();
        }
        result.append(technology.getName() + "\n");
        if (player.getScienceIncome() == 0) {
            result.append("will be finished when pigs fly\n");
        } else {
            int turnsLeft = (technology.getRemainingCost() + player.getScienceIncome() - 1) / player.getScienceIncome();
            result.append(turnsLeft + " turns left to be completely researched\n");
        }
        result.append("unlocks: " + technology.getUnlocks());
        return result.toString();
    }

    public static String showPanel() {
        StringBuilder result = new StringBuilder();
        result.append("researched technologies:\n");
        Player player = ((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null));
        for (Technology technology : player.getTechnologies()) {
            result.append(technology.getName());
        }
        result.append("current research:\n");
        result.append(showCurrent());
        return result.toString();
    }
}
