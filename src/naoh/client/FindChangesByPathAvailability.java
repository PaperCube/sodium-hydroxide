package naoh.client;

import naoh.client.interfaces.DetectRule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class FindChangesByPathAvailability implements DetectRule {
    List<Character> lastCheck = new ArrayList<>();

    @Override
    public List<File> findChanges() {
        List<File> current = asList(File.listRoots());
        List<File> result = current.stream()
                .map(v -> v.getAbsolutePath().charAt(0))
                .filter(v -> !lastCheck.contains(v))

                .map(v -> new File(String.format("%s:/", v)))
                .collect(Collectors.toList());
        lastCheck = current.stream().map(v -> v.getAbsolutePath().charAt(0)).collect(Collectors.toList());
        return result;
    }
}
