package Domain.characters.monsters;

import Domain.Coordinates;
import Domain.Level;
import Domain.characters.Direction;

import java.util.Random;

public class Ogre extends Monster {
    private String type = "Ogre";

    private Direction currentDirection;
    private final Direction[] directions = new Direction[]{Direction.UP, Direction.DOWN,
                                                           Direction.LEFT, Direction.RIGHT};
    private boolean isResting;

    public Ogre(Level level) {
        super(PropertyLevel.VERY_HIGH.getLevel(), PropertyLevel.LOW.getLevel(), PropertyLevel.VERY_HIGH.getLevel(), level);
        this.hostility = PropertyLevel.MEDIUM.getLevel();
        this.changeDirection();
        this.isResting = false;
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public String getSymbol() {
        return "O";
    }

    @Override
    public int getColorCode() {
        return 6; // желтый
    }

    /**
     * Ходит по комнате на две клетки.
     */
    @Override
    protected void moveByPattern() {


        Coordinates newCoordinates = new Coordinates(this.getCoordinates());

        for (int i = 0; i < 4; i++) {
            /* делаем два шага */
            newCoordinates.setX(newCoordinates.getX() + currentDirection.getCoordinatesShift().getX());
            newCoordinates.setX(newCoordinates.getX() + currentDirection.getCoordinatesShift().getX());

            newCoordinates.setY(newCoordinates.getY() + currentDirection.getCoordinatesShift().getY());
            newCoordinates.setY(newCoordinates.getY() + currentDirection.getCoordinatesShift().getY());

            if (newCoordinates.isFree(this.level.getField())) {
                this.setCoordinates(newCoordinates);
                break;
            } else {
                this.changeDirection();
            }
        }

        changeDirection();

    }

    private void changeDirection() {

        Random random = new Random();
        this.currentDirection = this.directions[random.nextInt(this.directions.length)];

    }

    @Override
    public String attack() {

        if (!this.isResting) {

            double damage = this.hit(this.level.getPlayer());

            if (!this.level.getPlayer().isAlive()) {
                return "You have been killed by the Ogre";
            }

            this.isResting = true;
            return "Ogre hits you! [-" + String.format("%.1f", damage) + " HP]";
        }
        this.isResting = false;
        return "Ogre wanted to attack you, but was too busy resting";
    }

}
