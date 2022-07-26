package enums.Types;

import java.util.ArrayList;
import java.util.Arrays;

public enum ImprovementType {
    CAMP("Camp", 0, 0, 0, new ArrayList<>(Arrays.asList(ResourceType.IVORY, ResourceType.FURS, ResourceType.DEER)), TechnologyType.TRAPPING, new ArrayList<>(Arrays.asList(TerrainFeature.FOREST, TerrainFeature.TUNDRA, TerrainFeature.PLAINS, TerrainFeature.HILL))),
    FARM("Farm", 0, 0, 1, new ArrayList<>(Arrays.asList(ResourceType.WHEAT)), TechnologyType.AGRICULTURE, new ArrayList<>(Arrays.asList(TerrainFeature.GRASSLAND, TerrainFeature.PLAINS, TerrainFeature.DESERT))),
    LUMBER_MILL("Lumber Mill", 0, 1, 0, new ArrayList<>(Arrays.asList()), TechnologyType.ENGINEERING, new ArrayList<>(Arrays.asList(TerrainFeature.FOREST))),
    MINE("Mine", 0, 1, 0, new ArrayList<>(Arrays.asList(ResourceType.IRON, ResourceType.COAL, ResourceType.GEMS, ResourceType.GOLD, ResourceType.SILVER)), TechnologyType.MINING, new ArrayList<>(Arrays.asList(TerrainFeature.GRASSLAND, TerrainFeature.PLAINS, TerrainFeature.DESERT, TerrainFeature.TUNDRA, TerrainFeature.JUNGLE, TerrainFeature.SNOW, TerrainFeature.HILL))),
    PASTURE("Pasture", 0, 0, 0, new ArrayList<>(Arrays.asList(ResourceType.HORSES, ResourceType.CATTLE, ResourceType.SHEEP)), TechnologyType.ANIMAL_HUSBANDRY, new ArrayList<>(Arrays.asList(TerrainFeature.GRASSLAND, TerrainFeature.PLAINS, TerrainFeature.DESERT, TerrainFeature.TUNDRA, TerrainFeature.HILL))),
    PLANTATION("Plantation", 0, 0, 0, new ArrayList<>(Arrays.asList(ResourceType.BANANAS, ResourceType.DYES, ResourceType.SILK, ResourceType.SUGAR, ResourceType.COTTON, ResourceType.INCENSE)), TechnologyType.CALENDAR, new ArrayList<>(Arrays.asList(TerrainFeature.GRASSLAND, TerrainFeature.PLAINS, TerrainFeature.DESERT, TerrainFeature.FOREST, TerrainFeature.MARSH, TerrainFeature.FLOOD_PLAINS, TerrainFeature.JUNGLE))),
    QUARRY("Quarry", 0, 0, 0, new ArrayList<>(Arrays.asList(ResourceType.MARBLE)), TechnologyType.MASONRY, new ArrayList<>(Arrays.asList(TerrainFeature.GRASSLAND, TerrainFeature.PLAINS, TerrainFeature.DESERT, TerrainFeature.TUNDRA, TerrainFeature.HILL))),
    TRADING_POST("Trading Post", 2, 0, 0, new ArrayList<>(Arrays.asList()), TechnologyType.TRAPPING, new ArrayList<>(Arrays.asList(TerrainFeature.GRASSLAND, TerrainFeature.PLAINS, TerrainFeature.DESERT, TerrainFeature.TUNDRA))),
    MANUFACTORY("Manufactory", 0, 3, 0, new ArrayList<>(Arrays.asList()), TechnologyType.ENGINEERING, new ArrayList<>(Arrays.asList(TerrainFeature.GRASSLAND, TerrainFeature.PLAINS, TerrainFeature.DESERT, TerrainFeature.TUNDRA, TerrainFeature.SNOW)));

    public final String name;
    public final int gold;
    public final int production;
    public final int food;
    public final ArrayList<ResourceType> improvingResources;
    public final TechnologyType neededTech;
    public final ArrayList<TerrainFeature> canBeOn;

    ImprovementType(String name, int gold, int production, int food, ArrayList<ResourceType> improvingResources, TechnologyType neededTech, ArrayList<TerrainFeature> canBeOn) {
        this.name = name;
        this.gold = gold;
        this.production = production;
        this.food = food;
        this.improvingResources = improvingResources;
        this.neededTech = neededTech;
        this.canBeOn = canBeOn;
    }

    public static ImprovementType getTypeByName(String name) {
        for (ImprovementType value : ImprovementType.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
