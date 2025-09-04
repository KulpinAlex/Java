package Domain.Items;

import com.google.gson.annotations.SerializedName;

import Constants.GameConstants;

public class Elixir extends Item {
    private int remainingTurns;
    private int maxTurns;
    private int agility;
    private int strength;
    @SerializedName("elixir_health")
    private int health;

    public Elixir() {
        super();
        this.remainingTurns = 0;
        this.maxTurns = 0;
        this.agility = 0;
        this.strength = 0;
        this.health = 0;
    }

    public Elixir(int elixirType) {
        super();
        setType(GameConstants.ELIXIR);

        switch (elixirType) {
            case GameConstants.HEALTH_ELIXIR:
                this.name = "Health Elixir";
                this.agility = 0;
                this.strength = 0;
                this.health = 10;
                this.maxTurns = this.remainingTurns = 10;
                break;
            case GameConstants.STRENGTH_ELIXIR:
                this.name = "Strength Elixir";
                this.agility = 3;
                this.strength = 7;
                this.health = 0;
                this.maxTurns = this.remainingTurns = 7;
                break;
            case GameConstants.AGILITY_ELIXIR:
                this.name = "Agility Elixir";
                this.agility = 10;
                this.strength = 0;
                this.health = 0;
                this.maxTurns = this.remainingTurns = 5;
                break;
            default:
                break;
        }
    }

    public void decrementTurns() {
        if (remainingTurns > 0) {
            remainingTurns--;
        }
    }

    public boolean isActive() {
        return remainingTurns > 0;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return getTypeName();
    }

    @Override
    public int getAgility() {
        return isActive() ? agility : 0;
    }

    @Override
    public int getStrength() {
        return isActive() ? strength : 0;
    }

    @Override
    public int getHealth() {
        return isActive() ? health : 0;
    }

    public int getBaseStrength() {
        return strength;
    }

    public int getBaseAgility() {
        return agility;
    }

    public int getBaseHealth() {
        return health;
    }
}