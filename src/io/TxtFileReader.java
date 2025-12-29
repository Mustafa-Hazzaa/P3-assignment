package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtFileReader {
    public List<String> readAllLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public String readLine(String filePath, int index) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int counter = 0;
            String line;
            String lastLine = null;
            while ((line = reader.readLine()) != null) {
                counter++;
                lastLine = line;
                if (index == counter)
                    return line;
            }
            return lastLine;
        }
    }
}
