package naoh.installer;

import naoh.client.Predifined;

import java.io.File;

public class Uninstaller {
    public Uninstaller(){
        new File(Predifined.defaultBridgePath.value).delete();
    }
}
