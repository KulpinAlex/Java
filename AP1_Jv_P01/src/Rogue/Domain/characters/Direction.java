package Domain.characters;

import Domain.Coordinates;

public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT,
    DIAGONAL_UP_LEFT,
    DIAGONAL_DOWN_LEFT,
    DIAGONAL_UP_RIGHT,
    DIAGONAL_DOWN_RIGHT;

    /**
     * Определяет сдвиг координаты по X и Y для шага в нужном направлении.
     * @return значения, которые нужно прибавить к X и Y координаты начала движения.
     */
    public Coordinates getCoordinatesShift() {
      return switch (this) {
        case UP -> new Coordinates(0, -1);
        case DOWN -> new Coordinates(0, 1);
        case RIGHT -> new Coordinates(1, 0);
        case LEFT -> new Coordinates(-1, 0);
        case DIAGONAL_UP_LEFT -> new Coordinates(-1, -1);
        case DIAGONAL_DOWN_LEFT -> new Coordinates(-1, 1);
        case DIAGONAL_UP_RIGHT -> new Coordinates(1, -1);
        case DIAGONAL_DOWN_RIGHT -> new Coordinates(1, 1);
        default -> new Coordinates(0, 0);
      };
    }
}