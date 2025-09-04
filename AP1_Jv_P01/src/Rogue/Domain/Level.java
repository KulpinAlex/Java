package Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Constants.GameConstants;
import Domain.characters.Player;
import Domain.level_essences.Room;
import Domain.level_essences.Tile;
import Domain.characters.monsters.*;
import Domain.level_essences.MazeGenerator;
import Domain.level_essences.Corridor;
import Domain.Items.*;

public class Level {
    private int x_exit_door;
    private int y_exit_door;
    private List<Room> rooms;
    private List<Corridor> corridors;
    private List<Item> items;
    private List<Monster> monsters;
    private Tile[][] field;
    private Player player;
    private int start_room_index;
    public int CURRENT_LEVEL;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setCorridors(List<Corridor> corridors) {
        this.corridors = corridors;
    }

    public void set_exit_door(int x, int y) {
        this.x_exit_door = x;
        this.y_exit_door = y;
    }

    public List<Corridor> getCorridors() {
        return corridors;
    }

    public Level(int currentLevel, Player player) {
        this.CURRENT_LEVEL = currentLevel;
        this.rooms = new ArrayList<>();
        this.corridors = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.items = new ArrayList<>();
        this.player = player;
        this.initialize_tiles();
        this.generateRooms();
        this.start_room_index = this.choiseStartRoom();
        this.generateMonsters(this.start_room_index);
        this.createCorridors();
        this.generateExit();
        this.generateItems();
        this.updateFogOfWar();
        this.clearFogRoom(this.rooms.get(this.start_room_index));
    }

    public Tile[][] getField() {
        return field;
    }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public Player getPlayer() {
        return player;
    }

    public int getX_exit_door() {
        return x_exit_door;
    }

    public int getY_exit_door() {
        return y_exit_door;
    }

    public List<Monster> getMonsters() {
        return this.monsters;
    }

    private void initialize_tiles() {
        this.field = new Tile[GameConstants.MAP_WIDTH][GameConstants.MAP_HEIGHT];
        for (int x = 0; x < GameConstants.MAP_WIDTH; x++) {
            for (int y = 0; y < GameConstants.MAP_HEIGHT; y++) {
                this.field[x][y] = new Tile();
            }
        }
    }

    private void generateExit() {
        Random random = new Random();
        int room_number = this.start_room_index;
        while (room_number == this.start_room_index) {
            room_number = random.nextInt(this.rooms.size());
        }
        this.x_exit_door = random.nextInt(this.rooms.get(room_number).getX_left_up(),
                this.rooms.get(room_number).getX_right_down() + 1);
        this.y_exit_door = random.nextInt(this.rooms.get(room_number).getY_left_up(),
                this.rooms.get(room_number).getY_right_down() + 1);
        this.field[this.x_exit_door][this.y_exit_door].setSymb("*");
        this.field[this.x_exit_door][this.y_exit_door].setColor(1);
    }

    private void generateRooms() {
        Random random = new Random();
        for (int i = 0; i < GameConstants.MAP_ROOMS; i++) {
            int width = random.nextInt(Math.min(GameConstants.MIN_ROOM_SIZE, GameConstants.MAP_WIDTH / 3 - 1),
                    Math.min(GameConstants.MAX_ROOM_SIZE, GameConstants.MAP_WIDTH / 3 - 1));
            int height = random.nextInt(Math.min(GameConstants.MIN_ROOM_SIZE, GameConstants.MAP_HEIGHT / 3 - 1),
                    Math.min(GameConstants.MAX_ROOM_SIZE, GameConstants.MAP_HEIGHT / 3 - 1));
            int x = random.nextInt((i % 3) * (GameConstants.MAP_WIDTH) / 3 + 1,
                    (i % 3 + 1) * (GameConstants.MAP_WIDTH - 1) / 3 - width + 1);
            int y = random.nextInt((i / 3) * (GameConstants.MAP_HEIGHT) / 3 + 1,
                    (i / 3 + 1) * (GameConstants.MAP_HEIGHT - 1) / 3 - height + 1);
            Room new_room = new Room(x, y, width, height);
            this.create_room(new_room);
            this.rooms.add(new_room);
        }
    }

    private void create_room(Room room) {
        for (int i = room.getX_left_up() - 1; i < room.getX_right_down() + 2; ++i) {
            for (int j = room.getY_left_up() - 1; j < room.getY_right_down() + 2; ++j) {
                if (room.is_room(i, j)) {
                    this.field[i][j].setBlocked(false);
                    this.field[i][j].setSymb(".");
                    this.field[i][j].setColor(5);
                }
            }
        }
    }

    private int choiseStartRoom() {
        Random random = new Random();
        this.start_room_index = random.nextInt(GameConstants.MAP_ROOMS);
        this.player.setCoordinates(Coordinates.generateCoordinates(this.rooms.get(start_room_index)));
        return this.start_room_index;
    }

