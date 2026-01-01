package Model;

import io.TxtFileWriter;
import io.TxtFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static final String FILE = "Data/Tasks.csv";
    private final TxtFileWriter writer = new TxtFileWriter();
    private final TxtFileReader reader = new TxtFileReader();

    public void saveAll(List<Task> tasks) {
        try {
            writer.writeLine(
                    FILE,
                    "id,productId,quantity,client,status,progress",
                    false
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Task task : tasks) {
            try {
                writer.writeLine(FILE, toCsv(task), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String toCsv(Task task) {
        return task.getId() + "," +
                task.getProduct().getId() + "," +
                task.getQuantity() + "," +
                task.getClient() + "," +
                task.getStatus() + "," +
                task.getProgress() + ",";
    }

    public List<Task> loadAll(InventoryService inventoryService) {
        List<Task> tasks = new ArrayList<>();

        List<String> lines = null;
        try {
            lines = reader.readAllLines(FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (lines.isEmpty()) return tasks;

        lines.remove(0); // header

        for (String line : lines) {
            String[] p = line.split(",");

            Product product = inventoryService.getProductById(
                    Integer.parseInt(p[1])
            );

            Task task = new Task(
                    Integer.parseInt(p[0]),
                    product,
                    Integer.parseInt(p[2]),
                    p[3],
                    null,
                    Integer.parseInt(p[6])
            );

            task.setStatus(TaskStatus.valueOf(p[4]));
            task.setProgress(Integer.parseInt(p[5]));

            tasks.add(task);
        }

        return tasks;
    }
}
