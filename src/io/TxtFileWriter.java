package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TxtFileWriter {

    public void writeLine(String filePath,String input,boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,append))) {
            writer.write(input);
            writer.newLine();
        }
    }

    public void writeLines(String filePath, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

}
