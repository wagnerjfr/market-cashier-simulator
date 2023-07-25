import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class ClientQueue extends JPanel implements Runnable {

    private final Queue<Client> clients = new LinkedList<>();
    private int clientNumber = 10;

    public ClientQueue() {
        setBackground(Color.lightGray);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        for (int i = 1; i <= clientNumber; i++) {
            addClient(i);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                int random = (int)(Math.random() * 10);
                Thread.sleep(random * 1000);
                addClient(++clientNumber);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addClient(int i) {
        Client client = new Client(i);
        clients.add(client);
        add(client);
        updateUI();
    }

    private Client removeClient() {
        Client client = clients.remove();
        remove(client);
        updateUI();
        return client;
    }

    public synchronized Client callClient() throws InterruptedException {
        while (clients.isEmpty()) {
            Thread.sleep(900);
        }
        return removeClient();
    }
}
