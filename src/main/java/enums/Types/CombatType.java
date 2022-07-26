package enums.Types;

public enum CombatType {
    RANGED("Ranged"),
    MOUNTED("Mounted"),
    RECON("Recon"),
    CIVILIAN("Civilian"),
    MELEE("Melee"),
    SIEGE("Siege"),
    GUNPOWDER("Gunpowder"),
    ARMORED("Armored");

    private final String name;

    CombatType(String name) {
        this.name = name;
    }

    public static CombatType getCombatTypeByName(String name) {
        for (CombatType combatType : CombatType.values()) {
            if (combatType.name.equals(name)) return combatType;
        }
        return null;
    }
}
