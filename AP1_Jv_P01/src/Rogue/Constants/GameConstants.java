package Constants;

import jcurses.system.InputChar;
import java.util.Set;

public final class GameConstants {
        public static final int MAP_WIDTH = 90;
        public static final int MAP_HEIGHT = 30;
        public static final int MAP_ROOMS = 9;
        public static final int MAX_ROOM_SIZE = 18;
        public static final int MIN_ROOM_SIZE = 5;
        public static final int MAX_SPACE = 9;
        public static final int MAX_LEVEL = 21;

        public static final int MAX_MONSTERS_PER_ROOM = 2;
        public static final int LEVEL_ADJUST_DIFFICULTY = 10;

        public static final int UNINITIALIZED = -1;
        public static final int ON_FIELD = 0;
        public static final int IN_INVENTORY = 1;
        public static final int REMOVED = 2;

        public static final int MAX_HEALTH = 30;

        // ITEMS
        public static final int WEAPON = 0;
        public static final int FOOD = 1;
        public static final int SCROLL = 2;
        public static final int ELIXIR = 3;
        public static final int TREASURE = 5;

        // ELIXIR
        public static final int HEALTH_ELIXIR = 0;
        public static final int STRENGTH_ELIXIR = 1;
        public static final int AGILITY_ELIXIR = 2;

        // SCROLL
        public static final int STRENGTH_SCROLL = 0;
        public static final int CURSED_STRENGTH_SCROLL = 1;
        public static final int AGILITY_SCROLL = 2;
        public static final int CURSED_AGILITY_SCROLL = 3;
        public static final int MAX_HEALTH_SCROLL = 4;

        // WEAPON
        public static final int AXE = 0;
        public static final int SCYTHE = 1;
        public static final int BOW = 2;
        public static final int HAMMER = 3;
        public static final int SPEAR = 4;

        public static final char WEAPON_SYMBOL = 'W';
        public static final char FOOD_SYMBOL = 'F';
        public static final char SCROLL_SYMBOL = 'S';
        public static final char ELIXIR_SYMBOL = 'E';
        public static final char TREASURE_SYMBOL = '$';

        // FIGHT
        public static final double AGILITY_FACTOR = 0.3;
        public static final double INITIAL_HIT_CHANCE = 0.5;
        public static final double STRENGTH_FACTOR = 0.3;

        public static final String[] FIRST_MENU_BUTTONS = { "New game", "Load game", "Scoreboard", "Help", "Exit" };
        public static final String[] MAIN_MENU_BUTTONS = { "Continue", "New game", "Load game", "Save game",
                        "Scoreboard",
                        "Help", "Exit" };
        public static final String[] MENU_OPTIONS = { "Weapons", "Food", "Elixirs", "Scrolls", "View All Items",
                        "Back" };
        public static final Set<Integer> KEY_UP = Set.of(InputChar.KEY_UP, (int) 'w', (int) 'W');
        public static final Set<Integer> KEY_RIGHT = Set.of(InputChar.KEY_RIGHT, (int) 'd', (int) 'D');
        public static final Set<Integer> KEY_LEFT = Set.of(InputChar.KEY_LEFT, (int) 'a', (int) 'A');
        public static final Set<Integer> KEY_DOWN = Set.of(InputChar.KEY_DOWN, (int) 's', (int) 'S');
        public static final Set<Integer> KEY_ENTER = Set.of(10, 13);
        public static final Set<Integer> KEY_DELETE = Set.of(263, 330);
        public static final int KEY_USE_WEAPON = 'h';
        public static final int KEY_USE_FOOD = 'j';
        public static final int KEY_USE_ELIXIR = 'k';
        public static final int KEY_USE_SCROLL = 'e';
        public static final int KEY_OPEN_INVENTORY = 'i';
        public static final Set<Integer> INVENTORY_KEYS = Set.of(KEY_USE_WEAPON, KEY_USE_FOOD, KEY_USE_ELIXIR,
                        KEY_USE_SCROLL, KEY_OPEN_INVENTORY);
        public static final String STATS_FILE_PATH = "Rogue/Datalayer/statistics.json";
        public static final String SAVE_FILE_PATH = "Rogue/Datalayer/saving.json";
        public static final String textHelp = """
                               Main menu:              ESC     \s
                               Backpack:               i     \s
                               Equip a sword:          h     \s
                               Eat food:               j     \s
                               Read a scroll:          e     \s
                               Drink elixir:           k     \s
                               Attack an enemy:        move towards the enemy   \s
                               Movements:              w,a,s,d  \
                        """;

