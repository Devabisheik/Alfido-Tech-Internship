import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/banking_system";
        String user = "root";
        String password = "Abi@2007";
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }
}
