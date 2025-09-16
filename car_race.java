import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CarRace extends JPanel implements Runnable {
    private int car1X = 50;
    private int car2X = 50;
    private final int finishLine = 600;

    public CarRace() {
        setPreferredSize(new Dimension(800, 300));
        setBackground(Color.WHITE);
        new Thread(this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw finish line
        g.setColor(Color.RED);
        g.fillRect(finishLine, 0, 10, getHeight());

        // Draw Car 1
        g.setColor(Color.BLUE);
        g.fillRect(car1X, 80, 60, 30);

        // Draw Car 2
        g.setColor(Color.GREEN);
        g.fillRect(car2X, 160, 60, 30);

        // Labels
        g.setColor(Color.BLACK);
        g.drawString("Car 1", car1X, 75);
        g.drawString("Car 2", car2X, 155);
    }

    @Override
    public void run() {
        Random rand = new Random();

        while (car1X < finishLine && car2X < finishLine) {
            car1X += rand.nextInt(10); // move by 0–9 pixels
            car2X += rand.nextInt(10);

            repaint();

            try {
                Thread.sleep(100); // pause for animation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Show result
        if (car1X >= finishLine && car2X >= finishLine) {
            JOptionPane.showMessageDialog(this, "It's a tie!");
        } else if (car1X >= finishLine) {
            JOptionPane.showMessageDialog(this, "🚗 Car 1 Wins!");
        } else {
            JOptionPane.showMessageDialog(this, "🚙 Car 2 Wins!");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Race Game");
        CarRace race = new CarRace();
        frame.add(race);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
