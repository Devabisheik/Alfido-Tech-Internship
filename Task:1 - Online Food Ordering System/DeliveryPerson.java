package foodordering;

public class DeliveryPerson {

    private int deliveryId;
    private String name;
    private String currentLocation;
    private boolean available;

    public DeliveryPerson(int deliveryId,
                          String name,
                          String currentLocation) {

        this.deliveryId = deliveryId;
        this.name = name;
        this.currentLocation = currentLocation;

        available = true;
    }

    public void acceptDelivery() {
        available = false;
        System.out.println(name + " accepted delivery.");
    }

    public void confirmDelivery() {
        available = true;
        System.out.println("Order Delivered.");
    }
}