package Model;

import enums.Types.TechnologyType;
import enums.Types.UnitType;

import java.io.Serializable;
import java.util.Random;

public class Ruin implements Serializable {
    public static final long serialVersionUID = 75L;
    private int gold;
    private int population;
    private UnitType unit;
    private TechnologyType technology;

    public Ruin() {
        this.gold = 0;
        this.population = 0;
        this.unit = null;
        this.technology = null;
    }

    public Ruin(int gold, int population, UnitType units, TechnologyType technologies) {
        this.gold = gold;
        this.population = population;
        this.unit = units;
        this.technology = technologies;
    }

    public static Ruin getRandomRuin() {
        Random random = new Random(System.currentTimeMillis());
        Ruin ruin = new Ruin();
        switch (random.nextInt(4)){
            case 0:
                ruin.setGold(random.nextInt(10) + 20);
                break;
            case 1:
                ruin.setPopulation(random.nextInt(3));
                break;
            case 2:
                if(random.nextBoolean()) ruin.setUnit(UnitType.WORKER);
                else ruin.setUnit(UnitType.SETTLER);
                break;
            case 3:
                ruin.setTechnology(TechnologyType.values()[random.nextInt(TechnologyType.values().length)]);
                break;
        }
        return ruin;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public UnitType getUnit() {
        return unit;
    }

    public void setUnit(UnitType unit) {
        this.unit = unit;
    }

    public TechnologyType getTechnology() {
        return technology;
    }

    public void setTechnology(TechnologyType technology) {
        this.technology = technology;
    }
}
