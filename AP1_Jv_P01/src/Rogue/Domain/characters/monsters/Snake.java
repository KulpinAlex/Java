package Domain.characters.monsters;

import java.util.Random;

import Domain.characters.Direction;
import Domain.Level;

public class Snake extends Monster {
    private String type = "Snake";
    private Direction currentDirection;
    private final Direction[] directions = new Direction[]{Direction.DIAGONAL_UP_LEFT, Direction.DIAGONAL_DOWN_LEFT,
                                               Direction.DIAGONAL_UP_RIGHT, Direction.DIAGONAL_DOWN_RIGHT};

    public Snake(Level level) {
        super(PropertyLevel.LOW.getLevel(), PropertyLevel.VERY_HIGH.getLevel(), PropertyLevel.MEDIUM.getLevel(), level);
        this.hostility = PropertyLevel.HIGH.getLevel();
        this.changeDirection();
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public String getSymbol() {
        return "S";
    }

    @Override
    public int getColorCode() {
        return 0; // белый
    }

    /**
     * Ходит по карте по диагонали, постоянно меняя сторону.
     */
    @Override
    protected void moveByPattern() {

        for (int i = 0; i < 4; i++) {

            if (moveByDirection(this.currentDirection, this.level.getField())) {
                break;
            } else {
                this.changeDirection();
            }
        }

        this.changeDirection();
    }

    private void changeDirection() {

        Random random = new Random();
        this.currentDirection = this.directions[random.nextInt(this.directions.length)];

    }

    @Override
    public String attack() {

        if (this.isHitSuccessful(this.level.getPlayer())) {

            Random random = new Random();

            /* у каждой успешной атаки есть вероятность «усыпить» игрока на один ход */
            this.level.getPlayer().setSleeping(random.nextBoolean());

            double damage = this.hit(this.level.getPlayer());

            if (!this.level.getPlayer().isAlive()) {
                return "You have been killed by the Snake";
            }

            return "Snake swings and scores an excellent hit on you! [-" + String.format("%.1f", damage) + " HP]";
        }

        return "Snake swings and misses you";
    }
}
