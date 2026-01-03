package Repository;

import Model.Task; // <<< NOTE: No more imports for Product, ProductLine, etc.
import Util.TaskStatus;

public class TaskRepository extends CsvRepository<Task> {

    public TaskRepository() {
        super("Data/Tasks.csv");

    }

    @Override
    protected String getHeader() {
        return "id,productName,quantity,client,status,productLineId,progress";
    }

    @Override
    protected String toCsv(Task task) {
        return task.getId() + "," +
                task.getProductName().toLowerCase() + "," +
                task.getQuantity() + "," +
                task.getClient() + "," +
                task.getStatus() + "," +
                task.getProductLineId() + "," +
                task.getProgress();
    }



    @Override
    public Task fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length != 7)
            throw new IllegalArgumentException("Invalid task data");
        return new Task(
                Integer.parseInt(data[0]),
                data[1].toLowerCase(),        // product name
                Integer.parseInt(data[2]),
                data[3],                      // client
                TaskStatus.valueOf(data[4]),
                Integer.parseInt(data[5]),// productLineId
                Integer.parseInt(data[6])
        );
    }

}
