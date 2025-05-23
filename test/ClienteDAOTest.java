import datarsians.DAO.factory.DAOFactory;
import datarsians.DAO.factory.MySQLDAOFactory;
import datarsians.DAO.factory.TipoDAO;
import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.modelo.Cliente;
import datarsians.modelo.ClienteEstandar;


public class ClienteDAOTest {
    public static void main(String[] args) {

        DAOFactory factory = DAOFactory.getDAOFactory(TipoDAO.HIBERNATE);
        ClienteDAO clienteDAO = factory.getClienteDAO();

        Cliente cliente = new ClienteEstandar("Juan Perez", "Calle Falsa 123", "12345678A", "juan.perez@example.com");

        try {
            clienteDAO.insertar(cliente);
            System.out.println("Cliente insertado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al insertar el cliente: " + e.getMessage());
        }
    }
}