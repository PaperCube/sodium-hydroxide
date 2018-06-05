package naoh.client;

import javafx.embed.swing.JFXPanel;
import naoh.installer.Installer;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;

import static java.lang.System.getProperty;

public class Application {
    public static void main(String[] args) {
        new JFXPanel();//This will prepare java fx environment
//        exitUnlessSingleton();
        Log.out.println("\r\n======================================");
        Log.v("Init sodium hydroxide");

        Log.i("SODIUM HYDROXIDE 1.1.4");
        Log.i("OS NAME:" + getProperty("os.name"));
        Log.i("OS VERSION:" + getProperty("os.version"));


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Log.printException(e);
        }

        Application app = new Application(args);
    }

    public static void exit() {
        Log.i("Application requested exit with exit code 233");
        System.exit(0);
    }

    public static boolean isSingleInstance() {
        try {

            File lock = new File(Predifined.defaultInstallationPath.value + "/SH.LCK");
            lock.getParentFile().mkdirs();
            if (!lock.exists()) lock.createNewFile();
            if (new FileOutputStream(lock).getChannel().tryLock() == null) {
                Log.i("APPLICATION NOT SINGLE INSTANCE BASED");
                return false;
            }
        } catch (Exception e) {
            Log.printFullStackTrace(e);
//            return false;
        }
        return true;
    }

    public static boolean debug = false;
    public static boolean restrictedLog = false;

    String[] args;
    String id;

    Application(String[] a) {
        args = a;

        applyArgOptions();
        if (!debug) System.setErr(Log.out); //redirect output when error occurs

    }

    private void applyArgOptions() {
        if (args.length < 1) {
            Log.i("Arg length less than 1");
            exit();
        }

        try {
            switch (args[0]) {
                case "run":
                    exitUnlessSingleton();
                    DriverDetector.init(new FindChangesByPathAvailability());// Here enters the client
                    Log.i("SELECTED MODE : RUN");
                    break;
                case "install":
                    new Installer();
                    Log.i("SELECTED MODE : INSTALLER");
                    break;
                default:
                    Log.i("UNKNOWN MODE : " + args[0]);
                    exit();
            }

            id = args[1];

            for (int i = 2; i < args.length; i++) {
                switch (args[i]) {
                    case "debugmode":
                        debug = true;
                        Log.i("DEBUG MODE ENABLED");
                        break;

                    case "restrictedlog":
                        restrictedLog = true;
                        Log.i("LOG RESTRICTED MODE");
                        break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //Ignore
        }
    }


    /**
     * 如果当前不是单例模式，那么直接执行执行exit方法
     *
     * @see #exit()
     */
    public static void exitUnlessSingleton() {
        if (!isSingleInstance()) exit();
    }

}
