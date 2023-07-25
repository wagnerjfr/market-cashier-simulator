import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MarketGUI extends JFrame implements Runnable {

    public MarketGUI(final String name, int desks, ClientQueue queue) {

        setTitle(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(100, 100, 650, 250);
        JPanel contentPane = new JPanel();
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

        contentPane.add(queue, BorderLayout.SOUTH);
    }

    @Override
    public void run() {
        this.setVisible(true);
    }
}