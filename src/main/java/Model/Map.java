package Model;

import enums.Types.TerrainType;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable {
    public static final long serialVersionUID = 65L;
    private static final int INF = 9999;
    private final int width;
    private final int height;
    private Tile[][] tiles;
    private final ArrayList<City> cities = new ArrayList<>();

    public Map(int height, int width) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    // gets a map and deep copies it, a copy with different reference is created
    public Map(Map map) {
        this.width = map.width;
        this.height = map.height;
        tiles = new Tile[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                this.tiles[row][column] = new Tile(map.getTile(row, column));
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public ArrayList<City> getCities() {
        return cities;
    }


    public City getCityByName(String name) {
        for (City city : cities) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    public void setTile(int row, int column, Tile tile) {
        tiles[row][column] = tile;
    }

    public Tile getTile(int row, int column) {
        if (row < 0 || row >= height) return null;
        if (column < 0 || column >= width) return null;
        return tiles[row][column];
    }

    public ArrayList<Tile> getNeighbouringTiles(int row, int column) {
        ArrayList<Tile> neighbours = new ArrayList<>();
        if (row > 0) {
            neighbours.add(tiles[row - 1][column]);
        }
        if (row < height - 1) {
            neighbours.add(tiles[row + 1][column]);
        }
        if (column > 0) {
            neighbours.add(tiles[row][column - 1]);
        }
        if (column < width - 1) {
            neighbours.add(tiles[row][column + 1]);
        }
        if (column % 2 == 0) {
            if (column > 0 && row < height - 1) {
                neighbours.add(tiles[row + 1][column - 1]);
            }
            if (column < width - 1 && row < height - 1) {
                neighbours.add(tiles[row + 1][column + 1]);
            }
        } else {
            if (column > 0 && row > 0) {
                neighbours.add(tiles[row - 1][column - 1]);
            }
            if (column < width - 1 && row > 0) {
                neighbours.add(tiles[row - 1][column + 1]);
            }
        }
        return neighbours;
    }

    public int getBasicDistance(Tile start, Tile finish) {
        // uses Dijkstra
        int[][] distance = new int[height][width];
        boolean[][] marked = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                distance[row][column] = INF + INF;
                marked[row][column] = false;
            }
        }
        distance[start.getRow()][start.getColumn()] = 0;
        for (int t = 0; t < width * height; t++) {
            Tile tile1 = null;
            int minDistance = INF + INF;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (!marked[i][j] && distance[i][j] <= minDistance) {
                        minDistance = distance[i][j];
                        tile1 = this.getTile(i, j);
                    }
                }
            }
            ArrayList<Tile> neighbours = this.getNeighbouringTiles(tile1.getRow(), tile1.getColumn()); // won't cause RT error
            for (Tile tile2 : neighbours) {
                if (minDistance + tile2.getMP(tile1) < distance[tile2.getRow()][tile2.getColumn()]) {
                    distance[tile2.getRow()][tile2.getColumn()] = minDistance + 1;
                }
            }
            marked[tile1.getRow()][tile1.getColumn()] = true;
        }
        return distance[finish.getRow()][finish.getColumn()];
    }

    public int getDistanceTo(Tile start, Tile finish) {
        // uses Dijkstra
        int[][] distance = new int[height][width];
        boolean[][] marked = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                distance[row][column] = INF + INF;
                marked[row][column] = false;
            }
        }
        distance[start.getRow()][start.getColumn()] = 0;
        for (int t = 0; t < width * height; t++) {
            Tile tile1 = null;
            int minDistance = INF + INF;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (!marked[i][j] && distance[i][j] <= minDistance) {
                        minDistance = distance[i][j];
                        tile1 = this.getTile(i, j);
                    }
                }
            }
            ArrayList<Tile> neighbours = this.getNeighbouringTiles(tile1.getRow(), tile1.getColumn()); // won't cause RT error
            for (Tile tile2 : neighbours) {
                if (minDistance + tile2.getMP(tile1) < distance[tile2.getRow()][tile2.getColumn()]) {
                    distance[tile2.getRow()][tile2.getColumn()] = minDistance + tile2.getMP(tile1);
                }
            }
            marked[tile1.getRow()][tile1.getColumn()] = true;
        }
        return distance[finish.getRow()][finish.getColumn()];
    }

    public Tile getNextMoveTo(Tile start, Tile finish) {
        if (start.equals(finish)) {
            return start;
        }
        // uses Dijkstra
        int[][] distance = new int[height][width];
        boolean[][] marked = new boolean[height][width];
        Tile[][] parent = new Tile[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                distance[row][column] = INF + INF;
                marked[row][column] = false;
            }
        }
        distance[start.getRow()][start.getColumn()] = 0;
        for (int t = 0; t < width * height; t++) {
            Tile tile1 = null;
            int minDistance = INF + INF;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (!marked[i][j] && distance[i][j] <= minDistance) {
                        minDistance = distance[i][j];
                        tile1 = this.getTile(i, j);
                    }
                }
            }
            ArrayList<Tile> neighbours = this.getNeighbouringTiles(tile1.getRow(), tile1.getColumn()); // won't cause RT error
            for (Tile tile2 : neighbours) {
                if (minDistance + tile2.getMP(tile1) < distance[tile2.getRow()][tile2.getColumn()]) {
                    distance[tile2.getRow()][tile2.getColumn()] = minDistance + tile2.getMP(tile1);
                    parent[tile2.getRow()][tile2.getColumn()] = tile1;
                }
            }
            marked[tile1.getRow()][tile1.getColumn()] = true;
        }
        Tile currentTile = finish;
        while (true) {
            if (parent[currentTile.getRow()][currentTile.getColumn()] == null) {
                return currentTile;
            }
            if (parent[currentTile.getRow()][currentTile.getColumn()].equals(start)) {
                return currentTile;
            }
            currentTile = parent[currentTile.getRow()][currentTile.getColumn()];
        }
    }


    public boolean isConnected() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (tiles[row][column].getTerrainType() != TerrainType.OCEAN) {
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if (tiles[i][j].getTerrainType() == TerrainType.OCEAN
                                    || tiles[i][j].getTerrainType() == TerrainType.MOUNTAIN) continue;
                            if (this.getDistanceTo(tiles[row][column], tiles[i][j]) >= INF) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
