package naoh.installer;

import javafx.application.Platform;
import javafx.stage.Stage;
import naoh.client.Log;
import naoh.client.Predifined;
import naoh.client.ui.fx.InstallerFx;

import java.io.*;

public class Installer {
    public Installer(){
        Platform.runLater(() -> {
            try {
                InstallerFx fx= new InstallerFx();
                fx.start(new Stage());
                new InstallPerformer(fx);
            } catch (Exception e) {
                Log.printException(e);
            }
        });
    }

    private static class InstallPerformer implements Runnable{
        InstallerFx fx;
        InstallPerformer(InstallerFx fx){
            this.fx=fx;
            Thread t = new Thread(this);
            t.setName("Install Performer");
        }
        @Override
        public void run() {
            checkAutoRun();
        }

        private boolean checkAutoRun(){
            String autoRunPath = System.getProperty("os.name").toLowerCase().contains("xp")? Predifined.defaultStartUpPathXP.value : Predifined.defaultStartUpPath.value;
            Log.i("XP DETECTED");
            try {
                if (!new File(autoRunPath).exists()) {
                    copyFile(new File("/process.bat"),new File(autoRunPath +"/process.bat"));
                }
            } catch (Exception e) {
                Log.printException(InstallationException.installFailed(e));
                return false;
            }
            return true;
        }

        /**
         *Use to figure out what version of windows is this computer running
         * Windows XP(32 bit) is NT 5.1, XP (64 bit) and server 2003 is NT5.2
         * Vista 6.0, Windows 7 6.1, Windows 8 6.2 windows8.1 6.3 Windows 10 10.0
         */
        private String getOSVersion(){
            return System.getProperty("os.version");
        }

        private void copyFile(File oriFile,File expectedFile){
            try {
                FileReader reader = new FileReader(oriFile);
                FileWriter writer = new FileWriter(expectedFile);

                int ch;
                do{
                    ch = reader.read();
                    if(ch!=-1) writer.write(ch);
                }while(ch!=-1);

            } catch (IOException e) {
                Log.printException(e);
            }

        }
    }
}
