import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountsDAO {

    public static void getAccount(long accNumber) throws SQLException {

        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM accounts WHERE account_number = ?";

        PreparedStatement pst = con.prepareStatement(query);

        pst.setLong(1, accNumber);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            System.out.println("\n===== ACCOUNT DETAILS =====");
            System.out.println("ACCOUNT NUMBER : " + rs.getLong("account_number"));
            System.out.println("BALANCE        : " + rs.getDouble("balance"));
            System.out.println("CLIENT ID      : " + rs.getString("client_id"));
            System.out.println("DATE           : " + rs.getTimestamp("transaction_date"));
        } else {
            System.out.println("Account Not Found...");
        }

        con.close();
    }

    public static void addAccount(String id, long accnumber, double balance) throws SQLException {
        String query = "INSERT INTO accounts VALUES(?,?,?,?)";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setLong(1, accnumber);
        pst.setDouble(2, balance);
        pst.setString(3, id);
        pst.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        int rows = pst.executeUpdate();
        if (rows > 0) {
            System.out.println("Your account successfully inserted...");
        }
        con.close();
    }

    public static Account findAccountObject(long accNumber) throws SQLException {
        Connection con = DBConnection.getConnection();
        String query = "SELECT * FROM accounts WHERE account_number = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setLong(1, accNumber);
        ResultSet rs = pst.executeQuery();
        Account account = null;
        if (rs.next()) {
            Client owner = ClientDAO.findClientObject(rs.getString("client_id"));
            account = new Account(
                    rs.getLong("account_number"),
                    owner
            );
            account.balance = rs.getDouble("balance");
        }
        con.close();
        return account;
    }

    public static List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        String query = "SELECT * FROM accounts";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Client owner = ClientDAO.findClientObject(rs.getString("client_id"));
            Account account = new Account(
                    rs.getLong("account_number"),
                    owner
            );

            account.balance = rs.getDouble("balance");

            accounts.add(account);
        }

        con.close();

        return accounts;
    }

    public static void deleteAccount(long accNumber) throws SQLException {

        Connection con = DBConnection.getConnection();

        String query = "DELETE FROM accounts WHERE account_number = ?";

        PreparedStatement pst = con.prepareStatement(query);

        pst.setLong(1, accNumber);

        int rows = pst.executeUpdate();

        if (rows > 0) {
            System.out.println("Account deleted successfully...");
        } else {
            System.out.println("Account not found...");
        }

        con.close();
    }

    public static double getBalance(long accNumber) throws SQLException {
        String query = "SELECT balance FROM accounts " + "WHERE account_number = ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setLong(1, accNumber);
        ResultSet rs = pst.executeQuery();
        double balance = 0;
        if (rs.next()) {
            balance = rs.getDouble("balance");
        }
        rs.close();
        pst.close();
        return balance;
    }
}