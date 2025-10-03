import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
    public static String dbURL = "jdbc:mariadb://localhost:3306/cinema";
    public static String dbUsername = "root";
    public static String dbPawwsord = "";

    public static Connection getConnection() throws SQLException {

        Connection connection = null;
        connection = DriverManager.getConnection(dbURL,dbUsername,dbPawwsord);
        return connection;
    }
}
