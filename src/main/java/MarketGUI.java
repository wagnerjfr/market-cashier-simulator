import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MarketGUI extends JFrame implements Runnable {

    private JPanel contentPane;
    private static int yPostion = 100;
    private static int height = 250;

    public MarketGUI(final String name, int desks, ClientQueue queue) {

        setTitle("MARKET");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, yPostion, 650, height);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        Summary summary = new Summary();
        contentPane.add(summary, BorderLayout.NORTH);

        JPanel panelDesks = new JPanel();
        contentPane.add(panelDesks, BorderLayout.CENTER);
        panelDesks.setBackground(Color.lightGray);
        panelDesks.setLayout(new FlowLayout());

        Cashier cashier;
        for (int i = 1; i <= desks; i++) {
            cashier = new Cashier(i, queue, summary);
            panelDesks.add(cashier);
            new Thread(cashier).start();
        }

        JPanel panelQueue = queue;
        contentPane.add(panelQueue, BorderLayout.SOUTH);
    }

    @Override
    public void run() {
        this.setVisible(true);
    }
}