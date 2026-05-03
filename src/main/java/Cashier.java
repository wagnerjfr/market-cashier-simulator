import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Iterator;

public class Cashier extends JPanel implements Runnable {

    private static final Color IDLE_COLOR = new Color(0, 100, 0);
    private static final Color BUSY_COLOR = Color.BLACK;

    private double money = 0;
    private Client client = null;
    private final ClientQueue queue;
    private final Summary summary;
    private final JLabel jLabelMoney;
    private final JLabel jLabelClient;
    private final JLabel jLabelNumItem;
    private final NumberFormat defaultFormat;
    private final String NO_CLIENT = "Client ?";
    private final String NO_ITEM = "Item: 0";

    public Cashier(int number, ClientQueue queue, Summary summary) {
        this.queue = queue;
        this.summary = summary;
        defaultFormat = NumberFormat.getCurrencyInstance();

        setBackground(IDLE_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel deskNumber = new JLabel("Cashier " + number);
        deskNumber.setForeground(Color.YELLOW);
        add(deskNumber);

        jLabelMoney = new JLabel(defaultFormat.format(money));
        jLabelMoney.setForeground(Color.GREEN);
        add(jLabelMoney);

        jLabelClient = new JLabel(NO_CLIENT);
        jLabelClient.setForeground(Color.WHITE);
        add(jLabelClient);

        jLabelNumItem = new JLabel(NO_ITEM);
        jLabelNumItem.setForeground(Color.lightGray);
        add(jLabelNumItem);
    }

    @Override
    public void run() {
        try {
            Thread.sleep((int)(Math.random() * 10) * 1000);
            while (true) {
                if (client == null) {
                    client = queue.callClient();
                    SwingUtilities.invokeLater(() -> setBackground(BUSY_COLOR));
                    // Wait client arrives
                    Thread.sleep(5000);
                    process();
                    SwingUtilities.invokeLater(() -> setBackground(IDLE_COLOR));
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void process() throws InterruptedException {
        int countItem = 0;
        double buy = 0;
        SwingUtilities.invokeLater(() -> jLabelClient.setText("Client " + client.getNumber()));
        for (Iterator<Double> it = client.getProducts(); it.hasNext();) {
            double item = it.next();
            buy = money += item;
            countItem++;
            double finalBuy = buy;
            int finalCountItem = countItem;
            SwingUtilities.invokeLater(() -> {
                jLabelMoney.setText(defaultFormat.format(finalBuy));
                jLabelNumItem.setText("Item: " + finalCountItem);
            });
            Thread.sleep(1000);
        }
        summary.setOperation(buy);
        SwingUtilities.invokeLater(() -> {
            jLabelClient.setText(NO_CLIENT);
            jLabelNumItem.setText(NO_ITEM);
            updateUI();
        });
        client = null;
    }
}
