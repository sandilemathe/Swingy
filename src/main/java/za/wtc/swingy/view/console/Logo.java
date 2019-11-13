package za.wtc.swingy.view.console;

import za.wtc.swingy.tools.Colors;

/**
 * Logo

 */
public class Logo {

    public static void displayLogo() {
        System.out.println("\u001B[32m" + "=======================================");
        System.out.println("=               SWINGY                =");
        System.out.println("=======================================" + "\u001B[0m");
    }

   public static String logoText = "Swingy by Sandile Mathe";
}