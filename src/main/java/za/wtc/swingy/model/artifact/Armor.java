package za.wtc.swingy.model.artifact;

import lombok.Getter;


@Getter
public class Armor extends Artifact {
    
    private int defense;

    /**
     * Armor constructor: since Armor extends
     * Artifact class, the Artifact constructor
     * with macthing parameter {@code name} will be
     * called. Set the {@code type} to "ARMOR" and
     * {@code defense} to the parameter. 
     * 
     * @param name The name of the artifact.
     * @param defense The armor's defense.
     */
    public Armor(String name, int defense) {
        super(name);
        this.type = ArtifactEnum.ARMOR;
        this.defense = defense; 
    }
}