import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Client extends JPanel {

    private final int number;
    private final List<Double> products = new ArrayList<>();

    public Client(int number) {
        this.number = number;
        createListOfProducts();
        try {
            SwingUtilities.invokeAndWait(() -> {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(new EmptyBorder(5, 5, 5, 5));

                JLabel clientNumber = new JLabel("Client " + number);
                clientNumber.setForeground(Color.YELLOW);
                add(clientNumber);

                JLabel numberOfItems = new JLabel("items: " + products.size());
                numberOfItems.setForeground(Color.white);
                add(numberOfItems);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void createListOfProducts() {
        Random rand = new Random();
        int MAX_ITEMS = 20;
        int MIN_ITEMS = 1;
        double MAX_VALUE = 20;
        double MIN_VALUE = 0.05;
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
        SwingUtilities.invokeLater(() -> {
            if (size < 8) {
                setBackground(Color.gray);
            } else if (size < 15) {
                setBackground(Color.darkGray);
            } else {
                setBackground(Color.black);
            }
            updateUI();
        });
    }
}
