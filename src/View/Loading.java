package View;

import javax.swing.*;
import java.awt.*;

public class Loading extends JFrame {

    private Runnable onFinish;

    public Loading(Runnable onFinish) {
        this.onFinish = onFinish;
        initUI();
    }

    private void initUI() {
        setUndecorated(true);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JLabel background = new JLabel(new ImageIcon("src/Images/background.jpg"));
        background.setLayout(new BorderLayout());
        add(background);


        JLabel loadingText = new JLabel("Loading...", SwingConstants.CENTER);
        loadingText.setForeground(Color.WHITE);
        loadingText.setFont(new Font("Arial", Font.BOLD, 24));
        background.add(loadingText, BorderLayout.CENTER);


        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(getWidth(), 10));
        progressBar.setForeground(new Color(255, 249, 230));
        progressBar.setBorderPainted(false);
        progressBar.setBackground(Color.BLACK);
        background.add(progressBar, BorderLayout.SOUTH);

        setVisible(true);


        int delay = 20;
        int totalDuration = 2000;
        int steps = totalDuration / delay;
        final int[] count = {0};

        Timer timer = new Timer(delay, e -> {
            count[0]++;
            int progress = (int) ((count[0] / (float) steps) * 100);
            progressBar.setValue(progress);
            loadingText.setText("Loading" + ".".repeat((count[0] / 5) % 4));

            if (count[0] >= steps) {
                ((Timer) e.getSource()).stop();
                dispose();
                if (onFinish != null) onFinish.run();
            }
        });

        timer.start();
    }
}