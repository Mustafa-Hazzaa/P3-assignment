package Model;

import java.util.Queue;

public class ProductLine implements Runnable  {
    int id;
    String name;
    String status;
    Queue<Task> taskQueue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Queue<Task> getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(Queue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }


    @Override
    public void run() {
//        while ()
//            Task currentTask = taskQueue.;
    }
}
