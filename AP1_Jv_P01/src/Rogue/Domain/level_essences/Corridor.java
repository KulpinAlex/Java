package Domain.level_essences;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Corridor {
    private final List<List<Integer>> coordinates;

    public List<List<Integer>> getCoordinates() {
        return coordinates;
    }

    public Corridor() {
        this.coordinates = new ArrayList<>();
    }

    private void createVerticalTunnel(int x, int y1, int y2) {
        for (int y = y1 + 1; y < y2; ++y) {
            this.coordinates.add(List.of(x, y));
        }
    }

    private void createHorizontalTunnel(int x1, int x2, int y) {
        for (int x = x1 + 1; x < x2; ++x) {
            this.coordinates.add(List.of(x, y));
        }
    }

    private void createCorridor(int x1, int y1, int x2, int y2) {
        this.createVerticalTunnel(x2, Math.min(y1, y2), Math.max(y1, y2));
        this.coordinates.add(List.of(x2, y1));
        this.createHorizontalTunnel(Math.min(x1, x2), Math.max(x1, x2), y1);
    }

    public void connectVerticalRooms(Room room1, Room room2) {
        int r1x1 = room1.getX_left_up();
        int r1y1 = room1.getY_left_up();
        int r1x2 = room1.getX_right_down();
        int r1y2 = room1.getY_right_down();
        int r2x1 = room2.getX_left_up();
        int r2y1 = room2.getY_left_up();
        int r2x2 = room2.getX_right_down();
        int r2y2 = room2.getY_right_down();
        Random random = new Random();
        if ((r1x1 + 1 < r2x2) && (r1x2 > r2x1 + 1)) {
            int x_rand = random.nextInt(Math.max(r1x1, r2x1) + 1, Math.min(r1x2, r2x2));
            this.createVerticalTunnel(x_rand, r1y2, r2y1);
        } else if (r1x1 > r2x1 + 2) {
            int x_rand = random.nextInt(r2x1 + 1, Math.min(r1x1 - 2, r2x2 - 1) + 1);
            int y_rand = random.nextInt(r1y1 + 1, r1y2);
            this.createCorridor(r1x1, y_rand, x_rand, r2y1);
        } else {
            int x_rand = random.nextInt(r1x1 + 1, Math.min(r2x1 - 2, r1x2 - 1) + 1);
            int y_rand = random.nextInt(r2y1 + 1, r2y2);
            this.createCorridor(r2x1, y_rand, x_rand, r1y2);
        }
    }

    public void connectHorizontalRooms(Room room1, Room room2) {
        int r1x1 = room1.getX_left_up();
        int r1y1 = room1.getY_left_up();
        int r1x2 = room1.getX_right_down();
        int r1y2 = room1.getY_right_down();
        int r2x1 = room2.getX_left_up();
        int r2y1 = room2.getY_left_up();
        int r2x2 = room2.getX_right_down();
        int r2y2 = room2.getY_right_down();
        Random random = new Random();
        if ((r1y2 > r2y1 + 1) && (r1y1 + 1 < r2y2)) {
            int y_rand = random.nextInt(Math.max(r1y1, r2y1) + 1, Math.min(r1y2, r2y2));
            this.createHorizontalTunnel(r1x2, r2x1, y_rand);
        } else if (r1y1 > r2y1 + 2) {
            int x_rand = random.nextInt(r1x1 + 1, r1x2);
            int y_rand = random.nextInt(r2y1 + 1, Math.min(r2y2 - 1, r1y1 - 2) + 1);
            this.createCorridor(r2x1, y_rand, x_rand, r1y1);
        } else {
            int x_rand = random.nextInt(r2x1 + 1, r2x2);
            int y_rand = random.nextInt(r1y1 + 1, Math.min(r1y2 - 1, r2y1 - 2) + 1);
            this.createCorridor(r1x2, y_rand, x_rand, r2y1);
        }
    }
}
