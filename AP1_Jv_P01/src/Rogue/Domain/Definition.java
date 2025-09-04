package Domain;

import com.google.gson.annotations.SerializedName;

import Constants.GameConstants;

public abstract class Definition {
    private int type;
    private int symbol;
    private Coordinates coordinate;
    private int status;
    @SerializedName("base_health")
    private int health;

    public Definition() {
        type = GameConstants.UNINITIALIZED;
        symbol = GameConstants.UNINITIALIZED;
        status = GameConstants.ON_FIELD;
        health = 0;
    }

    public Definition(int type, int symbol, Coordinates coordinate) {
        this.type = type;
        this.symbol = symbol;
        this.coordinate = new Coordinates(coordinate.getX(), coordinate.getY());
        this.status = GameConstants.ON_FIELD;
        this.health = 0;
    }

    public abstract String toString();

    public abstract int getStrength();

    public abstract int getAgility();

    public abstract int getHealth();

    public abstract void setHealth(int health);

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}