// package restaurant;

// import java.util.HashMap;

// public class DataStore {
//     private HashMap<Integer, Order> orders;

//     public DataStore() {
//         orders = new HashMap<>();
//     }

//     public void saveOrder(Order order) {
//         orders.put(order.getOrderId(), order);
//     }

//     public Order getOrderById(int id) {
//         return orders.get(id);
//     }

//     public void deleteOrder(Order order) {
//         orders.remove(order);
//     }
// }

package restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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

    // ✅ New: return all stored orders as a List
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    // ✅ New: delete by id
    public boolean deleteOrder(int id) {
        return orders.remove(id) != null;
    }

    // ✅ Convenience: delete by Order object
    public boolean deleteOrder(Order order) {
        if (order == null) return false;
        return deleteOrder(order.getOrderId());
    }
}