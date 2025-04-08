package datarsians.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BBDD_Util {
    private static final String URL = "jdbc:mysql://schulzeserver.com:4306/datarsians";
    private static final String USER = "DatarUser";
    private static final String PASSWORD = "7KKdizpDZ81DyI2mn8QC";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }
}
