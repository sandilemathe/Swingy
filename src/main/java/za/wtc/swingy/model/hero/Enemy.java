package za.wtc.swingy.model.hero;


public abstract class Enemy extends Hero  {

    /**
     * Enemy constructor: set the 
     * {@code level} of the enemy.
     * 
     * @param level The level of the enemy.
     */
    public Enemy(int level) {
        this.level = level;
    }
}