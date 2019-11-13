package za.wtc.swingy.model.hero;

public class Cuttlefish extends Hero {

    /**
     * Since Cuttlefish class is
     * a subclass of Hero class,
     * then invoke the Hero constructor.
     */
    public Cuttlefish() {
        super();
    }
    
    /**
     * Cuttlefish constructor: when creating a
     * new hero of type "Cuttlefish", the {@code type}
     * is set to "Crab" and {@code attack}, 
     * {@code defense} & {@code hitPoints} are set
     * to 8, 2 & 50 respectively.
     * 
     * @param name The name of the hero.
     */
    public Cuttlefish(String name) {
        super(name);
        this.type = "Cuttlefish";
        this.attack += 8;
        this.defense += 2;
        this.hitPoints += 50;
    }
}