package IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtFileReader {
    public List<String> readLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            lines.add(line);
        }
        return lines;
    }
}
