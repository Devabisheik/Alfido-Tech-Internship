package foodordering;

import java.util.ArrayList;

public class Order {

    private int orderId;
    private Customer customer;
    private Restaurant restaurant;

    private ArrayList<OrderItem> items;

    private OrderStatus status;
    private double totalAmount;

    public Order(int orderId,
                 Customer customer,
                 Restaurant restaurant) {

        this.orderId = orderId;
        this.customer = customer;
        this.restaurant = restaurant;

        items = new ArrayList<>();
        status = OrderStatus.PLACED;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void calculateTotal() {

        totalAmount = 0;

        for(OrderItem item : items) {
            totalAmount += item.getSubtotal();
        }
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void displayOrder() {

        System.out.println("\nOrder Details");

        for(OrderItem item : items) {
            item.display();
        }

        System.out.println("Total = Rs." + totalAmount);
        System.out.println("Status = " + status);
    }
}
