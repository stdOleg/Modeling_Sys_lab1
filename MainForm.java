import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainForm {
    static JFrame frame = new JFrame("Лабораторная работа.");

    static double wind_v;
    static double wind_alpha;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Write speed for wind: ");
        wind_v = in.nextDouble();
        System.out.println("Write alpha for wind between 0 and 360: ");
        wind_alpha = in.nextDouble() / 180 * Math.PI;
        frame.setSize(1400, 700);
        frame.setLocation(50, 50);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        PaingPanel panel;
        frame.add(panel = new PaingPanel(), BorderLayout.CENTER);
        JButton start = new JButton("Start");
        frame.add(start, BorderLayout.SOUTH);
        start.addActionListener(event -> {
            start.setEnabled(false);
            new Thread(() -> {
                while (panel.nextT().clacCoord()) {
                    panel.invalidate();
                    panel.repaint();
                    if (panel.check()) {
                        JOptionPane.showMessageDialog(panel, "Попал! " + "Угол: " + panel.getAlpha());
                        panel.reset();
                        panel.resetDeltaAlpha();
                    }
                    try {
                        Thread.sleep(TimeUnit.MILLISECONDS.toMillis(50));
                    } catch (InterruptedException ie) {
                        //
                    }
                }
                System.out.println("Animation done.");
                start.setEnabled(true);
                panel.reset();
            }).start();

        });
        frame.setVisible(true);
    }
}
