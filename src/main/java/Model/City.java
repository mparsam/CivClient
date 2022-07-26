package Model;

import Model.Units.Troop;
import Model.Units.Unit;
import enums.Types.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class City implements Serializable {
    public static final long serialVersionUID = 68L;
    private String name;
    private Player owner;
    private Tile capitalTile; // TODO: 4/17/2022 Palace must be handled
    private ArrayList<Tile> territory;
    private ArrayList<Building> buildings;
    private Building buildingInProgress;
    private ArrayList<Building> incompleteBuildings;
    private Unit unitInProgress;
    private ArrayList<Unit> incompleteUnits;
    private int freeCitizens;
    private int foodIncome, population, health, baseStrength;
    private int sightRange;
    private Troop garrisonedTroop;
    private int neededFoodForNewCitizen = 10, storedFoodForNewCitizen = 0;
    private double HP;
    private boolean hasAttacked;

    public City(String name, Player owner, Tile capitalTile, ArrayList<Tile> territory) {
        this.name = name;
        this.owner = owner;
        this.capitalTile = capitalTile;
        this.territory = territory;
        this.freeCitizens = 2;
        this.population = 2;
        this.buildings = new ArrayList<>();
        this.incompleteBuildings = new ArrayList<>();
        this.incompleteUnits = new ArrayList<>();
        this.health = 20;
        this.HP = 20;
        this.baseStrength = 20;
        this.hasAttacked = false;
        this.sightRange = 2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Tile getCapitalTile() {
        return capitalTile;
    }

    public void setCapitalTile(Tile capitalTile) {
        this.capitalTile = capitalTile;
    }

    public ArrayList<Tile> getTerritory() {
        return territory;
    }

    public void setTerritory(ArrayList<Tile> territory) {
        this.territory = territory;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public int getFoodIncome() {
        return foodIncome;
    }

    public void setFoodIncome(int foodIncome) {
        this.foodIncome = foodIncome;
    }

    public int getProductionIncome() { // TODO: 5/10/2022 building effects must applied later
        int production = 0;
        production += territory.stream().mapToInt(Tile::getProduction).sum();
        production += freeCitizens;
        if(hasBuildingByType(BuildingType.WINDMILL) && getCapitalTile().getTerrainType() != TerrainType.HILL){
            production = production * 115 / 100;
        }
        if(hasBuildingByType(BuildingType.FACTORY)){
            production = production * 3 / 2;
        }
        return production;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void addPopulation(int population){
        this.population += population;
        this.freeCitizens += population;
    }

    public void setNeededFoodForNewCitizen(int neededFoodForNewCitizen) {
        this.neededFoodForNewCitizen = neededFoodForNewCitizen;
    }

    public int getStoredFoodForNewCitizen() {
        return storedFoodForNewCitizen;
    }

    public void setStoredFoodForNewCitizen(int storedFoodForNewCitizen) {
        this.storedFoodForNewCitizen = storedFoodForNewCitizen;
    }

    public int getFreeCitizens() {
        return freeCitizens;
    }

    public void setFreeCitizens(int freeCitizens) {
        this.freeCitizens = freeCitizens;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
    }

    public double getStrength(){
        double strength = baseStrength + population;
        if(garrisonedTroop != null) strength += garrisonedTroop.getMeleeStrength();
        if(capitalTile.getTerrainType().equals(TerrainType.HILL)){
            strength *= 1.2;
        }
        return strength;
    }

    public boolean HasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public int getNeededFoodForNewCitizen() {
        return neededFoodForNewCitizen;
    }

    public int getSightRange() {
        return sightRange;
    }

    public void setSightRange(int sightRange) {
        this.sightRange = sightRange;
    }

    public Building getBuildingInProgress() {
        return buildingInProgress;
    }

    public void setBuildingInProgress(Building buildingInProgress) {
        this.buildingInProgress = buildingInProgress;
    }

    public ArrayList<Building> getIncompleteBuildings() {
        return incompleteBuildings;
    }

    public void setIncompleteBuildings(ArrayList<Building> incompleteBuildings) {
        this.incompleteBuildings = incompleteBuildings;
    }

    public Unit getUnitInProgress() {
        return unitInProgress;
    }

    public void setUnitInProgress(Unit unitInProgress) {
        this.unitInProgress = unitInProgress;
    }

    public ArrayList<Unit> getIncompleteUnits() {
        return incompleteUnits;
    }

    public void setIncompleteUnits(ArrayList<Unit> incompleteUnits) {
        this.incompleteUnits = incompleteUnits;
    }

    public Troop getGarrisonedTroop() {
        return garrisonedTroop;
    }

    public void setGarrisonedTroop(Troop garrisonedTroop) {
        this.garrisonedTroop = garrisonedTroop;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public void addTile(Tile tile) {
        this.territory.add(tile);
    }

    public void removeTile(Tile tile) {
        this.territory.remove(tile);
    }

    public Tile getTileByXY(int x, int y) {
        for (Tile tile : territory) {
            if (tile.getRow() == x && tile.getColumn() == y) {
                return tile;
            }
        }
        return null;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
        this.owner.addBuilding(building);
    }

    public void removeBuilding(Building building) {
        this.buildings.remove(building);
    }

    public Building getBuildingByName(String name) {
        for (Building building : buildings) {
            if (building.getName().equals(name)) {
                return building;
            }
        }
        return null;
    }

    public Building getBuildingByType(BuildingType buildingType) {
        for (Building building : buildings) {
            if (building.getBuildingType().equals(buildingType)) {
                return building;
            }
        }
        return null;
    }

    public void addIncompleteBuilding(Building building) {
        if (incompleteBuildings.contains(building))
            System.err.println("OH NO, THIS BUILDING ALREADY EXISTS IN IN PROGRESS BUILDINGS, CANT ADD IT AGAIN");
        this.incompleteBuildings.add(building);
    }

    public void removeIncompleteBuilding(Building building) {
        if (!incompleteBuildings.contains(building))
            System.err.println("CANT REMOVE NONEXISTENT BUILDING FROM IN PROGRESS BUILDINGS");
        this.incompleteBuildings.remove(building);
    }

    public Building getIncompleteBuildingByName(String name) {
        for (Building building : incompleteBuildings) {
            if (building.getName().equals(name)) {
                return building;
            }
        }
        return null;
    }

    public Building getIncompleteBuildingByType(BuildingType buildingType) {
        for (Building building : incompleteBuildings) {
            if (building.getBuildingType().equals(buildingType)) {
                return building;
            }
        }
        return null;
    }

    public void addIncompleteUnit(Unit unit) {
        this.incompleteUnits.add(unit);
    }

    public void removeIncompleteUnit(Unit unit) {
        this.incompleteUnits.remove(unit);
    }

    public Unit getIncompleteUnitByType(UnitType unitType) {
        for (Unit incompleteUnit : incompleteUnits) {
            if (incompleteUnit.getUnitType().equals(unitType)) {
                return incompleteUnit;
            }
        }
        return null;
    }

    private ArrayList<Unit> getUnits() {
        // returns unit in territory (but why??)
        ArrayList<Unit> units = new ArrayList<>();
        for (Tile tile : territory) {
            units.addAll(Arrays.asList(tile.getUnit()));
        }
        return units;
    }

    public int getTilesFoodIncome() {
        int tilesFood = 0;
        for (Tile tile : territory) {
            tilesFood += tile.getFood();
        }
        return tilesFood;
    }

    public boolean hasRiver() {
        for (Tile tile : territory) {
            if (tile.getIsRiver().containsValue(1)) return true;
        }
        return false;
    }

    // adds to stored food for new citizen, if is possible adds new citizen;
    public void updateNewCitizenStoredFood() {
        storedFoodForNewCitizen += foodIncome;
        if (storedFoodForNewCitizen > neededFoodForNewCitizen) {
            storedFoodForNewCitizen -= neededFoodForNewCitizen;
            population++;
            freeCitizens++;
            neededFoodForNewCitizen *= 1.4;
        }
    }

    public ArrayList<ResourceType> getWorkingResources() {
        ArrayList<ResourceType> resourceTypes = new ArrayList<>();
        for (Tile tile : territory) {
            if (tile.isHasCitizen()) resourceTypes.add(tile.getResourceType());
        }
        return resourceTypes;
    }

    public int getGoldIncome() {
        return territory.stream().mapToInt(Tile::getGold).sum();
    }

    public int getScienceIncome() {
        ArrayList<BuildingType> buildingTypes = this.getBuildings().stream().map(Building::getBuildingType).collect(Collectors.toCollection(ArrayList::new));
        double cityScience = this.getPopulation();
        if (buildingTypes.contains(BuildingType.LIBRARY)) cityScience += this.getPopulation() / 2;
        if (buildingTypes.contains(BuildingType.UNIVERSITY)) {
            for (Tile tile : this.getTerritory()) {
                if (tile.getTerrain().getTerrainFeature().equals(TerrainFeature.JUNGLE)) cityScience += 2;
            }
            cityScience *= 1.5;
        }
        if (buildingTypes.contains(BuildingType.PUBLIC_SCHOOL)) cityScience *= 1.5;
        return (int) cityScience;
    }

    public boolean hasBuildingByType(BuildingType buildingType){
        for (Building building : buildings) {
            if(building.getBuildingType().equals(buildingType)){
                return true;
            }
        }
        return false;
    }

    public void destroy(){
        this.getOwner().removeCity(this);
        for (Tile tile : this.getTerritory()) {
            tile.setCity(null);
        }
    }

    public ArrayList<UnitType> getPossibleUnits() {
        ArrayList<UnitType> unitTypes = new ArrayList<>();
        for (UnitType unitType : UnitType.values()) {
            if (unitType.neededTech != null && owner.getTechnologyByType(unitType.neededTech) == null) {
                continue;
            }
            if (unitType.neededResource != null &&
                    owner.getCities().stream().noneMatch(t -> t.getWorkingResources().contains(unitType.neededResource))) {
                continue;
            }
            unitTypes.add(unitType);
        }
        return unitTypes;
    }

    public ArrayList<BuildingType> getPossibleBuildings() {
        ArrayList<BuildingType> buildingTypes = new ArrayList<>();
        for (BuildingType buildingType : BuildingType.values()) {
            boolean possible = true;
            for (Building building : getBuildings()) {
                if (building.getBuildingType() == buildingType)
                    possible = false;
            }
            if(buildingType.technologyType != null && owner.getTechnologyByType(buildingType.technologyType) == null) possible = false;
            if(possible){
                buildingTypes.add(buildingType);
            }
        }
        return buildingTypes;
    }
}
