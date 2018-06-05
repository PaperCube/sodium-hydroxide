package naoh.client.interfaces;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface DetectRule {
    List<Character> preventList = new ArrayList<>();

    List<File> findChanges();
}
