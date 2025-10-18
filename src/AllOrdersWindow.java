import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AllOrdersWindow extends JFrame {
    private DataStore dataStore;
    private JPanel orderListPanel;

    public AllOrdersWindow(DataStore dataStore) {
        this.dataStore = dataStore;
        setTitle("All Orders");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel header = new JLabel("All Saved Orders", JLabel.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        header.setForeground(new Color(30,30,30));
        add(header, BorderLayout.NORTH);

        // Main Panel with scroll
        orderListPanel = new JPanel();
        orderListPanel.setLayout(new BoxLayout(orderListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(orderListPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(scrollPane, BorderLayout.CENTER);

        refreshOrderList();
    }

    private void refreshOrderList() {
        orderListPanel.removeAll();

        List<Order> orders = dataStore.getAllOrders();

        if (orders.isEmpty()) {
            JLabel none = new JLabel("No active orders.", JLabel.CENTER);
            none.setFont(new Font("SansSerif", Font.ITALIC, 16));
            none.setForeground(Color.DARK_GRAY);
            none.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            orderListPanel.add(none);
        } else {
            for (Order order : orders) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(245,245,245));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                        BorderFactory.createEmptyBorder(10,10,10,10)
                ));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

                // ===== Order Details =====
                StringBuilder sb = new StringBuilder("<html>");
                sb.append("<b>Order ID:</b> ").append(order.getOrderId()).append("<br>");
                sb.append("<b>Items:</b><br>");
                for (OrderItem oi : order.getItems()) {
                    sb.append(oi.getItem().getName())
                      .append(" x").append(oi.getQuantity())
                      .append(" = ").append(oi.getTotalPrice()).append("৳<br>");
                }
                sb.append("<b>Total:</b> ").append(order.getTotalPrice()).append("৳");
                sb.append("</html>");

                JLabel details = new JLabel(sb.toString());
                details.setFont(new Font("Monospaced", Font.PLAIN, 14));
                details.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                card.add(details, BorderLayout.CENTER);

                // ===== Paid Button =====
                JPanel right = new JPanel();
                right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
                right.setOpaque(false);
                JButton paidBtn = new JButton("Paid");
                paidBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
                paidBtn.setBackground(new Color(70,180,90));
                paidBtn.setForeground(Color.WHITE);
                paidBtn.setFocusPainted(false);
                paidBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
                paidBtn.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Mark Order " + order.getOrderId() + " as Paid and remove it?",
                            "Confirm Paid", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean removed = dataStore.deleteOrder(order.getOrderId());
                        if (removed) {
                            JOptionPane.showMessageDialog(this, "✅ Order " + order.getOrderId() + " removed.");
                        } else {
                            JOptionPane.showMessageDialog(this, "❌ Could not remove order.");
                        }
                        refreshOrderList();
                    }
                });

                right.add(Box.createVerticalGlue());
                right.add(paidBtn);
                right.add(Box.createVerticalGlue());
                card.add(right, BorderLayout.EAST);

                // Add card to list
                orderListPanel.add(card);
                orderListPanel.add(Box.createRigidArea(new Dimension(0,10)));
            }
        }

        orderListPanel.revalidate();
        orderListPanel.repaint();
    }
}
