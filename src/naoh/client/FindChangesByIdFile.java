package naoh.client;

import naoh.client.interfaces.DetectRule;
import naoh.client.interfaces.FileAttributes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FindChangesByIdFile implements DetectRule {

    Map<Character, String> driverIdMap = new HashMap<>();

    FindChangesByIdFile() {

    }

    @Override
    public List<File> findChanges() {
        List<File> result = new ArrayList<>();

        File[] rootFiles = File.listRoots();
        for (File dir : rootFiles) {
            if (preventList.contains(dir.getAbsolutePath().charAt(0))) continue;
            if (!dir.exists()) continue;
            if (new File(dir.getAbsolutePath() + "/.protect").exists()) continue;

            File idFile = new File(dir.getAbsolutePath() + "/.id").getAbsoluteFile();
            if (idFile.exists()) {
                try (Scanner s = new Scanner(idFile)) {
                    if (!idFile.canWrite() || idFile.isHidden()) FileAttributes.clearSpecialAttribute(idFile);
                    try {
                        char volume = dir.getAbsolutePath().charAt(0);
                        if (s.nextLine().equals(driverIdMap.get(volume))) continue;
                    } catch (NoSuchElementException e) {
                        //Ignore this exception
                    }
                } catch (Exception e) {
                    Log.e(e.toString());
                }
            }
            applyNewId(idFile);
            result.add(dir);
        }
        return result;
    }

    private void applyNewId(File idFile) {
        try (FileWriter fw = new FileWriter(idFile.getAbsolutePath(), false)) {
            idFile.createNewFile();
            String rdm = nextRandomLong();
            fw.write(rdm);
            driverIdMap.put(idFile.getAbsolutePath().charAt(0), rdm);
        } catch (IOException e) {
            Log.printException(e);
        }
    }

    private String nextRandomLong() {
        return String.valueOf(new Random().nextLong());
    }
}
