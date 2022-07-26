package Model;

import enums.Types.ImprovementType;
import enums.Types.ResourceType;
import enums.Types.TechnologyType;
import enums.Types.TerrainFeature;

import java.io.Serializable;
import java.util.ArrayList;

public class Improvement implements Serializable {
    public static final long serialVersionUID = 70L;
    private final ImprovementType improvementType;
    private String name;
    private final Tile tile;
    private final int addedFood, addedGold, addedProduction;
    private final ArrayList<TechnologyType> neededTechs = new ArrayList<>();
    private final ArrayList<ResourceType> unlockingResources = new ArrayList<>();
    private final ArrayList<TerrainFeature> possibleTerrainFeatures = new ArrayList<>();
    private int remainingTurns;
    private final int requiredTurns;

    public Improvement(ImprovementType improvementType, Tile tile) {
        this.improvementType = improvementType;
        this.name = improvementType.name;
        this.tile = tile;
        this.addedFood = improvementType.food;
        this.addedGold = improvementType.gold;
        this.addedProduction = improvementType.production;
        this.requiredTurns = 6;
        this.remainingTurns = this.requiredTurns;
    }

    public Improvement(Improvement improvement) {
        this.name = improvement.name;
        this.improvementType = improvement.improvementType;
        this.tile = improvement.tile;
        this.addedFood = improvement.addedFood;
        this.addedGold = improvement.addedGold;
        this.addedProduction = improvement.addedProduction;
        this.requiredTurns = 6;
        this.remainingTurns = this.requiredTurns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }

    public ImprovementType getImprovementType() {
        return improvementType;
    }

    public Tile getTile() {
        return tile;
    }

    public int getAddedFood() {
        return addedFood;
    }

    public int getAddedGold() {
        return addedGold;
    }

    public int getAddedProduction() {
        return addedProduction;
    }

    public ArrayList<TechnologyType> getNeededTechs() {
        return neededTechs;
    }

    public ArrayList<ResourceType> getUnlockingResources() {
        return unlockingResources;
    }

    public ArrayList<TerrainFeature> getPossibleTerrainFeatures() {
        return possibleTerrainFeatures;
    }

    public int getRequiredTurns() {
        return requiredTurns;
    }
}
