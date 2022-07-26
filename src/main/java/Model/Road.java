package Model;

import enums.Types.RoadType;

import java.io.Serializable;

public class Road implements Serializable {
    public static final long serialVersionUID = 74L;
    private RoadType type;
    private int remainingTurns;

    public Road(RoadType roadType) {
        this.type = roadType;
        this.remainingTurns = 3;
    }

    public Road(Road road) {
        this.type = road.type;
        this.remainingTurns = road.remainingTurns;
    }

    public RoadType getType() {
        return type;
    }

    public void setType(RoadType type) {
        this.type = type;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }
}
