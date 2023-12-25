import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

class QueueVisualization extends JFrame {

    private final Queue<Integer> queue;
    private static final int QUEUE_CAPACITY = 5;

    public QueueVisualization() {
        super("Queue Visualization");
        queue = new LinkedList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        QueuePanel queuePanel = new QueuePanel();
        mainPanel.add(queuePanel, BorderLayout.CENTER);

        JButton enqueueButton = new JButton("Enqueue");
        enqueueButton.addActionListener(e -> {
            if (queue.size() < QUEUE_CAPACITY) {
                // Ask user for input
                String userInput = JOptionPane.showInputDialog(this, "Enter a number to enqueue:");
                try {
                    int element = Integer.parseInt(userInput);
                    queue.add(element);
                    queuePanel.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Queue is full!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton dequeueButton = new JButton("Dequeue");
        dequeueButton.addActionListener(e -> {
            if (!queue.isEmpty()) {
                queue.poll();
                queuePanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Queue is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(enqueueButton);
        buttonPanel.add(dequeueButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    class QueuePanel extends JPanel {
        private static final int ELEMENT_WIDTH = 50;
        private static final int ELEMENT_HEIGHT = 30;
        private static final int SPACING = 10;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            //Color constantElementColor = Color.BLUE;
            int index = 0;
            for (Integer element : queue) {
                int x = index * (ELEMENT_WIDTH + SPACING);
                int y = 0;

                // Generate a random color for each element
                Color elementColor = new Color((int) (Math.random() * 256),
                        (int) (Math.random() * 256),
                        (int) (Math.random() * 256));

                g2d.setColor(elementColor);
                g2d.fillRect(x, y, ELEMENT_WIDTH, ELEMENT_HEIGHT);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, ELEMENT_WIDTH, ELEMENT_HEIGHT);
                g2d.drawString(element.toString(), x + ELEMENT_WIDTH / 2 - 5, y + ELEMENT_HEIGHT / 2 + 5);

                index++;
            }

            // Draw arrows between elements
            g2d.setColor(Color.RED);
            if (queue.size() > 1) {
                int arrowX = ELEMENT_WIDTH + SPACING / 2;
                int arrowY = ELEMENT_HEIGHT / 2;
                for (int i = 0; i < queue.size() - 1; i++) {
                    g2d.drawLine(arrowX, arrowY, arrowX + SPACING, arrowY);
                    arrowX += ELEMENT_WIDTH + SPACING;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QueueVisualization().setVisible(true));
    }
}

