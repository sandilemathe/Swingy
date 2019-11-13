package za.wtc.swingy;

import za.wtc.swingy.view.MapView;
import za.wtc.swingy.model.hero.Hero;
import za.wtc.swingy.model.hero.Enemy;

import javax.swing.JTextArea;

import za.wtc.swingy.model.artifact.Artifact;


public class StaticGlobal {

    public static Hero hero;
    public static MapView map;
    public static Enemy enemy;
    public static Artifact artifact;
    
    public static JTextArea logTextArea;
    public static JTextArea displayTextArea;

    public static boolean HERO_RAN = false;
    public static boolean DISPLAY_LOGO = true;
    public static boolean CONSOLE_MODE = false;
    public static boolean ARTIFACT_DROPPED = false;
    public static boolean GOAL_REACHED = false;
}