package Model;

import enums.Types.TechnologyType;

import java.io.Serializable;
import java.util.ArrayList;

public class Technology implements Serializable {
    public static final long serialVersionUID = 76L;

    private final ArrayList<TechnologyType> neededTechs;
    private final String name;
    private final TechnologyType technologyType;
    private int requiredCost;
    private int remainingCost;
    private String unlocks;

    public Technology(TechnologyType technologyType) {
        this.technologyType = technologyType;
        this.name = this.technologyType.name;
        this.neededTechs = technologyType.neededTechs;
        this.requiredCost = technologyType.cost;
        this.remainingCost = this.requiredCost;
        this.unlocks = technologyType.unlocks;
    }

    public ArrayList<TechnologyType> getNeededTechs() {
        return neededTechs;
    }

    public String getName() {
        return name;
    }

    public TechnologyType getTechnologyType() {
        return technologyType;
    }

    public int getRequiredCost() {
        return requiredCost;
    }

    public void setRequiredCost(int requiredCost) {
        this.requiredCost = requiredCost;
    }

    public int getRemainingCost() {
        return remainingCost;
    }

    public void setRemainingCost(int remainingCost) {
        this.remainingCost = remainingCost;
    }

    public String getUnlocks() {
        return unlocks;
    }

    public void setUnlocks(String unlocks) {
        this.unlocks = unlocks;
    }
}