    public void updateField() {
        for (int i = 0; i < GameConstants.MAP_WIDTH; i++) {
            for (int j = 0; j < GameConstants.MAP_HEIGHT; j++) {
                if (!field[i][j].isBlocked()) {
                    field[i][j].setSymb(".");
                    field[i][j].setColor(5);
                }
            }
        }
        this.rooms.forEach(room -> {
            for (int i = room.getX_left_up(); i < room.getX_right_down() + 1; ++i) {
                for (int j = room.getY_left_up(); j < room.getY_right_down() + 1; ++j) {
                    this.field[i][j].setSymb(".");
                    this.field[i][j].setColor(5);
                }
            }
        });
        this.corridors.forEach(corr -> {
            for (List<Integer> x_y : corr.getCoordinates()) {
                this.field[x_y.get(0)][x_y.get(1)].setSymb(".");
                this.field[x_y.get(0)][x_y.get(1)].setColor(5);
            }
        });
        for (Item item : items) {
            if (item.getCoordinates() != null) {
                int x = item.getCoordinates().getX();
                int y = item.getCoordinates().getY();
                field[x][y].setSymb(String.valueOf(getItemSymbol(item)));
                field[x][y].setColor(getItemColor(item));
            }
        }
        this.monsters.stream().filter(Monster::isVisible).forEach(monster -> {
            this.field[monster.getCoordinates().getX()][monster.getCoordinates().getY()]
                    .setSymb(monster.getSymbol());
            this.field[monster.getCoordinates().getX()][monster.getCoordinates().getY()]
                    .setColor(monster.getColorCode());
        });
        this.field[this.x_exit_door][this.y_exit_door].setSymb("*");
        this.field[this.x_exit_door][this.y_exit_door].setColor(1);
        this.field[this.player.getCoordinates().getX()][this.player.getCoordinates().getY()]
                .setSymb(this.player.getSymbol());
        this.field[this.player.getCoordinates().getX()][this.player.getCoordinates().getY()]
                .setColor(this.player.getColor());
        this.clearFogOfWar(player.getCoordinates().getX(), this.player.getCoordinates().getY());
    }

    public void generateMonsters(int startRoom) {
        Random random = new Random();

        /* определим максимально возможное кол-во для текущего уровня */
        int maxMonstersNum = GameConstants.MAX_MONSTERS_PER_ROOM
                + this.CURRENT_LEVEL / GameConstants.LEVEL_ADJUST_DIFFICULTY;
        for (int room = 0; room < GameConstants.MAP_ROOMS; room++) {

            if (room == startRoom) {
                continue;
            }

            /* рандомное кол-во для данной комнаты */
            int monstersInRoom = random.nextInt(maxMonstersNum) + 1;

            for (int i = 0; i < monstersInRoom; i++) {
                MonsterType type = MonsterType.values()[random.nextInt(MonsterType.values().length)];
                Monster newMonster = type.createMonster(this);
                Coordinates newCoordinates;
                do {
                    newCoordinates = Coordinates.generateCoordinates(this.rooms.get(room));
                } while (!newCoordinates.isFree(this.field));
                newMonster.setCoordinates(newCoordinates);
                this.field[newMonster.getCoordinates().getX()][newMonster.getCoordinates().getY()]
                        .setSymb(newMonster.getSymbol());
                this.field[newMonster.getCoordinates().getX()][newMonster.getCoordinates().getY()]
                        .setColor(newMonster.getColorCode());
                this.monsters.add(newMonster);
            }
        }
    }

    private void createCorridors() {
        List<List<Integer>> mazePath = new MazeGenerator().createSecMaze();
        mazePath.forEach(path -> {
            Corridor corr = new Corridor();
            if (Math.abs(path.get(0) - path.get(1)) == 1) {
                corr.connectHorizontalRooms(this.rooms.get(path.get(0)), this.rooms.get(path.get(1)));
            } else {
                corr.connectVerticalRooms(this.rooms.get(path.get(0)), this.rooms.get(path.get(1)));
            }
            this.corridors.add(corr);
        });
        this.corridors.forEach(corr -> {
            for (List<Integer> x_y : corr.getCoordinates()) {
                this.field[x_y.get(0)][x_y.get(1)].setSymb(".");
                this.field[x_y.get(0)][x_y.get(1)].setColor(5);
                this.field[x_y.get(0)][x_y.get(1)].setBlocked(false);
            }
        });
    }

    private Item createRandomItem() {
        Random random = new Random();
        int itemType = random.nextInt(6);

        return switch (itemType) {
            case 0 -> new Weapon(random.nextInt(5));
            case 1 -> new Food(GameConstants.FOOD, GameConstants.FOOD_SYMBOL, new Coordinates(0, 0));
            case 2 -> new Scroll(random.nextInt(5));
            case 3 -> new Elixir(random.nextInt(3));
            case 4 -> new Treasure(this.CURRENT_LEVEL);
            default -> new Weapon(0);
        };
    }

