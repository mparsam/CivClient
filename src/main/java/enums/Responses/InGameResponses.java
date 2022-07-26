package enums.Responses;

public class InGameResponses {

    public enum Building {
        CITY_NOT_IN_POSSESS("selected city is not yours"),
        ALREADY_EXISTS("\"$\" already exists in this city"),
        IN_PROGRESS_BUILDING_CHANGED("In progress building changed to \"$\""),
        CITY_NOT_SELECTED("No city is selected"),
        NO_BUILDING_IN_PROGRESS("There are no in progress buildings"),
        BUILDING_PAUSED("Building progress paused");

        private final String message;

        Building(String message) {
            this.message = message;
        }

        // can get a string and adds it to corresponding location in the response message
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }

    public enum Unit {
        //GENERAL(FOR ALL)
        CITY_NOT_SELECTED("No city is selected"),
        NO_UNIT_SELECTED("no unit is selected"),
        UNIT_NOT_AVAILABLE("selected unit is not available"),
        UNIT_NOT_IN_POSSESS("selected unit is not in possess"),
        CITY_NOT_IN_POSSESS("selected city is not yours"),
        UNIT_IS_TIRED("selected unit is out of moves"),
        //MOVE TO
        TILE_NOT_REACHABLE("movement to this position is not possible because its not reachable"),
        TILE_IS_FILLED("movement to this position is not possible because there is a troop on it"),
        //ALERT , FORTIFY , GARRISON , ATTACK
        UNIT_NOT_MILITARY("selected unit is non-military"),
        UNIT_CANT_FORTIFY("selected unit can't fortify"),
        //GARRISON
        UNIT_NOT_PRESENT_IN_CITY("unit is not present in your city"),
        //SETUP
        UNIT_NOT_SIEGE("selected unit is not a siege unit"),
        //ATTACK
        ATTACK_NOT_POSSIBLE("attack to this position is not possible"),
        UNIT_NOT_SETUP("the siege unit is not yet set up"),
        UNIT_OUT_OF_RANGE("unit is out of range"),
        TARGET_EMPTY("target is empty (no city or troop on it)"),
        OWN_TARGET("selected target is not an enemy"),
        //FOUND
        UNIT_NOT_A_SETTLER("selected unit is not a settler"),
        CITY_FOUNDATION_NOT_POSSIBLE("city foundation in the current position is not possible"),
        //PILLAGE
        NO_IMPROVEMENT("there is no improvement on the current tile"),
        OWN_IMPROVEMENT("this improvement belongs to you, you can't pillage it"),
        //BUILD , REMOVE , REPAIR
        INVALID_IMPROVEMENT("invalid improvement type"),
        INVALID_ROAD("invalid road type"),
        UNIT_NOT_A_WORKER("selected unit is not a worker"),
        TILE_NOT_FOREST("selected unit is not on a forest tile"),
        TILE_NOT_JUNGLE("selected unit is not on a jungle tile"),
        TILE_NOT_MARSH("selected unit is not on a marsh tile"),
        //BUILD
        BLOCKED_BY_FEATURE("the feature of this tile doesn't allow for building this improvement. you must remove it first"),
        ROAD_ALREADY_EXISTS("road already exists in the current position"),
        RAILROAD_ALREADY_EXISTS("railroad already exists in the current position"),
        IMPROVEMENT_ALREADY_EXISTS("this improvement already exists in the current position"),
        BUILDING_NOT_POSSIBLE("building not possible in the current position"),
        CONTINUING_BUILDING("worker is continuing the building"),
        NO_THE_WHEEL("you haven't researched The Wheel technology yet"),
        NO_RAILROAD("you haven't researched Railroad technology yet"),
        //REMOVE
        ROUTE_NOT_AVAILABLE("selected unit is not on a tile with a route"),
        NO_BRONZE_WORKING("you haven't researched Bronze Working technology yet"),
        NO_MASONRY("you haven't researched Masonry technology yet"),
        NO_MINING("you haven't researched Mining technology yet"),
        //REPAIR
        RUIN_NOT_AVAILABLE("ruin is not available in the current position"),
        //BUILDING THE UNIT
        UNIT_BUILDING_PAUSED("unit building successfully paused"),
        UNIT_BUILDING_SUCCESSFUL("unit is being built successfully"),
        UNIT_ALREADY_IN_MAKING("a unit of this type is already being built"),
        DO_NOT_HAVE_TECH("you don't have the required technology to build this"),
        DO_NOT_HAVE_RESOURCE("you don't have the resources to build this unit"),

