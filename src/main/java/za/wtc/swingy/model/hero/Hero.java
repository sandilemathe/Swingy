package za.wtc.swingy.model.hero;

import lombok.Getter;
import lombok.Setter;

import za.wtc.swingy.view.MapView;
import za.wtc.swingy.model.artifact.Helm;
import za.wtc.swingy.model.artifact.Armor;
import za.wtc.swingy.model.artifact.Weapon;
import za.wtc.swingy.model.artifact.Artifact;
import za.wtc.swingy.model.artifact.ArtifactEnum;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import static za.wtc.swingy.tools.Log.*;
import static za.wtc.swingy.tools.Colors.*;


@Getter
@Setter
public abstract class Hero {

    @NotNull
    @Size(min = 2, max = 30)
    protected String name;

    protected int level;
    protected int attack;
    protected int defense;
    protected String type;
    protected int hitPoints;
    protected int experience;
    protected int xCoordinate;
    protected int yCoordinate;
    protected Armor armor;
    protected Helm helm;
    protected Weapon weapon;
    private MapView observer;

    /**
     * Empty constructor, to instantiate
     * the class with no parameters.
     */
    Hero() {

    }

    /**
     * Hero constructor: when creating
     * a hero, set the parameter to the 
     * {@code name}, and initialize the
     * {@code level} & {@code experience}
     * to 1 and 0 respectively.
     * 
     * @param name The name of the Hero.
     */
    Hero(String name) {
        this.name = name;
        this.level = 1;
        this.experience = 0;

        Armor defaultArmor = new  Armor("Default Armor", 1);
        Helm defaultHelm = new Helm("Default Helm", 1);
        Weapon defaultWeapon = new Weapon("Default Weapon", 1);

        equipHero(defaultArmor, ArtifactEnum.ARMOR);
        equipHero(defaultHelm, ArtifactEnum.HELM);
        equipHero(defaultWeapon, ArtifactEnum.WEAPON);
    }

    /**
     * When the object {@code map} of the
     * {@code MapView} changes state, the
     * {@code observer} object is notified
     * and updated automatically.
     *  
     * @param map The MapView object.
     */
	public void register(MapView map) {
       observer = map; 
    }
    
    /**
     * After updating the hero's position,
     * update the state of the map based on
     * the newly updated hero's position
     */
    private void updateMap() {
        observer.updateHeroPosition();
    }

    /**
     * Set the x and the y coordinates
     * of the position to the parameters
     * respectively and update the state
     * of the map.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public void setPosition(int x, int y) {
        this.xCoordinate += x;
        this.yCoordinate += y;
        updateMap();
    }

    /**
     * When attacking, just apply the
     * hero's {@code attack} as a damage
     * to the defender.
     * 
     * @param hero The attackee.
     */
    public void attack(Hero enemy) {
        int earnedExperience = 0;

        enemy.defend(this.attack);
        if (enemy.getHitPoints() <= 0) {
            if (enemy.getType().equals("Horsefish")) {
                earnedExperience = (int) (Math.ceil((float)this.level / 2) * 750);
                this.experience += earnedExperience;
            } else if (enemy.getType().equals("Jellyfish")) {
                earnedExperience = (int) (Math.ceil((float)this.level / 2) * 500);
                this.experience += earnedExperience;
            }
            log(ANSI_CYAN + " >>> Well Done, Your Earned " + earnedExperience + "XP" + ANSI_RESET);
            if (this.experience >= (this.level * 1000 + Math.pow(this.level - 1, 2) * 450)) {
                levelUp();
            }
        }
    }

    /**
     * When the hero is defending, to get 
     * the actual damage caused by the
     * enemy, subtract the hero's {@code defense}
     * from the taken damage. If the actual damage
     * is less or equal to 0, reset the actual damage
     * to 1. Subtract the actual damaged caused from
     * the hero's life i.e {@code hitPoints}.
     * 
     * @param enemyDamage The damage taken.
     */
    public void defend(int enemyDamage) {
        int damage = enemyDamage - this.defense;

        if (damage <= 0) {
            damage = 1;
        }
        if (damage > 0) {
            this.hitPoints -= damage;
        }
    }

    /**
     * Level up the hero.
     */
    private void levelUp() {
        int levelUp;
 
        this.level++;
        levelUp = this.level;
        this.attack += levelUp;
        this.hitPoints += levelUp;
        this.defense += 1;
    }

    /**
     * Equip the hero with corresponfing artifact. If the object of
     * the {@code Artifact} is {@code null} assign it the {@code artifact}
     * parameter.
     *  
     * @param artifact The Artifact object.
     * @param type The type of Artifacts.
     */
	public void equipHero(Artifact artifact, ArtifactEnum type) {
        switch (type) {
            case ARMOR:
                if (armor != null) {
                    defense -= armor.getDefense(); 
                } else {
                    armor = (Armor)artifact;
                    defense += armor.getDefense();
                }
                break;
            case HELM:
                if (helm != null) {
                    hitPoints -= helm.getHitPoints();
                } else {
                    helm = (Helm)artifact;
                    hitPoints += helm.getHitPoints();
                }
                break;
            case WEAPON:
                if (weapon != null) {
                    attack -= weapon.getAttack();
                } else {
                    weapon = (Weapon)artifact;
                    attack += weapon.getAttack();
                }
                break;
        }
    }
}
