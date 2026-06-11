import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Customer {

    private UUID customerId;
    private String name;
    private String email;
    private String address;

    public Customer(String name, String email, String address) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.address = address;
    }

    /**
     * Creates and returns a new Order for this customer, pre-populated with
     * the provided line items. Each item's stock is reduced on the associated
     * Product at the moment the order is placed.
     *
     * Throws IllegalArgumentException if the items list is null or empty.
     * Throws IllegalStateException (propagated from Product) if any item
     * requests more stock than is available.
     */
    public Order placeOrder(List<LineItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot place an order with a null or empty items list.");
        }

        // Reduce stock for every product before creating the order.
        // If any stock reduction fails, the exception propagates and no
        // Order object is created, keeping the system in a consistent state.
        for (LineItem item : items) {
            item.getProduct().reduceStock(item.getQuantity());
        }

        Order order = new Order(this, items);
        return order;
    }

    /**
     * Updates the customer's mutable profile fields.
     * Null or blank values are rejected to prevent data corruption.
     */
    public void updateProfileInfo(String name, String email, String address) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank.");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or blank.");
        }
        this.name = name;
        this.email = email;
        this.address = address;
    }

    // --- Getters ---

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}