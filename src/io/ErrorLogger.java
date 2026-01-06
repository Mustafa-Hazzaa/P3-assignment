package io;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLogger {

    private final TxtFileWriter writer = new TxtFileWriter();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String  errorFilePath = "Data/Errors.csv";

    public void log(String errorMessage) {
        try {
            String time = dtf.format(LocalDateTime.now());
            String Message = String.format("[%s] ERROR: %s", time, errorMessage);
            writer.writeLine(errorFilePath, Message, true);
        } catch (IOException e) {
            System.err.println("Failed to write error log");
        }
    }
}
