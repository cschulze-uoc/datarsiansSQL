package datarsians.vista;
import datarsians.DAO.factory.DAOFactory;
import datarsians.DAO.factory.TipoDAO;
import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.DAO.interfaz.PedidoDAO;
import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorPedidos;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.sql.SQLException;
import java.util.logging.LogManager;

public class MenuPrincipal {
    private final VistaCliente vistaCliente;
    private final VistaArticulos vistaArticulos;
    private final VistaPedidos vistaPedidos;

    public MenuPrincipal(ControladorArticulo controladorArticulo, ControladorCliente controladorCliente, ControladorPedidos controladorPedidos) throws SQLException {
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

        this.vistaCliente = new VistaCliente(controladorCliente);
        this.vistaArticulos = new VistaArticulos(controladorArticulo);
        this.vistaPedidos = new VistaPedidos(
                controladorArticulo,
                controladorCliente,
                controladorPedidos,
                this.vistaCliente);
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() throws SQLException {
        boolean salir = false;
        int opcion;

        do {
            System.out.println("\n============= MENÚ PRINCIPAL =============");
            System.out.println(" 1. Gestionar Artículos");
            System.out.println(" 2. Gestionar Clientes");
            System.out.println(" 3. Gestionar Pedidos");
            System.out.println(" 0. Salir");

            opcion = ConsoleHelper.SolicitarNumeroPorConsola(0, 3, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                     vistaArticulos.menuArticulos();
                    break;
                case 2:
                    vistaCliente.menuClientes();
                    break;
                case 3:
                    vistaPedidos.menuPedidos();
                    break;
                case 0:
                    salir = true;
                    break;
            }
        } while (!salir);
    }

}
