package Util;

import io.TxtFileReader;
import io.TxtFileWriter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimulatedClock {

    private static final int SPEED = 60;

    private final LocalDateTime realStart;
    private final LocalDateTime simulatedStart;
    private final TxtFileWriter writer = new TxtFileWriter();
    private final TxtFileReader reader = new TxtFileReader();
    private final String TIME_FILE = "Data/simulated_time.txt";
    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private static SimulatedClock instance;

    private SimulatedClock() {
        this.simulatedStart = loadLastSimulatedTime();
        this.realStart = LocalDateTime.now();
    }

    public static SimulatedClock getInstance() {
        if (instance == null) {
            synchronized (SimulatedClock.class) {
                if (instance == null) {
                    instance = new SimulatedClock();
                }
            }
        }
        return instance;
    }

    public LocalDateTime now() {
        Duration realElapsed = Duration.between(realStart, LocalDateTime.now());

        long simulatedSeconds =
                (realElapsed.toMillis() * SPEED) / 1000;

        return simulatedStart.plusSeconds(simulatedSeconds);
    }

    public void shutdown() {
        try {
            writer.writeLine(TIME_FILE, FORMAT.format(now()), false);
        } catch (IOException e) {
            System.err.println("Failed to save simulated time");
        }
    }

    private LocalDateTime loadLastSimulatedTime() {
        try {
            String line = reader.readLine(TIME_FILE, -1);
            if (line != null && !line.isBlank())
                return LocalDateTime.parse(line, FORMAT);
        } catch (IOException ignored) {}

        return LocalDateTime.now();
    }
}
