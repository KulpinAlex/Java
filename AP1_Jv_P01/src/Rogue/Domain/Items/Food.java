package Domain.Items;

import Domain.Coordinates;

public class Food extends Item {
    private int health;

    public Food() {
        super();
        this.health = (int)(Math.random() * 15 + 1);
        this.name = "Food";
    }

    public Food(int type, int symbol, Coordinates coordinate) {
        super(type, symbol, coordinate);
        this.health = (int)(Math.random() * 15 + 1);
        this.name = "Food";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getStrength() {
        return 0;
    }

    @Override
    public int getAgility() {
        return 0;
    }
}