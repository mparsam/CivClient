package enums.Types;

import View.GameView;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public enum UnitType {
    ARCHER("Archer", 70, CombatType.RANGED, 4, 6, 2, 2, null, TechnologyType.ARCHERY),
    CHARIOT_ARCHER("Chariot Archer", 60, CombatType.MOUNTED, 3, 6, 2, 4, ResourceType.HORSES, TechnologyType.THE_WHEEL),
    SCOUT("Scout", 25, CombatType.RECON, 4, 0, 0, 2, null, null),
    SETTLER("Settler", 89, CombatType.CIVILIAN, 0, 0, 0, 2, null, null),
    SPEARMAN("Spearman", 50, CombatType.MELEE, 7, 0, 0, 2, null, TechnologyType.BRONZE_WORKING),
    WARRIOR("Warrior", 40, CombatType.MELEE, 6, 0, 0, 2, null, null),
    WORKER("Worker", 70, CombatType.CIVILIAN, 0, 0, 0, 2, null, null),
    CATAPULT("Catapult", 100, CombatType.SIEGE, 4, 14, 2, 2, ResourceType.IRON, TechnologyType.MATHEMATICS),
    HORSEMAN("Horseman", 80, CombatType.MOUNTED, 12, 0, 0, 4, ResourceType.HORSES, TechnologyType.HORSEBACK_RIDING),
    SWORDSMAN("Swordsman", 80, CombatType.MELEE, 11, 0, 0, 2, ResourceType.IRON, TechnologyType.IRON_WORKING),
    CROSSBOWMAN("Crossbowman", 120, CombatType.RANGED, 6, 12, 2, 2, null, TechnologyType.MACHINERY),
    KNIGHT("Knight", 150, CombatType.MOUNTED, 18, 0, 0, 3, ResourceType.HORSES, TechnologyType.CHIVALRY),
    LONGSWORDSMAN("Longswordsman", 150, CombatType.MELEE, 18, 0, 0, 3, ResourceType.IRON, TechnologyType.STEEL),
    PIKEMAN("Pikeman", 100, CombatType.MELEE, 10, 0, 0, 2, null, TechnologyType.CIVIL_SERVICE),
    TREBUCHET("Trebuchet", 170, CombatType.SIEGE, 6, 20, 2, 2, ResourceType.IRON, TechnologyType.PHYSICS),
    CANON("Canon", 250, CombatType.SIEGE, 10, 26, 2, 2, null, TechnologyType.CHEMISTRY),
    CAVALRY("Cavalry", 260, CombatType.MOUNTED, 25, 0, 0, 3, ResourceType.HORSES, TechnologyType.MILITARY_SCIENCE),
    LANCER("Lancer", 220, CombatType.MOUNTED, 22, 0, 0, 4, ResourceType.HORSES, TechnologyType.METALLURGY),
    MUSKETMAN("Musketman", 120, CombatType.GUNPOWDER, 16, 0, 0, 2, null, TechnologyType.GUNPOWDER),
    RIFLEMAN("Rifleman", 200, CombatType.GUNPOWDER, 25, 0, 0, 2, null, TechnologyType.RIFLING),
    ANTI_TANK_GUN("Anti-Tank Gun", 300, CombatType.GUNPOWDER, 32, 0, 0, 2, null, TechnologyType.REPLACEABLE_PARTS),
    ARTILLERY("Artillery", 420, CombatType.SIEGE, 16, 32, 3, 2, null, TechnologyType.DYNAMITE),
    INFANTRY("Infantry", 300, CombatType.GUNPOWDER, 36, 0, 0, 2, null, TechnologyType.REPLACEABLE_PARTS),
    PANZER("Panzer", 450, CombatType.ARMORED, 60, 0, 0, 5, null, TechnologyType.COMBUSTION),
    TANK("Tank", 450, CombatType.ARMORED, 50, 0, 0, 4, null, TechnologyType.COMBUSTION);

    public final String name;
    public final int cost;
    public final CombatType combatType;
    public final int strength;
    public final int rangedStrength;
    public final int range;
    public final int movement;
    public final ResourceType neededResource;
    public final TechnologyType neededTech;

    UnitType(String name, int cost, CombatType combatType, int strength, int rangedStrength, int range, int movement, ResourceType neededResource, TechnologyType neededTech) {
        this.name = name;
        this.cost = cost;
        this.combatType = combatType;
        this.strength = strength;
        this.rangedStrength = rangedStrength;
        this.range = range;
        this.movement = movement;
        this.neededResource = neededResource;
        this.neededTech = neededTech;
    }

    public static UnitType getUnitTypeByName(String name) {
        for (UnitType unitType : UnitType.values()) {
            if (unitType.name.equals(name)) return unitType;
        }
        return null;
    }

    public static ArrayList<UnitType> getUnitsByCombatType(CombatType... combatType) {
        ArrayList<UnitType> units = new ArrayList();
        for (UnitType unitType : UnitType.values()) {
            for (CombatType type : combatType) {
                if (unitType.combatType.equals(type)) {
                    units.add(unitType);
                }
            }
        }
        return units;
    }

    public ImageView getImage(){
        return new ImageView(GameView.class.getClassLoader().getResource("images/Units/" + name + ".png").toExternalForm());
    }

}
















