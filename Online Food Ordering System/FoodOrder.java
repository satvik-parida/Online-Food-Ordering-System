import java.util.ArrayList;
import java.util.List;

public class FoodOrder {
    private final int orderId;
    private final List<FoodItem> items;
    private final double total;

    public FoodOrder(int orderId, List<FoodItem> items) {
        this.orderId = orderId;
        this.items = new ArrayList<>(items);
        double sum = 0;
        for (FoodItem item : this.items) sum += item.getPrice();
        this.total = sum;
    }

    public int getOrderId() { return orderId; }
    public List<FoodItem> getItems() { return items; }
    public double getTotal() { return total; }
}
