package za.wtc.swingy.controller;

import java.util.Random;
import java.util.Scanner;

import java.io.IOException;
import java.sql.SQLException;

import za.wtc.swingy.model.hero.Enemy;
import za.wtc.swingy.model.artifact.Helm;
import za.wtc.swingy.model.hero.HeroEnum;
import za.wtc.swingy.model.artifact.Armor;
import za.wtc.swingy.model.artifact.Weapon;
import za.wtc.swingy.database.DatabaseHandler;
import za.wtc.swingy.view.console.ConsoleView;

import static za.wtc.swingy.tools.Log.*;
import static za.wtc.swingy.tools.Colors.*;
import static za.wtc.swingy.StaticGlobal.*;


public class GameController {

    private static int[] previousPosition = new int[2];

    /**
     * Move the hero based on the {@code direction}:
     * <li>1 - North<li>
     * <li>2 - East<li>
     * <li>3 - South<li>
     * <li>4 - West<li>
     * and set the current position of the hero.
     * If the Enemy is encountered the user {@code action()} 
     * will give the user to run or fight the enemy.
     * 
     * @param direction The chosen direction.
     */
    public static void moveHero(int direction) {
        switch (direction) {
        case 1:
            hero.setPosition(-1, 0);
            previousPosition[0] = -1;
            previousPosition[1] = 0;
            break;
        case 2:
            hero.setPosition(0, 1);
            previousPosition[0] = -1;
            previousPosition[1] = 0;
            break;
        case 3:
            hero.setPosition(1, 0);
            previousPosition[0] = -1;
            previousPosition[1] = 0;
            break;
        case 4:
            hero.setPosition(0, -1);
            previousPosition[0] = -1;
            previousPosition[1] = 0;
            break;
        }
        if (map.getMap()[hero.getXCoordinate()][hero.getYCoordinate()] == 'X') {
            int random = new Random().nextInt(3);
            if (random == 2) {
                enemy = (Enemy) HeroFactory.newEnemy(hero, HeroEnum.HORSEFISH);
            } else {
                enemy = (Enemy) HeroFactory.newEnemy(hero, HeroEnum.JELLYFISH);
            }
            if (CONSOLE_MODE == true) {
                action();
            }
        }
    }

