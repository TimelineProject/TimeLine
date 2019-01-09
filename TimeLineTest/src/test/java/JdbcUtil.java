
import java.sql.*;

public class JdbcUtil {
    private static String url = null;
    private static String username = null;
    private static String password = null;
    private static String dirverName = null;

    static {
        url = "jdbc:mysql://127.0.0.1:3306/timeline?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
        username = "root";
        password = "fujiaming123*";
        dirverName = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(dirverName);
        } catch (ClassNotFoundException var1) {
            var1.printStackTrace();
        }

    }

    public JdbcUtil() {
    }

    public static Connection getCon() throws SQLException {
        Connection conn = null;
        conn = (Connection)DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static void close(Statement statement, Connection conn) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException var4) {
                var4.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException var3) {
                var3.printStackTrace();
            }
        }

    }

    public static void close(PreparedStatement preparedStatement, Connection conn, ResultSet resultSet) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException var6) {
                var6.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException var5) {
                var5.printStackTrace();
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException var4) {
                var4.printStackTrace();
            }
        }

    }
}
