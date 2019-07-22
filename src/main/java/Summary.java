import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;

public class Summary extends JPanel {
    private double total = 0;
    private int numClients = 0;
    private final String AVERAGE = "Average by client: ";
    private final String TOTAL = "Total: ";
    private final String CLIENTS = "Number of clients: ";
    private JLabel jLabelTotal, jLabelAverage, jLabelNumClients;
    private NumberFormat defaultFormat;

    public Summary() {
        defaultFormat = NumberFormat.getCurrencyInstance();

        setBackground(Color.DARK_GRAY);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        jLabelTotal = new JLabel(TOTAL);
        jLabelTotal.setForeground(Color.GREEN);
        add(jLabelTotal);

        jLabelAverage = new JLabel(AVERAGE);
        jLabelAverage.setForeground(Color.YELLOW);
        add(jLabelAverage);

        jLabelNumClients = new JLabel(CLIENTS);
        jLabelNumClients.setForeground(Color.WHITE);
        add(jLabelNumClients);
    }

    public synchronized void setOperation(double value) {
        numClients++;
        total+=value;
        jLabelTotal.setText(TOTAL + defaultFormat.format(total));
        jLabelAverage.setText(AVERAGE + defaultFormat.format(total/numClients));
        jLabelNumClients.setText(CLIENTS + numClients);
        updateUI();
    }
}
