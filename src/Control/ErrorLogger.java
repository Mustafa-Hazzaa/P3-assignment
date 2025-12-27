package Control;

import io.TxtFileWriter;
import java.io.IOException;

public class ErrorLogger {

    private final TxtFileWriter writer;
    private final String errorFilePath;

    public ErrorLogger(TxtFileWriter writer, String errorFilePath) {
        this.writer = writer;
        this.errorFilePath = errorFilePath;
    }

    public void log(String errorMessage) {
        try {
            writer.writeLine(errorFilePath, errorMessage, true);
        } catch (IOException e) {
//            System.err.println("Failed to write error log");
        }
    }
}
