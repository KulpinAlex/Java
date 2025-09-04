package Domain.Items;

import Constants.GameConstants;

public class Scroll extends Item {
    private int agility;
    private int strength;
    private int health;

    public Scroll() {
        this.name = null;
        this.agility = 0;
        this.strength = 0;
        this.health = 0;
    }

    @Override
    public String toString() {
        return getTypeName();
    }

    public Scroll(int scrollType) {
        super();
        setType(GameConstants.SCROLL);
        switch (scrollType) {
            case GameConstants.STRENGTH_SCROLL:
                this.name = "Strength Scroll";
                this.agility = 0;
                this.strength = 5;
                this.health = 0;
                break;
            case GameConstants.CURSED_STRENGTH_SCROLL:
                this.name = "Cursed strength scroll";
                this.agility = 0;
                this.strength = -5;
                this.health = 0;
                break;
            case GameConstants.AGILITY_SCROLL:
                this.name = "Agility Scroll";
                this.agility = 5;
                this.strength = 0;
                this.health = 0;
                break;
            case GameConstants.CURSED_AGILITY_SCROLL:
                this.name = "cursed agility scroll";
                this.agility = -5;
                this.strength = 0;
                this.health = 0;
                break;
            case GameConstants.MAX_HEALTH_SCROLL:
                this.name = "Max health Scroll";
                this.agility = 0;
                this.strength = 0;
                this.health = 0;
                break;
            default:
                break;
        }
    }

    @Override
    public int getAgility() {
        return agility;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

}