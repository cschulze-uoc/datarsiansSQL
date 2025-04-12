
import datarsians.DAO.ArticuloDAO;
import datarsians.DAO.ClienteDAO;
import datarsians.DAO.PedidoDAO;
import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorPedidos;
import datarsians.DAO.factory.DAOFactory;
import datarsians.vista.MenuPrincipal;
import java.sql.SQLException;

public static void main(String[] args) throws SQLException {

    DAOFactory factory = DAOFactory.getDAOFactory();

    ClienteDAO clienteDAO = factory.getClienteDAO();
    ArticuloDAO articuloDAO = factory.getArticuloDAO();
    PedidoDAO pedidoDao = factory.getPedidoDAO();

    new MenuPrincipal(
            new ControladorArticulo(articuloDAO),
            new ControladorCliente(clienteDAO),
            new ControladorPedidos(articuloDAO, clienteDAO, pedidoDao)
    );

}