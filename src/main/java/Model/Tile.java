package Model;

import Model.Units.Troop;
import Model.Units.Unit;
import enums.Types.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tile implements Serializable {
    public static final long serialVersionUID = 66L;
    private int row, column;
    private Terrain terrain;
    private Resource resource;
    private Ruin ruin;
    private City city;  //can be null
    private Improvement improvement;
    private Unit unit;
    private Troop troop;
    private FogState fogState;
    private Road road; // can be null
    private HashMap<Integer, Integer> isRiver; // Clock-based directions: 0 - 2 - 4 - 6 - 8 - 10
    private boolean hasCitizen;

    public Tile(int row, int column, Terrain terrain, FogState fogState, Ruin ruin) {
        this.row = row;
        this.column = column;
        this.terrain = terrain;
        this.fogState = fogState;
        if (terrain.getResourceType() != null) this.resource = new Resource(terrain.getResourceType());
        this.ruin = ruin;
        this.city = null;
        this.improvement = null;
        this.unit = null;
        this.troop = null;
        this.road = null;
        this.isRiver = new HashMap<>();
        isRiver.put(0, 0);
        isRiver.put(2, 0);
        isRiver.put(4, 0);
        isRiver.put(6, 0);
        isRiver.put(8, 0);
        isRiver.put(10, 0);
    }

    // builds a tile based on a tile
    public Tile(Tile tile) {
        this.row = tile.row;
        this.column = tile.column;
        this.terrain = tile.terrain;
        this.fogState = tile.fogState;
        this.resource = tile.resource;
        this.ruin = tile.ruin;
        this.city = tile.city;
        this.improvement = tile.improvement;
        this.unit = tile.unit;
        this.troop = tile.troop;
        this.road = tile.road;
        this.isRiver = tile.isRiver;
        this.hasCitizen = tile.hasCitizen;
        // todo this should be debugged
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public TerrainType getTerrainType() {
        return terrain.getTerrainType();
    }

    public TerrainFeature getTerrainFeature() {
        return terrain.getTerrainFeature();
    }

    public TerrainFeature getBaseFeature() {
        return terrain.getBaseFeature();
    }

    public ResourceType getResourceType() {
        return terrain.getResourceType();
    }

    public Ruin getRuin() {
        return ruin;
    }

    public void setRuin(Ruin ruin) {
        this.ruin = ruin;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Troop getTroop() {
        return troop;
    }

    public void setTroop(Troop troop) {
        this.troop = troop;
    }

    public FogState getFogState() {
        return fogState;
    }

    public void setFogState(FogState fogState) {
        this.fogState = fogState;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public RoadType getRoadType() {
        if (this.road == null) return null;
        return road.getType();
    }

    public void setRoadType(RoadType roadType) {
        if (this.road == null) return;
        this.road.setType(roadType);
    }

    public HashMap<Integer, Integer> getIsRiver() {
        return isRiver;
    }

    public void setIsRiver(HashMap<Integer, Integer> isRiver) {
        this.isRiver = isRiver;
    }

    public void setRiverInDirection(int direction, int river) {
        isRiver.put(direction, river);
    }

    public int getRiverInDirection(int direction) {
        return isRiver.get(direction);
    }

    public boolean isHasCitizen() {
        return hasCitizen;
    }

    public void setHasCitizen(boolean hasCitizen) {
        this.hasCitizen = hasCitizen;
    }

    public int getDirectionTo(Tile tile) {
        if (tile.column == this.column) {
            if (tile.row == this.row - 1) return 0;
            if (tile.row == this.row + 1) return 6;
        }
        if (this.column % 2 == 0) {
            if (this.row == tile.row && this.column - 1 == tile.column) return 10;
            if (this.row == tile.row && this.column + 1 == tile.column) return 2;
            if (this.row + 1 == tile.row && this.column - 1 == tile.column) return 8;
            if (this.row + 1 == tile.row && this.column + 1 == tile.column) return 4;
        } else {
            if (this.row - 1 == tile.row && this.column - 1 == tile.column) return 10;
            if (this.row - 1 == tile.row && this.column + 1 == tile.column) return 2;
            if (this.row == tile.row && this.column - 1 == tile.column) return 8;
            if (this.row == tile.row && this.column + 1 == tile.column) return 4;
        }
        return -1; // meaning they are not neighbours
    }

    public Tile getTileInDirection(Map map, int direction) {
        if (direction == 0) return map.getTile(this.row - 1, this.column);
        if (direction == 6) return map.getTile(this.row + 1, this.column);
        if (this.column % 2 == 0) {
            if (direction == 10) return map.getTile(this.row, this.column - 1);
            if (direction == 2) return map.getTile(this.row, this.column + 1);
            if (direction == 8) return map.getTile(this.row + 1, this.column - 1);
            if (direction == 4) return map.getTile(this.row + 1, this.column + 1);
        } else {
            if (direction == 10) return map.getTile(this.row - 1, this.column - 1);
            if (direction == 2) return map.getTile(this.row - 1, this.column + 1);
            if (direction == 8) return map.getTile(this.row, this.column - 1);
            if (direction == 4) return map.getTile(this.row, this.column + 1);
        }
        return null;
    }

    public ArrayList<Tile> getNeighbouringTiles(Map map) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int d = 0; d < 12; d += 2) {
            if (this.getTileInDirection(map, d) != null) {
                tiles.add(this.getTileInDirection(map, d));
            }
        }
        return tiles;
    }

    // the next 3 methods are for both troops and normal units
    public boolean canFit(Unit unit) {
        if (unit instanceof Troop) {
            return (this.troop == null);
        } else {
            if (this.unit != null) return false;
            return this.troop == null || this.troop.getOwner() == unit.getOwner();
        }
    }

    public void putUnit(Unit unit) {
        if (unit instanceof Troop) {
            this.troop = (Troop) unit;
        } else {
            this.unit = unit;
        }
    }

    public void takeUnit(Unit unit) {
        if (unit instanceof Troop) {
            this.troop = null;
        } else {
            this.unit = null;
        }
    }

    public int getMP(Tile incomingTile) {
        int mp = 0;
        mp += terrain.getMP();
        int direction = incomingTile.getDirectionTo(this);
        if (direction == -1) return 9999;
        mp += incomingTile.getIsRiver().get(direction);
        if (this.getRoadType() == RoadType.ROAD && incomingTile.getRoadType() == RoadType.ROAD) {
            if (this.getRoad().getRemainingTurns() <= 0) {
                mp -= 1;
            }
        }
        if (this.getRoadType() == RoadType.RAILROAD && incomingTile.getRoadType() == RoadType.RAILROAD) {
            if (this.getRoad().getRemainingTurns() <= 0) {
                mp -= 2;
            }
        }
        return mp;
    }

    public int getFood() {
        // checks food income based on Terrain object and the improvements and resource and building
        if (!hasCitizen) return 0;
        return terrain.getFood() +
                (isResourceGettable() ? getResourceType().food : 0) +
                (isImprovementGettable() ? getImprovement().getAddedFood() : 0);
    }

    public int getGold() {
        // TODO: anything else?
        // + : terrain, terrain Feature, resource  improved, rivers
        if (!hasCitizen) return 0;
        return terrain.getGold() +
                (isResourceGettable() ? getResourceType().gold : 0) +
                (isImprovementGettable() ? getImprovement().getAddedGold() : 0) +
                (int) isRiver.values().stream().filter(i -> i.equals(1)).count() -
                ((road == null) ? 0 : road.getType().getMaintenanceCost());
    }

    public int getProduction() {
        // checks production income based on Terrain object and the improvements and resource
        if (!hasCitizen) return 0;
        return terrain.getProduction() +
                (isResourceGettable() ? getResourceType().production : 0) +
                (isImprovementGettable() ? getImprovement().getAddedProduction() : 0);

    }

    private boolean isResourceGettable() {
        List<TechnologyType> ownerTechs = city.getOwner().getTechnologies().stream().map(Technology::getTechnologyType).toList();
        if (!hasCitizen) return false;
        if (getResourceType().equals(ResourceType.NULL)) return false;
        if (getImprovement() == null) return false;
        if (!getResourceType().getNeededImprovementType().equals(getImprovement().getImprovementType())) return false;
        if (getResourceType().isStrategic()) {
            if (getResourceType().equals(ResourceType.COAL) && !ownerTechs.contains(TechnologyType.SCIENTIFIC_THEORY))
                return false;
            if (getResourceType().equals(ResourceType.HORSES) && !ownerTechs.contains(TechnologyType.ANIMAL_HUSBANDRY))
                return false;
            return !getResourceType().equals(ResourceType.IRON) || ownerTechs.contains(TechnologyType.MINING);
        }
        return true;
    }

    private boolean isImprovementGettable() {
        if (getImprovement() == null) return false;
        return getResourceType().getNeededImprovementType().equals(getImprovement().getImprovementType()) &&
                getImprovement().getRemainingTurns() == 0;
    }

    private boolean isCityCapital() {
        if(city == null) return false;
        return (row == city.getCapitalTile().row && column == city.getCapitalTile().column);
    }

    public Pane getTileImage(){
        Pane imagePane = new Pane();
        if(fogState == FogState.UNKNOWN){
            ImageView imageView = new ImageView(Tile.class.getClassLoader().getResource("images/Clouds.png").toExternalForm());
            imageView.setFitWidth(140);
            imageView.setFitHeight(120);
            imagePane.getChildren().add(imageView);
            return imagePane;
        }
        imagePane.getChildren().add(terrain.getTerrainImage());
        ImageView featureImage = terrain.getFeatureImage();
        featureImage.setTranslateX(35);
        featureImage.setTranslateY(25);
        featureImage.setFitWidth(70);
        featureImage.setFitHeight(70);
        imagePane.getChildren().add(featureImage);
        ImageView resourceImage = terrain.getResourceImage();
        resourceImage.setTranslateX(50);
        resourceImage.setTranslateY(75);
        resourceImage.setFitWidth(40);
        resourceImage.setFitHeight(40);
        imagePane.getChildren().add(resourceImage);
        if(isCityCapital()) {
            System.err.println("HI!");
            ImageView imageView = new ImageView(Tile.class.getClassLoader().getResource("images/City.png").toExternalForm());
            imageView.setTranslateX(30);
            imageView.setTranslateY(20);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imagePane.getChildren().add(imageView);
        }
        if(fogState == FogState.VISIBLE &&  unit != null) {
            ImageView unitImage = unit.getUnitImage();
            unitImage.setTranslateX(20);
            unitImage.setTranslateY(40);
            unitImage.setFitWidth(50);
            unitImage.setFitHeight(50);
            imagePane.getChildren().add(unitImage);
        }
        if(fogState == FogState.VISIBLE && troop != null) {
            ImageView unitImage = troop.getUnitImage();
            unitImage.setTranslateX(80);
            unitImage.setTranslateY(40);
            unitImage.setFitWidth(50);
            unitImage.setFitHeight(50);
            imagePane.getChildren().add(unitImage);
        }
        return imagePane;
    }

}
