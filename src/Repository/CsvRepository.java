package Repository;

import io.ErrorLogger;
import io.TxtFileReader;
import io.TxtFileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class CsvRepository<T> {

    protected final String filePath;
    private final TxtFileReader reader = new TxtFileReader();
    private final TxtFileWriter writer = new TxtFileWriter();
    private final ErrorLogger errorLogger = new ErrorLogger();

    protected CsvRepository(String filePath) {
        this.filePath = filePath;
    }

    // the abstract method to implement
    protected abstract String getHeader();
    protected abstract String toCsv(T object);
    protected abstract T fromCsv(String csvLine);


    // read the file normally
    //and usees the  generic to convert it into object
    public List<T> loadAll() {
        List<T> items = new ArrayList<>();
        List<String> lines;

        try {
            lines = reader.readAllLines(filePath);
        } catch (IOException e) {
            errorLogger.log(e.getMessage());
            return items;
        }

        if (!lines.isEmpty()) {
            lines.remove(0);
        }

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            try {
                items.add(fromCsv(line));
            } catch (Exception e) {

                errorLogger.log(e.getMessage());
            }
        }
        return items;
    }


    public void saveAll(List<T> items) {
        try {
            writer.writeLine(filePath, getHeader(), false);
        } catch (IOException e) {
            errorLogger.log(e.getMessage());
            return;
        }

        for (T item : items) {
            try {
                writer.writeLine(filePath, toCsv(item), true);
            } catch (IOException e) {
                errorLogger.log("Could not write item " + toCsv(item) +"\t" + e.getMessage());
            }
        }
    }
}
