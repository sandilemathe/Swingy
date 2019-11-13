package za.wtc.swingy.model.artifact;

import lombok.Getter;

@Getter
public class Weapon extends Artifact {
    
    private int attack;

    /**
     * Weapon constructor: since Weapon extends
     * Artifact class, the Artifact constructor
     * with macthing parameter {@code name} will be
     * called. Set the {@code type} to "WEAPON" and
     * {@code attack} to the parameter.
     * 
     * @param name The name of the artifact
     * @param attack The weapon's damage.
     */
    public Weapon(String name, int attack) {
        super(name);
        this.type = ArtifactEnum.WEAPON;
        this.attack = attack;
    }
}