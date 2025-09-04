package Domain;

import Domain.Items.Item;
import Domain.Items.Treasure;
import Domain.characters.Direction;
import Domain.characters.Player;
import Domain.characters.monsters.Monster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import Constants.GameConstants;
import Datalayer.Saving;
import Datalayer.Stats;

public class GameSession {

    private Player player;
    private int levelNumber;
    private Level level;
    private String message_bar;
    private Stats statistics;
    public Saving save;

    public GameSession(String playerName) {
        this.levelNumber = 0;
        this.player = new Player();
        this.player.setName(playerName);
        this.createNewLevel();
        this.statistics = new Stats();
        this.save = new Saving();
        this.message_bar = "Goodluck, " + this.player.getName() + "!";
        this.update_field();

    }

    public void saveGame() throws IOException {
        save.setField(level.getField());
        save.setMonsters(level.getMonsters());
        save.setRooms(level.getRooms());
        save.setPlayer(player);
        save.setInventory(player.getInventory().getItems());
        save.setLevelNumber(levelNumber);
        save.setItems(level.getItems());
        save.setCorridors(level.getCorridors());
        save.set_exit_door(level.getX_exit_door(), level.getY_exit_door());
        save.saveGame();
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public void clearMessage() {
        this.message_bar = "";
    }

    public String getMessage_bar() {
        return message_bar;
    }

    public void setMessage_bar(String message_bar) {
        this.message_bar = message_bar;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Level getLevel() {
        return level;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void move(Direction direction) {

        Coordinates oldPos = new Coordinates(player.getCoordinates());

        Optional<Monster> playerAttacks = this.checkIfPlayerAttack(direction);
        playerAttacks.ifPresentOrElse(
                /* если в новой клетке есть монстр, игрок атакует */
                monster -> this.message_bar = this.player.attack(monster),
                () -> { /* если нет, игрок делает шаг */
                    this.player.move(direction, this.level.getField());
                });

        /* проверяем, есть ли монстр, который готов атаковать */
        Monster monster = this.checkIfMonsterAttack();
        if (monster != null) {
            double playerHealth = this.player.getHealth();
            this.message_bar += "\n";
            this.message_bar += monster.attack();
            if (playerHealth - this.player.getHealth() > 0) {
                player.addTakenHitsCount();
            }
        } else {
            /* проверяем, изменились ли координаты игрока */
            if (!oldPos.equals(player.getCoordinates())) {
                this.rotateMonsters();
            }
        }
        this.update_field();
    }

    public void createNewLevel() {
        this.levelNumber++;
        this.level = new Level(this.levelNumber, this.player);
    }

    public void update_field() {
        this.level.updateField();
    }

    public boolean isExitDoor() {
        return this.player.getCoordinates()
                .equals(new Coordinates(this.level.getX_exit_door(), this.level.getY_exit_door()));
    }

    /**
     * Проверяет, есть ли монстр в той клетке, куда собирается пойти игрок.
     * Если да, игрок атакует.
     *
     * @param direction в какую сторону собирается сходить игрок
     * @return экзкмпляр монстра, с которым игрок вступает в бой
     */
    private Optional<Monster> checkIfPlayerAttack(Direction direction) {

        Coordinates newPosition = new Coordinates(this.player.getCoordinates());
        newPosition.setX(this.player.getCoordinates().getX() + direction.getCoordinatesShift().getX());
        newPosition.setY(this.player.getCoordinates().getY() + direction.getCoordinatesShift().getY());

        return this.level.getMonsters().stream()
                .filter(monster -> monster.getCoordinates().equals(newPosition) && !monster.isDead())
                .findFirst();
    }

    /**
     * Проверяет, есть ли игрок в той клетке, куда собирается сходить монстр.
     * Если да, монстр атакует.
     *
     * @return экземпляр монстра, который вступает в бой, или null если никто не
     *         вступает в бой
     */
    private Monster checkIfMonsterAttack() {

        for (Monster monster : this.level.getMonsters()) {
            if (!monster.isDead() && monster.isChasing() && monster.isReadyToAttack()) {
                return monster;
            }
        }
        return null;
    }

    /**
     * Вызывает передвижение всех монстров.
     * Вызывается после того, как сходил игрок, если не инициализирован бой.
     */
    public void rotateMonsters() {

        int monstersNum = this.level.getMonsters().size();
        for (int i = 0; i < monstersNum; i++) {
            Monster monster = this.level.getMonsters().get(i);
            if (!monster.isDead()) {
                monster.move();
            } else {
                this.level.getMonsters().remove(monster);
                monstersNum--;
            }
        }
    }

    public void checkItemPickup(int x, int y) {
        Item item = this.level.pickUpItem(x, y);
        if (item != null) {
            if (this.player.pickUpItem(item)) {
                if (item instanceof Treasure treasure) {
                    this.message_bar = treasure.getName() + " - " + treasure.getGoldValue() + " gold";
                } else {
                    this.message_bar = "Picked up: " + item.getTypeName() + "-" + item.getName();
                }
            } else {
                this.message_bar = "Inventory full! Couldn't pick up: " + item.getTypeName() + "-" + item.getName();
                this.level.getItems().add(item);
            }
        }

    }

    public void updateStats() {
        statistics.setGold(player.getGold());
        statistics.setEnemies_defeated(player.getDefeatedMonsters());
        statistics.setEaten_food(player.getFoodEatenCount());
        statistics.setUsed_elixirs(player.getElixirsDrunkCount());
        statistics.setUsed_scrolls(player.getScrollsReadCount());
        statistics.setHits_dealt(player.getSuccessfulHitsCount());
        statistics.setHits_taken(player.getTakenHitsCount());
        statistics.setTiles_walked(player.getMovesCount());
    }

    public Stats getStatistics() {
        statistics.setPlayer_name(player.getName());
        statistics.setLevel_number(levelNumber);
        return statistics;
    }

    public void showInventory() {
        this.message_bar = player.getInventory().toString();
    }

    public List<Item> handleInventorySelection(int input) {
        HashMap<Integer, Integer> inventory_map_keys = new HashMap<>();
        inventory_map_keys.put(GameConstants.KEY_USE_WEAPON, GameConstants.WEAPON);
        inventory_map_keys.put(GameConstants.KEY_USE_FOOD, GameConstants.FOOD);
        inventory_map_keys.put(GameConstants.KEY_USE_SCROLL, GameConstants.SCROLL);
        inventory_map_keys.put(GameConstants.KEY_USE_ELIXIR, GameConstants.ELIXIR);
        if (input == GameConstants.KEY_OPEN_INVENTORY) {
            showInventory();
        } else {
            List<Item> items = new ArrayList<>();
            this.message_bar = player.getItemsByType(inventory_map_keys.get(input), items);
            if (!items.isEmpty()) {
                return items;
            }
        }
        return null;
    }
}
