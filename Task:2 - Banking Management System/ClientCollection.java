import java.util.ArrayList;
import java.util.List;

class ClientCollection {
    private List<Client> clients;

    ClientCollection() {
        clients = new ArrayList<>();
    }

    public void add(Client client) {
        clients.add(client);
        System.out.println("Your details was successfully created");
    }

    public void remove(Client client) {
        try {
            clients.remove(client);
            System.out.println("your details was successfully removed");
        } catch (Exception e) {
            System.err.println("Caught: " + e.getMessage());
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public Client getClient(String id) {
        for (Client client : clients) {
            if (client.getId().equals(id)) {
                return client;
            }
        }
        System.out.println("Client with ID " + id + " not found");
        return null;
    }
}