        //SUCCESS
        MOVETO_SUCCESSFUL("unit successfully moved"),
        SLEEP_SUCCESSFUL("unit successfully slept"),
        ALERT_SUCCESSFUL("unit successfully alerted"),
        FORTIFY_SUCCESSFUL("unit successfully fortified"),
        HEAL_SUCCESSFUL("unit is successfully healing"),
        GARRISON_SUCCESSFUL("unit successfully garrisoned"),
        SETUP_SUCCESSFUL("unit successfully setup"),
        ATTACK_SUCCESSFUL("unit successfully attacked"),
        FOUND_SUCCESSFUL("city successfully founded"),
        CANCEL_SUCCESSFUL("mission successfully canceled"),
        WAKE_SUCCESSFUL("unit successfully woken"),
        DELETE_SUCCESSFUL("unit successfully deleted"),
        BUILD_SUCCESSFUL("successfully built"),
        REMOVE_SUCCESSFUL("successfully removed"),
        PILLAGE_SUCCESSFUL("pillage successful"),
        REPAIR_SUCCESSFUL("successfully repaired");

        private final String message;

        Unit(String message) {
            this.message = message;
        }

        // can get a string and adds it to corresponding location in the response message
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }

    public enum City {
        // general
        CITY_NOT_IN_POSSESS("selected city is not yours"),
        NO_CITY_SELECTED("you haven't selected any city yet"),
        LOCATION_NOT_VALID("the coordinates are not valid"),
        // citizen assigning/freeing
        NO_FREE_CITIZEN("the city has no free citizens"),
        TILE_NOT_IN_TERRITORY("selected tile is not inside this city"),
        TILE_ALREADY_FULL("this tile already has a citizen working on it"),
        TILE_IS_EMPTY("this tile has no citizen working on it"),
        ASSIGNMENT_SUCCESSFUL("a citizen was successfully assigned to this tile"),
        FREEING_SUCCESSFUL("the citizen is now unemployed"),
        // purchase units
        CAPITAL_IS_FULL("capital tile is currently fulled"),
        NOT_ENOUGH_GOLD("not enough gold to buy the unit"),
        UNIT_BUY_SUCCESSFUL("unit purchased successfully"),
        // purchase tile
        TILE_ALREADY_BOUGHT("tile is already bought"),
        CANT_BUY_TILE("this tile is not available to buy"),
        TILE_TOO_FAR("this tile is not near the selected city"),
        TILE_BUY_SUCCESSFUL("tile purchased successfully"),
        // attack
        CITY_TIRED("the city has already attacked this turn"),
        TILE_OUT_OF_RANGE("the given tile is too far to be attacked by the city"),
        TILE_EMPTY("no troop exists in the selected tile"),
        OWN_TARGET("selected target is not an enemy"),
        ATTACK_SUCCESSFUL("city attacked successfully"),
        // delete
        DELETE_SUCCESSFUL("city deleted successfully");
        private final String message;

        City(String message) {
            this.message = message;
        }

        // can get a string and adds it to corresponding location in the response message
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }

    public enum Map {
        //SHOW
        INVALID_POSITION("invalid position"),
        INVALID_CITY_NAME("invalid city name"),

        //SUCCESS
        SHOW_SUCCESSFUL("successfully shown map"),
        MOVE_SUCCESSFUL("successfully moved map");

        private final String message;

        Map(String message) {
            this.message = message;
        }

        // can get a string and adds it to corresponding location in the response message
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }

    public enum Select {
        //UNIT
        INVALID_POSITION("invalid position"),
        MILITARY_UNIT_ALREADY_PRESENT("military unit is already present in the position"),
        NON_MILITARY_UNIT_ALREADY_PRESENT("non-military unit is already present in the position"),
        //CITY
        INVALID_CITY_NAME("invalid city name"),
        CITY_ALREADY_EXISTS("city already exists in the position"),

        //SUCCESS
        UNIT_SELECTION_SUCCESSFUL("unit successfully selected"),
        CITY_SELECTION_SUCCESSFUL("city successfully selected");

        private final String message;

        Select(String message) {
            this.message = message;
        }

        // can get a string and adds it to corresponding location in the response message
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }

    public enum Technology {
        TECH_ALREADY_DONE("you have already researched this technology"),
        TECH_NOT_YET_READY("you don't have the prerequisites for this technology"),
        TECH_RESEARCHED("technology is being researched"),
        TECH_INVALID("invalid technology");

        private final String message;

        Technology(String message) {
            this.message = message;
        }

        // can get a string and adds it to corresponding location in the response message
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }

    public enum Info {
        //GENERAL(FOR ALL)  needed??
        INVALID_ID("the id given is out of bounds"),
        ACCEPT("accept");

        private final String message;

        Info(String message) {
            this.message = message;
        }

        // can get a string and adds it to corresponding location in the response message
        public String getString(String... dynamicSubstring) {
            String messageText = this.message;
            if (dynamicSubstring.length == 0 || !messageText.contains("$")) return this.message;
            return messageText.replaceFirst("\\$", dynamicSubstring[0]);
        }
    }
}