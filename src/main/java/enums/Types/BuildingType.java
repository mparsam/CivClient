package enums.Types;

public enum BuildingType {
    BARRACKS("Barracks", TechnologyType.BRONZE_WORKING, 80, 1),
    GRANARY("Granary", TechnologyType.POTTERY, 100, 1),
    LIBRARY("Library", TechnologyType.WRITING, 80, 1),
    MONUMENT("Monument", null, 60, 1),
    WALLS("Walls", TechnologyType.MASONRY, 100, 1),
    WATER_MILL("Water Mill", TechnologyType.THE_WHEEL, 120, 2),
    ARMORY("Armory", TechnologyType.IRON_WORKING, 130, 3),
    BURIAL_TOMB("Burial Tomb", TechnologyType.PHILOSOPHY, 120, 0),
    CIRCUS("Circus", TechnologyType.HORSEBACK_RIDING, 150, 3),
    COLOSSEUM("Colosseum", TechnologyType.CONSTRUCTION, 150, 3),
    COURTHOUSE("Courthouse", TechnologyType.MATHEMATICS, 200, 5),
    STABLE("Stable", TechnologyType.HORSEBACK_RIDING, 100, 1),
    TEMPLE("Temple", TechnologyType.PHILOSOPHY, 120, 2),
    CASTLE("Castle", TechnologyType.CHIVALRY, 200, 3),
    FORGE("Forge", TechnologyType.METAL_CASTING, 150, 2),
    GARDEN("Garden", TechnologyType.THEOLOGY, 120, 2),
    MARKET("Market", TechnologyType.CURRENCY, 120, 0),
    MINT("Mint", TechnologyType.CURRENCY, 120, 0),
    MONASTERY("Monastery", TechnologyType.THEOLOGY, 120, 2),
    UNIVERSITY("University", TechnologyType.EDUCATION, 200, 3),
    WORKSHOP("Workshop", TechnologyType.METAL_CASTING, 100, 2),
    BANK("Bank", TechnologyType.BANKING, 220, 0),
    MILITARY_ACADEMY("Military Academy", TechnologyType.MILITARY_SCIENCE, 350, 3),
    MUSEUM("Museum", TechnologyType.ARCHAEOLOGY, 350, 3),
    OPERA_HOUSE("Opera House", TechnologyType.ACOUSTICS, 220, 3),
    PUBLIC_SCHOOL("Public School", TechnologyType.SCIENTIFIC_THEORY, 350, 3),
    SATRAP_COURT("Satrap’s Court", TechnologyType.BANKING, 220, 0),
    THEATER("Theater", TechnologyType.PRINTING_PRESS, 300, 5),
    WINDMILL("Windmill", TechnologyType.ECONOMICS,180, 2),
    ARSENAL("Arsenal", TechnologyType.RAILROAD, 350, 3),
    BROADCAST_TOWER("Broadcast Tower", TechnologyType.RADIO, 600, 3),
    FACTORY("Factory", TechnologyType.STEAM_POWER, 300, 3),
    HOSPITAL("Hospital", TechnologyType.BIOLOGY, 400, 2),
    MILITARY_BASE("Military Base", TechnologyType.TELEGRAPH, 450, 4),
    STOCK_EXCHANGE("Stock Exchange", TechnologyType.ELECTRICITY, 650, 0);


    public final String name;
    public final TechnologyType technologyType;
    public final int cost;
    public final int maintenance;

    BuildingType(String name, TechnologyType technologyType, int cost, int maintenance) {
        this.name = name;
        this.technologyType = technologyType;
        this.cost = cost;
        this.maintenance = maintenance;
    }

    public static BuildingType getBuildingTypeByName(String name) {
        for (BuildingType buildingType : BuildingType.values()) {
            if(buildingType.name.equals(name)){
                return buildingType;
            }
        }
        return null;
    }
}
