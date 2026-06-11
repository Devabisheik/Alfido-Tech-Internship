import java.util.List;
import java.util.UUID;

/**
 * Interface defining the persistence contract for Order entities.
 * Concrete implementations may use an in-memory Map, a relational
 * database, or any other storage mechanism without affecting the
 * OrderManager or any other consumer of this interface.
 */
public interface OrderRepository {

    /**
     * Persists a new Order or overwrites an existing one with the same ID.
     *
     * @param order the Order to save; must not be null.
     */
    void saveOrder(Order order);

    /**
     * Retrieves an Order by its unique identifier.
     *
     * @param id the UUID of the order to find; must not be null.
     * @return the matching Order.
     * @throws java.util.NoSuchElementException if no order exists for the given ID.
     */
    Order findOrderById(UUID id);

    /**
     * Returns all orders currently held in the repository.
     *
     * @return an unmodifiable List of all Orders; never null, may be empty.
     */
    List<Order> findAllOrders();

    /**
     * Removes an order from the repository by its unique identifier.
     *
     * @param id the UUID of the order to delete; must not be null.
     * @throws java.util.NoSuchElementException if no order exists for the given ID.
     */
    void deleteOrder(UUID id);
}