class Client {
    private String id;
    private String name;
    private String address;

    Client() {
        this.id = "C000";
        this.name = "";
        this.address = "";
    }

    Client(String id, String name, String address) {
        this.name = name;
        this.id = id;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String toString() {
        return "ID: " + this.id + "\nName: " + this.name + "\nAddress: " +
                this.address;
    }

}
