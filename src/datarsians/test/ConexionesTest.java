package datarsians.test;

import java.sql.Connection;
import datarsians.DAO.factory.*;

public class ConexionesTest {


    public class ConexionTest {
        public static void main(String[] args) {
            var factory = DAOFactory.getDAOFactory();
            try (Connection conn = factory.getConexion()) {
                System.out.println("Conexión exitosa a la base de datos.");
            } catch (Exception e) {
                System.out.println("Error al conectar: " + e.getMessage());
            }
        }
    }
}
