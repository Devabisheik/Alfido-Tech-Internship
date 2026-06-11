import java.util.List;
import java.util.UUID;
import java.util.NoSuchElementException;

/**
 * OrderManager is the central application-service (facade) for all
 * order-related operations. It depends on OrderRepository (via the
 * interface, not the concrete class) and coordinates the full lifecycle
 * of an order: creation, payment, status progression, retrieval,
 * and cancellation.
 *
 * Dependency direction (matches UML):
 *   OrderManager -----> OrderRepository  (dependency / uses)
 *   OrderManager is reached via a dashed arrow from OrderRepository in
 *   the diagram, meaning OrderManager realises the use of the repository
 *   contract without extending or implementing it.
 */
public class OrderManager {

    private final OrderRepository orderRepository;

    /**
     * Constructor injection ensures OrderManager is always in a valid,
     * usable state and makes the dependency explicit and testable.
     *
     * @param orderRepository the repository implementation to use for
     *                        persisting and retrieving orders; must not be null.
     */
    public OrderManager(OrderRepository orderRepository) {
        if (orderRepository == null) {
            throw new IllegalArgumentException(
                    "OrderRepository must not be null.");
        }
        this.orderRepository = orderRepository;
    }

    /**
     * Places a new order on behalf of a customer, persists it, and
     * returns the saved Order.
     *
     * Stock reduction is handled inside Customer.placeOrder(). If any
     * product has insufficient stock the exception propagates and no
     * order is persisted, keeping the system consistent.
     *
     * @param customer the Customer placing the order; must not be null.
     * @param items    the list of LineItems to include; must not be null or empty.
     * @return the newly created and persisted Order.
     * @throws IllegalArgumentException if customer or items are null/empty.
     * @throws IllegalStateException    if any product has insufficient stock.
     */
    public Order createOrder(Customer customer, List<LineItem> items) {
        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer must not be null when creating an order.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException(
                    "Items list must not be null or empty when creating an order.");
        }
        Order order = customer.placeOrder(items);
        orderRepository.saveOrder(order);
        System.out.println("[OrderManager] New order created and saved: " + order.getOrderId());
        return order;
    }

    /**
     * Processes payment for an existing order using the supplied gateway.
     * On successful authorisation the order status advances to PAID and
     * the updated order is persisted.
     *
     * @param orderId         UUID of the order to pay; must not be null.
     * @param paymentGateway  the gateway to use for authorisation; must not be null.
     * @return true if payment was authorised and the order is now PAID;
     *         false if the gateway declined the charge.
     * @throws IllegalArgumentException if orderId or paymentGateway is null.
     * @throws NoSuchElementException   if no order exists for the given ID.
     */
    public boolean processOrderPayment(UUID orderId, PaymentGateway paymentGateway) {
        if (orderId == null) {
            throw new IllegalArgumentException(
                    "Order ID must not be null when processing payment.");
        }
        if (paymentGateway == null) {
            throw new IllegalArgumentException(
                    "PaymentGateway must not be null when processing payment.");
        }
        Order order = orderRepository.findOrderById(orderId);
        boolean success = order.processPayment(paymentGateway);
        if (success) {
            orderRepository.saveOrder(order);
            System.out.println("[OrderManager] Payment successful. Order " +
                    orderId + " is now PAID.");
        } else {
            System.out.println("[OrderManager] Payment declined for order: " + orderId);
        }
        return success;
    }

    /**
     * Advances an order to the next status in its lifecycle and persists
     * the change.
     *
     * Valid transitions enforced by Order.updateStatus():
     *   NEW -> PAID -> SHIPPED -> DELIVERED
     *   Any non-terminal status -> CANCELLED
     *
     * @param orderId   UUID of the order to update; must not be null.
     * @param newStatus the target OrderStatus; must not be null.
     * @throws IllegalArgumentException if orderId or newStatus is null.
     * @throws NoSuchElementException   if no order exists for the given ID.
     * @throws IllegalStateException    if the transition is invalid.
     */
    public void updateOrderStatus(UUID orderId, OrderStatus newStatus) {
        if (orderId == null) {
            throw new IllegalArgumentException(
                    "Order ID must not be null when updating status.");
        }
        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "New status must not be null when updating status.");
        }
        Order order = orderRepository.findOrderById(orderId);
        order.updateStatus(newStatus);
        orderRepository.saveOrder(order);
        System.out.println("[OrderManager] Order " + orderId +
                " status updated to: " + newStatus);
    }

    /**
     * Retrieves a single order by its UUID.
     *
     * @param orderId UUID of the order to retrieve; must not be null.
     * @return the matching Order.
     * @throws IllegalArgumentException if orderId is null.
     * @throws NoSuchElementException   if no order exists for the given ID.
     */
    public Order getOrderById(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException(
                    "Order ID must not be null when retrieving an order.");
        }
        return orderRepository.findOrderById(orderId);
    }

    /**
     * Returns all orders currently held in the repository.
     *
     * @return unmodifiable List of all Orders; never null, may be empty.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }

    /**
     * Cancels an order by transitioning it to CANCELLED status and
     * persisting the change.
     *
     * @param orderId UUID of the order to cancel; must not be null.
     * @throws IllegalArgumentException if orderId is null.
     * @throws NoSuchElementException   if no order exists for the given ID.
     * @throws IllegalStateException    if the order is already DELIVERED or CANCELLED.
     */
    public void cancelOrder(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException(
                    "Order ID must not be null when cancelling an order.");
        }
        Order order = orderRepository.findOrderById(orderId);
        order.updateStatus(OrderStatus.CANCELLED);
        orderRepository.saveOrder(order);
        System.out.println("[OrderManager] Order " + orderId + " has been CANCELLED.");
    }

    /**
     * Prints a formatted summary of every order in the repository to stdout.
     * Useful for diagnostics and end-to-end demo runs.
     */
    public void printAllOrders() {
        List<Order> orders = orderRepository.findAllOrders();
        if (orders.isEmpty()) {
            System.out.println("[OrderManager] No orders found in repository.");
            return;
        }
        System.out.println("[OrderManager] === All Orders (" + orders.size() + ") ===");
        for (Order order : orders) {
            System.out.println(order.getOrderDetails());
        }
    }

    @Override
    public String toString() {
        return "OrderManager{repository=" + orderRepository + "}";
    }
}