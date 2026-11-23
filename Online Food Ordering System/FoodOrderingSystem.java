import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class FoodOrderingSystem {
    private final ArrayList<FoodItem> menu = new ArrayList<>();
    private final ArrayList<FoodItem> cart = new ArrayList<>();
    private final Stack<FoodItem> actionStack = new Stack<>();
    private final Queue<FoodOrder> orderQueue = new LinkedList<>();
    private final Stack<FoodOrder> history = new Stack<>();
    private int nextOrderId = 1;

    public void addMenuItem(FoodItem item) { menu.add(item); }
    public int menuSize() { return menu.size(); }
    public int pendingOrders() { return orderQueue.size(); }

    public void sortMenuByPrice() { menu.sort(Comparator.comparingDouble(FoodItem::getPrice)); }
    public void sortMenuByName() { menu.sort(Comparator.comparing(f -> f.getName().toLowerCase())); }

    public void displayMenu() {
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }

    public boolean addToCart(int menuIndex) {
        if (menuIndex < 0 || menuIndex >= menu.size()) {
            System.out.println("Invalid menu selection.");
            return false;
        }
        FoodItem item = menu.get(menuIndex);
        cart.add(item);
        actionStack.push(item);
        System.out.println("Added: " + item.getName());
        return true;
    }

    public void undoLastAddition() {
        if (actionStack.empty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        FoodItem last = actionStack.pop();
        cart.remove(last);
        System.out.println("Removed latest addition: " + last.getName());
    }

    public void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        double total = 0;
        for (FoodItem item : cart) {
            System.out.println("  - " + item);
            total += item.getPrice();
        }
        System.out.println("  Cart total: Rs. " + String.format("%.2f", total));
    }

    public void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add items before placing an order.");
            return;
        }
        FoodOrder order = new FoodOrder(nextOrderId++, cart);
        orderQueue.offer(order);
        cart.clear();
        actionStack.clear();
        System.out.println("Order #" + order.getOrderId() + " placed. Total: Rs. "
                + String.format("%.2f", order.getTotal()));
    }

    public void processNextOrder() {
        FoodOrder order = orderQueue.poll();
        if (order == null) {
            System.out.println("No pending orders.");
            return;
        }
        System.out.println("Processing order #" + order.getOrderId());
        for (FoodItem item : order.getItems()) {
            System.out.println("  - " + item);
        }
        history.push(order);
    }

    public void displayHistory() {
        if (history.empty()) {
            System.out.println("No processed orders yet.");
            return;
        }
        System.out.println("Order History (latest first):");
        for (int i = history.size() - 1; i >= 0; i--) {
            FoodOrder o = history.get(i);
            System.out.println("  Order #" + o.getOrderId() + " - " + o.getItems().size()
                    + " item(s) - Rs. " + String.format("%.2f", o.getTotal()));
        }
    }
}
