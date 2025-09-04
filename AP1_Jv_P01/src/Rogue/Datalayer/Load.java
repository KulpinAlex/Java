package Datalayer;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import Constants.GameConstants;
import Domain.GameSession;
import Domain.Items.Elixir;
import Domain.Items.Food;
import Domain.Items.Item;
import Domain.Items.Scroll;
import Domain.Items.Weapon;
import Domain.characters.Player;
import Domain.characters.monsters.*;
import Domain.level_essences.Corridor;
import Domain.level_essences.Room;
import Domain.level_essences.Tile;

public class Load {
    private Tile[][] field;
    private List<Room> rooms;
    private List<Item> items;
    private List<Item> inventory_items;
    private List<Monster> monsters;
    private Player player;
    private int levelNumber;
    private List<Corridor> corridors;
    private int x_exit_door;
    private int y_exit_door;

    public int getX_exit_door() {
        return x_exit_door;
    }

    public int getY_exit_door() {
        return y_exit_door;
    }

    public List<Corridor> getCorridors() {
        return corridors;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public Tile[][] getField() {
        return field;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Item> getInventory_items() {
        return inventory_items;
    }

    public Player getPlayer() {
        return this.player;
    }

    private void loadMonsters(Saving load, Gson gson) {
        this.monsters = new ArrayList<>();
        for (JsonObject obj : load.getMonsters()) {
            String type = obj.get("type").getAsString();
            switch (type) {
                case "Zombie":
                    monsters.add(gson.fromJson(obj, Zombie.class));
                    break;
                case "Mimic":
                    monsters.add(gson.fromJson(obj, Mimic.class));
                    break;
                case "Ghost":
                    monsters.add(gson.fromJson(obj, Ghost.class));
                    break;
                case "Ogre":
                    monsters.add(gson.fromJson(obj, Ogre.class));
                    break;
                case "Snake":
                    monsters.add(gson.fromJson(obj, Snake.class));
                    break;
                case "Vampire":
                    monsters.add(gson.fromJson(obj, Vampire.class));
                    break;
                default:
                    break;
            }

        }
    }

    private void loadItems(List<JsonObject> loadItems, List<Item> items, Gson gson) {
        for (JsonObject obj : loadItems) {
            int type = obj.get("type").getAsInt();
            switch (type) {
                case GameConstants.WEAPON:
                    items.add(gson.fromJson(obj, Weapon.class));
                    break;
                case GameConstants.FOOD:
                    items.add(gson.fromJson(obj, Food.class));
                    break;
                case GameConstants.SCROLL:
                    items.add(gson.fromJson(obj, Scroll.class));
                    break;
                case GameConstants.ELIXIR:
                    items.add(gson.fromJson(obj, Elixir.class));
                    break;
                default:
                    break;
            }

        }
    }

    public GameSession loadGame() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader reader = new FileReader(GameConstants.SAVE_FILE_PATH);
        Saving load = gson.fromJson(reader, Saving.class);
        this.items = new ArrayList<>();
        this.inventory_items = new ArrayList<>();
        loadMonsters(load, gson);
        loadItems(load.getInventory(), this.inventory_items, gson);
        loadItems(load.getItems(), this.items, gson);
        this.rooms = load.getRooms();
        this.field = load.getField();
        this.player = load.getPlayer();
        this.levelNumber = load.getLevelNumber();
        this.corridors = load.getCorridors();
        this.x_exit_door = load.getX_exit_door();
        this.y_exit_door = load.getY_exit_door();
        GameSession session = new GameSession("player");
        session.setLevelNumber(levelNumber);
        session.getLevel().CURRENT_LEVEL = levelNumber;
        for (Monster monster : monsters) {
            monster.setLevel(session.getLevel());
        }
        session.getLevel().setRooms(rooms);
        session.getLevel().setMonsters(monsters);
        session.getLevel().setField(field);
        session.getLevel().setCorridors(corridors);
        session.getLevel().setItems(items);
        session.setPlayer(player);
        session.getPlayer().getInventory().setItems(inventory_items);
        session.getLevel().setPlayer(player);
        session.getLevel().set_exit_door(load.getX_exit_door(), load.getY_exit_door());
        session.setMessage_bar("Goodluck, " + this.player.getName() + "!");
        session.update_field();
        return session;
    }
}
