package enums.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public enum TechnologyType {
    AGRICULTURE("Agriculture", 20, new ArrayList<>(List.of()), "farm"),
    ANIMAL_HUSBANDRY("Animal Husbandry", 35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)), "horse, pasture"),
    ARCHERY("Archery", 35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)), "archer"),
    MINING("Mining", 35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)), "mines, deforestation"),
    BRONZE_WORKING("Bronze Working", 55, new ArrayList<>(List.of(TechnologyType.MINING)), "spearman, barracks, dejunglation"),
    POTTERY("Pottery", 35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)), "granary"),
    CALENDAR("Calendar", 70, new ArrayList<>(List.of(TechnologyType.POTTERY)), "plantation"),
    MASONRY("Masonry", 55, new ArrayList<>(List.of(TechnologyType.MINING)), "walls, quarry, demarsh"),
    THE_WHEEL("The-Wheel", 55, new ArrayList<>(List.of(TechnologyType.ANIMAL_HUSBANDRY)), "chariot archer, water mill, road"),
    TRAPPING("Trapping", 55, new ArrayList<>(List.of(TechnologyType.ANIMAL_HUSBANDRY)), "trading post, camp"),
    WRITING("Writing", 55, new ArrayList<>(List.of(TechnologyType.POTTERY)), "library"),
    CONSTRUCTION("Construction", 100, new ArrayList<>(List.of(TechnologyType.MASONRY)), "colosseum, bridge"),
    HORSEBACK_RIDING("Horseback Riding", 100, new ArrayList<>(List.of(TechnologyType.THE_WHEEL)), "horseman, stable, circus"),
    IRON_WORKING("Iron Working", 150, new ArrayList<>(List.of(TechnologyType.BRONZE_WORKING)), "swordsman, legion, armory, iron"),
    MATHEMATICS("Mathematics", 100, new ArrayList<>(Arrays.asList(TechnologyType.THE_WHEEL, TechnologyType.ARCHERY)), "catapult, courthouse"),
    PHILOSOPHY("Philosophy", 100, new ArrayList<>(List.of(TechnologyType.WRITING)), "burial tomb, temple"),
    CIVIL_SERVICE("Civil Service", 400, new ArrayList<>(Arrays.asList(TechnologyType.PHILOSOPHY, TechnologyType.TRAPPING)), "Pikeman"),
    CURRENCY("Currency", 250, new ArrayList<>(List.of(TechnologyType.MATHEMATICS)), "market"),
    CHIVALRY("Chivalry", 440, new ArrayList<>(Arrays.asList(TechnologyType.CIVIL_SERVICE, TechnologyType.HORSEBACK_RIDING, TechnologyType.CURRENCY)), "Knight, Camel Archer, Castle"),
    THEOLOGY("Theology", 250, new ArrayList<>(Arrays.asList(TechnologyType.CALENDAR, TechnologyType.PHILOSOPHY)), "monastery garden"),
    EDUCATION("Education", 440, new ArrayList<>(List.of(TechnologyType.THEOLOGY)), "University"),
    ENGINEERING("Engineering", 250, new ArrayList<>(Arrays.asList(TechnologyType.MATHEMATICS, TechnologyType.CONSTRUCTION)), ""),
    MACHINERY("Machinery", 440, new ArrayList<>(List.of(TechnologyType.ENGINEERING)), "crossbowman, faster road"),
    METAL_CASTING("Metal Casting", 240, new ArrayList<>(List.of(TechnologyType.IRON_WORKING)), "forge, workshop"),
    PHYSICS("Physics", 440, new ArrayList<>(Arrays.asList(TechnologyType.ENGINEERING, TechnologyType.METAL_CASTING)), "trebuchet"),
    STEEL("Steel", 440, new ArrayList<>(List.of(TechnologyType.METAL_CASTING)), "longswordsman"),
    ACOUSTICS("Acoustics", 650, new ArrayList<>(List.of(TechnologyType.EDUCATION)), ""),
    ARCHAEOLOGY("Archaeology", 1300, new ArrayList<>(List.of(TechnologyType.ACOUSTICS)), "museum"),
    BANKING("Banking", 650, new ArrayList<>(Arrays.asList(TechnologyType.EDUCATION, TechnologyType.CHIVALRY)), "satrap's court, bank"),
    GUNPOWDER("Gunpowder", 680, new ArrayList<>(Arrays.asList(TechnologyType.PHYSICS, TechnologyType.STEEL)), "musketman"),
    CHEMISTRY("Chemistry", 900, new ArrayList<>(List.of(TechnologyType.GUNPOWDER)), "ironworks"),
    PRINTING_PRESS("Printing Press", 650, new ArrayList<>(Arrays.asList(TechnologyType.MACHINERY, TechnologyType.PHYSICS)), "theater"),
    ECONOMICS("Economics", 900, new ArrayList<>(Arrays.asList(TechnologyType.BANKING, TechnologyType.PRINTING_PRESS)), "windmill"),
    FERTILIZER("Fertilizer", 1300, new ArrayList<>(List.of(TechnologyType.CHEMISTRY)), "Farms without Fresh Water yield increased by 1"),
    METALLURGY("Metallurgy", 900, new ArrayList<>(List.of(TechnologyType.GUNPOWDER)), "lancer"),
    MILITARY_SCIENCE("Military Science", 1300, new ArrayList<>(Arrays.asList(TechnologyType.ECONOMICS, TechnologyType.CHEMISTRY)), "cavalry, military, academy"),
    RIFLING("Rifling", 1425, new ArrayList<>(List.of(TechnologyType.METALLURGY)), "rifleman"),
    SCIENTIFIC_THEORY("Scientific Theory", 1300, new ArrayList<>(List.of(TechnologyType.ACOUSTICS)), "public school, coal"),
    BIOLOGY("Biology", 1680, new ArrayList<>(Arrays.asList(TechnologyType.ARCHAEOLOGY, TechnologyType.SCIENTIFIC_THEORY)), ""),
    DYNAMITE("Dynamite", 1900, new ArrayList<>(Arrays.asList(TechnologyType.FERTILIZER, TechnologyType.RIFLING)), "artillery"),
    STEAM_POWER("Steam Power", 1680, new ArrayList<>(Arrays.asList(TechnologyType.SCIENTIFIC_THEORY, TechnologyType.MILITARY_SCIENCE)), "factory"),
    ELECTRICITY("Electricity", 1900, new ArrayList<>(Arrays.asList(TechnologyType.BIOLOGY, TechnologyType.STEAM_POWER)), "stock exchange"),
    RADIO("Radio", 2200, new ArrayList<>(List.of(TechnologyType.ELECTRICITY)), "broadcast tower"),
    RAILROAD("Railroad", 1900, new ArrayList<>(List.of(TechnologyType.STEAM_POWER)), "arsenal, railroad"),
    REPLACEABLE_PARTS("Replaceable Parts", 1900, new ArrayList<>(List.of(TechnologyType.STEAM_POWER)), "anti-tank"),
    COMBUSTION("Combustion", 2200, new ArrayList<>(Arrays.asList(TechnologyType.REPLACEABLE_PARTS, TechnologyType.RAILROAD, TechnologyType.DYNAMITE)), "tank, panzer"),
    TELEGRAPH("Telegraph", 2200, new ArrayList<>(List.of(TechnologyType.ELECTRICITY)), "military base");


    public final String name;
    public final int cost;
    public final ArrayList<TechnologyType> neededTechs;
    public final String unlocks;

    TechnologyType(String name, int cost, ArrayList<TechnologyType> neededTechs, String unlocks) {
        this.name = name;
        this.cost = cost;
        this.neededTechs = neededTechs;
        this.unlocks = unlocks;
    }

    /**
     * gets a techName and returns corresponding enum
     *
     * @return TechnologyType or null
     */
    public static TechnologyType getTypeByName(String name) {
        for (TechnologyType technologyType : TechnologyType.values()) {
            if (technologyType.name.toLowerCase(Locale.ROOT).equals(name.toLowerCase(Locale.ROOT)))
                return technologyType;
        }
        return null;
    }

    public ArrayList<TechnologyType> getUnlockingTechs() {
        return Arrays.stream(TechnologyType.values()).filter(t -> t.neededTechs.contains(this)).collect(Collectors.toCollection(ArrayList::new));
    }
}
