package foodordering;
public class User extends Customer {

    private String permissions;

    public User(int customerId, String name, String email,
                String address, String phone, String permissions) {

        super(customerId, name, email, address, phone);
        this.permissions = permissions;
    }

    public void manageOrders() {
        System.out.println("Managing Orders...");
    }

    public void updateStatus() {
        System.out.println("Order Status Updated.");
    }
}