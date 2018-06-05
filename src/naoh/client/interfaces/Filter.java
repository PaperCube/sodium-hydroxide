package naoh.client.interfaces;

import java.io.File;

public interface Filter {
    boolean accept(File f);
}
