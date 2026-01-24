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
        return "id,productName,Quantity,totalQuantity,client,status,productLineId,Progress,startTime,endTime";

    }

    @Override
    protected String toCsv(Task task) {
        return task.getId() + "," +
                task.getProductName().toLowerCase() + "," +
                task.getQuantity() + "," +
                task.getTotalQuantity() + "," +
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
        if (data.length != 10)
            throw new IllegalArgumentException("Invalid task data");


        LocalDateTime start = data[8].equals("null") ? null : LocalDateTime.parse(data[8]);
        LocalDateTime end = data[9].equals("null") ? null : LocalDateTime.parse(data[9]);

        Task task = new Task(
                Integer.parseInt(data[0]),
                data[1].toLowerCase(),        // product name
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                data[4],                      // client
                TaskStatus.valueOf(data[5]),
                Integer.parseInt(data[6]),// productLineId
                Integer.parseInt(data[7])
        );
        task.setStartTime(start);
        task.setEndTime(end);

        return task;
    }

}
