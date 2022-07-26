package Model;

import enums.Types.BuildingType;
import enums.Types.TechnologyType;

import java.io.Serializable;
import java.util.ArrayList;

public class Building implements Serializable {
    public static final long serialVersionUID = 67L;
    private final String name;
    private final int cost;
    private final BuildingType buildingType;
    private final int maintenanceCost;
    private final TechnologyType neededTech;
    private int remainingCost;

    public Building(BuildingType buildingType) {
        this.buildingType = buildingType;
        this.name = buildingType.name;
        this.neededTech = buildingType.technologyType;
        this.cost = buildingType.cost;
        this.remainingCost = this.cost;
        this.maintenanceCost = buildingType.maintenance;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getMaintenanceCost() {
        return maintenanceCost;
    }

    public TechnologyType getNeededTech() {
        return neededTech;
    }

    public int getRemainingCost() {
        return remainingCost;
    }

    public void setRemainingCost(int remainingTurns) {
        this.remainingCost = remainingTurns;
    }
}
