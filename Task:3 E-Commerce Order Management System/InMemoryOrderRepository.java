import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * In-memory implementation of OrderRepository backed by a HashMap.
 * All data is stored in the JVM heap for the lifetime of the application.
 * This implementation is self-contained and requires no external dependencies,
 * making it ideal for development, testing, and demo environments.
 *
 * Thread-safety note: this implementation is not synchronised. For concurrent
 * environments, wrap with Collections.synchronizedMap() or use ConcurrentHashMap.
 */
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<UUID, Order> store = new HashMap<>();

    /**
     * Saves the given order into the in-memory store.
     * If an order with the same ID already exists it is overwritten,
     * which supports both insert and update use cases.
     *
     * @param order the Order to persist; must not be null.
     * @throws IllegalArgumentException if order is null.
     */
    @Override
    public void saveOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException(
                    "Cannot save a null Order.");
        }
        store.put(order.getOrderId(), order);
        System.out.println("[InMemoryOrderRepository] Order saved: " + order.getOrderId());
    }

    /**
     * Retrieves an Order by its UUID.
     *
     * @param id the UUID to look up; must not be null.
     * @return the matching Order.
     * @throws IllegalArgumentException  if id is null.
     * @throws NoSuchElementException    if no order exists for the given ID.
     */
    @Override
    public Order findOrderById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Cannot search for an order with a null ID.");
        }
        Order order = store.get(id);
        if (order == null) {
            throw new NoSuchElementException(
                    "No order found with ID: " + id);
        }
        return order;
    }

    /**
     * Returns an unmodifiable view of all orders in the repository.
     * The returned list reflects the state of the store at call time;
     * subsequent saves or deletes are not reflected in the returned list.
     *
     * @return unmodifiable List of all Orders; never null, may be empty.
     */
    @Override
    public List<Order> findAllOrders() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

    /**
     * Removes an order from the store by its UUID.
     *
     * @param id the UUID of the order to remove; must not be null.
     * @throws IllegalArgumentException if id is null.
     * @throws NoSuchElementException   if no order exists for the given ID.
     */
    @Override
    public void deleteOrder(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Cannot delete an order with a null ID.");
        }
        if (!store.containsKey(id)) {
            throw new NoSuchElementException(
                    "Cannot delete — no order found with ID: " + id);
        }
        store.remove(id);
        System.out.println("[InMemoryOrderRepository] Order deleted: " + id);
    }

    /**
     * Returns the number of orders currently held in the repository.
     * Useful for diagnostics and assertions in tests.
     *
     * @return count of stored orders.
     */
    public int count() {
        return store.size();
    }

    @Override
    public String toString() {
        return "InMemoryOrderRepository{orderCount=" + store.size() + "}";
    }
}