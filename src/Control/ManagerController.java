//package Control;
//
//import Model.*;
//import Service.*;
//import View.*;
//
//import javax.swing.*;
//import java.util.Collection;
//
//public class ManagerController {
//    ImageIcon workingGif=new ImageIcon(getClass().getResource("/Images/workingFactory.gif"));
//    private ProductLineService productLineService;
//    private final ManagerView view;
//
//    public ManagerController(ManagerView view, ProductLineService productLineService) {
//        this.view = view;
//        this.productLineService = productLineService;
//
//        Collection<ProductLine> productLines = productLineService.getAll();
//        this.view.createPanelsForLines(productLines);
//
//        new Thread(() -> {
//            try {
//                while (true) {
//                    Thread.sleep(1000);
//                    for (ProductLine line : productLineService.getAll()) {
//                        view.refreshLine(line);
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//    }
//
//
//    public void onMenuClick(String menu) {
//        switch (menu) {
//            case "⚙️ Edit Production Line" -> System.out.println("Edit Production Line clicked");
//            case "📊 Reports" -> System.out.println("Reports clicked");
//            case "👥 Workers" -> System.out.println("Workers clicked");
//        }
//    }
//}
