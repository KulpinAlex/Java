package View;

import Constants.GameConstants;
import Datalayer.Stats;
import Domain.GameSession;

import jcurses.system.CharColor;
import jcurses.system.Toolkit;

import java.util.List;
import java.util.Locale;

public class Render {
    private final CharColor[] colors;
    private final int maxWidth;
    private final int maxHeight;

    public Render() {
        Locale.setDefault(Locale.US);
        this.maxWidth = Toolkit.getScreenWidth();
        this.maxHeight = Toolkit.getScreenHeight();
        this.colors = new CharColor[] {
                new CharColor(CharColor.BLACK, CharColor.WHITE),
                new CharColor(CharColor.MAGENTA, CharColor.MAGENTA),
                new CharColor(CharColor.BLACK, CharColor.RED),
                new CharColor(CharColor.BLACK, CharColor.CYAN),
                new CharColor(CharColor.BLACK, CharColor.BLUE),
                new CharColor(CharColor.BLACK, CharColor.GREEN),
                new CharColor(CharColor.BLACK, CharColor.YELLOW),
                new CharColor(CharColor.BLACK, CharColor.BLACK),
                new CharColor(CharColor.WHITE, CharColor.BLACK),
        };
    }

    public void printString(String text, int x, int y, CharColor color) {
        short background_color = color.getBackground();
        CharColor space_color = new CharColor(background_color, background_color);
        int space_pos = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                Toolkit.printString(text.substring(space_pos, i), x + space_pos, y, color);
                Toolkit.printString(".", x + i, y, space_color);
                space_pos = i + 1;
            }
            if (c == '\n') {
                Toolkit.printString(text.substring(space_pos, i), x + space_pos, y, color);
                this.printString(text.substring(i + 1), x, y + 1, color);
                return;
            }
        }
        Toolkit.printString(text.substring(space_pos), x + space_pos, y, color);
    }

    public void renderTile(GameSession session) {
        this.colors[6].setColorAttribute((short) 2);
        updateMessageBar(session.getMessage_bar());
        for (int i = 0; i < GameConstants.MAP_WIDTH; i++) {
            for (int j = 0; j < GameConstants.MAP_HEIGHT; j++) {
                if (session.getLevel().getField()[i][j].isVisited()) {
                    if (!session.getLevel().getField()[i][j].isBlocked_sight()) {
                        Toolkit.printString(session.getLevel().getField()[i][j].getSymb(), i + 1, j + 3,
                                this.colors[session.getLevel().getField()[i][j].getColor()]);
                    } else {
                        Toolkit.printString("*", i + 1, j + 3, colors[7]);
                    }
                }
            }
        }
        printStatusBar(session);
    }

    public void startLevelRender(GameSession session) {
        Toolkit.clearScreen(colors[7]);
        for (int i = 0; i < GameConstants.MAP_WIDTH; i++) {
            for (int j = 0; j < GameConstants.MAP_HEIGHT; j++) {
                Toolkit.printString(".", i + 1, j + 3, colors[7]);
            }
        }
        Toolkit.printString(".", 0, 2, colors[0]);
        Toolkit.printString("'", 0, GameConstants.MAP_HEIGHT + 3, colors[0]);
        Toolkit.printString(".", GameConstants.MAP_WIDTH + 1, 2, colors[0]);
        Toolkit.printString("'", GameConstants.MAP_WIDTH + 1, GameConstants.MAP_HEIGHT + 3, colors[0]);
        for (int i = 0; i < GameConstants.MAP_WIDTH; i++) {
            Toolkit.printString("_", i + 1, 2, colors[0]);
            Toolkit.printString("-", i + 1, GameConstants.MAP_HEIGHT + 3, colors[0]);
            for (int j = 0; j < GameConstants.MAP_HEIGHT; j++) {
                if (i == 0) {
                    Toolkit.printString("|", 0, j + 3, colors[0]);
                    Toolkit.printString("|", GameConstants.MAP_WIDTH + 1, j + 3, colors[0]);
                }
            }
        }
        this.colors[6].setColorAttribute((short) 2);
        this.printString("Backpack: (i) | Sword: (h) | Food: (j) | Elixir: (k) | Scroll: (e)", 1,
                GameConstants.MAP_HEIGHT + 5, colors[6]);
        renderTile(session);
    }

    private void printStatusBar(GameSession session) {
        this.printString(" ".repeat(GameConstants.MAP_WIDTH), 1, GameConstants.MAP_HEIGHT + 4, this.colors[6]);
        this.printString(
                "Level: " + session.getLevelNumber() +
                        " | HP: " + String.format("%.1f", session.getPlayer().getHealth()) + "/"
                        + String.format("%.1f", session.getPlayer().getMaxHealth()) +
                        " | Str: " + session.getPlayer().getStrength() +
                        " | Agi: " + session.getPlayer().getAgility() +
                        " | Gold: " + session.getPlayer().getGold(),
                1, GameConstants.MAP_HEIGHT + 4, colors[6]);
    }

    public void printMenu(int currentButtonIndex, int lastButtonIndex, String[] menuButtons) {
        colors[6].setColorAttribute((short) 0);
        if (currentButtonIndex == lastButtonIndex) {
            for (int i = 0; i < menuButtons.length; ++i) {
                int x = this.maxWidth / 2 - menuButtons[i].length() / 2;
                int y = this.maxHeight / 2 + i - menuButtons.length / 2;
                if (i == currentButtonIndex) {
                    this.printString(menuButtons[i], x, y, this.colors[8]);
                } else {
                    this.printString(menuButtons[i], x, y, this.colors[6]);
                }
            }
        } else {
            int xCurr = this.maxWidth / 2 - menuButtons[currentButtonIndex].length() / 2;
            int yCurr = this.maxHeight / 2 + currentButtonIndex - menuButtons.length / 2;
            int xLast = this.maxWidth / 2 - menuButtons[lastButtonIndex].length() / 2;
            int yLast = this.maxHeight / 2 + lastButtonIndex - menuButtons.length / 2;
            this.printString(menuButtons[currentButtonIndex], xCurr, yCurr, this.colors[8]);
            this.printString(menuButtons[lastButtonIndex], xLast, yLast, this.colors[6]);
        }
    }

    public void printErrorLoading() {
        this.clearRender();
        String textError = "Game loading error";
        int x = (this.maxWidth - textError.length()) / 2;
        int y = this.maxHeight / 2;
        this.printString(textError, x, y, this.colors[6]);
    }

    public void printErrorStats(String textError) {
        this.clearRender();
        int x = (this.maxWidth - textError.length()) / 2;
        int y = this.maxHeight / 2;
        this.printString(textError, x, y, this.colors[6]);
    }

    public void printHelp() {
        this.clearRender();
        int x = (this.maxWidth - 50) / 2;
        int y = this.maxHeight / 2 - 5;
        this.printString(GameConstants.textHelp, x, y, this.colors[6]);
    }

    public void printScoreboard(List<Stats> stats) {
        this.clearRender();
        int x = 0;
        int[] x_corr = new int[GameConstants.textHeaders.length];
        for (int i = 0; i < GameConstants.textHeaders.length; ++i) {
            this.printString(GameConstants.textHeaders[i], x, 0, this.colors[6]);

            x = x + 5 + GameConstants.textHeaders[i].length();
            x_corr[i] = x - 5;
        }
        x_corr[0] = 0;
        int rows = Math.min(stats.size(), 10);
        for (int i = 0; i < rows; ++i) {
            Stats print = stats.get(i);
            this.printString(print.getPlayer_name(), x_corr[0], i + 1, this.colors[6]);
            this.printString(print.getGold() + "", x_corr[1] - (print.getGold() + "").length(), i + 1, this.colors[6]);
            this.printString(print.getLevel_number() + "", x_corr[2] - (print.getLevel_number() + "").length(), i + 1,
                    this.colors[6]);
            this.printString(print.getEnemies_defeated() + "", x_corr[3] - (print.getEnemies_defeated() + "").length(),
                    i + 1, this.colors[6]);
            this.printString(print.getEaten_food() + "", x_corr[4] - (print.getEaten_food() + "").length(), i + 1,
                    this.colors[6]);
            this.printString(print.getUsed_elixirs() + "", x_corr[5] - (print.getUsed_elixirs() + "").length(), i + 1,
                    this.colors[6]);
            this.printString(print.getUsed_scrolls() + "", x_corr[6] - (print.getUsed_scrolls() + "").length(), i + 1,
                    this.colors[6]);
            this.printString(print.getHits_dealt() + "", x_corr[7] - (print.getHits_dealt() + "").length(), i + 1,
                    this.colors[6]);
            this.printString(print.getHits_taken() + "", x_corr[8] - (print.getHits_taken() + "").length(), i + 1,
                    this.colors[6]);
            this.printString(print.getTiles_walked() + "", x_corr[9] - (print.getTiles_walked() + "").length(), i + 1,
                    this.colors[6]);
        }
    }

    public void printInputPlayerName(String playerName) {

        String enterPlayerName = "Enter your name:";
        int xEnterName = (this.maxWidth - enterPlayerName.length()) / 2;
        int xPlayerName = (this.maxWidth - playerName.length()) / 2;
        int y = this.maxHeight / 2;
        this.printString(enterPlayerName, xEnterName, y, this.colors[6]);
        this.printString(" ".repeat(playerName.length() + 2), xPlayerName - 1, y + 1, this.colors[6]);
        this.printString(playerName, xPlayerName, y + 1, this.colors[6]);

    }

    public void printArt() {
        if (this.maxWidth >= 135) {
            int x = (this.maxWidth - 135) / 2;
            this.printString(GameConstants.rogue_art, x, 0, this.colors[2]);
        }
    }

    public void loseArt() {
        this.clearRender();
        int x = (this.maxWidth - 145) / 2;
        int y = this.maxHeight / 2 - 5;
        this.printString(GameConstants.loseArt, x, y, this.colors[2]);
    }

    public void winArt() {
        this.clearRender();
        int x = (this.maxWidth - 121) / 2;
        int y = this.maxHeight / 2 - 5;
        this.printString(GameConstants.winArt, x, y, this.colors[5]);
    }

    public void clearRender() {
        Toolkit.clearScreen(colors[6]);
        Toolkit.clearScreen(colors[7]);
        colors[6].setColorAttribute((short) 0);
    }

    public void updateMessageBar(String message) {
        this.printString(" ".repeat(GameConstants.MAP_WIDTH), 1, 0, this.colors[6]);
        this.printString(" ".repeat(GameConstants.MAP_WIDTH), 1, 1, this.colors[6]);
        this.printString(message, 1, 0, this.colors[6]);
    }
}
