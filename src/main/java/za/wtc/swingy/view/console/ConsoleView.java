package za.wtc.swingy.view.console;

import java.util.Scanner;

import za.wtc.swingy.controller.ConsoleController;
import za.wtc.swingy.view.gui.GameWindow;

import static za.wtc.swingy.tools.Log.*;
import static za.wtc.swingy.tools.Colors.*;
import static za.wtc.swingy.StaticGlobal.*;

/**
 * ConsoleView
 
 */
public class ConsoleView {

    /** Display menu choices after launching the game. */
    public static void displayMenuChoices() {
        log(ANSI_RED + "1." + ANSI_CYAN + " Create A New Hero." + ANSI_RESET);
        log(ANSI_RED + "2." + ANSI_CYAN + " Select A Hero." + ANSI_RESET);
    }

    /** Display all validtypes of heroes. */
    public static void displayHeroTypes() {

        log(ANSI_RED + "1." + ANSI_CYAN + " Crab" + ANSI_RESET);
        log(ANSI_RED + "2." + ANSI_CYAN + " Cuttlefish" + ANSI_RESET);
        log(ANSI_RED + "3." + ANSI_CYAN + " Octopus" + ANSI_RESET);
    }

    /** The menu with cool swingy logo :). */
    public static void menu() {
        if (DISPLAY_LOGO == true) {
            Logo.displayLogo();   
        }
        log("");
        log(ANSI_YELLOW + "::: SELECT YOUR CHOICE");
        displayMenuChoices();
        DISPLAY_LOGO = false;
    }

    /** Display Directions or move list. */
    public static void displayMoveList() {
        log(ANSI_YELLOW + "::: Move");
        log(ANSI_RED + "1." + ANSI_CYAN + " North" + ANSI_RESET);
        log(ANSI_RED + "2." + ANSI_CYAN + " East" + ANSI_RESET);
        log(ANSI_RED + "3." + ANSI_CYAN + " South" + ANSI_RESET);
        log(ANSI_RED + "4." + ANSI_CYAN + " West" + ANSI_RESET);
    }

    /** Display Actions i.e fight or run. */
    public static void displayActions() {
        log(ANSI_YELLOW + "::: Action");
        log(ANSI_RED + "1." + ANSI_CYAN + " Fight" + ANSI_RESET);
        log(ANSI_RED + "2." + ANSI_CYAN + " Run" + ANSI_RESET);
    }

    /**
     * Read the user input, if the input
     * is 1: {@code createHero()} creates
     * a new hero, else if it is 2: 
     * {@code selectHeroType()}, selects the
     * already existing hero, if it is 3:
     * {@code WelcomeWindow.run()} launches the
     * GUI view. 
     */
    public static void run() {
        menu();
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("1") || line.equals("2")
                || line.equals("3")) {
                Integer choice = Integer.parseInt(line);
                switch (choice) {
                    case 1:
                        ConsoleController.chooseHeroType();
                        break;
                    case 2:
                        ConsoleController.selectExistingHero();
                        break;
                    case 3:
                        GameWindow.run();
                        break;
                }
            } else {
                log(ANSI_RED + " >>> Incorrect Choice, Try Again!");
            }
        }
    }
}