        public static final String[] textHeaders = { "Name", "Treasures", "Level", "Enemies", "Food", "Elixirs",
                        "Scrolls", "Hits dealt", "Hits Taken", "Moves" };

        public static final String rogue_art = """
                        ____/\\\\\\\\\\\\\\\\\\_____        _______/\\\\\\\\\\______        _____/\\\\\\\\\\\\\\\\\\\\\\\\_        __/\\\\\\________/\\\\\\_        __/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_       \s
                         __/\\\\\\///////\\\\\\___        _____/\\\\\\///\\\\\\____        ___/\\\\\\//////////__        _\\/\\\\\\_______\\/\\\\\\_        _\\/\\\\\\///////////__      \s
                          _\\/\\\\\\_____\\/\\\\\\___        ___/\\\\\\/__\\///\\\\\\__        __/\\\\\\_____________        _\\/\\\\\\_______\\/\\\\\\_        _\\/\\\\\\_____________     \s
                           _\\/\\\\\\\\\\\\\\\\\\\\\\/____        __/\\\\\\______\\//\\\\\\_        _\\/\\\\\\____/\\\\\\\\\\\\\\_        _\\/\\\\\\_______\\/\\\\\\_        _\\/\\\\\\\\\\\\\\\\\\\\\\_____    \s
                            _\\/\\\\\\//////\\\\\\____        _\\/\\\\\\_______\\/\\\\\\_        _\\/\\\\\\___\\/////\\\\\\_        _\\/\\\\\\_______\\/\\\\\\_        _\\/\\\\\\///////______   \s
                             _\\/\\\\\\____\\//\\\\\\___        _\\//\\\\\\______/\\\\\\__        _\\/\\\\\\_______\\/\\\\\\_        _\\/\\\\\\_______\\/\\\\\\_        _\\/\\\\\\_____________  \s
                              _\\/\\\\\\_____\\//\\\\\\__        __\\///\\\\\\__/\\\\\\____        _\\/\\\\\\_______\\/\\\\\\_        _\\//\\\\\\______/\\\\\\__        _\\/\\\\\\_____________ \s
                               _\\/\\\\\\______\\//\\\\\\_        ____\\///\\\\\\\\\\/_____        _\\//\\\\\\\\\\\\\\\\\\\\\\\\/__        __\\///\\\\\\\\\\\\\\\\\\/___        _\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_\s
                                _\\///________\\///__        ______\\/////_______        __\\////////////____        ____\\/////////_____        _\\///////////////__
                        """;
        public static final String winArt = """
                        __/\\\\\\________/\\\\\\________________________________________/\\\\\\______________/\\\\\\_________________________/\\\\\\____       \s
                         _\\///\\\\\\____/\\\\\\/________________________________________\\/\\\\\\_____________\\/\\\\\\_______________________/\\\\\\\\\\\\\\__      \s
                          ___\\///\\\\\\/\\\\\\/__________________________________________\\/\\\\\\_____________\\/\\\\\\__/\\\\\\________________/\\\\\\\\\\\\\\\\\\_     \s
                           _____\\///\\\\\\/__________/\\\\\\\\\\_____/\\\\\\____/\\\\\\___________\\//\\\\\\____/\\\\\\____/\\\\\\__\\///___/\\\\/\\\\\\\\\\\\___\\//\\\\\\\\\\\\\\__    \s
                            _______\\/\\\\\\_________/\\\\\\///\\\\\\__\\/\\\\\\___\\/\\\\\\____________\\//\\\\\\__/\\\\\\\\\\__/\\\\\\____/\\\\\\_\\/\\\\\\////\\\\\\___\\//\\\\\\\\\\___   \s
                             _______\\/\\\\\\________/\\\\\\__\\//\\\\\\_\\/\\\\\\___\\/\\\\\\_____________\\//\\\\\\/\\\\\\/\\\\\\/\\\\\\____\\/\\\\\\_\\/\\\\\\__\\//\\\\\\___\\//\\\\\\____  \s
                              _______\\/\\\\\\_______\\//\\\\\\__/\\\\\\__\\/\\\\\\___\\/\\\\\\______________\\//\\\\\\\\\\\\//\\\\\\\\\\_____\\/\\\\\\_\\/\\\\\\___\\/\\\\\\____\\///_____ \s
                               _______\\/\\\\\\________\\///\\\\\\\\\\/___\\//\\\\\\\\\\\\\\\\\\________________\\//\\\\\\__\\//\\\\\\______\\/\\\\\\_\\/\\\\\\___\\/\\\\\\_____/\\\\\\____\s
                                _______\\///___________\\/////______\\/////////__________________\\///____\\///_______\\///__\\///____\\///_____\\///_____
                        """;
        public static final String loseArt = """
                        __/\\\\\\________/\\\\\\________________________________________/\\\\\\\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\\\\\\\\\\________/\\\\\\____       \s
                         _\\///\\\\\\____/\\\\\\/________________________________________\\/\\\\\\////////\\\\\\__\\/////\\\\\\///__\\/\\\\\\///////////__\\/\\\\\\////////\\\\\\____/\\\\\\\\\\\\\\__      \s
                          ___\\///\\\\\\/\\\\\\/__________________________________________\\/\\\\\\______\\//\\\\\\_____\\/\\\\\\_____\\/\\\\\\_____________\\/\\\\\\______\\//\\\\\\__/\\\\\\\\\\\\\\\\\\_     \s
                           _____\\///\\\\\\/__________/\\\\\\\\\\_____/\\\\\\____/\\\\\\___________\\/\\\\\\_______\\/\\\\\\_____\\/\\\\\\_____\\/\\\\\\\\\\\\\\\\\\\\\\_____\\/\\\\\\_______\\/\\\\\\_\\//\\\\\\\\\\\\\\__    \s
                            _______\\/\\\\\\_________/\\\\\\///\\\\\\__\\/\\\\\\___\\/\\\\\\___________\\/\\\\\\_______\\/\\\\\\_____\\/\\\\\\_____\\/\\\\\\///////______\\/\\\\\\_______\\/\\\\\\__\\//\\\\\\\\\\___   \s
                             _______\\/\\\\\\________/\\\\\\__\\//\\\\\\_\\/\\\\\\___\\/\\\\\\___________\\/\\\\\\_______\\/\\\\\\_____\\/\\\\\\_____\\/\\\\\\_____________\\/\\\\\\_______\\/\\\\\\___\\//\\\\\\____  \s
                              _______\\/\\\\\\_______\\//\\\\\\__/\\\\\\__\\/\\\\\\___\\/\\\\\\___________\\/\\\\\\_______/\\\\\\______\\/\\\\\\_____\\/\\\\\\_____________\\/\\\\\\_______/\\\\\\_____\\///_____ \s
                               _______\\/\\\\\\________\\///\\\\\\\\\\/___\\//\\\\\\\\\\\\\\\\\\____________\\/\\\\\\\\\\\\\\\\\\\\\\\\/____/\\\\\\\\\\\\\\\\\\\\\\_\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_\\/\\\\\\\\\\\\\\\\\\\\\\\\/_______/\\\\\\____\s
                                _______\\///___________\\/////______\\/////////_____________\\////////////_____\\///////////__\\///////////////__\\////////////________\\///_____
                        """;
}
