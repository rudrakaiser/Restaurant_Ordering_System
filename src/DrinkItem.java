public class DrinkItem extends MenuItem {
    private String size;

    public DrinkItem(String name, int price, String size) {
        super(name, price);
        this.size = size;
    }

    @Override
    public String getCategory() {
        return "Drink - " + size;
    }
}
