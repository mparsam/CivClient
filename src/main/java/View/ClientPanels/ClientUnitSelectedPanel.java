package View.ClientPanels;

import Model.Units.Unit;
import View.GameView;
import View.Network;
import enums.RequestActions;
import enums.Responses.InGameResponses;
import enums.Types.ImprovementType;
import net.bytebuddy.utility.RandomString;

public class ClientUnitSelectedPanel extends GameView {

    public static String showSelected() {
        Unit unit = selectedUnit;
        StringBuilder resp = new StringBuilder();
        resp.append("location: " + unit.getTile().getRow() + " " + unit.getTile().getColumn() + "\n");
        resp.append("type: " + unit.getUnitType().name + "\n");
        resp.append("owner: " + unit.getOwner().getName() + "\n");
        resp.append("HP: " + unit.getHP() + "\n");
        resp.append("current order status: " + unit.getOrderType().toString());
        return resp.toString();
    }

    public static void moveTo() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("move unit -l " + selectedRow + " " + selectedColumn));
        if(response != InGameResponses.Unit.MOVETO_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        System.err.println(response.getString());
        show(stage);
    }

    public static void foundCity() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("build city -cn " + RandomString.make(5))); // TODO: 7/24/2022 custom name
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.FOUND_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        selectedUnit = null;
        show(stage);
    }

    public static void sleep() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("sleep"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        System.err.println(response.getString());
        show(stage);
    }

    public static void alert() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("alert"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.ALERT_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void fortify() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("fortify"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.FORTIFY_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void heal() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("heal"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.HEAL_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void wake() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("wake"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.WAKE_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);    }

    public static void delete() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("unit delete"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.DELETE_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void buildImprovement(ImprovementType improvementType) {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("build improvement -t " + improvementType.name));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.BUILD_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void buildRoad(String type) {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("build road -t " + type));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.BUILD_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void removeForest() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("remove forest"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.REMOVE_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void removeJungle() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("remove jungle"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.REMOVE_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void removeMarsh() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("remove jungle"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.REMOVE_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void removeRoute() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("remove route"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.REMOVE_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void pillage() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("pillage"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.PILLAGE_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void repair() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("repair"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.REPAIR_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void setup() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("set up"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.SETUP_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void garrison() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("garrison"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.GARRISON_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void attack() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("attack -l " + selectedRow + " " + selectedColumn));
        if(response != InGameResponses.Unit.MOVETO_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        System.err.println(response.getString());
        show(stage);
    }

}
