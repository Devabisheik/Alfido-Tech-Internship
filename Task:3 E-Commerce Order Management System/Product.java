import java.util.UUID;

public class Product {

    private UUID productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;

    public Product(String name, String description, double price, int stockQuantity) {
        this.productId = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /**
     * Reduces stock by the given quantity.
     * Throws IllegalArgumentException if quantity is invalid.
     * Throws IllegalStateException if insufficient stock is available.
     */
    public boolean reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity to reduce must be greater than zero. Provided: " + quantity);
        }
        if (quantity > this.stockQuantity) {
            throw new IllegalStateException(
                    "Insufficient stock for product '" + this.name + "'. " +
                            "Requested: " + quantity + ", Available: " + this.stockQuantity);
        }
        this.stockQuantity -= quantity;
        return true;
    }

    // --- Getters ---

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    // --- Setters ---

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}