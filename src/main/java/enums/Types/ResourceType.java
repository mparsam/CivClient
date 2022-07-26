package enums.Types;

import View.GameView;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public enum ResourceType {
    NULL("Null", 0, 0, 0),
    BANANAS("Bananas", 1, 0, 0),
    CATTLE("Cattle", 1, 0, 0),
    DEER("Deer", 1, 0, 0),
    SHEEP("Sheep", 1, 0, 0),
    WHEAT("Wheat", 1, 0, 0),
    COAL("Coal", 0, 1, 0),
    HORSES("Horses", 0, 1, 0),
    IRON("Iron", 0, 1, 0),
    COTTON("Cotton", 0, 0, 2),
    DYES("Dyes", 0, 0, 2),
    FURS("Furs", 0, 0, 2),
    GEMS("Gems", 0, 0, 3),
    GOLD("Gold", 0, 0, 2),
    INCENSE("Incense", 0, 0, 2),
    IVORY("Ivory", 0, 0, 2),
    MARBLE("Marble", 0, 0, 2),
    SILK("Silk", 0, 0, 2),
    SILVER("Silver", 0, 0, 2),
    SUGAR("Sugar", 0, 0, 2);

    public final String name;
    public final int food, production, gold;

    ResourceType(String name, int food, int production, int gold) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
    }

    public static ResourceType getResourceTypeByName(String resources) {
        for (ResourceType resourceType : ResourceType.values()) {
            if (resourceType.name.equals(resources)) return resourceType;
        }
        System.err.println("Resource Not Found!");
        return null;
    }

    public boolean isBonus() {
        return (this.ordinal() <= 5 && this.ordinal() > 0);
    }

    public boolean isStrategic() {
        return (this.ordinal() >= 6 && this.ordinal() <= 8);
    }

    public boolean isLuxury() {
        return (this.ordinal() >= 9);
    }

    public static ArrayList<ResourceType> getLuxuryResourceTypes() {
        ArrayList<ResourceType> luxs = new ArrayList<>();
        for (ResourceType value : ResourceType.values()) {
            if (value.isLuxury()) luxs.add(value);
        }
        return luxs;
    }

    public ImprovementType getNeededImprovementType() {
        for (ImprovementType improvementType : ImprovementType.values()) {
            if (improvementType.improvingResources.contains(this)) {
                return improvementType;
            }
        }
        return null;
    }

    public ImageView getImage(){
        return new ImageView(GameView.class.getClassLoader().getResource("images/Resources/" + name + ".png").toExternalForm());
    }
}
