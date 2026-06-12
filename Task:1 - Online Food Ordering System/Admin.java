package foodordering;

public class Admin {

    private int adminId;
    private String permissions;

    public Admin(int adminId, String permissions) {
        this.adminId = adminId;
        this.permissions = permissions;
    }

    public void manageUsers() {
        System.out.println("Users Managed.");
    }

    public void manageRestaurants() {
        System.out.println("Restaurants Managed.");
    }

    public void generateReports() {
        System.out.println("Report Generated.");
    }

    public void systemMaintenance() {
        System.out.println("System Maintenance Running.");
    }
}
