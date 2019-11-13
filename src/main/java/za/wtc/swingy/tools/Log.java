package za.wtc.swingy.tools;

import static za.wtc.swingy.StaticGlobal.*;

/**
 * Log
 */
public class Log {

    /**
     * 
     * @param message
     */
    public static void log(String message) {
        if (CONSOLE_MODE == true) {
            System.out.println(message);   
        }
    }
}