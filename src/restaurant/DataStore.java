package restaurant;

import java.util.HashMap;

public class DataStore {
    private HashMap<Integer, Order> orders;

    public DataStore() {
        orders = new HashMap<>();
    }

    public void saveOrder(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public Order getOrderById(int id) {
        return orders.get(id);
    }
}