    /**
     * When a hero moves to a position occupied by
     * an enemy, the hero has two options.
     * 
     * 1. Fight: {@code fight()}, which engages him in
     * a battle with the enemy.
     * 
     * 2. Run: {@code run()},  which gives him a 50% chance
     * of returning to the position. If the odds arent on his side,
     * he must fight the eneny.
     */
    public static void action() {
        Scanner scanner = new Scanner(System.in);

        log(ANSI_YELLOW + "::: You Are Facing: " + enemy.getName() + ANSI_RESET);
        ConsoleView.displayActions();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Integer choice = Integer.parseInt(line);

            if (choice == 1 || choice == 2) {
                switch (choice) {
                case 1:
                    fight();
                    return;
                case 2:
                    run();
                    return;
                default:
                    break;
                }
            } else {
                log("Try again!");
                ConsoleView.displayActions();
            }
        }

    }

    /**
     * Engages the Hero in a battle with
     * the enemy. If the hero ran and the odds
     * were against him, the enemy will attack first
     * otherwise the hero will attack first.
     */
    public static void fight() {
        if (HERO_RAN == false) {
            while (hero.getHitPoints() > 0 && enemy.getHitPoints() > 0) {
                hero.attack(enemy);
                if (enemy.getHitPoints() > 0) {
                    enemy.attack(hero);
                }
            }
        } else if (HERO_RAN == true) {
            while (hero.getHitPoints() > 0 && enemy.getHitPoints() > 0) {
                enemy.attack(hero);
                if (hero.getHitPoints() > 0) {
                    hero.attack(enemy);
                }
            }
        }
        if (hero.getHitPoints() <= 0) {
            if (CONSOLE_MODE == true) {
                log(ANSI_RED + ">>> You Lost, Game Over!");
                ConsoleView.run();
            }
        } else {
            try {
                DatabaseHandler.getInstance().updateHero(hero);
                hero.setPosition(0, 0);
                battleGains();
                log(ANSI_CYAN + "::: Congratulations, You Won The Battle!");
            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gives the hero a 50% chance of returning to 
     * the previous position. If the odds arent on 
     * their side, the hero must fight the Enemy.
     */
    public static void run() {
        int chance = new Random().nextInt(2);

        if (chance == 1) {
            log(ANSI_PURPLE + ">>> Hahaha, You Can't Run My Friend, We Gonna Fight!" + ANSI_RESET);
            HERO_RAN = true;
            fight();
        } else {
            HERO_RAN = false;
            log(ANSI_RED + ">>> Coward! You Ran Away!" + ANSI_RESET);
            hero.setPosition(previousPosition[0] * -1, previousPosition[1] * -1);
        }
    }

    /**
     * If hero wins a battle, he gains:
     * 1. Experience points, based on the enemy power.
     * 2. An artifact, which he can keep or leave.
     * But winning a battle does guarantee that an arifact
     * will be dropped.
     */
    private static void battleGains() {
        int drop = new Random().nextInt(2);
        boolean artifactIsDropped = drop == 1 ? true : false;

        if (artifactIsDropped == true) {
            ARTIFACT_DROPPED = true;
            try {
                log(ANSI_YELLOW + "::: Artifact is Dropped!");
                String[] artifacts = {"ARMOR", "HELM", "WEAPON", "EXPERIENCE"};
                String artifactType = artifacts[new Random().nextInt(4)];
                int variety = hero.getLevel() + 1;
    
                switch (artifactType) {
                    case "ARMOR":
                        artifact = new Armor("Dropped Armor", variety);
                        int gainedDefense = (((Armor) artifact).getDefense() - hero.getArmor().getDefense());
                        log(ANSI_CYAN + "::: If You Keep This Artifact Your Defense Increases by " + gainedDefense + ".");
                        break;
                    case "HELM":
                        artifact = new Helm("Dropped Helmet", variety);
                        int gainedHitPoints = (((Helm) artifact).getHitPoints() - hero.getHelm().getHitPoints());
                        log(ANSI_CYAN + "::: If You Keep This Artifact Your Hit Point(s) Increase by " + gainedHitPoints + ".");
                        break;
                    case "WEAPON":
                        artifact = new Weapon("Dropped Weapon", variety);
                        int gainedAttack = (((Weapon) artifact).getAttack() - hero.getWeapon().getAttack());
                        log(ANSI_CYAN + "::: If You Keep This Artifact Your Attack Increases by " + gainedAttack + ".");
                        break;
                    case "EXPERIENCE":
                        hero.setHitPoints(hero.getHitPoints() + variety);
                        log(ANSI_YELLOW +"::: Healed Up, Current Health: " + hero.getHitPoints());
                        return;
                }
                // Equip the hero.
                equip(artifactType);
            } catch (Exception exception) {
                exception.printStackTrace();
            } 
        } else if (artifactIsDropped == false) {
            log( ANSI_RED + ">>> Sorry, No Artifact Dropped!");
        }     
    }

    /**
     * Equip the hero with the chosen artifact.
     * The hero wont be equiped if the Experience
     * is chosen instead of an Artifact.
     * 
     * @param artifactType The artifact type.
     */
    private static void equip(String artifactType) {
        if (CONSOLE_MODE == true) {
            Scanner scanner = new Scanner(System.in);
            log(ANSI_YELLOW + "::: Do You Wanna Keep The Artifact?\n1. YES!\n2. NO!" + ANSI_RESET);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("1") || line.equals("2")) {
                    Integer choice = Integer.parseInt(line.trim());
                    if (choice == 1) {
                        hero.equipHero(artifact, artifact.getType());
                        log(ANSI_PURPLE +"::: " + hero.getName() + " Is Equipped With " + artifactType);
                        break;
                    } else if (choice == 2) {
                        break;
                    }   
                } else {
                    log(ANSI_RED + ">>> Incorrect Choice, Try Again!" + ANSI_RESET);
                }
            }
        }
    }

    /**
     * The hero wins if he reaches one of
     * the borders of the map.
     */
    public static void goal() {
        if (hero.getXCoordinate() == map.getSize() - 1 ||
            hero.getYCoordinate() == map.getSize() - 1 ||
            hero.getXCoordinate() == 0 ||
            hero.getYCoordinate() == 0) {
                log(ANSI_CYAN + "::: Congratutations, You Reached Your Goal!" + ANSI_RESET);
                map = MapFactory.generateMap(hero);
                GOAL_REACHED = true;
        } else {
            GOAL_REACHED = false;
        }
    }
}