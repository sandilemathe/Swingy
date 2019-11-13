package za.wtc.swingy.controller;

import java.util.Scanner;
import java.io.IOException;
import java.sql.SQLException;

import za.wtc.swingy.model.hero.Hero;
import za.wtc.swingy.model.hero.HeroEnum;
import za.wtc.swingy.database.DatabaseHandler;
import za.wtc.swingy.view.console.ConsoleView;

import static za.wtc.swingy.tools.Log.*;
import static za.wtc.swingy.StaticGlobal.*;
import static za.wtc.swingy.tools.Colors.*;

/**
 * ConsoleController
 */
public class ConsoleController {

     /**
     * Create hero based on the user's choice and store 
     * the created hero in a database. Only three types
     * of heroes are available i.e Crab, Cuttlefish and
     * Octopus
     */
    public static void chooseHeroType() {
        Scanner scanner = new Scanner(System.in);

        log(ANSI_YELLOW + "::: SELECT A HERO TYPE" + ANSI_RESET);
        ConsoleView.displayHeroTypes();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("1") || line.equals("2") || line.equals("3")) {
                Integer choice = Integer.parseInt(line);
                switch (choice) {
                case 1:
                    createHero(HeroEnum.CRAB);
                    break;
                case 2:
                    createHero(HeroEnum.CUTTLEFISH);
                    break;
                case 3:
                    createHero(HeroEnum.OCTOPUS);
                    break;
                }
                break;
            } else {
                log(ANSI_RED + ">>> Hero Type Does Not Exist, Try Again!" + ANSI_RESET);
            }
        }
        ConsoleView.displayMenuChoices();
    }

    /**
     * This method creates a hero of a specified {@code type},
     * before creating a hero, hero must be given a name, and the
     * name must be between 2 & 25 characters.
     * {@code DatabaseHandler.getInstance().heroExists(line)} checks
     * if the specified name does not exist in the database to avoid
     * duplicates, if it return {@code false}, which means the name does 
     * not exists in the database then from
     * {@code DatabaseHandler.getInstance().insertHero((Hero) HeroFactory.newHero(line.trim(), type))} :
     * {@code (Hero) HeroFactory.newHero(line.trim(), type)} creates a hero then 
     * {@code DatabaseHandler.getInstance().insertHero()} inserts the created hero in the
     * database.
     * 
     * @param type The hero type.
     */
    public static void createHero(HeroEnum type) {
        Scanner scanner = new Scanner(System.in);

        log(ANSI_YELLOW + "::: Enter The Name Of The Hero");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            /**
             * Check if the name has atleast 2 and atmost 25
             * characters. 
             */
            if (line.length() >= 2 && line.length() < 26) {
                try {
                    // Create the hero and store it in the database.
                   if (!DatabaseHandler.getInstance().heroExists(line)) {
                       DatabaseHandler.getInstance().insertHero((Hero) HeroFactory.newHero(line.trim(), type));
                       log(ANSI_CYAN + "Created Hero Named: " + ANSI_YELLOW + line);
                    } else {
                        log(ANSI_RED + " >>> " + line + " Hero Exists, Try again!" + ANSI_RESET);
                    }
                } catch (SQLException | IOException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
                break;
            } else if (line.length() < 3 || line.length() > 25) {
                log(
                        ANSI_RED + ">>> Name Must Have 2 - 25 Characters, Try Again:" + ANSI_RESET);
            }
        }
    }

    /**
     * Select an existing hero from the database, 
     * {@code DatabaseHandler.getInstance().numberOfHeroes()} returns
     * the number of stored heroes in the database. If is returns a
     * number greater than Zero it means there is atleast one hero
     * stored in the database. Then displays the available heroes to 
     * the user to choose their preferred hero. 
     * {@code Database.getInstance().heroExists} checks if the specified
     * name exists in the database, if the name exists it returns {@code true}
     * otherwise it returns {@code false}.
     */
    public static void selectExistingHero() {
        Scanner scanner = new Scanner(System.in);

        try {
            // First check if there are heroes in the database.
            if (DatabaseHandler.getInstance().numberOfHeroes() > 0) {
                if (DatabaseHandler.getInstance().numberOfHeroes() == 1) {
                    log(ANSI_YELLOW + DatabaseHandler.getInstance().numberOfHeroes()
                            + " Hero Available, Choose: " + ANSI_RESET);
                } else {
                    log(ANSI_YELLOW + DatabaseHandler.getInstance().numberOfHeroes()
                            + " Heroes Available: " + ANSI_RESET);
                }
                // Display all the available heroes in the database.
                DatabaseHandler.getInstance().retrieveAllHeroes();
            } else {
                log(ANSI_RED + ">>> No Heroes Available!" + ANSI_RESET);
                ConsoleView.displayMenuChoices();
            }
        } catch (IOException | ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            try {
                // Check if the specified hero name exist in the database,
                // If the hero name exist in the database, retrieve the data to hero object,
                // And lastly generate the map.
                if (DatabaseHandler.getInstance().heroExists(line)) {
                    hero = DatabaseHandler.getInstance().retrieveHeroData(line.trim());
                    map = MapFactory.generateMap(hero);
                    if (CONSOLE_MODE == true) {
                        directions();
                    }
                } else {
                    log(ANSI_RED + ">>> Hero Does not Exist, Try Again!" + ANSI_RESET);
                }
            } catch (ClassNotFoundException | SQLException | IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Take use directions in order to move the hero.
     * {@code GameController.moveHero(direction)} moves the
     * hero based on the user's choice {@code direction}.
     * 1 for NORTH, 2 for EAST, 3 for SOUTH & 4 for WEST.
     * First check if the input is correct before attempting
     * to move the hero. After moving the hero,
     * {@code GameController.goal()} checks if the
     * the hero reached the borders.
     */
    public static void directions() {
        Scanner scanner = new Scanner(System.in);

        ConsoleView.displayMoveList();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Integer direction = Integer.parseInt(line);
            if (direction == 1 || direction == 2 ||
                direction == 3 || direction == 4) {
                    GameController.moveHero(direction);
                    GameController.goal();
            } else {
                log(ANSI_RED + ">>> Incorrect Choice, Try Again!" + ANSI_RESET);
            }
            ConsoleView.displayMoveList();
        }
        scanner.close();
    }
}