import java.sql.*;

public class Koneksi {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String HOST = "localhost";
    static final int PORT = 3306;
    static final String DATABASE = "appmatkul_db";
    static final String DB_URL = "jdbc:mysql://" + HOST + ':'+ PORT +'/'+ DATABASE;
    static final String username = "root";
    static final String password = "";

    static Connection conn;
    public static Connection connection() {
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, username, password);
        } catch (Exception e) {
            System.out.println("Connection Error");
            e.printStackTrace();
        }

        return conn;
    }
}
