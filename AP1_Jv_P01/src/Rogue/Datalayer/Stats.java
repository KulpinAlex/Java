package Datalayer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import Constants.GameConstants;
import java.util.Comparator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Domain.GameSession;

public class Stats {
    private String player_name = "";
    private int gold = 0;
    private int level_number = 1;
    private int enemies_defeated = 0;
    private int eaten_food = 0;
    private int used_elixirs = 0;
    private int used_scrolls = 0;
    private int hits_dealt = 0;
    private int hits_taken = 0;
    private int tiles_walked = 0;

    public int getLevel_number() {
        return level_number;
    }

    public void setLevel_number(int level_number) {
        this.level_number = level_number;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getEnemies_defeated() {
        return this.enemies_defeated;
    }

    public void setEnemies_defeated(int enemies_defeated) {
        this.enemies_defeated = enemies_defeated;
    }

    public int getEaten_food() {
        return this.eaten_food;
    }

    public void setEaten_food(int eaten_food) {
        this.eaten_food = eaten_food;
    }

    public int getUsed_elixirs() {
        return this.used_elixirs;
    }

    public void setUsed_elixirs(int used_elixirs) {
        this.used_elixirs = used_elixirs;
    }

    public int getUsed_scrolls() {
        return this.used_scrolls;
    }

    public void setUsed_scrolls(int used_scrolls) {
        this.used_scrolls = used_scrolls;
    }

    public int getHits_dealt() {
        return this.hits_dealt;
    }

    public void setHits_dealt(int hits_dealt) {
        this.hits_dealt = hits_dealt;
    }

    public int getHits_taken() {
        return this.hits_taken;
    }

    public void setHits_taken(int hits_taken) {
        this.hits_taken = hits_taken;
    }

    public int getTiles_walked() {
        return this.tiles_walked;
    }

    public void setTiles_walked(int tiles_walked) {
        this.tiles_walked = tiles_walked;
    }

    public List<Stats> loadStats() throws IOException {
        String jsonArray = Files.readString(Paths.get(GameConstants.STATS_FILE_PATH));
        Type listType = new TypeToken<ArrayList<Stats>>() {
        }.getType();
        List<Stats> stats = new Gson().fromJson(jsonArray, listType);
        return stats != null ? stats : new ArrayList<>();
    }

    public void addSessionStatistic(List<Stats> statistic, GameSession game_session) {
        statistic.add(game_session.getStatistics());
        statistic.sort(Comparator
                .comparing(Stats::getGold)
                .reversed());
    }

    public void saveStats(List<Stats> statistic) throws IOException {
        Gson gson = new Gson();
        FileOutputStream fileOutputStream = new FileOutputStream(GameConstants.STATS_FILE_PATH);
        String json = gson.toJson(statistic);
        fileOutputStream.write(json.getBytes());
        fileOutputStream.close();
    }

}
