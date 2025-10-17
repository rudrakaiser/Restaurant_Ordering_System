package restaurant;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AllOrdersWindow extends JFrame {
    private DataStore dataStore;
    private JPanel orderListPanel;

    public AllOrdersWindow(DataStore dataStore) {
        this.dataStore = dataStore;
        setTitle("All Orders");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("All Saved Orders", JLabel.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(header, BorderLayout.NORTH);

        orderListPanel = new JPanel();
        orderListPanel.setLayout(new BoxLayout(orderListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(orderListPanel);
        add(scrollPane, BorderLayout.CENTER);

        refreshOrderList();
    }

    private void refreshOrderList() {
        orderListPanel.removeAll();

        java.util.List<Order> orders = dataStore.getAllOrders();

        if (orders.isEmpty()) {
            JLabel none = new JLabel("No active orders.", JLabel.CENTER);
            none.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            orderListPanel.add(none);
        } else {
            for (Order order : orders) {
                JPanel one = new JPanel(new BorderLayout());
                one.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                one.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

                // Left: order details (multi-line)
                StringBuilder sb = new StringBuilder("<html>");
                sb.append("<b>Order ID:</b> ").append(order.getOrderId()).append("<br>");
                sb.append("<b>Items:</b><br>");
                for (OrderItem oi : order.getItems()) {
                    sb.append(oi.getItem().getName())
                      .append(" x").append(oi.getQuantity())
                      .append(" = ").append(oi.getTotalPrice()).append("৳<br>");
                }
                sb.append("<b>Total:</b> ").append(order.generateBill()).append("৳");
                sb.append("</html>");

                JLabel details = new JLabel(sb.toString());
                details.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
                one.add(details, BorderLayout.CENTER);

                // Right: Paid button
                JPanel right = new JPanel();
                right.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
                right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
                JButton paidBtn = new JButton("Paid");
                paidBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
                paidBtn.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this,
                        "Mark Order " + order.getOrderId() + " as Paid and remove it?",
                        "Confirm Paid", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean removed = dataStore.deleteOrder(order);
                        if (removed) {
                            JOptionPane.showMessageDialog(this, "Order " + order.getOrderId() + " removed.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Could not remove order (maybe already removed).");
                        }
                        refreshOrderList();
                    }
                });

                right.add(Box.createVerticalGlue());
                right.add(paidBtn);
                right.add(Box.createVerticalGlue());

                one.add(right, BorderLayout.EAST);

                orderListPanel.add(one);
                orderListPanel.add(Box.createRigidArea(new Dimension(0,8)));
            }
        }

        orderListPanel.revalidate();
        orderListPanel.repaint();
    }
}