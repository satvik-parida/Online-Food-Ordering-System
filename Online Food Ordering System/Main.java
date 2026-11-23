import java.util.Scanner;

public class Main {

    static FoodOrderingSystem loadSystem() {
        FoodOrderingSystem s = new FoodOrderingSystem();
        s.addMenuItem(new FoodItem(1, "Veg Burger", "Snacks", 90));
        s.addMenuItem(new FoodItem(2, "Paneer Roll", "Snacks", 120));
        s.addMenuItem(new FoodItem(3, "Masala Dosa", "South Indian", 80));
        s.addMenuItem(new FoodItem(4, "Veg Biryani", "Main Course", 150));
        s.addMenuItem(new FoodItem(5, "Cold Coffee", "Beverages", 70));
        s.addMenuItem(new FoodItem(6, "Gulab Jamun", "Dessert", 50));
        return s;
    }

    static void demo() {
        FoodOrderingSystem s = new FoodOrderingSystem();
        s.addMenuItem(new FoodItem(1, "Veg Burger", "Snacks", 90));
        s.addMenuItem(new FoodItem(2, "Paneer Roll", "Snacks", 120));
        s.addMenuItem(new FoodItem(3, "Masala Dosa", "South Indian", 80));
        System.out.println("Menu sorted by price:");
        s.sortMenuByPrice();
        s.displayMenu();
        System.out.println();
        s.addToCart(0);
        s.addToCart(2);
        s.undoLastAddition();
        s.addToCart(1);
        System.out.println();
        s.placeOrder();
        System.out.println();
        s.processNextOrder();
        System.out.println();
        s.displayHistory();
    }

    static int readInt(Scanner in, String prompt) {
        System.out.print(prompt);
        if (!in.hasNextLine()) return -1;
        String line = in.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            demo();
            return;
        }
        FoodOrderingSystem system = loadSystem();
        Scanner in = new Scanner(System.in);
        System.out.println("=== Online Food Ordering System ===");
        while (true) {
            System.out.println();
            System.out.println("1. View / sort menu");
            System.out.println("2. Add item to cart");
            System.out.println("3. Undo last addition");
            System.out.println("4. View cart");
            System.out.println("5. Place order");
            System.out.println("6. Process next order");
            System.out.println("7. Order history");
            System.out.println("8. Exit");
            int choice = readInt(in, "Enter choice: ");
            if (choice == -1) break; // end of input
            switch (choice) {
                case 1:
                    int sort = readInt(in, "Sort by (1 = price, 2 = name, 0 = as is): ");
                    if (sort == 1) system.sortMenuByPrice();
                    else if (sort == 2) system.sortMenuByName();
                    system.displayMenu();
                    break;
                case 2:
                    system.displayMenu();
                    int n = readInt(in, "Menu number to add: ");
                    if (n == Integer.MIN_VALUE) System.out.println("Please enter a number.");
                    else system.addToCart(n - 1);
                    break;
                case 3:
                    system.undoLastAddition();
                    break;
                case 4:
                    system.displayCart();
                    break;
                case 5:
                    system.placeOrder();
                    break;
                case 6:
                    system.processNextOrder();
                    break;
                case 7:
                    system.displayHistory();
                    break;
                case 8:
                    if (system.pendingOrders() > 0) {
                        System.out.println(system.pendingOrders() + " order(s) are still pending.");
                    }
                    system.displayHistory();
                    System.out.println("Thank you for using the Online Food Ordering System.");
                    return;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 8.");
            }
        }
    }
}
