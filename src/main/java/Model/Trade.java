package Model;

import java.io.Serializable;

public class Trade implements Serializable {
    private Player player1, player2;
    private int goldGiven, goldTaken;
    private int scienceGiven, scienceTaken;
    private Resource resourceGiven, resourceTaken;

    public Trade(Player player1, Player player2, int goldGiven, int goldTaken, Resource resourceGiven, Resource resourceTaken) {
        this.player1 = player1;
        this.player2 = player2;
        this.goldGiven = goldGiven;
        this.goldTaken = goldTaken;
        this.resourceGiven = resourceGiven;
        this.resourceTaken = resourceTaken;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getGoldGiven() {
        return goldGiven;
    }

    public void setGoldGiven(int goldGiven) {
        this.goldGiven = goldGiven;
    }

    public int getGoldTaken() {
        return goldTaken;
    }

    public void setGoldTaken(int goldTaken) {
        this.goldTaken = goldTaken;
    }

    public Resource getResourceGiven() {
        return resourceGiven;
    }

    public void setResourceGiven(Resource resourceGiven) {
        this.resourceGiven = resourceGiven;
    }

    public Resource getResourceTaken() {
        return resourceTaken;
    }

    public void setResourceTaken(Resource resourceTaken) {
        this.resourceTaken = resourceTaken;
    }

    public int getScienceGiven() {
        return scienceGiven;
    }

    public void setScienceGiven(int scienceGiven) {
        this.scienceGiven = scienceGiven;
    }

    public int getScienceTaken() {
        return scienceTaken;
    }

    public void setScienceTaken(int scienceTaken) {
        this.scienceTaken = scienceTaken;
    }
}
