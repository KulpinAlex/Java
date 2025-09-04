package Datalayer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import Constants.GameConstants;
import Domain.Items.Item;
import Domain.characters.Player;
import Domain.characters.monsters.Monster;
import Domain.level_essences.Corridor;
import Domain.level_essences.Room;
import Domain.level_essences.Tile;

public class Saving {
    private Tile[][] field;
    private List<Room> rooms;
    private List<JsonObject> items;
    private List<JsonObject> monsters;
    private Player player;
    private List<JsonObject> inventory_items;
    private int levelNumber;
    private int x_exit_door;
    private int y_exit_door;
    private List<Corridor> corridors;

    public int getX_exit_door() {
        return x_exit_door;
    }

    public void set_exit_door(int x, int y) {
        this.x_exit_door = x;
        this.y_exit_door = y;
    }

    public int getY_exit_door() {
        return y_exit_door;
    }
    
    public List<Corridor> getCorridors() {
        return corridors;
    }

    public void setCorridors(List<Corridor> corridors) {
        this.corridors = corridors;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public List<JsonObject> getInventory() {
        return inventory_items;
    }

    public void setInventory(List<Item> inventory) {
        Gson gson = new Gson();
        this.inventory_items = inventory.stream().map(item -> gson.toJsonTree(item).getAsJsonObject()).collect(Collectors.toList());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<JsonObject> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        Gson gson = new Gson();
        this.items = items.stream().map(item -> gson.toJsonTree(item).getAsJsonObject()).collect(Collectors.toList());
    }

    public void setMonsters(List<Monster> monsters) {
        Gson gson = new Gson();
        this.monsters = monsters.stream().map(monster -> gson.toJsonTree(monster).getAsJsonObject()).collect(Collectors.toList());
    }

    public void setField(Tile[][] field) {
        this.field = field;
    }

    public Tile[][] getField() {
        return field;
    }

    public List<JsonObject> getMonsters() {
        return monsters;
    }

    public void saveGame() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(GameConstants.SAVE_FILE_PATH);
        gson.toJson(this, writer);
        writer.close();
    }
}
