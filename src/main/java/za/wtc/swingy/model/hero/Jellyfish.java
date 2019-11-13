package za.wtc.swingy.model.hero;


public class Jellyfish extends Enemy {
    
    /**
     * Jellyfish constructor: when creating an enemy
     * of type "Jellyfish", {@code type}
     * is set to "Horsefish" and {@code attack}, 
     * {@code defense} & {@code hitPoints} are set
     * to 5, 2 & 12 plus its level respectively.
     * 
     * @param level The level of the enemy.
     */
    public Jellyfish(int level) {
        super(level);
        this.name = "Blue Jellyfish";
        this.type = "Jellyfish";
        this.attack = level + 5;
        this.defense = level + 2;
        this.hitPoints = level + 12;
    }
}