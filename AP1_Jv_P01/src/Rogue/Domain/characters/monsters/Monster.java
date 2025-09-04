package Domain.characters.monsters;

import Domain.Coordinates;
import Domain.characters.Character;
import Domain.Level;
import Domain.characters.Direction;
import View.Render;

/**
 * Базовый класс для всех монстров.
 * Наследует от Character.
 * Содержит методы определения враждебности и передвижения монстра.
 */
public abstract class Monster extends Character {

    /**
     * Уровень характеристики монстра.
     */
    protected enum PropertyLevel {
        LOW(2),
        MEDIUM(4),
        HIGH(6),
        VERY_HIGH(9);

        private final int level;

        PropertyLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return this.level;
        }
    }

    transient protected Level level;
    protected int hostility;
    protected boolean isVisible;
    private boolean isChasing;
    private boolean isDead;

    public Monster(double health, int agility, int strength, Level level) {
        super(health, agility, strength);
        this.level = level;
        this.isVisible = true;
        this.isChasing = false;
        this.isDead = false;
        this.adjustPropertiesDifficulty();
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public int getHostility() {
        return this.hostility;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public boolean checkIfDead() {
        this.isDead = (this.health <= 0);
        if (this.isDead) {
            this.setVisible(false);
        }
        return this.isDead;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public int giveGold() {
        return this.getStrength() + this.getAgility() + this.getHostility();
    }

    /**
     * Возвращает символ отображения для данного монстра.
     */
    public abstract String getSymbol();

    /**
     * Возвращает код цвета отображения данного монстра
     * из таблицы colors класса Render.
     * 
     * @see Render
     */
    public abstract int getColorCode();

    /**
     * Преследует ли монстр игрока.
     * 
     * @return true если преследует, иначе false
     */
    public boolean isChasing() {
        return this.isChasing;
    }

    public boolean isFirstHitTrue() {
        return false;
    }

    public void setFirstHitTrue() {
    }

    /**
     * Повышает уровень сложности монстра каждые 3 уровня.
     */
    private void adjustPropertiesDifficulty() {

        this.health += (double) this.level.CURRENT_LEVEL / 3;
        this.agility += this.level.CURRENT_LEVEL / 3;
        this.strength += this.level.CURRENT_LEVEL / 3;
    }

    /**
     * Определяет траекторию движения монстра.
     * Если монстр не преследует игрока, каждый движется по своему паттерну,
     * иначе у всех один паттерн - по кратчайшему пути до игрока.
     */
    public void move() {

        if (this.isPlayerNear()) {
            this.setVisible(true);
            this.isChasing = true;
            chasePlayer();
        } else {
            this.isChasing = false;
            this.moveByPattern();
        }
    }

    /**
     * Определяет паттерн движения для каждого монстра.
     */
    protected abstract void moveByPattern();

    /**
     * Метод вызывает преследование игрока.
     */
    private void chasePlayer() {

        Coordinates newCoordinates = new Coordinates(this.coordinates);
        Coordinates playerPosition = this.level.getPlayer().getCoordinates();

        if (newCoordinates.getX() > playerPosition.getX()) {
            newCoordinates.setX(newCoordinates.getX() - 1);
        } else if (this.coordinates.getX() < playerPosition.getX()) {
            newCoordinates.setX(newCoordinates.getX() + 1);
        }
        if (this.coordinates.getY() > playerPosition.getY()) {
            newCoordinates.setY(newCoordinates.getY() - 1);
        } else if (this.coordinates.getY() < playerPosition.getY()) {
            newCoordinates.setY(newCoordinates.getY() + 1);
        }

        if (newCoordinates.isFree(this.level.getField())) {
            this.setCoordinates(newCoordinates);
        }

    }

    /**
     * Определяет, находится ли игрок в радиусе преследования для данного монстра.
     */
    private boolean isPlayerNear() {

        int radius = Math.abs(this.level.getPlayer().getCoordinates().getX() - this.coordinates.getX());
        radius += Math.abs(this.level.getPlayer().getCoordinates().getY() - this.coordinates.getY());

        return (this.hostility >= radius);
    }

    /**
     * Проверят, находится ли монстр в одном шаге от игрока.
     * 
     * @return true если монстр в одном шаге от игрока, иначе false
     */
    public boolean isReadyToAttack() {

        Coordinates playerPosition = this.level.getPlayer().getCoordinates();

        for (Direction direction : Direction.values()) {
            Coordinates newCoordinates = new Coordinates(this.coordinates);
            newCoordinates.setX(newCoordinates.getX() + direction.getCoordinatesShift().getX());
            newCoordinates.setY(newCoordinates.getY() + direction.getCoordinatesShift().getY());

            if (newCoordinates.equals(playerPosition)) {
                return true;
            }
        }
        return false;
    }

    public String attack() {

        if (this.isHitSuccessful(this.level.getPlayer())) {

            double damage = this.hit(this.level.getPlayer());

            if (!this.level.getPlayer().isAlive()) {
                return "You have been killed by the " + this.toString();
            }
            return this.toString() + " attacks you! [-" + String.format("%.1f", damage) + " HP]";
        }

        return this.toString() + " barely misses you";
    }

}
