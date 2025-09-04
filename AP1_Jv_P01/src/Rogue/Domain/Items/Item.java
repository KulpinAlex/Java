package Domain.Items;

import Constants.GameConstants;
import Domain.Definition;
import Domain.Coordinates;

public abstract class Item extends Definition {
    protected String name;
    private Coordinates coordinates; // Добавляем поле для координат

    public Item(int type, int symbol, Coordinates coordinate) {
        super(type, symbol, coordinate != null ? coordinate : new Coordinates(0, 0));
        this.coordinates = coordinate != null ? coordinate : new Coordinates(0, 0);
    }

    public Item() {
        super(-1, '?', new Coordinates(0, 0));
        this.coordinates = new Coordinates(0, 0);
    }

    // Методы для работы с координатами
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new Coordinates(0, 0);
        }
        this.coordinates.setX(coordinates.getX());
        this.coordinates.setY(coordinates.getY());
    }

    public void setCoordinates(int x, int y) {
        if (this.coordinates == null) {
            this.coordinates = new Coordinates(0, 0);
        }
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }

    // Абстрактные методы
    public abstract int getHealth();

    public abstract int getStrength();

    public abstract int getAgility();

    public abstract void setHealth(int health);

    public String getName() {
        return name;
    }

    public String getTypeName() {
      return switch (getType()) {
        case GameConstants.WEAPON -> "Weapon";
        case GameConstants.FOOD -> "Food";
        case GameConstants.ELIXIR -> "Elixir";
        case GameConstants.SCROLL -> "Scroll";
        case GameConstants.TREASURE -> "Treasure";
        default -> "Item";
      };
    }

    public boolean isPlayerClose(int x, int y) {
        return this.coordinates.getX() == x && this.coordinates.getY() == y;
    }
}