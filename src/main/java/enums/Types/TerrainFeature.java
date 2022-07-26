package enums.Types;

import View.GameView;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public enum TerrainFeature {
    NULL(" Null", 0, 0, 0, 0, 0, new ArrayList<>(), "Null.png"),
    FLOOD_PLAINS("Flood Plains", 2, 0, 0, -0.33, 1, new ArrayList<>(Arrays.asList(ResourceType.WHEAT, ResourceType.SUGAR)), "Flood plains.png"),
    FOREST("Forest", 1, 1, 0, 0.25, 2, new ArrayList<>(Arrays.asList(ResourceType.DEER, ResourceType.FURS, ResourceType.DYES, ResourceType.SILK)), "Forest.png"),
    ICE("Ice", 0, 0, 0, 0.0, 9999, new ArrayList<>(Arrays.asList()), "Ice.png"),
    JUNGLE("Jungle", 1, -1, 0, 0.25, 2, new ArrayList<>(Arrays.asList(ResourceType.BANANAS, ResourceType.GEMS, ResourceType.DYES)), "Jungle.png"),
    MARSH("Marsh", -1, 0, 0, -0.33, 2, new ArrayList<>(Arrays.asList(ResourceType.SUGAR)), "Marsh.png"),
    OASIS("Oasis", 3, 0, 1, -0.33, 1, new ArrayList<>(Arrays.asList()), "Oasis.png"),
    DESERT("Desert", 0, 0, 0, -0.33, 1, new ArrayList<>(Arrays.asList(ResourceType.IRON, ResourceType.GOLD, ResourceType.SILVER, ResourceType.GEMS, ResourceType.MARBLE, ResourceType.COTTON, ResourceType.INCENSE, ResourceType.SHEEP)), "Desert.png"),
    GRASSLAND("Grassland", 2, 0, 0, -0.33, 1, new ArrayList<>(Arrays.asList(ResourceType.IRON, ResourceType.HORSES, ResourceType.COAL, ResourceType.CATTLE, ResourceType.GOLD, ResourceType.GEMS, ResourceType.MARBLE, ResourceType.COTTON, ResourceType.SHEEP)), "Grassland.png"),
    HILL("Hill", 0, 2, 0, 0.25, 2, new ArrayList<>(Arrays.asList(ResourceType.IRON, ResourceType.COAL, ResourceType.DEER, ResourceType.GOLD, ResourceType.SILVER, ResourceType.GEMS, ResourceType.MARBLE, ResourceType.SHEEP)), "Hill.png"),
    MOUNTAIN("Mountain", 0, 0, 0, 0.25, 9999, new ArrayList<>(Arrays.asList()), "Mountain.png"),
    OCEAN("Ocean", 1, 0, 1, 0.0, 1, new ArrayList<>(Arrays.asList()), "Ocean.png"),
    PLAINS("Plains", 1, 1, 0, -0.33, 1, new ArrayList<>(Arrays.asList(ResourceType.IRON, ResourceType.HORSES, ResourceType.COAL, ResourceType.WHEAT, ResourceType.GOLD, ResourceType.GEMS, ResourceType.MARBLE, ResourceType.IVORY, ResourceType.COTTON, ResourceType.INCENSE, ResourceType.SHEEP)), "Plains.png"),
    SNOW("Snow", 0, 0, 0, -0.33, 1, new ArrayList<>(Arrays.asList(ResourceType.IRON)), "Snow.png"),
    TUNDRA("Tundra", 1, 0, 1, 0.0, 1, new ArrayList<>(Arrays.asList(ResourceType.IRON, ResourceType.HORSES, ResourceType.DEER, ResourceType.SILVER, ResourceType.GEMS, ResourceType.MARBLE, ResourceType.FURS)), "Tundra.png");

    public final String name;
    public final int food, production, gold;
    public final double combat;
    public final int movement;
    public final ArrayList<ResourceType> possibleResources;
    public final String imageAddress;

    TerrainFeature(String name, int food, int production, int gold, double combat, int movement, ArrayList<ResourceType> possibleResources, String imageAddress) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combat = combat;
        this.movement = movement;
        this.possibleResources = possibleResources;
        this.imageAddress = imageAddress;
    }

    public static ArrayList<TerrainFeature> getRoughTerrain() {
        return new ArrayList<>(Arrays.asList(TerrainFeature.FOREST, TerrainFeature.JUNGLE, TerrainFeature.MARSH, TerrainFeature.HILL));
    }

    public ImageView getImage(){
        return new ImageView(GameView.class.getClassLoader().getResource("images/Features/" + imageAddress).toExternalForm());
    }
}
