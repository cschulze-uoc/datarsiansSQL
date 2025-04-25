
import datarsians.DAO.factory.TipoDAO;
import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.DAO.interfaz.PedidoDAO;
import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorPedidos;
import datarsians.DAO.factory.DAOFactory;
import datarsians.vista.MenuPrincipal;
import java.sql.SQLException;

public static void main(String[] args) throws SQLException {

    DAOFactory factory = DAOFactory.getDAOFactory(TipoDAO.HIBERNATE);

    ClienteDAO clienteDAO = factory.getClienteDAO();
    ArticuloDAO articuloDAO = factory.getArticuloDAO();
    PedidoDAO pedidoDao = factory.getPedidoDAO();

    new MenuPrincipal(
            new ControladorArticulo(articuloDAO),
            new ControladorCliente(clienteDAO),
            new ControladorPedidos(articuloDAO, clienteDAO, pedidoDao)
    );

}