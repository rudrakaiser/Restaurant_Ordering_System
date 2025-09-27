package restaurant;

public interface Orderable {
    void addItem(MenuItem item, int quantity) throws InvalidQuantityException;
    void removeItem(MenuItem item) throws ItemNotFoundException;
    double generateBill();
}
