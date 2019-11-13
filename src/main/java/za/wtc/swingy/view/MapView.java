package za.wtc.swingy.view;

import lombok.Getter;

import java.util.Random;

import za.wtc.swingy.tools.Colors;
import za.wtc.swingy.model.hero.Hero;

import static za.wtc.swingy.StaticGlobal.*;


@Getter
public class MapView {

    private Hero  heroObj;
    private char[][] map;
    private int size;
    private int[] previousPosition = new int[] {-1, -1};

    /**
     * SwingyMap constructor: initialize the {@code size} with
     * the parameter {@code size} and give the map a size. Since the map is
     * a square, the number of rows is the same as the number
     * of columns, hence: {@code this.map = new char[size][size];}
     * 
     * @param size The size of the column/row.
     */
    public MapView(int size) {
        this.size = size;
        this.map = new char[size][size]; 
    }

     /**
     * Registers the hero to the map and set
     * the initial position of the hero(H) as the
     * center of the map. The hero(H) will always
     * start at the center of the map.
     * 
     * @param hero The hero to be registered.
     */
    public void registerHero(Hero hero) {

        this.heroObj = hero;
        this.heroObj.register(this);
        this.heroObj.setXCoordinate(size / 2);
        this.heroObj.setYCoordinate(size / 2);

        previousPosition[0] = this.heroObj.getXCoordinate();
        previousPosition[1] = this.heroObj.getYCoordinate();
        this.map[size / 2][size / 2] = 'H';
    }

    /**
     * Updates the position of the hero, sets the previous position to '.' 
     * character and set the current coordinates to the previous position
     * coordinates. If the hero(H) gets to an enemy(E)'s position, set 'X'
     * to show that the Hero(H) came accross an enemy(E) and then display
     * the updated positions on the map.
     */
    public void updateHeroPosition() {
        this.map[previousPosition[0]][previousPosition[1]] = '.';
        previousPosition[0] = hero.getXCoordinate();
        previousPosition[1] = hero.getYCoordinate();

        if (this.map[hero.getXCoordinate()][hero.getYCoordinate()] == 'E') {
            this.map[hero.getXCoordinate()][hero.getYCoordinate()] = 'X';
        } else {
            this.map[hero.getXCoordinate()][hero.getYCoordinate()] = 'H';
        }
        if (CONSOLE_MODE == true) {
            displayMap();
        } 
    }

    /**
     * When a map is generated, villians of
     * varying power are spread randomly over
     * the map.
     */
    public void spreadEnemies() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] != 'H') {
                    int random = new Random().nextInt(3);
                    if (random == 0) {
                        map[i][j] = 'E';
                    }
                }
            }
        }
        if (CONSOLE_MODE == true) {
            displayMap();
        }
    }

    /**
     * Dipslay the current position of the
     * randomly spred villians and the hero
     * on the map with some cool colors. 
     */
    public void displayMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (map[i][j]) {
                    case 'H':
                        System.out.print(Colors.ANSI_GREEN + map[i][j] + "  " + Colors.ANSI_RESET);
                        break;
                    case 'E':
                        System.out.print(Colors.ANSI_RED + map[i][j] + "  " + Colors.ANSI_RESET);
                        break;
                    case 'X':
                        System.out.print(Colors.ANSI_PURPLE + map[i][j] + "  " + Colors.ANSI_RESET);
                        break;
                    default:
                        System.out.print(Colors.ANSI_YELLOW + ".  " + Colors.ANSI_RESET);
                        break;
                }
            }
            System.out.println("\n");
        }
    }
}