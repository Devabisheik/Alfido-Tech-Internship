package foodordering;

import java.util.ArrayList;

public class Restaurant {

    private int restaurantId;
    private String name;
    private String address;
    private double rating;

    private ArrayList<MenuItem> menuItems;

    public Restaurant(int restaurantId,
                      String name,
                      String address,
                      double rating) {

        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.rating = rating;

        menuItems = new ArrayList<>();
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public String getName() {
        return name;
    }

    public void showMenu() {

        System.out.println("\nMenu of " + name);

        for(MenuItem item : menuItems) {
            item.display();
        }
    }
}
