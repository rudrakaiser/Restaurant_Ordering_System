package restaurant;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int counter = 1;
    private int orderId;
    private List<OrderItem> items;

    public Order() {
        this.orderId = counter++;
        this.items = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public int generateBill() {
        int total = 0;
        for (OrderItem oi : items) {
            total += oi.getTotalPrice();
        }
        return total;
    }
}

