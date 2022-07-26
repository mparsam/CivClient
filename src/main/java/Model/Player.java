package Model;


import Model.Units.Troop;
import Model.Units.Unit;
import enums.Color;
import enums.Types.BuildingType;
import enums.Types.TechnologyType;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    public static final long serialVersionUID = 70L;
    private final int id;
    private static int count = 0;
    private final String username;
    private final String name;
    private Map map;
    private final ArrayList<Unit> units; // Only complete units are here
    private final ArrayList<Building> buildings;
    private final ArrayList<Technology> technologies;
    private Technology technologyInProgress;
    private final ArrayList<Technology> incompleteTechnologies;
    private final ArrayList<City> cities;
    private City capital;
    private int gold;
    private int score;
    private int scienceIncome;
    private int goldIncome;
    private int XP;
    private int happiness;
    private int population;
    private final ArrayList<Player> inWarPlayers;
    private final ArrayList<String> notifications;
    private int cameraRow;
    private int cameraColumn;
    private final Color backgroundColor;
    private final Color color;

    public Player(User user, int cameraRow, int cameraColumn) {
        this.username = user.getUsername();
        this.name = user.getNickname();
        this.cameraRow = cameraRow;
        this.cameraColumn = cameraColumn;
        this.units = new ArrayList<>();
        this.buildings = new ArrayList<>(); // may need to change this
        this.technologies = new ArrayList<>();
        this.cities = new ArrayList<>(); // and this
        this.inWarPlayers = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.incompleteTechnologies = new ArrayList<>();
        this.id = count;
        this.backgroundColor = Color.values()[this.id];
        this.color = Color.values()[this.id + 8];
        this.happiness = 100;
        count++;
        this.score = 0;
    }

    public int getScienceIncome() {
        return scienceIncome;
    }

    public void setScienceIncome(int scienceIncome) {
        this.scienceIncome = scienceIncome;
    }

    public int getGoldIncome() {
        return goldIncome;
    }

    public void setGoldIncome(int goldIncome) {
        this.goldIncome = goldIncome;
    }

    public int getXP() {
        return XP;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }

    public Technology getTechnologyInProgress() {
        return technologyInProgress;
    }

    public void setTechnologyInProgress(Technology technologyInProgress) {
        this.technologyInProgress = technologyInProgress;
    }

    public ArrayList<Technology> getIncompleteTechnologies() {
        return incompleteTechnologies;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getRowP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    //updates when ever gotten
    public int getPopulation() {
        population = 0;
        for (City city : cities) {
            population += city.getPopulation();
        }
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public ArrayList<Player> getInWarPlayers() {
        return inWarPlayers;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public int getCameraRow() {
        return cameraRow;
    }

    public int getCameraColumn() {
        return cameraColumn;
    }

    public void setCameraRow(int cameraRow) {
        this.cameraRow = cameraRow;
    }

    public void setCameraColumn(int cameraColumn) {
        this.cameraColumn = cameraColumn;
    }

    public void setCamera(int cameraRow, int cameraColumn) {
        this.cameraRow = cameraRow;
        this.cameraColumn = cameraColumn;
    }

    public void setCamera(Tile tile) {
        this.setCamera(tile.getRow(), tile.getColumn());
    }

    public void addUnit(Unit unit) {
        unit.setRemainingCost(0);
        this.units.add(unit);
        this.score += 1;
    }

    public void removeUnit(Unit unit) {
        this.units.remove(unit);
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
        this.score += 2;
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

    public void addTechnology(Technology technology) {
        this.technologies.add(technology);
        this.score += 3;
    }

    public void removeTechnology(Technology technology) {
        this.technologies.remove(technology);
    }

    public Technology getTechnologyByName(String name) {
        for (Technology technology : technologies) {
            if (technology.getName().equals(name)) {
                return technology;
            }
        }
        return null;
    }

    public Technology getTechnologyByType(TechnologyType technologyType) {
        for (Technology technology : technologies) {
            if (technology.getTechnologyType().equals(technologyType)) {
                return technology;
            }
        }
        return null;
    }

    public void addIncompleteTechnology(Technology technology) {
        this.incompleteTechnologies.add(technology);
    }

    public void removeIncompleteTechnology(Technology technology) {
        this.incompleteTechnologies.remove(technology);
    }

    public Technology getIncompleteTechnologyByName(String name) {
        for (Technology technology : incompleteTechnologies) {
            if (technology.getName().equals(name)) {
                return technology;
            }
        }
        return null;
    }

    public Technology getIncompleteTechnologyByType(TechnologyType technologyType) {
        for (Technology technology : incompleteTechnologies) {
            if (technology.getTechnologyType().equals(technologyType)) {
                return technology;
            }
        }
        return null;
    }

    public void addCity(City city) {
        if(this.cities.isEmpty()){
            this.capital = city;
        }
        this.cities.add(city);
        this.score += 10;
    }

    public void removeCity(City city) {
        this.cities.remove(city);
    }

    public City getCityByName(String name) {
        for (City city : cities) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
    }

    public City getCityByXY(int x, int y) {
        for (City city : cities) {
            if (city.getCapitalTile().getRow() == x && city.getCapitalTile().getColumn() == y) {
                return city;
            }
        }
        return null;
    }

    public City getCapital() {
        if(capital == null && !cities.isEmpty()) {
            capital = cities.get(0);
        }
        return capital;
    }

    public void addInWarPlayer(Player player) {
        this.inWarPlayers.add(player);
    }

    public void removeInWarPlayer(Player player) {
        this.inWarPlayers.remove(player);
    }

    public Player getInWarPlayerByName(String name) {
        for (Player inWarPlayer : inWarPlayers) {
            if (inWarPlayer.getName().equals(name)) {
                return inWarPlayer;
            }
        }
        return null;
    }

    public void addNotification(String notification) {
        notifications.add(notification);
    }

    public int getScore() {
        return score;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public Color getColor() {
        return this.color;
    }

    public void updateGoldByIncome() {
        gold += goldIncome;
        // only happens if income in negative
        if (gold < 0) {
            scienceIncome += goldIncome;
            if (scienceIncome < 0) setScienceIncome(0);
            setGold(0);
        }
    }

    public int getProduction() {
        return this.getCities().stream().mapToInt(City::getProductionIncome).sum();
    }

    public int getTerritoryCount() {
        return cities.stream().mapToInt(c -> c.getTerritory().size()).sum();
    }

    public int getTroopCount() {
        return (int) units.stream().filter(u -> (u instanceof Troop)).count();
    }

    public ArrayList<TechnologyType> getPossibleTechs() {
        ArrayList<TechnologyType> techs = new ArrayList<>();
        for (TechnologyType tech : TechnologyType.values()) {
            boolean possible = true;
            for (TechnologyType neededTech : tech.neededTechs) {
                boolean has = false;
                for (Technology technology : technologies) {
                    if(technology.getRemainingCost() <= 0 && technology.getTechnologyType() == neededTech){
                        has = true;
                    }
                }
                if(!has){
                    possible = false;
                }
            }
            if(possible){
                techs.add(tech);
            }
        }
        return techs;
    }

}
