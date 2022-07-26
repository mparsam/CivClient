package enums.Types;

public enum RoadType {
    // costs are DELLY
    ROAD("Road", 1),
    RAILROAD("Railroad", 2);

    private final String name;
    private final int maintenanceCost;

    RoadType(String name, int maintenanceCost) {
        this.name = name;
        this.maintenanceCost = maintenanceCost;
    }

    public static RoadType getTypeByName(String name) {
        for (RoadType type : RoadType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

    public int getMaintenanceCost() {
        return maintenanceCost;
    }
}
