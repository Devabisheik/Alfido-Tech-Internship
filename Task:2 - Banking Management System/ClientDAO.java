import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public static void getClient(String id) throws SQLException {
        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM clients WHERE client_id = ?";

        PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, id);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            System.out.println("Client details are fetched from database...");
            System.out.println("ID: " + rs.getString("client_id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Address: " + rs.getString("address"));
        } else {
            System.out.println("Client not found in database.");
        }

        con.close();
    }
    public static void addClient(String id, String name, String address) throws SQLException {
        String query = "INSERT INTO clients VALUES(?,?,?)";

        Connection con = DBConnection.getConnection();

        PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, id);
        pst.setString(2, name);
        pst.setString(3, address);

        int rows = pst.executeUpdate();

        if (rows > 0) {
            System.out.println("Your details successfully inserted...");
        }

        con.close();
    }
    public static Client findClientObject(String id) throws SQLException {

        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM clients WHERE client_id = ?";

        PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, id);

        ResultSet rs = pst.executeQuery();

        Client client = null;

        if (rs.next()) {
            client = new Client(
                    rs.getString("client_id"),
                    rs.getString("name"),
                    rs.getString("address")
            );
        }

        con.close();

        return client;
    }

    public static List<Client> getAllClients() throws SQLException {

        List<Client> clients = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM clients";

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {

            Client client = new Client(
                    rs.getString("client_id"),
                    rs.getString("name"),
                    rs.getString("address")
            );

            clients.add(client);
        }

        con.close();

        return clients;
    }

    public static void deleteClient(String id) throws SQLException {

        Connection con = DBConnection.getConnection();

        String query = "DELETE FROM clients WHERE client_id = ?";

        PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, id);

        int rows = pst.executeUpdate();

        if (rows > 0) {
            System.out.println("Client deleted successfully...");
        } else {
            System.out.println("Client not found...");
        }

        con.close();
    }
}