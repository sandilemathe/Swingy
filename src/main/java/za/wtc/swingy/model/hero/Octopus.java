package za.wtc.swingy.model.hero;


public class Octopus extends Hero {

    /**
     * Since Octopus class is
     * a subclass of Hero class,
     * then invoke the Hero constructor.
     */
    public Octopus() {

        super();
    }
    
    /**
     * Crab constructor: when creating a
     * new hero of type "Octopus", the {@code type}
     * is set to "Octopus" and {@code attack}, 
     * {@code defense} & {@code hitPoints} are set
     * to 10, 3 & 75 respectively.
     * 
     * @param name The name of the hero.
     */
    public Octopus(String name) {
        super(name);
        this.type = "Octopus";
        this.attack += 10;
        this.defense += 3;
        this.hitPoints += 75;
    }
}