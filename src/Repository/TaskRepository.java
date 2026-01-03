package Repository;

import Model.Task; // <<< NOTE: No more imports for Product, ProductLine, etc.
import Util.TaskStatus;

import java.time.LocalDateTime;

public class TaskRepository extends CsvRepository<Task> {

    public TaskRepository() {
        super("Data/Tasks.csv");

    }

    @Override
    protected String getHeader() {
        return "id,productName,totalQuantity,producedQuantity,client,status,productLineId,startTime,endTime";

    }

    @Override
    protected String toCsv(Task task) {
        return task.getId() + "," +
                task.getProductName().toLowerCase() + "," +
                task.getQuantity() + "," +
                task.getClient() + "," +
                task.getStatus() + "," +
                task.getProductLineId() + "," +
                task.getProgress() + "," +
                (task.getStartTime() == null ? "null" : task.getStartTime()) + "," +
                (task.getEndTime() == null ? "null" : task.getEndTime());
    }



    @Override
    public Task fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length != 9)
            throw new IllegalArgumentException("Invalid task data");


        LocalDateTime start = data[7].equals("null") ? null : LocalDateTime.parse(data[7]);
        LocalDateTime end = data[8].equals("null") ? null : LocalDateTime.parse(data[8]);

        Task task = new Task(
                Integer.parseInt(data[0]),
                data[1].toLowerCase(),        // product name
                Integer.parseInt(data[2]),
                data[3],                      // client
                TaskStatus.valueOf(data[4]),
                Integer.parseInt(data[5]),// productLineId
                Integer.parseInt(data[6])
        );
        task.setStartTime(start);
        task.setEndTime(end);

        return task;
    }

}
