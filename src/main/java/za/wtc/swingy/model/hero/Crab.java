package za.wtc.swingy.model.hero;


public class Crab extends Hero {

    /**
     * Since Crab class is
     * a subclass of Hero class,
     * then invoke the Hero constructor.
     */
    public Crab() {
        super(); 
    }

    /**
     * Crab constructor: when creating a
     * new hero of type "Crab", the {@code type}
     * is set to "Crab" and {@code attack}, 
     * {@code defense} & {@code hitPoints} are set
     * to 6, 1 & 25 respectively.
     * 
     * @param name The name of the hero.
     */
    public Crab(String name) {
        super(name);
        this.type = "Crab";
        this.attack += 6;
        this.defense += 1;
        this.hitPoints += 25;
    }
}