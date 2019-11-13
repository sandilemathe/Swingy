package za.wtc.swingy.controller;

import za.wtc.swingy.model.hero.Hero;
import za.wtc.swingy.model.hero.Crab;
import za.wtc.swingy.model.hero.Octopus;
import za.wtc.swingy.model.hero.HeroEnum;
import za.wtc.swingy.model.hero.Horsefish;
import za.wtc.swingy.model.hero.Jellyfish;
import za.wtc.swingy.model.hero.Cuttlefish;


public abstract class HeroFactory {

    private static Hero newHero;
    private static Hero newEnemy;

    /**
     * Creates new hero, depending on the hero {@code type} with
     * the given {@code name}
     * 
     * @param name Name of the hero.
     * @param type Hero type.
     * @return the newly created hero.
     */
    public static Hero newHero(String name, HeroEnum type) {
        switch (type) {
            case CRAB:
                newHero = new Crab(name);
                break;
            case CUTTLEFISH:
                newHero = new Cuttlefish(name);
                break;
            case OCTOPUS:
                newHero = new Octopus(name);
                break;
            default:
                break;
        }
        return newHero;
    }

    /**
     * Creates new Hero(enemy) that will spawned to the map, 
     * depending on the hero(enemy) {@code type} with the given name.
     * 
     * @param level The level of the hero(enemy).
     * @param type Hero(type) type.
     * @return the newly created hero(enemy).
     */
    public static Hero newEnemy(Hero hero, HeroEnum type) {
        switch (type) {
            case HORSEFISH:
                newEnemy = new Horsefish(hero.getLevel());
                break;
            case JELLYFISH:
                newEnemy = new Jellyfish(hero.getLevel());
                break;
            default:
                break;
        }
        return newEnemy;
    }
}