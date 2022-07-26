package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    public static final long serialVersionUID = 69L;
    private Map map;
    private ArrayList<Player> players;
    private int currentPlayerID;
    int turnCount;

    public Game(Map map, ArrayList<Player> players) {
        this.map = map;
        this.players = players;
        turnCount = 1;
        currentPlayerID = 0;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerID);
    }

    public void nextTurn() {
        currentPlayerID = currentPlayerID + 1;
        if(currentPlayerID == players.size()) {
            currentPlayerID = 0;
            turnCount++;
        }
    }


}
