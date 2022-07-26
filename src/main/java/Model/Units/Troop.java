package Model.Units;

import Model.Player;
import Model.Tile;
import enums.Types.*;

import java.io.Serializable;

public class Troop extends Unit implements Serializable {
    public static final long serialVersionUID = 78L;
    private int meleeStrength;
    private int rangedStrength;
    private int range;
    private int fortifyBonus;
    private final ResourceType neededResource;
    private final TechnologyType neededTechnology;

    public Troop(Tile tile, Player owner, UnitType unitType) {
        super(tile, owner, unitType);
        this.meleeStrength = unitType.strength;
        this.rangedStrength = unitType.rangedStrength;
        this.range = unitType.range;
        this.fortifyBonus = 0;
        this.neededResource = unitType.neededResource;
        this.neededTechnology = unitType.neededTech;
    }

    public static Troop troopify(Unit unit){
        if(unit.getCombatType().equals(CombatType.CIVILIAN)) return null;
        Troop troop = new Troop(unit.getTile(), unit.getOwner(), unit.getUnitType());
        return troop;
    }

    public double getMeleeStrength() {
        double strength = meleeStrength;
        return strength;
    }

    public void setMeleeStrength(int meleeStrength) {
        this.meleeStrength = meleeStrength;
    }

    public double getRangedStrength() {
        double strength = rangedStrength;
        if (this.getUnitType() == UnitType.CHARIOT_ARCHER && TerrainFeature.getRoughTerrain().contains(this.getTile().getTerrainFeature())) {
            strength *= 1.1;
        }
        return strength;
    }

    public void setRangedStrength(int rangedStrength) {
        this.rangedStrength = rangedStrength;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getFortifyBonus() {
        return fortifyBonus;
    }

    public void setFortifyBonus(int fortifyBonus) {
        this.fortifyBonus = fortifyBonus;
    }

    public ResourceType getNeededResource() {
        return neededResource;
    }

    public TechnologyType getNeededTechnology() {
        return neededTechnology;
    }

}
