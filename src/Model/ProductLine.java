package Model;// package Model;

import Model.Task;
import Util.LineStatus;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProductLine {
    private static int nextId = 1;
    private int id;
    private String name;
    private LineStatus status;
    private BlockingQueue<Task> tasks = new LinkedBlockingQueue<>();
    private Task runningTask = null;

    public ProductLine(int id, String name, LineStatus status) {
        this.id = id;
        syncNextId(id);
        this.name = name;
        this.status = status;
    }

    public ProductLine( String name, LineStatus status) {
        this.id = generateId();
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
        runningTask = tasks.take();
        return runningTask;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public BlockingQueue<Task> getAllTasks() {
        return this.tasks;
    }

    public synchronized Task currentTask() {
        return runningTask != null ? runningTask : tasks.peek();
    }

    public synchronized void completeRunningTask() {
        runningTask = null;
    }

    private static synchronized int generateId() {
        return nextId++;
    }

    private static synchronized void syncNextId(int id) {
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
}
