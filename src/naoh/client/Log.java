package naoh.client;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Log {
    static PrintStream out;

    static {
        File f = new File(String.format(Predifined.defaultLogPath.value, LocalDate.now()));
        boolean createFileSucceed = f.getParentFile().mkdirs();
        if (!createFileSucceed)
            System.err.println(String.format("FAILED CREATING LOG FILE %s, this file may have already existed", f.getParent()));

        try {
            if (!f.exists()) f.createNewFile();
            out = new PrintStream(new FileOutputStream(f, true), true);
        } catch (Exception e) {
            printException(e);
        }
    }

    private static boolean restrictedLog() {
        return Application.restrictedLog;
    }

    public static <T> void v(T s) {
        if (restrictedLog()) return;
        writeLog(LogLevel.Verbose, s);
    }

    public static <T> void i(T s) {
        writeLog(LogLevel.Information, s);
    }


    public static <T> void e(T s) {
        writeLog(LogLevel.Error, s);
    }

    public static <T> void w(T s) {
        writeLog(LogLevel.Warning, s);
    }

    private static <T> void writeLog(LogLevel level, T s) {
        synchronized (Log.class) {
            String res = String.format("#--[%s][%s]%s", formatTime(), level.toString(), s);
            //if (Application.debug) 在1.1.2被移除。对下一行代码有效
//          (level == LogLevel.Error ? System.err : System.out).println(res);

            System.out.println(res);
            out.println(res);
        }
    }

    public static String formatTime() {
        return String.format("%s %s", LocalDate.now(), LocalTime.now());
    }

    public static void printException(Throwable e) {
        if (Application.debug) {
            printFullStackTrace(e);
        } else {
            Log.e(e.toString());
        }
    }

    public static void printFullStackTrace(Throwable e) {
        StringWriter exceptionStackTraceWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(exceptionStackTraceWriter);
        e.printStackTrace(pw);

        Log.e(exceptionStackTraceWriter.toString());
    }

    private enum LogLevel {
        Verbose,
        Information,
        Warning,
        Error
    }
}
