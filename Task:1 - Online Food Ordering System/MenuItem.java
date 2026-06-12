package foodordering;

public class MenuItem {

    private int itemId;
    private String name;
    private String description;
    private double price;
    private boolean availability;

    public MenuItem(int itemId, String name,
                    String description,
                    double price,
                    boolean availability) {

        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void display() {
        System.out.println(itemId + " - "
                + name + " - Rs." + price);
    }
}
