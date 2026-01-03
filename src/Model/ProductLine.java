package Model;// package Model;

import Model.Task;
import Util.LineStatus;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// NOTE: No 'implements Runnable', no services, no run() method.
public class ProductLine {
    private int id;
    private String name;
    private LineStatus status;
    private BlockingQueue<Task> tasks = new LinkedBlockingQueue<>();
    // private final ErrorLogger logger = new ErrorLogger(); // This can be moved to the worker

    public ProductLine(int id, String name, LineStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public LineStatus getStatus() { return status; }
    public void setStatus(LineStatus status) { this.status = status; }

    public void addTask(Task task) throws InterruptedException {
        tasks.put(task);
    }

    public Task takeTask() throws InterruptedException {
        return tasks.take();
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }
}
