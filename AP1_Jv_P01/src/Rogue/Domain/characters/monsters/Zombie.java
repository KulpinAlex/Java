package Domain.characters.monsters;

import Domain.Level;

public class Zombie extends Monster {
    private String type = "Zombie";

    public Zombie(Level level) {
        super(PropertyLevel.HIGH.getLevel(), PropertyLevel.LOW.getLevel(), PropertyLevel.MEDIUM.getLevel(), level);
        this.hostility = PropertyLevel.MEDIUM.getLevel();
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public String getSymbol() {
        return "Z";
    }

    @Override
    public int getColorCode() {
        return 5; // зеленый
    }

    @Override
    protected void moveByPattern() {
    }

}
