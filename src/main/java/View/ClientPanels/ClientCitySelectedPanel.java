package View.ClientPanels;

import Model.City;
import Model.Tile;
import View.GameView;
import View.Network;
import enums.RequestActions;
import enums.Responses.InGameResponses;
import enums.Types.BuildingType;
import enums.Types.UnitType;

public class ClientCitySelectedPanel extends GameView {

    public static void buildBuilding(BuildingType buildingType) {
        InGameResponses.Building response = ((InGameResponses.Building) Network.getResponseObjOfPanelCommand("build building -t " + buildingType.name));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Building.IN_PROGRESS_BUILDING_CHANGED){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void buildUnit(UnitType unitType) {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("build unit -t " + unitType.name));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.UNIT_BUILDING_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void pauseUnit() {
        InGameResponses.Unit response = ((InGameResponses.Unit) Network.getResponseObjOfPanelCommand("pause unit"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.Unit.UNIT_BUILDING_PAUSED){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void buyUnit(UnitType unitType) {
        InGameResponses.City response = ((InGameResponses.City) Network.getResponseObjOfPanelCommand("buy unit -t " + unitType.name));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.City.UNIT_BUY_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void assignCitizen() {
        InGameResponses.City response = ((InGameResponses.City) Network.getResponseObjOfPanelCommand("assign citizen -l " + selectedRow + " " + selectedColumn));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.City.ASSIGNMENT_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void freeCitizen() {
        InGameResponses.City response = ((InGameResponses.City) Network.getResponseObjOfPanelCommand("free citizen -l " + selectedRow + " " + selectedColumn));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.City.FREEING_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void buyTile() {
        InGameResponses.City response = ((InGameResponses.City) Network.getResponseObjOfPanelCommand("buy tile -l " + selectedRow + " " + selectedColumn));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.City.TILE_BUY_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void attack() {
        InGameResponses.City response = ((InGameResponses.City) Network.getResponseObjOfPanelCommand("city attack -l " + selectedRow + " " + selectedColumn));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.City.ATTACK_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static void delete() {
        InGameResponses.City response = ((InGameResponses.City) Network.getResponseObjOfPanelCommand("city delete"));
        Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
        if(response != InGameResponses.City.DELETE_SUCCESSFUL){
            showAlert(invalidAlert, response.getString());
        }
        System.err.println(response.getString());
        show(stage);
    }

    public static String showBanner() {
        City city = selectedCity;
        StringBuilder info = new StringBuilder();
        info.append(city.getName() + " owned by " + city.getOwner().getName() + "\n");
        info.append("location: " + city.getCapitalTile().getRow() + "," + city.getCapitalTile().getColumn() + "\n");
        info.append("HP: " + city.getHP() + "\n");
        info.append("strength: " + city.getStrength() + "\n");
        info.append("population: " + city.getPopulation() + "\n");
        info.append("production: " + city.getProductionIncome() + "\n");
        info.append("food: " + city.getFoodIncome() + "\n");
        info.append("gold: " + city.getGoldIncome() + "\n");
        info.append("unemployed citizen cnt: " + city.getFreeCitizens() + "\n");
        info.append("tiles being worked on: \n");
        for (Tile tile : city.getTerritory()) {
            if(tile.isHasCitizen()) info.append(tile.getRow() + "," + tile.getColumn() + "\n");
        }
        if (city.getUnitInProgress() != null) {
            info.append("currently building a " + city.getUnitInProgress().getUnitType().name + "\n");
            int remainingTurns = (city.getUnitInProgress().getRemainingCost() + city.getProductionIncome() - 1) / city.getProductionIncome();
            info.append("will be completed in: " + remainingTurns + "\n");
        }
        if (city.getBuildingInProgress() != null) {
            info.append("currently building a " + city.getBuildingInProgress().getBuildingType().name + "\n");
            int remainingTurns = (city.getBuildingInProgress().getRemainingCost() + city.getProductionIncome() - 1) / city.getProductionIncome();
            info.append("will be completed in: " + remainingTurns + "\n");
        }
        return info.toString();
    }
}
