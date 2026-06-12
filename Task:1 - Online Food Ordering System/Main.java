package foodordering;

public class Main {

    public static void main(String[] args) {

        Customer customer =
                new Customer(
                        1,
                        "Arshad",
                        "arshad@gmail.com",
                        "Asansol",
                        "9999999999"
                );

        Restaurant restaurant =
                new Restaurant(
                        101,
                        "Food Hub",
                        "City Center",
                        4.5
                );

        MenuItem burger =
                new MenuItem(
                        1,
                        "Burger",
                        "Veg Burger",
                        120,
                        true
                );

        MenuItem pizza =
                new MenuItem(
                        2,
                        "Pizza",
                        "Cheese Pizza",
                        250,
                        true
                );

        restaurant.addMenuItem(burger);
        restaurant.addMenuItem(pizza);

        restaurant.showMenu();

        Order order =
                new Order(
                        1001,
                        customer,
                        restaurant
                );

        order.addItem(
                new OrderItem(burger,2)
        );

        order.addItem(
                new OrderItem(pizza,1)
        );

        order.calculateTotal();

        order.displayOrder();

        Payment payment =
                new Payment(
                        1,
                        1001,
                        order.getTotalAmount(),
                        "UPI"
                );

        payment.processPayment();

        DeliveryPerson dp =
                new DeliveryPerson(
                        1,
                        "Rahul",
                        "Asansol"
                );

        dp.acceptDelivery();

        order.updateStatus(
                OrderStatus.DELIVERED
        );

        dp.confirmDelivery();

        order.displayOrder();
    }
}