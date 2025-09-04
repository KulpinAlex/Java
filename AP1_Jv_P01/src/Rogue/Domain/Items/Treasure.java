package Domain.Items;

import Constants.GameConstants;
import java.util.Random;
import Domain.Coordinates;
import Domain.characters.Player;

public class Treasure extends Item {
    private int goldValue;


    public Treasure(int playerLevel) {
        super(GameConstants.TREASURE, GameConstants.TREASURE_SYMBOL, new Coordinates(0, 0));
        this.name = "Gold Coins";
        int step = playerLevel / 3;
        int baseValue = 1 + step;
        this.goldValue = baseValue + new Random().nextInt(2);
    }
    public int getGoldValue() {
        return goldValue;
    }

    public void activate(Player player) {
        player.addGold(this.goldValue);
    }

    @Override
    public String toString() {
        return name + " (Value: " + goldValue + ")";
    }
    @Override
    public int getHealth() {
        return 0;
    }
    @Override
    public int getStrength() {
        return 0;
    }

    @Override
    public int getAgility() {
        return 0;
    }
    @Override
    public void setHealth(int health) {}

}