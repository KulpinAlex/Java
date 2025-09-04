package Domain.characters.monsters;

import Domain.Level;

import java.util.List;
import java.util.Random;

public class Mimic extends Monster {

    private String symbol;
    private String type = "Mimic";

    public Mimic(Level level) {
        super(PropertyLevel.HIGH.getLevel(), PropertyLevel.HIGH.getLevel(), PropertyLevel.LOW.getLevel(), level);
        this.hostility = PropertyLevel.LOW.getLevel();
        this.generateSymbol();
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public int getColorCode() {
        return 0; // белый
    }

    @Override
    public void moveByPattern() {}

    private void generateSymbol() {

        List<String> symbols = List.of("W", "F", "S", "E");
        Random random = new Random();

        this.symbol = symbols.get(random.nextInt(symbols.size()));
    }
}
