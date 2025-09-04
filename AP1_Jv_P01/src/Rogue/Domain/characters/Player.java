package Domain.characters;

import Domain.characters.monsters.Monster;
import Domain.characters.monsters.Vampire;
import Domain.level_essences.Tile;

import java.util.List;

import Domain.Items.*;

/**
 * Класс, описывающий игрока. Наследуется от Character.
 */
public class Player extends Character {

    private String name;
    private final double DEFAULT_MAX_HEALTH = 10;
    private double maxHealth;
    protected int gold;
    private final int color;
    transient private Inventory inventory;
    private Elixir activeElixir;
    private boolean isSleeping;
    private int monstersDefeatedCount;
    private int foodEatenCount;
    private int elixirsDrunkCount;
    private int scrollsReadCount;
    private int successfulHitsCount;
    private int takenHitsCount;
    private int movesCount;

    // Временные модификаторы от эликсиров
    private int tempStrength = 0;
    private int tempAgility = 0;
    private int tempHealth = 0;
    private int weaponStrength = 0;
    private int weaponAgility = 0;

    private int scrollStrength = 0;
    private int scrollAgility = 0;
    private int scrollHealth = 0;

    public Player() {
        super(10, 10, 10); // дефолтные значения
        maxHealth = DEFAULT_MAX_HEALTH;
        this.color = 0;
        this.isSleeping = false;
        this.gold = 0;
        this.monstersDefeatedCount = 0;
        this.foodEatenCount = 0;
        this.elixirsDrunkCount = 0;
        this.scrollsReadCount = 0;
        this.successfulHitsCount = 0;
        this.takenHitsCount = 0;
        this.movesCount = 0;
        this.inventory = new Inventory();

    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getGold() {
        return this.gold;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    public int getDefeatedMonsters() {
        return this.monstersDefeatedCount;
    }

    public String getSymbol() {
        return "@";
    }

    public int getColor() {
        return this.color;
    }

    public boolean isSleeping() {
        return this.isSleeping;
    }

    public void setSleeping(boolean sleep) {
        this.isSleeping = sleep;
    }

    public boolean isAlive() {
        return (this.getHealth() > 0);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoodEatenCount() {
        return this.foodEatenCount;
    }

    public int getElixirsDrunkCount() {
        return this.elixirsDrunkCount;
    }

    public int getScrollsReadCount() {
        return this.scrollsReadCount;
    }

    public int getSuccessfulHitsCount() {
        return this.successfulHitsCount;
    }

    public int getTakenHitsCount() {
        return this.takenHitsCount;
    }

    public int addTakenHitsCount() {
        return this.takenHitsCount++;
    }

    public int getMovesCount() {
        return this.movesCount;
    }

    public void move(Direction direction, Tile[][] field) {
        if (this.moveByDirection(direction, field)) {
            this.movesCount++;
        }
    }

    public String equipWeapon(Weapon weapon) {
        this.weaponStrength = 0;
        this.weaponAgility = 0;

        if (weapon != null) {
            this.weaponStrength = weapon.getStrength();
            this.weaponAgility = weapon.getAgility();
        }
        return weapon.getName() + " Str:" + weapon.getStrength() + ", Agi:" + weapon.getAgility();
    }

    public boolean pickUpItem(Item item) {
        if (item == null)
            return false;

        if (item instanceof Treasure) {
            ((Treasure) item).activate(this);
            return true;
        }

        return inventory.addItem(item);
    }

    public String useElixir(Elixir elixir) {
        if (this.activeElixir != null) {
            // Снимаем эффекты текущего эликсира
            this.tempStrength -= this.activeElixir.getStrength();
            this.tempAgility -= this.activeElixir.getAgility();
            this.tempHealth -= this.activeElixir.getHealth();
        }

        this.activeElixir = elixir;
        // Применяем новые эффекты
        this.tempStrength += elixir.getStrength();
        this.tempAgility += elixir.getAgility();
        this.tempHealth += elixir.getHealth();
        this.elixirsDrunkCount++;
        return elixir.getName() + " Str:" + elixir.getStrength() + ", Agi:" + elixir.getAgility() + ", Heal:" + elixir.getHealth();
    }

    public String useScroll(Scroll scroll) {
        if (scroll == null)
            return "";

        this.scrollStrength = 0;
        this.scrollAgility = 0;
        this.scrollHealth = 0;

        this.scrollStrength = scroll.getStrength();
        this.scrollAgility = scroll.getAgility();
        this.scrollHealth = scroll.getHealth();

        if (scroll.getHealth() > 0) {
            this.maxHealth += scroll.getHealth();
        }

        this.scrollsReadCount++;
        return scroll.getName() + ", Str:" + scroll.getStrength() + ", Agi:" + scroll.getAgility() + ", Heal:" + scroll.getHealth();
    }

    public String eatFood(Food food) {
        if (food == null)
            return "";

        this.heal(food.getHealth());
        this.foodEatenCount++;
        return "Food healed by " + food.getHealth();
    }

    public void endTurn() {
        if (this.activeElixir != null) {
            this.activeElixir.decrementTurns();

            if (!this.activeElixir.isActive()) {
                this.tempStrength -= this.activeElixir.getBaseStrength();
                this.tempAgility -= this.activeElixir.getBaseAgility();
                this.tempHealth -= this.activeElixir.getBaseHealth();
                this.activeElixir = null;
            }
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public int getStrength() {
        return Math.max(1, super.getStrength() + this.tempStrength + this.weaponStrength + this.scrollStrength);
    }

    @Override
    public int getAgility() {
        return Math.max(1, super.getAgility() + this.tempAgility + this.weaponAgility + this.scrollAgility);
    }

    @Override
    public double getHealth() {
        return Math.min(this.maxHealth, super.getHealth() + this.tempHealth + this.scrollHealth);
    }

    public void heal(int amount) {
        this.setHealth(Math.min(getHealth() + amount, this.maxHealth));
    }

    public String attack(Monster monster) {

        if (monster instanceof Vampire) {
            if (!monster.isFirstHitTrue()) {
                monster.setFirstHitTrue();
                return "Oops... you missed the first hit on the Vampire";
            }
        }

        if (this.isHitSuccessful(monster) && !this.isSleeping()) {

            this.hit(monster);
            this.successfulHitsCount++;

            if (monster.checkIfDead()) {
                int loot = monster.giveGold();
                this.addGold(loot);
                this.monstersDefeatedCount++;
                return "You defeated the " + monster.toString() + " [+" + loot + " gold]";
            } else {
                return "You injured the " + monster.toString();
            }
        }

        if (this.isSleeping()) {
            this.setSleeping(false);
            return "You couldn't defend yourself, you were sleeping...z...z...";
        }
        return "You missed the " + monster.toString();
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getItemsByType(int item_type, List<Item> items) {
        for (Item item : inventory.getItems()) {
            if (item.getType() == item_type) {
                items.add(item);
            }
        }
        if (items.isEmpty()) {
            return "You don't have any items from the selected type in your backpack!";
        }
        StringBuilder sb = new StringBuilder("Available items: ");
        items.forEach(item -> {
            sb.append(item.getName());
            sb.append(", ");
        });
        sb.append("\nChoose item to use it...(1-9)");
        return sb.toString();
    }

    public String useItem(Item item) {
        switch (item.getTypeName()) {
            case "Weapon":
                return equipWeapon((Weapon)item);

            case "Food":
                return eatFood((Food) item);

            case "Elixir":
                return useElixir((Elixir) item);

            case "Scroll":
                return useScroll((Scroll) item);

            default:
                break;
        }
        return "";
    }
}
