package Controller;

import Domain.Items.*;
import View.IOHandler;
import View.Render;
import Domain.characters.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Constants.GameConstants;
import Domain.GameSession;
import Datalayer.Stats;
import Domain.Coordinates;

public class Controller {

    private final IOHandler ioHandler;
    private final Render render;
    private GameSession gameSession;

    public Controller(IOHandler handler, GameSession session, Render render) {
        this.ioHandler = handler;
        this.render = render;
        this.gameSession = session;
    }

    public void runGameSession() {

        render.startLevelRender(gameSession);
        Set<Integer> unionMoveKey = new HashSet<>(GameConstants.KEY_UP);
        unionMoveKey.addAll(GameConstants.KEY_DOWN);
        unionMoveKey.addAll(GameConstants.KEY_LEFT);
        unionMoveKey.addAll(GameConstants.KEY_RIGHT);

        while (true) {
            gameSession.clearMessage();
            ioHandler.handleInput();
            char input = ioHandler.getChr();
            if (unionMoveKey.contains((int) input)) {
                handleMovement(input);
            } else if (GameConstants.INVENTORY_KEYS.contains((int) input)) {
                List<Item> selectedItems = gameSession.handleInventorySelection(input);
                render.updateMessageBar(gameSession.getMessage_bar());
                if (selectedItems != null && !selectedItems.isEmpty()) {
                    ioHandler.handleInput();
                    if (ioHandler.getChr() >= '1' && ioHandler.getChr() <= '9') {
                        if (ioHandler.getChr() - '1' < selectedItems.size()) {
                            String msg = gameSession.getPlayer().useItem(selectedItems.get(ioHandler.getChr() - '1'));
                            gameSession.setMessage_bar(msg);
                            gameSession.getPlayer().getInventory()
                                    .removeItem(selectedItems.get(ioHandler.getChr() - '1'));
                        }

                    } else {
                        gameSession.setMessage_bar("Invalid item selection!");
                    }
                }
                render.updateMessageBar(gameSession.getMessage_bar());
            }

            if (ioHandler.getChr() == 27) {
                gameSession = ioHandler.handleMenu(render, gameSession);
                if (gameSession == null) {
                    return;
                }
                render.clearRender();
                render.startLevelRender(gameSession);
            }

            /* если игрок убит, выходим из игры */
            if (!gameSession.getPlayer().isAlive()) {
                render.loseArt();
                ioHandler.handleInput();
                this.sendingStats();
                gameSession = ioHandler.handleMenu(render, null);
                if (gameSession != null) {
                    render.startLevelRender(gameSession);
                } else {
                    break;
                }
            }

            if (gameSession.isExitDoor()) {
                render.clearRender();
                if (gameSession.getLevelNumber() == GameConstants.MAX_LEVEL) {
                    render.winArt();
                    ioHandler.handleInput();
                    this.sendingStats();
                    gameSession = ioHandler.handleMenu(render, null);
                    if (gameSession != null) {
                        render.startLevelRender(gameSession);
                        continue;
                    } else {
                        break;
                    }
                }
                gameSession.createNewLevel();
                try {
                    gameSession.saveGame();
                    render.printErrorStats("Game was successfully saved");
                } catch (Exception e) {
                    render.printErrorStats("Error saving game:\n" + e);
                }
                ioHandler.handleInput();
                render.startLevelRender(gameSession);
            }
            gameSession.update_field();
            render.renderTile(gameSession);
        }

    }

    private void handleMovement(char direction) {
        Direction dir;
        switch (direction) {
            case 'w':
                dir = Direction.UP;
                break;
            case 's':
                dir = Direction.DOWN;
                break;
            case 'a':
                dir = Direction.LEFT;
                break;
            case 'd':
                dir = Direction.RIGHT;
                break;
            default:
                return;
        }

        Coordinates shift = dir.getCoordinatesShift();
        int newX = gameSession.getPlayer().getCoordinates().getX() + shift.getX();
        int newY = gameSession.getPlayer().getCoordinates().getY() + shift.getY();

        if (newX < 0 || newX >= GameConstants.MAP_WIDTH ||
                newY < 0 || newY >= GameConstants.MAP_HEIGHT) {
            return;
        }

        this.gameSession.checkItemPickup(newX, newY);

        gameSession.move(dir);
        gameSession.updateStats();
        gameSession.getPlayer().endTurn();
    }

    private void sendingStats() {
        Stats stats = new Stats();
        List<Stats> statistic = new ArrayList<>();
        try {
            statistic = stats.loadStats();
        } catch (Exception e) {
            render.printErrorStats("Error loading statistics:\n" + e);
            ioHandler.handleInput();
        }
        stats.addSessionStatistic(statistic, this.gameSession);
        try {
            stats.saveStats(statistic);
        } catch (Exception e) {
            render.printErrorStats("Error saving statistics:\n" + e);
            ioHandler.handleInput();
        }
    }
}