import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Order {

    private UUID orderId;
    private Customer customer;
    private List<LineItem> items;
    private LocalDateTime orderDate;
    private double totalAmount;
    private OrderStatus status;

    /**
     * Package-level constructor — Orders are created exclusively through
     * Customer.placeOrder() to ensure stock has been reduced first.
     * A defensive copy of the items list is made to prevent external mutation.
     */
    Order(Customer customer, List<LineItem> items) {
        this.orderId = UUID.randomUUID();
        this.customer = customer;
        this.items = new ArrayList<>(items);
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.NEW;
        this.totalAmount = calculateTotal();
    }

    /**
     * Adds a single LineItem to the order and recalculates the total.
     * Only permitted while the order is still in NEW status.
     */
    public void addLineItem(Product product, int quantity) {
        if (this.status != OrderStatus.NEW) {
            throw new IllegalStateException(
                    "Cannot add items to an order that is not in NEW status. " +
                            "Current status: " + this.status);
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero. Provided: " + quantity);
        }
        // Reduce stock on the product before adding to order.
        product.reduceStock(quantity);
        LineItem newItem = new LineItem(product, quantity);
        this.items.add(newItem);
        this.totalAmount = calculateTotal();
    }

    /**
     * Iterates over all line items and sums their subtotals.
     * Returns 0.0 for an empty items list.
     */
    public double calculateTotal() {
        double total = 0.0;
        for (LineItem item : this.items) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Delegates to the provided PaymentGateway to authorise payment.
     * On success, advances the order status to PAID.
     * Returns true if authorisation succeeded, false otherwise.
     */
    public boolean processPayment(PaymentGateway paymentProvider) {
        if (paymentProvider == null) {
            throw new IllegalArgumentException("PaymentGateway cannot be null.");
        }
        if (this.status != OrderStatus.NEW) {
            throw new IllegalStateException(
                    "Payment can only be processed for orders in NEW status. " +
                            "Current status: " + this.status);
        }
        boolean authorised = paymentProvider.authorizePayment(this);
        if (authorised) {
            this.status = OrderStatus.PAID;
        }
        return authorised;
    }

    /**
     * Updates the order status following valid transition rules:
     * NEW -> PAID -> SHIPPED -> DELIVERED
     * Any status -> CANCELLED (only if not already DELIVERED)
     */
    public void updateStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("New status cannot be null.");
        }
        if (this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                    "Cannot change the status of an already DELIVERED order.");
        }
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cannot change the status of an already CANCELLED order.");
        }
        if (newStatus == OrderStatus.CANCELLED) {
            this.status = OrderStatus.CANCELLED;
            return;
        }
        // Enforce the forward-only progression: NEW->PAID->SHIPPED->DELIVERED
        boolean validTransition =
                (this.status == OrderStatus.NEW      && newStatus == OrderStatus.PAID)     ||
                        (this.status == OrderStatus.PAID     && newStatus == OrderStatus.SHIPPED)  ||
                        (this.status == OrderStatus.SHIPPED  && newStatus == OrderStatus.DELIVERED);

        if (!validTransition) {
            throw new IllegalStateException(
                    "Invalid status transition from " + this.status + " to " + newStatus + ". " +
                            "Allowed progression: NEW -> PAID -> SHIPPED -> DELIVERED.");
        }
        this.status = newStatus;
    }

    /**
     * Returns a formatted, human-readable summary of the order.
     */
    public String getOrderDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Order Details ===\n");
        sb.append("Order ID    : ").append(orderId).append("\n");
        sb.append("Customer    : ").append(customer.getName())
                .append(" (").append(customer.getEmail()).append(")\n");
        sb.append("Order Date  : ").append(orderDate).append("\n");
        sb.append("Status      : ").append(status).append("\n");
        sb.append("Items:\n");
        for (LineItem item : items) {
            sb.append("  - ").append(item.getProduct().getName())
                    .append(" | Qty: ").append(item.getQuantity())
                    .append(" | Unit Price: $").append(String.format("%.2f", item.getUnitPrice()))
                    .append(" | Subtotal: $").append(String.format("%.2f", item.getSubtotal()))
                    .append("\n");
        }
        sb.append("Total Amount: $").append(String.format("%.2f", totalAmount)).append("\n");
        sb.append("=====================");
        return sb.toString();
    }

    // --- Getters ---

    public UUID getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<LineItem> getItems() {
        return new ArrayList<>(items); // Return defensive copy
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customer=" + customer.getName() +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", itemCount=" + items.size() +
                '}';
    }
}