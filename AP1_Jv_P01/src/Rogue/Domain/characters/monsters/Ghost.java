package Domain.characters.monsters;

import Domain.Level;
import Domain.level_essences.Room;
import Constants.GameConstants;
import Domain.Coordinates;

public class Ghost extends Monster {
    private String type = "Ghost";

    public Ghost(Level level) {
        super(PropertyLevel.LOW.getLevel(), PropertyLevel.HIGH.getLevel(), PropertyLevel.LOW.getLevel(), level);
        this.hostility = PropertyLevel.LOW.getLevel();
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public String getSymbol() {
        return "G";
    }

    @Override
    public int getColorCode() {
        return 0; // белый
    }

    /**
     * Постоянно телепортируется по комнате и становится невидимым через один ход,
     * пока не начал преследование игрока.
     */
    @Override
    protected void moveByPattern() {

        Coordinates newCoordinates = new Coordinates(0, 0);
        int roomNumber = this.findInWhichRoom();

        if (roomNumber != -1) {

            Room currentRoom = this.level.getRooms().get(roomNumber);

            do {
                newCoordinates = Coordinates.generateCoordinates(currentRoom);
            } while (!newCoordinates.isFree(this.level.getField()));

            this.setCoordinates(newCoordinates);
            this.setVisible(!this.isVisible());
        }
    }

    /**
     * Определяет в какой комнате находится призрак.
     * 
     * @return номер комнаты
     */
    private int findInWhichRoom() {

        for (int room = 0; room < GameConstants.MAP_ROOMS; room++) {

            Room currentRoom = this.level.getRooms().get(room);

            int xLeftUp = currentRoom.getX_left_up();
            int yLeftUp = currentRoom.getY_left_up();
            int xRightDown = currentRoom.getX_right_down();
            int yRightDown = currentRoom.getY_right_down();

            int x = this.coordinates.getX();
            int y = this.coordinates.getY();

            if (x >= xLeftUp && x <= xRightDown && y >= yLeftUp && y <= yRightDown) {
                return room;
            }
        }

        /* если не нашли комнату */
        return -1;
    }

}
