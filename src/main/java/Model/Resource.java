package Model;

import enums.Types.ImprovementType;
import enums.Types.ResourceType;

import java.io.Serializable;

public class Resource implements Serializable {
    public static final long serialVersionUID = 73L;
    private final ResourceType resourceType;
    private String name;
    private int food, production, gold;
    private ImprovementType neededImprovement;

    public Resource(ResourceType resourceType) {
        this.resourceType = resourceType;
        this.food = resourceType.food;
        this.gold = resourceType.gold;
        this.production = resourceType.production;
        this.name = resourceType.name;
        this.neededImprovement = resourceType.getNeededImprovementType();
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public ImprovementType getNeededImprovement() {
        return neededImprovement;
    }

    public void setNeededImprovement(ImprovementType neededImprovement) {
        this.neededImprovement = neededImprovement;
    }

}
