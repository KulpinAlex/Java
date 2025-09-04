package Domain.characters.monsters;

import Domain.Level;

public class Vampire extends Monster {
    private String type = "Vampire";

    private boolean firstHit; ///< первый удар по вампиру всегда промах

    public Vampire(Level level) {
        super(PropertyLevel.HIGH.getLevel(), PropertyLevel.HIGH.getLevel(), PropertyLevel.MEDIUM.getLevel(), level);
        this.hostility = PropertyLevel.HIGH.getLevel();
        this.firstHit = false;
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public String getSymbol() {
        return "V";
    }

    @Override
    public int getColorCode() {
        return 2; // красный
    }

    @Override
    public void moveByPattern() {}

    @Override
    public boolean isFirstHitTrue() {
        return this.firstHit;
    }

    @Override
    public void setFirstHitTrue() {
        this.firstHit = true;
    }

    @Override
    public String attack() {

        if (this.isHitSuccessful(this.level.getPlayer())) {

            this.cutPlayerMaxHealth();
            double damage = this.hit(this.level.getPlayer());

            if (!this.level.getPlayer().isAlive()) {
                return "You have been killed by the Vampire";
            }

            return "Vampire injures you! [-" + String.format("%.1f", damage) + " HP]";
        }

        return "Vampire barely misses you";
    }

    /**
     * Отнимает некоторое количество максимального уровня здоровья игроку при успешной атаке.
     */
    private void cutPlayerMaxHealth() {
        double maxHealth = this.level.getPlayer().getMaxHealth();
        maxHealth *= 0.9;
        this.level.getPlayer().setMaxHealth(maxHealth);
    }

}
