package Domain;

import java.util.concurrent.ThreadLocalRandom;

import Constants.GameConstants;
import Domain.level_essences.Room;
import Domain.level_essences.Tile;

public class Coordinates {

    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates other) {
        this.x = other.x;
        this.y = other.y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Coordinates other) {
        return (this.x == other.getX() && this.y == other.getY());
    }

    /**
     * Проверяет, находится ли координата внутри игрового пространства
     * и не занята другим персонажем или предметом.
     */
    public boolean isFree(Tile[][] field) {

        if(this.x >= GameConstants.MAP_WIDTH || this.x < 0 || this.y >= GameConstants.MAP_HEIGHT || this.y < 0) {
            return false;
        }
        return !(field[this.x][this.y].isBlocked());
    }

    public static Coordinates generateCoordinates(Room room) {

        int xLeftUp = room.getX_left_up() + 1;
        int yLeftUp =  room.getY_left_up() + 1;

        int xRightDown = room.getX_right_down();
        int yRightDown = room.getY_right_down();

        int x = ThreadLocalRandom.current().nextInt(xLeftUp, xRightDown + 1);
        int y = ThreadLocalRandom.current().nextInt(yLeftUp, yRightDown + 1);

        return new Coordinates(x, y);
    }
}
