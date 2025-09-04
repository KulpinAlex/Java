package Domain.Items;

import Constants.GameConstants;

public class Weapon extends Item {
    private int strength;
    private int agility;
    private int health;

    public Weapon(String name, int strength) {
        this.name = name;
        this.strength = strength;
        this.agility = 0;
        this.health = 0;
    }

    public Weapon() {
        this.name = null;
        this.strength = 0;
        this.agility = 0;
        this.health = 0;
    }

    @Override
    public String toString() {
        return getTypeName();
    }

    public Weapon(int weapon_type) {
        super();
        setType(GameConstants.WEAPON);
        this.agility = 0;
        this.health = 0;
        switch (weapon_type) {
            case GameConstants.AXE:
                this.name = "Axe";
                this.strength = 11;
                this.agility = -5;
                break;
            case GameConstants.SCYTHE:
                this.name = "Scythe";
                this.strength = 7;
                this.agility = -1;
                break;
            case GameConstants.BOW:
                this.name = "bow";
                this.strength = 5;
                this.agility = 3;
                break;
            case GameConstants.HAMMER:
                this.name = "Hammer";
                this.strength = 10;
                this.agility = -7;
                break;
            case GameConstants.SPEAR:
                this.name = "Spear";
                this.strength = 18;
                this.agility = -2;
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