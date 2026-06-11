import java.util.*;

public class Main {

    // ── Shared state ──────────────────────────────────────────────────────────
    private static final OrderRepository  repository   = new InMemoryOrderRepository();
    private static final OrderManager orderManager = new OrderManager(repository);
    private static final PaymentGateway stripe       = new StripePaymentProvider();
    private static final List<Product> catalogue    = new ArrayList<>();
    private static final List<Customer> customers    = new ArrayList<>();
    private static final Scanner sc           = new Scanner(System.in);

    // ── Entry point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        seedCatalogue();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1  -> productMenu();
                case 2  -> customerMenu();
                case 3  -> orderMenu();
                case 4  -> paymentMenu();
                case 5  -> statusMenu();
                case 6  -> viewMenu();
                case 0  -> { running = false; System.out.println("\n  Goodbye!\n"); }
                default -> System.out.println("  Invalid option. Please try again.");
            }
        }
        sc.close();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MENUS
    // ══════════════════════════════════════════════════════════════════════════

    private static void printMainMenu() {
        line();
        System.out.println("      E-COMMERCE ORDER MANAGEMENT SYSTEM");
        line();
        System.out.println("  [1] Product Management");
        System.out.println("  [2] Customer Management");
        System.out.println("  [3] Order Management");
        System.out.println("  [4] Payment Processing");
        System.out.println("  [5] Update Order Status");
        System.out.println("  [6] View Reports");
        System.out.println("  [0] Exit");
        line();
    }

    // ── 1. Product Management ─────────────────────────────────────────────────
    private static void productMenu() {
        boolean back = false;
        while (!back) {
            line();
            System.out.println("  PRODUCT MANAGEMENT");
            line();
            System.out.println("  [1] Add New Product");
            System.out.println("  [2] View All Products");
            System.out.println("  [3] Restock a Product");
            System.out.println("  [0] Back");
            line();
            switch (readInt("Enter choice: ")) {
                case 1  -> addProduct();
                case 2  -> listProducts();
                case 3  -> restockProduct();
                case 0  -> back = true;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private static void addProduct() {
        System.out.println("\n  -- Add New Product --");
        String name  = readString("  Product name        : ");
        String desc  = readString("  Description         : ");
        double price = readDouble("  Price ($)           : ");
        int    stock = readInt("  Stock quantity      : ");
        Product p = new Product(name, desc, price, stock);
        catalogue.add(p);
        System.out.println("Product added: " + p.getName() + " (ID: " + p.getProductId() + ")");
    }

    private static void listProducts() {
        System.out.println();
        if (catalogue.isEmpty()) { System.out.println("  No products in catalogue."); return; }
        System.out.printf("  %-4s %-25s %-10s %-8s%n", "#", "Name", "Price", "Stock");
        separator();
        for (int i = 0; i < catalogue.size(); i++) {
            Product p = catalogue.get(i);
            System.out.printf("  %-4d %-25s $%-9.2f %-8d%n",
                    i + 1, p.getName(), p.getPrice(), p.getStockQuantity());
        }
        System.out.println();
    }

    private static void restockProduct() {
        listProducts();
        if (catalogue.isEmpty()) return;
        int idx = readInt("  Select product #: ") - 1;
        if (idx < 0 || idx >= catalogue.size()) { System.out.println("  Invalid selection."); return; }
        int qty = readInt("  Quantity to add  : ");
        Product p = catalogue.get(idx);
        p.setStockQuantity(p.getStockQuantity() + qty);
        System.out.println(p.getName() + " restocked. New stock: " + p.getStockQuantity());
    }

    // ── 2. Customer Management ────────────────────────────────────────────────
    private static void customerMenu() {
        boolean back = false;
        while (!back) {
            line();
            System.out.println("  CUSTOMER MANAGEMENT");
            line();
            System.out.println("  [1] Register New Customer");
            System.out.println("  [2] View All Customers");
            System.out.println("  [3] Update Customer Profile");
            System.out.println("  [0] Back");
            line();
            switch (readInt("Enter choice: ")) {
                case 1  -> registerCustomer();
                case 2  -> listCustomers();
                case 3  -> updateCustomer();
                case 0  -> back = true;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private static void registerCustomer() {
        System.out.println("\n  -- Register New Customer --");
        String name    = readString("  Full name : ");
        String email   = readString("  Email     : ");
        String address = readString("  Address   : ");
        Customer c = new Customer(name, email, address);
        customers.add(c);
        System.out.println("Customer registered: " + c.getName() + " (ID: " + c.getCustomerId() + ")");
    }

    private static void listCustomers() {
        System.out.println();
        if (customers.isEmpty()) { System.out.println("  No customers registered."); return; }
        System.out.printf("  %-4s %-20s %-30s %-25s%n", "#", "Name", "Email", "Address");
        separator();
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.printf("  %-4d %-20s %-30s %-25s%n",
                    i + 1, c.getName(), c.getEmail(), c.getAddress());
        }
        System.out.println();
    }

    private static void updateCustomer() {
        listCustomers();
        if (customers.isEmpty()) return;
        int idx = readInt("  Select customer #: ") - 1;
        if (idx < 0 || idx >= customers.size()) { System.out.println("  Invalid selection."); return; }
        Customer c = customers.get(idx);
        System.out.println("  Leave blank to keep current value.");
        String name    = readStringOptional("  New name    [" + c.getName()    + "]: ", c.getName());
        String email   = readStringOptional("  New email   [" + c.getEmail()   + "]: ", c.getEmail());
        String address = readStringOptional("  New address [" + c.getAddress() + "]: ", c.getAddress());
        try {
            c.updateProfileInfo(name, email, address);
            System.out.println("Profile updated for: " + c.getName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // ── 3. Order Management ───────────────────────────────────────────────────
    private static void orderMenu() {
        boolean back = false;
        while (!back) {
            line();
            System.out.println("  ORDER MANAGEMENT");
            line();
            System.out.println("  [1] Place New Order");
            System.out.println("  [2] Add Item to Existing Order");
            System.out.println("  [3] Cancel an Order");
            System.out.println("  [4] View Order Details");
            System.out.println("  [0] Back");
            line();
            switch (readInt("Enter choice: ")) {
                case 1  -> placeOrder();
                case 2  -> addItemToOrder();
                case 3  -> cancelOrder();
                case 4  -> viewOrderDetails();
                case 0  -> back = true;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private static void placeOrder() {
        System.out.println("\n  -- Place New Order --");
        listCustomers();
        if (customers.isEmpty()) { System.out.println("  Please register a customer first."); return; }
        int cidx = readInt("  Select customer #: ") - 1;
        if (cidx < 0 || cidx >= customers.size()) { System.out.println("  Invalid selection."); return; }
        Customer customer = customers.get(cidx);

        listProducts();
        if (catalogue.isEmpty()) { System.out.println("  No products available."); return; }

        List<LineItem> items = new ArrayList<>();
        boolean addingItems = true;
        while (addingItems) {
            int pidx = readInt("  Select product # to add (0 to finish): ") - 1;
            if (pidx == -1) {
                if (items.isEmpty()) { System.out.println("  Add at least one item."); continue; }
                addingItems = false;
            } else if (pidx < 0 || pidx >= catalogue.size()) {
                System.out.println("  Invalid selection.");
            } else {
                Product p = catalogue.get(pidx);
                int qty = readInt("  Quantity (available: " + p.getStockQuantity() + "): ");
                try {
                    items.add(new LineItem(p, qty));
                    System.out.println("Added: " + p.getName() + " x" + qty);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        try {
            Order order = orderManager.createOrder(customer, items);
            System.out.println("\n" + order.getOrderDetails());
        } catch (Exception e) {
            System.out.println("Order failed: " + e.getMessage());
        }
    }

    private static void addItemToOrder() {
        System.out.println("\n  -- Add Item to Existing Order --");
        List<Order> orders = getNewOrders();
        if (orders.isEmpty()) { System.out.println("  No orders in NEW status available."); return; }
        printOrderList(orders);
        int oidx = readInt("  Select order #: ") - 1;
        if (oidx < 0 || oidx >= orders.size()) { System.out.println("  Invalid selection."); return; }
        Order order = orders.get(oidx);

        listProducts();
        int pidx = readInt("  Select product #: ") - 1;
        if (pidx < 0 || pidx >= catalogue.size()) { System.out.println("  Invalid selection."); return; }
        Product p = catalogue.get(pidx);
        int qty = readInt("  Quantity (available: " + p.getStockQuantity() + "): ");
        try {
            order.addLineItem(p, qty);
            repository.saveOrder(order);
            System.out.println("Item added. Updated total: $" + String.format("%.2f", order.getTotalAmount()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cancelOrder() {
        System.out.println("\n  -- Cancel Order --");
        List<Order> allOrders = orderManager.getAllOrders();
        List<Order> cancellable = new ArrayList<>();
        for (Order o : allOrders) {
            if (o.getStatus() != OrderStatus.DELIVERED && o.getStatus() != OrderStatus.CANCELLED)
                cancellable.add(o);
        }
        if (cancellable.isEmpty()) { System.out.println("  No cancellable orders found."); return; }
        printOrderList(cancellable);
        int oidx = readInt("  Select order # to cancel: ") - 1;
        if (oidx < 0 || oidx >= cancellable.size()) { System.out.println("  Invalid selection."); return; }
        try {
            orderManager.cancelOrder(cancellable.get(oidx).getOrderId());
            System.out.println("Order cancelled successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewOrderDetails() {
        System.out.println("\n  -- View Order Details --");
        List<Order> allOrders = orderManager.getAllOrders();
        if (allOrders.isEmpty()) { System.out.println("  No orders found."); return; }
        printOrderList(allOrders);
        int oidx = readInt("  Select order #: ") - 1;
        if (oidx < 0 || oidx >= allOrders.size()) { System.out.println("  Invalid selection."); return; }
        System.out.println("\n" + allOrders.get(oidx).getOrderDetails());
    }

    // ── 4. Payment Processing ─────────────────────────────────────────────────
    private static void paymentMenu() {
        System.out.println("\n  -- Payment Processing (Stripe) --");
        List<Order> newOrders = getNewOrders();
        if (newOrders.isEmpty()) { System.out.println("  No orders in NEW status awaiting payment."); return; }
        printOrderList(newOrders);
        int oidx = readInt("  Select order # to pay: ") - 1;
        if (oidx < 0 || oidx >= newOrders.size()) { System.out.println("  Invalid selection."); return; }
        Order order = newOrders.get(oidx);
        System.out.println("  Processing payment of $" + String.format("%.2f", order.getTotalAmount()) + "...");
        try {
            boolean result = orderManager.processOrderPayment(order.getOrderId(), stripe);
            if (result) System.out.println("Payment authorised. Order is now PAID.");
            else        System.out.println("Payment declined by Stripe.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ── 5. Update Order Status ────────────────────────────────────────────────
    private static void statusMenu() {
        System.out.println("\n  -- Update Order Status --");
        List<Order> allOrders = orderManager.getAllOrders();
        List<Order> updatable = new ArrayList<>();
        for (Order o : allOrders) {
            if (o.getStatus() != OrderStatus.DELIVERED && o.getStatus() != OrderStatus.CANCELLED)
                updatable.add(o);
        }
        if (updatable.isEmpty()) { System.out.println("  No orders available for status update."); return; }
        printOrderList(updatable);
        int oidx = readInt("  Select order #: ") - 1;
        if (oidx < 0 || oidx >= updatable.size()) { System.out.println("  Invalid selection."); return; }
        Order order = updatable.get(oidx);

        System.out.println("  Current status: " + order.getStatus());
        System.out.println("  Available statuses:");
        OrderStatus[] statuses = OrderStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + statuses[i]);
        }
        int sidx = readInt("  Select new status #: ") - 1;
        if (sidx < 0 || sidx >= statuses.length) { System.out.println("  Invalid selection."); return; }
        try {
            orderManager.updateOrderStatus(order.getOrderId(), statuses[sidx]);
            System.out.println("Status updated to: " + statuses[sidx]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ── 6. View Reports ───────────────────────────────────────────────────────
    private static void viewMenu() {
        boolean back = false;
        while (!back) {
            line();
            System.out.println("  VIEW REPORTS");
            line();
            System.out.println("  [1] All Products");
            System.out.println("  [2] All Customers");
            System.out.println("  [3] All Orders");
            System.out.println("  [4] Orders by Status");
            System.out.println("  [5] Order Summary (totals)");
            System.out.println("  [0] Back");
            line();
            switch (readInt("Enter choice: ")) {
                case 1  -> listProducts();
                case 2  -> listCustomers();
                case 3  -> orderManager.printAllOrders();
                case 4  -> ordersByStatus();
                case 5  -> orderSummary();
                case 0  -> back = true;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private static void ordersByStatus() {
        System.out.println("\n  Select status:");
        OrderStatus[] statuses = OrderStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + statuses[i]);
        }
        int sidx = readInt("  Choice: ") - 1;
        if (sidx < 0 || sidx >= statuses.length) { System.out.println("  Invalid selection."); return; }
        OrderStatus filter = statuses[sidx];
        List<Order> allOrders = orderManager.getAllOrders();
        boolean found = false;
        for (Order o : allOrders) {
            if (o.getStatus() == filter) { System.out.println(o.getOrderDetails()); found = true; }
        }
        if (!found) System.out.println("  No orders found with status: " + filter);
    }

    private static void orderSummary() {
        List<Order> allOrders = orderManager.getAllOrders();
        if (allOrders.isEmpty()) { System.out.println("  No orders found."); return; }
        System.out.println();
        System.out.printf("  %-8s %-20s %-12s %-10s%n", "No.", "Customer", "Status", "Total");
        separator();
        double grandTotal = 0;
        for (int i = 0; i < allOrders.size(); i++) {
            Order o = allOrders.get(i);
            System.out.printf("  %-8d %-20s %-12s $%-10.2f%n",
                    i + 1, o.getCustomer().getName(), o.getStatus(), o.getTotalAmount());
            grandTotal += o.getTotalAmount();
        }
        separator();
        System.out.printf("  %-41s $%-10.2f%n", "GRAND TOTAL", grandTotal);
        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HELPERS
    // ══════════════════════════════════════════════════════════════════════════

    private static void seedCatalogue() {
        catalogue.add(new Product("Laptop Pro 15",        "High-performance laptop",    1_299.99, 10));
        catalogue.add(new Product("Wireless Mouse",       "Ergonomic wireless mouse",      29.99, 50));
        catalogue.add(new Product("4K Monitor 27\"",      "Ultra-HD display monitor",     499.99,  5));
        catalogue.add(new Product("Noise-Cancel Headset", "Premium ANC headset",          199.99, 20));
        catalogue.add(new Product("Mechanical Keyboard",  "RGB mechanical keyboard",      149.99, 15));
        System.out.println("\nCatalogue seeded with " + catalogue.size() + " products.");
    }

    private static List<Order> getNewOrders() {
        List<Order> result = new ArrayList<>();
        for (Order o : orderManager.getAllOrders()) {
            if (o.getStatus() == OrderStatus.NEW) result.add(o);
        }
        return result;
    }

    private static void printOrderList(List<Order> orders) {
        System.out.println();
        System.out.printf("  %-4s %-20s %-12s %-10s%n", "#", "Customer", "Status", "Total");
        separator();
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            System.out.printf("  %-4d %-20s %-12s $%-10.2f%n",
                    i + 1, o.getCustomer().getName(), o.getStatus(), o.getTotalAmount());
        }
        System.out.println();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = sc.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = sc.nextLine().trim();
                double val = Double.parseDouble(line);
                if (val < 0) { System.out.println("  Value cannot be negative."); continue; }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (!val.isBlank()) return val;
            System.out.println("  This field cannot be blank.");
        }
    }

    private static String readStringOptional(String prompt, String defaultVal) {
        System.out.print(prompt);
        String val = sc.nextLine().trim();
        return val.isBlank() ? defaultVal : val;
    }

    private static void line()      { System.out.println("  ════════════════════════════════════════════════════════════════"); }
    private static void separator() { System.out.println("  ----------------------------------------------------------------"); }
}
