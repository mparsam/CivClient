package Model.Units;

import Model.Player;
import Model.Tile;
import enums.Types.CombatType;
import enums.Types.ImprovementType;
import enums.Types.OrderType;
import enums.Types.UnitType;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class Unit implements Serializable {
    public static final long serialVersionUID = 80L;

    private Tile tile;
    private Player owner;
    private final int health; // this is for reference
    private int cost;
    private int movement;
    private int MP; // needs to be refilled at the end of each turn
    private double HP; // this is what changes in attacks
    private int sightRange;
    private UnitType unitType;
    private Tile destination;
    private int remainingCost;
    private OrderType orderType;
    private CombatType combatType;

    public Unit(Tile tile, Player owner, UnitType unitType) {
        this.tile = tile;
        this.owner = owner;
        this.unitType = unitType;
        this.destination = this.tile;
        this.health = 10;
        this.cost = unitType.cost;
        this.movement = unitType.movement;
        this.HP = this.health;
        this.MP = this.movement;
        this.remainingCost = this.cost;
        this.combatType = unitType.combatType;
        this.sightRange = 2;
        if (this.combatType.equals(CombatType.SIEGE) || unitType == UnitType.PANZER) this.sightRange = 1;
        this.orderType = OrderType.AWAKE;
        if (tile != null) tile.putUnit(this);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getHealth() {
        return health;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public int getSightRange() {
        return sightRange;
    }

    public void setSightRange(int sightRange) {
        this.sightRange = sightRange;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public CombatType getCombatType() {
        return combatType;
    }

    public void setCombatType(CombatType combatType) {
        this.combatType = combatType;
    }

    public Tile getDestination() {
        return destination;
    }

    public void setDestination(Tile destination) {
        this.destination = destination;
    }

    public int getRemainingCost() {
        return remainingCost;
    }

    public void setRemainingCost(int remainingTurn) {
        this.remainingCost = remainingTurn;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public int getRow() {
        return tile.getRow();
    }

    public int getColumn() {
        return tile.getColumn();
    }


    public void destroy() {
        this.tile.takeUnit(this);
        this.owner.removeUnit(this);
    }

    public ImageView getUnitImage() {
        if (unitType == null) return null;
        return unitType.getImage();
    }

    public ArrayList<ImprovementType> getPossibleImprovements() {
        ArrayList<ImprovementType> improvementTypes = new ArrayList<>();
        if (unitType != UnitType.WORKER) return improvementTypes;
        for (ImprovementType improvementType : ImprovementType.values()) {
            if (owner.getTechnologyByType(improvementType.neededTech) != null) {
                if (improvementType.canBeOn.contains(tile.getTerrainFeature())) {
                    improvementTypes.add(improvementType);
                }
            }
        }
        return improvementTypes;
    }
}
