package naoh.client.interfaces;

import naoh.client.Log;

import java.io.File;
import java.io.IOException;

public interface FileAttributes {
    Runtime runtime = Runtime.getRuntime();

    static void setHidden(String absolutePath, boolean isHidden) throws IOException {
        runtime.exec(String.format("attrib %sH %s", isHidden ? '+' : '-', absolutePath));
    }

    static void setReadOnly(String absolutePath, boolean isReadOnly) throws IOException {
        runtime.exec(String.format("attrib %sR %s", isReadOnly ? '+' : '-', absolutePath));
    }

    static void clearSpecialAttribute(File file) {
        try {
            if (file.setReadOnly()) FileAttributes.setReadOnly(file.getAbsolutePath(), false);
            if (file.isHidden()) FileAttributes.setHidden(file.getAbsolutePath(), false);
        } catch (IOException e) {
            Log.printException(e);
        }
    }
}
