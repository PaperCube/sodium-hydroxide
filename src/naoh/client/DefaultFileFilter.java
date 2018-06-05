package naoh.client;

import naoh.client.interfaces.Filter;

import java.io.File;
import java.util.stream.Stream;

public class DefaultFileFilter implements Filter {
    Character[] excludeList = {'C', 'D'};

    //    HashSet<Character> excluded = new HashSet<>(Arrays.asList(excludeList));
    @Override
    public boolean accept(File f) {
        Character volume = f.getAbsolutePath().toUpperCase().charAt(0);
        if (Stream.of(excludeList)
                .anyMatch(v -> v == volume)) {
            return false;
        }

        return !new File(
                String.format("%s:/.protect", volume)
        ).exists();
    }
}
