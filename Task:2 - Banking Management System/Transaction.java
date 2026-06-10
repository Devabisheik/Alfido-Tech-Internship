import java.sql.*;
public class Transaction {
    public static void addDetails(long accNumber, double amount, double balanceAmount, String type) throws SQLException {
        String query = "INSERT INTO transactions " + "(account_number, transaction_type, amount, " + "balance_after_transaction, transaction_time) " + "VALUES (?, ?, ?, ?, ?)";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setLong(1, accNumber);
        pst.setString(2, type);
        pst.setDouble(3, amount);
        pst.setDouble(4, balanceAmount);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        pst.setTimestamp(5, time);
        int rows = pst.executeUpdate();
        if (rows > 0) {
            System.out.println("Transaction completed...");
        }
        pst.close();
    }
    public static void seeHistory(long accNumber) throws SQLException {
        String query = "SELECT * FROM transactions " + "WHERE account_number = ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setLong(1, accNumber);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println("======== " + rs.getLong("transaction_id") + " ========");
            System.out.println("Transaction Type: " + rs.getString("transaction_type"));
            System.out.println("Account Number: " + rs.getLong("account_number"));
            System.out.println("Amount: " + rs.getDouble("amount"));
            System.out.println("Balance After Transaction: " + rs.getDouble("balance_after_transaction"));
            System.out.println("Time: " + rs.getTimestamp("transaction_time"));
            System.out.println();
        }
        rs.close();
        pst.close();
    }
}