package Domain.characters.monsters;

import Domain.Level;

public enum MonsterType {
    ZOMBIE,
    GHOST,
    VAMPIRE,
    OGRE,
    SNAKE,
    MIMIC;

    public Monster createMonster(Level level) {
      return switch (this) {
        case ZOMBIE -> new Zombie(level);
        case GHOST -> new Ghost(level);
        case VAMPIRE -> new Vampire(level);
        case OGRE -> new Ogre(level);
        case SNAKE -> new Snake(level);
        case MIMIC -> new Mimic(level);
        default -> throw new IllegalArgumentException("Unknown monster type");
      };
    }
}
