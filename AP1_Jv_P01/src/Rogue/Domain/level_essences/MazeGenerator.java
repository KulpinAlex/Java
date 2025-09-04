package Domain.level_essences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MazeGenerator {
    private final Map<Integer, List<Integer>> structMaze;

    public MazeGenerator() {
        this.structMaze = new HashMap<>();
        this.structMaze.put(0, Arrays.asList(1, 3));
        this.structMaze.put(1, Arrays.asList(0, 2, 4));
        this.structMaze.put(2, Arrays.asList(1, 5));
        this.structMaze.put(3, Arrays.asList(0, 4, 6));
        this.structMaze.put(4, Arrays.asList(1, 3, 5, 7));
        this.structMaze.put(5, Arrays.asList(2, 4, 8));
        this.structMaze.put(6, Arrays.asList(3, 7));
        this.structMaze.put(7, Arrays.asList(4, 6, 8));
        this.structMaze.put(8, Arrays.asList(5, 7));
    }

    private List<Integer> createPath(List<Integer> remainingRooms) {
        List<Integer> current_path = new ArrayList<>();
        Random random = new Random();
        while (!remainingRooms.isEmpty()) {
            if (current_path.isEmpty()) {
                int index = random.nextInt(remainingRooms.size());
                current_path.add(remainingRooms.get(index));
                remainingRooms.remove(index);
            } else {
                List<Integer> connected_rooms = new ArrayList<>();
                remainingRooms.forEach(room -> {
                    if (this.structMaze.get(current_path.getLast())
                            .contains(room)) {
                        connected_rooms.add(room);
                    }
                });
                if (connected_rooms.isEmpty()) {
                    break;
                }
                int index = random.nextInt(connected_rooms.size());
                current_path.add(connected_rooms.get(index));
                remainingRooms.remove(connected_rooms.get(index));
            }

        }
        if (!remainingRooms.isEmpty()) {
            for (Integer room : remainingRooms) {
                if (structMaze.get(room).contains(current_path.getFirst())) {
                    current_path.addFirst(room);
                    remainingRooms.remove(room);
                    break;
                }
            }
        }
        return current_path;
    }

    public List<List<Integer>> createSecMaze() {
        List<Integer> remainingRooms = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));
        List<List<Integer>> allPaths = new ArrayList<>();
        while (!remainingRooms.isEmpty()) {
            List<Integer> path = this.createPath(remainingRooms);
            allPaths.add(path);
        }
        if (allPaths.size() > 1) {
            for (int i = 1; i < allPaths.size(); ++i) {
                for (int j = 0; j < allPaths.getFirst().size(); ++j) {
                    if (this.structMaze.get(allPaths.get(i).getFirst()).contains(allPaths.getFirst().get(j))) {
                        allPaths.get(i).addFirst(allPaths.getFirst().get(j));
                        break;
                    }
                    if (this.structMaze.get(allPaths.get(i).getLast())
                            .contains(allPaths.getFirst().get(j))) {
                        allPaths.get(i).add(allPaths.getFirst().get(j));
                        break;
                    }
                }

            }
        }
        List<List<Integer>> connectedPairs = this.transformToPairs(allPaths);
        this.addBonusRooms(connectedPairs);
        return connectedPairs;
    }

    private List<List<Integer>> transformToPairs(List<List<Integer>> allPaths) {
        List<List<Integer>> pairs = new ArrayList<>();
        for (List<Integer> allPath : allPaths) {
            for (int j = 0; j < allPath.size() - 1; ++j) {
                pairs.add(Arrays.asList(allPath.get(j), allPath.get(j + 1)));
            }
        }
        return pairs;
    }

    private List<List<Integer>> getFreeRooms(List<List<Integer>> connectedPairs) {
        connectedPairs.forEach(Collections::sort);
        List<List<Integer>> free_rooms = new ArrayList<>();
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if ((structMaze.get(i).contains(j) || structMaze.get(j).contains(i))
                        && (!connectedPairs.contains(Arrays.asList(Math.min(i, j), Math.max(i, j))))) {
                    if (!free_rooms.contains(Arrays.asList(Math.min(i, j), Math.max(i, j)))) {
                        free_rooms.add(Arrays.asList(Math.min(i, j), Math.max(i, j)));
                    }
                }
            }
        }
        return free_rooms;
    }

    private void addBonusRooms(List<List<Integer>> allPaths) {
        List<List<Integer>> freeRooms = this.getFreeRooms(allPaths);
        Random random = new Random();
        int choises = random.nextInt(1, freeRooms.size());
        for (int i = 0; i < choises; ++i) {
            Collections.shuffle(freeRooms);
            allPaths.add(freeRooms.getFirst());
            freeRooms.removeFirst();
        }
    }

}