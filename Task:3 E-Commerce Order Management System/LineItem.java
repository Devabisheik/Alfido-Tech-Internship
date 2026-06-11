public class LineItem {

    private Product product;
    private int quantity;
    private double unitPrice;

    public LineItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero. Provided: " + quantity);
        }
        this.product = product;
        this.quantity = quantity;
        // Snapshot the unit price at the time the line item is created,
        // so future price changes on the Product do not alter existing orders.
        this.unitPrice = product.getPrice();
    }

    /**
     * Returns the subtotal for this line item (unitPrice * quantity).
     */
    public double getSubtotal() {
        return this.unitPrice * this.quantity;
    }

    // --- Getters ---

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    // --- Setters ---

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero. Provided: " + quantity);
        }
        this.quantity = quantity;
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative.");
        }
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}