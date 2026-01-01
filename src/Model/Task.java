package Model;

import java.time.LocalDateTime;

public class Task {
    private int id;
    private String item;
    private int quantity;
    private String client;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private ProductLine productLine;
    private int progress;
    private int secondsNeeded;
    private SimulatedClock simulatedClock = new SimulatedClock();

    public Task(int id, String item, int quantity, String client, ProductLine productLine, int secondsNeeded) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.client = client;
        this.productLine = productLine;
        this.secondsNeeded = secondsNeeded;
        this.startTime = null;
        this.endTime = null;
        this.status = "Pending";
        this.progress = 0;
    }

    public void setStatus(String status) {
        this.status = status;
    }

        public void processTask() {
            setStatus("Working");
            this.startTime = simulatedClock.now();



            this.endTime = LocalDateTime.now();
            setStatus("Completed");
        }



}
