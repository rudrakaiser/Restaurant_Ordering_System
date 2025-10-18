public class FoodItem extends MenuItem {
    private String type;

    public FoodItem(String name, int price, String type) {
        super(name, price);
        this.type = type;
    }

    @Override
    public String getCategory() {
        return type;
    }
}
