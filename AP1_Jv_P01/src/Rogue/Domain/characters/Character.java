package Domain.characters;

import Constants.GameConstants;
import Domain.Coordinates;
import Domain.level_essences.Tile;

import java.util.Random;

/**
 * Базовый класс всех сущностей в игре.
 * Определяет общие базовые характеристики для всех персонажей,
 * методы для доступа к харктеритикам.
 */
public abstract class Character {

    protected double health;
    protected int agility;
    protected int strength;
    protected Coordinates coordinates;

    protected Character(double health, int agility, int strength) {
        this.health = health;
        this.agility = agility;
        this.strength = strength;
        coordinates = new Coordinates(0,0);
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health) {
            this.health = health;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        if (agility > 0) {
            this.agility = agility;
        }
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        if (strength > 0) {
            this.strength = strength;
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates.setX(coordinates.getX());
        this.coordinates.setY(coordinates.getY());
    }

    public void setCoordinates(int x, int y) {
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }

    protected boolean moveByDirection(Direction direction, Tile[][] field) {
        Coordinates newCoordinates = new Coordinates(this.coordinates);
        newCoordinates.setX(this.coordinates.getX() + direction.getCoordinatesShift().getX());
        newCoordinates.setY(this.coordinates.getY() + direction.getCoordinatesShift().getY());

        if (newCoordinates.isFree(field)) {
            this.setCoordinates(newCoordinates);
            return true;
        }
        return false;
    }

    protected boolean isHitSuccessful(Character target) {
        Random random = new Random();
        double chance = this.hitChance(target.getAgility());
        return (random.nextDouble(this.getAgility() * GameConstants.INITIAL_HIT_CHANCE) > chance);
    }

    private double hitChance(int targetAgility) {
        return (targetAgility * GameConstants.AGILITY_FACTOR);
    }

    private double calculateDamage() {
        return ((double) this.getStrength() * GameConstants.STRENGTH_FACTOR);
    }

    protected double hit(Character target) {
        double damage = this.calculateDamage();
        target.setHealth(target.getHealth() - damage);
        return damage;
    }

}


