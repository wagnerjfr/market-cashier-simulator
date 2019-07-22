import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Client extends JPanel {

    private int number;
    private List<Double> products = new ArrayList<>();
    private final int MAX_ITEMS = 20;
    private final int MIN_ITEMS = 1;
    private final double MAX_VALUE = 20;
    private final double MIN_VALUE = 0.05;

    public Client(int number) {
        this.number = number;
        createListOfProducts();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel clientNumber = new JLabel("Client " + number);
        clientNumber.setForeground(Color.YELLOW);
        add(clientNumber);

        JLabel numberOfItems = new JLabel("items: " + products.size());
        numberOfItems.setForeground(Color.white);
        add(numberOfItems);
    }

    private void createListOfProducts() {
        Random rand = new Random();
        int random = rand.nextInt((MAX_ITEMS - MIN_ITEMS) + 1) + MIN_ITEMS;

        for (int i = 0; i < random; i++) {
            double value = MIN_VALUE + (MAX_VALUE - MIN_VALUE) * rand.nextDouble();
            products.add(value);
        }
        setColor(products.size());
    }

    public int getNumber() {
        return number;
    }

    public Iterator<Double> getProducts() {
        return products.iterator();
    }

    private void setColor(int size) {
        if (size < 8) {
            setBackground(Color.gray);
        }
        else if (size >= 8 && size < 15) {
            setBackground(Color.darkGray);
        }
        else {
            setBackground(Color.black);
        }
        updateUI();
    }
}
