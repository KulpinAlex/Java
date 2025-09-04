package Domain.level_essences;

public class Room {
    private final int x_left_up;
    private final int y_left_up;
    private final int x_right_down;
    private final int y_right_down;

    public Room(int x, int y, int width, int height) {
        this.x_left_up = x;
        this.y_left_up = y;
        this.x_right_down = x + width - 1;
        this.y_right_down = y + height - 1;
    }

    public int getX_left_up() {
        return x_left_up;
    }

    public int getY_left_up() {
        return y_left_up;
    }

    public int getX_right_down() {
        return x_right_down;
    }

    public int getY_right_down() {
        return y_right_down;
    }

    public boolean is_room(int x, int y) {
        return (this.x_left_up <= x && this.x_right_down >= x
                && this.y_left_up <= y && this.y_right_down >= y);
    }
}