package foodordering;

public class OrderItem {

    private MenuItem menuItem;
    private int quantity;
    private double subtotal;

    public OrderItem(MenuItem menuItem, int quantity) {

        this.menuItem = menuItem;
        this.quantity = quantity;

        subtotal = menuItem.getPrice() * quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void display() {

        System.out.println(
                menuItem.getName()
                + " x "
                + quantity
                + " = Rs."
                + subtotal
        );
    }
}
