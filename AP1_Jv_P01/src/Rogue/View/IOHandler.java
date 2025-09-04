package View;

import Domain.GameSession;
import jcurses.system.Toolkit;
import Constants.GameConstants;
import Datalayer.Load;
import Datalayer.Stats;

public class IOHandler {
    private int chr;

    public char getChr() {
        return (char) chr;
    }

    public void handleInput() {
        this.chr = Toolkit.readCharacter().getCode();
    }

    public GameSession handleMenu(Render render, GameSession session) {
        render.clearRender();
        boolean isFirst = session == null;
        while (true) {
            render.printArt();
            String menu_choice = this.initMainMenu(render, isFirst);
            switch (menu_choice) {
                case "Continue":
                    return session;

                case "New game":
                    String playerName = "";
                    render.clearRender();
                    while (true) {
                        render.printInputPlayerName(playerName);
                        this.handleInput();

                        if (!playerName.isEmpty() && GameConstants.KEY_DELETE.contains(this.chr)) {
                            playerName = playerName.substring(0, playerName.length() - 1);
                            continue;
                        }
                        if (GameConstants.KEY_ENTER.contains(this.chr)) {
                            break;
                        }
                        playerName += (char) this.chr;
                    }
                    if (playerName.isEmpty()) {
                        playerName = "Adventurer";
                    }
                    return new GameSession(playerName);

                case "Load game":
                    try {
                        return (new Load()).loadGame();
                    } catch (Exception e) {
                        render.printErrorStats("Error loading game:\n" + e);
                        this.handleInput();
                        render.clearRender();
                    }
                    break;

                case "Save game":
                    try {
                        session.saveGame();
                        render.printErrorStats("Game was successfully saved");
                    } catch (Exception e) {
                        render.printErrorStats("Error saving game:\n" + e);
                    }
                    this.handleInput();
                    render.clearRender();
                    break;

                case "Scoreboard":
                    try {
                        render.printScoreboard((new Stats()).loadStats());
                    } catch (Exception e) {
                        render.printErrorStats("Error loading statistics:\n" + e);
                    }
                    this.handleInput();
                    render.clearRender();
                    break;

                case "Help":
                    render.printHelp();
                    this.handleInput();
                    render.clearRender();
                    break;

                case "Exit":
                    return null;

                default:
                    break;
            }
        }
    }

    private String initMainMenu(Render render, boolean isFirst) {
        int currentButtonIndex = 0;
        int lastButtonIndex = 0;
        String[] menuButtons = isFirst ? GameConstants.FIRST_MENU_BUTTONS : GameConstants.MAIN_MENU_BUTTONS;
        while (true) {
            render.printMenu(currentButtonIndex, lastButtonIndex, menuButtons);
            this.handleInput();
            lastButtonIndex = currentButtonIndex;
            if (GameConstants.KEY_UP.contains(this.chr) && currentButtonIndex != 0) {
                currentButtonIndex--;
            } else if (GameConstants.KEY_DOWN.contains(this.chr) && (currentButtonIndex != menuButtons.length - 1)) {
                currentButtonIndex++;
            } else if (GameConstants.KEY_ENTER.contains(this.chr)) {
                return menuButtons[currentButtonIndex];
            }
        }
    }
}