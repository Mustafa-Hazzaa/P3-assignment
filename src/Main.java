import Model.InventoryRepository;
import Model.InventoryService;
import Model.Item;
import io.TxtFileReader;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
//        SimulatedClock clock = new SimulatedClock();
//        System.out.println(clock.nowFormatted());
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(clock.nowFormatted());
//        clock.shutdown()
//        ;
        InventoryRepository repo = new InventoryRepository();
        InventoryService inventory = new  InventoryService(repo);

        // ----------------------------
        // Add new items
        // ----------------------------


    }
}
