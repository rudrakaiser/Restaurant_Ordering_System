import javax.swing.*;
import java.awt.*;

public class BillDialog extends JDialog {
    public BillDialog(JFrame parent, DataStore dataStore, Order order) {
        super(parent, "Order ID " + order.getOrderId(), true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10,10));

        // ===== Bill Text Area =====
        JTextArea billArea = new JTextArea();
        billArea.setEditable(false);
        billArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        sb.append("\n BILL\n______________________________________\n\n");
        sb.append(" Order ID: ").append(order.getOrderId()).append("\n\n Items:\n\n");

        for (OrderItem oi : order.getItems()) {
            sb.append(" -> ").append(oi.getItem().getName())
              .append(" x").append(oi.getQuantity())
              .append(" = ").append(oi.getTotalPrice())
              .append(" Taka\n");
        }
        sb.append("______________________________________\n\n");
        sb.append(" Total: ").append(order.getTotalPrice()).append(" Taka\n");

        billArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(billArea);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Buttons Panel =====
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton okBtn = new JButton("OK");
        okBtn.setBackground(Color.GRAY);
        okBtn.setForeground(Color.WHITE);
        okBtn.setFocusPainted(false);
        okBtn.addActionListener(e -> dispose());

        JButton paidBtn = new JButton("Paid");
        paidBtn.setBackground(new Color(70, 180, 90)); // Green Color
        // paidBtn.setBackground(new Color(211, 130, 1)); Golden Color
        paidBtn.setForeground(Color.WHITE);
        paidBtn.setFocusPainted(false);
        paidBtn.addActionListener(e -> {
            dataStore.deleteOrder(order.getOrderId());
            JOptionPane.showMessageDialog(parent,
                    "Order #" + order.getOrderId() + " has been marked as paid!",
                    "Payment Successful", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnPanel.add(okBtn);
        btnPanel.add(paidBtn);

        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
