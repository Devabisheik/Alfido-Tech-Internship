import java.sql.*;

public class BankServiceDAO {
    public static void deposit_money(long accNumber, double amount) throws SQLException {
        String query = "UPDATE accounts SET balance = COALESCE(balance, 0) + ? WHERE account_number = ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setDouble(1, amount);
        pst.setLong(2, accNumber);
        int rows = pst.executeUpdate();
        if (rows > 0) {
            System.out.println("Amount was credited to an account [" + accNumber + "]");
        }
        con.close();
    }
    public static void withdraw_money(long accNumber, double amount) throws SQLException {
        String query = "UPDATE accounts SET balance = COALESCE(balance, 0) - ? WHERE account_number = ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setDouble(1, amount);
        pst.setLong(2, accNumber);
        int rows = pst.executeUpdate();
        if (rows > 0) {
            System.out.println("Amount [" + amount + "] was debited from your account to an account [" + accNumber + "]");
        }
        con.close();
    }

    public static void transfer(long fromAcc, long toAcc, double amount) throws SQLException {
        String query1 = "UPDATE accounts SET balance = COALESCE(balance, 0) - ? WHERE account_number = ?";
        String query2 = "UPDATE accounts SET balance = COALESCE(balance, 0) + ? WHERE account_number = ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst1 = con.prepareStatement(query1);
        PreparedStatement pst2 = con.prepareStatement(query2);
        pst1.setDouble(1, amount);
        pst1.setLong(2, fromAcc);
        pst2.setDouble(1, amount);
        pst2.setLong(2, toAcc);
        con.setAutoCommit(false);
        int rows1 = pst1.executeUpdate();
        int rows2 = pst2.executeUpdate();

        if (rows1 > 0 && rows2 > 0) {
            con.commit();
            System.out.println("transaction completed successfully...");
        } else {
            con.rollback();
            System.out.println("Transaction failed. Changes have been rolled back.");
        }
        con.close();
    }
}
