
import datarsians.DAO.factory.TipoDAO;
import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.DAO.interfaz.PedidoDAO;
import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorPedidos;
import datarsians.DAO.factory.DAOFactory;
import datarsians.vista.MenuPrincipal;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.sql.SQLException;
import java.util.logging.LogManager;

public static void main(String[] args) throws SQLException {

    LogManager.getLogManager().reset();
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");
    System.setProperty("org.slf4j.simpleLogger.log.org.hibernate", "off");
    System.setProperty("org.slf4j.simpleLogger.log.org.hibernate.SQL", "off");
    System.setProperty("org.slf4j.simpleLogger.log.org.hibernate.type.descriptor.sql", "off");
    System.setProperty("org.slf4j.simpleLogger.log.org.hibernate.orm.deprecation", "off");

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