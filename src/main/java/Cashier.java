import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Iterator;

public class Cashier extends JPanel implements Runnable {

    private double money = 0;
    private Client client = null;
    private final ClientQueue queue;
    private final Summary summary;
    private final JLabel jLabelMoney;
    private final JLabel jLabelClient;
    private final JLabel jLableNumItem;
    private final NumberFormat defaultFormat;
    private final String NO_CLIENT = "Client ?";
    private final String NO_ITEM = "Item: 0";

    public Cashier(int number, ClientQueue queue, Summary summary) {
        this.queue = queue;
        this.summary = summary;
        defaultFormat = NumberFormat.getCurrencyInstance();

        setBackground(Color.BLACK);
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

        jLableNumItem = new JLabel(NO_ITEM);
        jLableNumItem.setForeground(Color.lightGray);
        add(jLableNumItem);
    }

    @Override
    public void run() {
        try {
            Thread.sleep((int)(Math.random() * 10) * 1000);
            while (true) {
                if (client == null) {
                    client = queue.callClient();
                    // Wait client arrives
                    Thread.sleep(5000);
                    process();
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
        jLabelClient.setText("Client " + client.getNumber());
        for (Iterator<Double> it = client.getProducts(); it.hasNext();) {
            double item = it.next();
            buy = money += item;
            countItem++;
            jLabelMoney.setText(defaultFormat.format(money));
            jLableNumItem.setText("Item: " + countItem);
            Thread.sleep(1000);
        }
        summary.setOperation(buy);
        jLabelClient.setText(NO_CLIENT);
        jLableNumItem.setText(NO_ITEM);
        updateUI();
        client = null;
    }
}
