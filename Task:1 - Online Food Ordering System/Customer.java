package foodordering;
public class Customer {

    private int customerId;
    private String name;
    private String email;
    private String address;
    private String phone;

    public Customer(int customerId, String name, String email,
                    String address, String phone) {

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void viewMenu() {
        System.out.println("Viewing Menu...");
    }

    public void placeOrder() {
        System.out.println("Order Placed.");
    }

    public void trackOrder() {
        System.out.println("Tracking Order...");
    }

    public void makePayment() {
        System.out.println("Payment Done.");
    }
}