    private void updateItemOnMap(Item item) {
        if (item.getCoordinates() != null) {
            int x = item.getCoordinates().getX();
            int y = item.getCoordinates().getY();
            field[x][y].setSymb(String.valueOf(getItemSymbol(item)));
            field[x][y].setColor(getItemColor(item));
        }
    }

    private char getItemSymbol(Item item) {
        if (item instanceof Weapon)
            return GameConstants.WEAPON_SYMBOL;
        if (item instanceof Food)
            return GameConstants.FOOD_SYMBOL;
        if (item instanceof Scroll)
            return GameConstants.SCROLL_SYMBOL;
        if (item instanceof Elixir)
            return GameConstants.ELIXIR_SYMBOL;
        if (item instanceof Treasure)
            return GameConstants.TREASURE_SYMBOL;
        return '?';
    }

    private int getItemColor(Item item) {
        if (item instanceof Weapon)
            return 3;
        if (item instanceof Food)
            return 2;
        if (item instanceof Scroll)
            return 6;
        if (item instanceof Elixir)
            return 5;
      return 0;
    }

    public Item pickUpItem(int x, int y) {
        for (Item item : items) {
            if (item.getCoordinates() != null &&
                    item.getCoordinates().getX() == x &&
                    item.getCoordinates().getY() == y) {
                Item pickedItem = item;
                items.remove(item);
                field[x][y].setSymb(".");
                return pickedItem;
            }
        }
        return null;
    }

    private void generateItems() {
        Random random = new Random();
        for (Room room : rooms) {
            // Пропускаем стартовую комнату
            if (rooms.indexOf(room) == start_room_index) {
                continue;
            }

            // Генерируем 1-3 предмета в комнате
            int itemsCount = random.nextInt(2) + 1;
            for (int i = 0; i < itemsCount; i++) {
                Item newItem = createRandomItem();
                Coordinates itemPos;
                do {
                    itemPos = Coordinates.generateCoordinates(room);
                } while (!itemPos.isFree(field));

                newItem.setCoordinates(itemPos);
                items.add(newItem);
                updateItemOnMap(newItem);
            }
        }
    }

    private void updateFogOfWar() {
        for (int i = 0; i < GameConstants.MAP_WIDTH; ++i) {
            for (int j = 0; j < GameConstants.MAP_HEIGHT; ++j) {
                if (!field[i][j].isBlocked()) {
                    field[i][j].setBlocked_sight(true);
                }
            }
        }
    }

    private void clearFogRoom(Room room) {
        for (int i = room.getX_left_up() - 1; i < room.getX_right_down() + 2; ++i) {
            for (int j = room.getY_left_up() - 1; j < room.getY_right_down() + 2; ++j) {
                this.field[i][j].setBlocked_sight(false);
            }
        }
    }

    private void clearFogIfRoom(int x, int y) {
        for (Room room : this.rooms) {
            if (room.is_room(x, y)) {
                this.clearFogRoom(room);
                break;
            }
        }
    }

    private void clearFogOfWar(int x, int y) {
        this.updateFogOfWar();
        this.clearFogIfRoom(x, y);
        for (int angle_offset = 0; angle_offset < 360; angle_offset += 5) {
            double ray_angle = Math.toRadians(angle_offset);
            this.rayCasting(x, y, ray_angle);
        }
    }

    private List<int[]> bresenham(int x0, int y0, int x1, int y1) {
        List<int[]> points = new ArrayList<>();
        int dx = Math.abs(x1 - x0);
        int dy = -Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx + dy;
        while (true) {
            points.add(new int[] { x0, y0 });
            if (x0 == x1 && y0 == y1) {
                break;
            }
            int e2 = err * 2;
            if (e2 >= dy) {
                err += dy;
                x0 += sx;
            }
            if (e2 <= dx) {
                err += dx;
                y0 += sy;
            }
        }
        return points;
    }

    private void rayCasting(int x, int y, double angle) {
        int max_distance = 10;
        for (int depth = 0; depth < max_distance; ++depth) {
            int ray_x = (int) (x + depth * Math.cos(angle));
            int ray_y = (int) (y + depth * Math.sin(angle));
            List<int[]> points = this.bresenham(x, y, ray_x, ray_y);
            for (int[] point : points) {
                int rx = point[0];
                int ry = point[1];
                if (rx < 0 || rx >= GameConstants.MAP_WIDTH || ry < 0 || ry >= GameConstants.MAP_HEIGHT) {
                    break;
                }
                this.field[rx][ry].setBlocked_sight(false);
                if (this.field[rx][ry].isBlocked()) {
                    break;
                }
            }

        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void setField(Tile[][] field) {
        this.field = field;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
