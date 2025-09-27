package restaurant;

public abstract class MenuItem {
    private String name;
    private int price; // integer টাকা

    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }

    public abstract String getCategory();
}
