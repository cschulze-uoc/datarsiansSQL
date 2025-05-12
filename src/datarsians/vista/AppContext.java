package datarsians.vista;

import datarsians.DAO.factory.DAOFactory;
import datarsians.DAO.factory.TipoDAO;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorPedidos;

public class AppContext {
    private static final DAOFactory factory = DAOFactory.getDAOFactory(TipoDAO.HIBERNATE);

    private static final ControladorCliente controladorCliente =
            new ControladorCliente(factory.getClienteDAO());

    private static final ControladorArticulo controladorArticulo =
            new ControladorArticulo(factory.getArticuloDAO());

    private static final ControladorPedidos controladorPedido =
            new ControladorPedidos(factory.getArticuloDAO(), factory.getClienteDAO(), factory.getPedidoDAO()
                    );

    public static ControladorCliente getControladorCliente() {
        return controladorCliente;
    }

    public static ControladorArticulo getControladorArticulo() {
        return controladorArticulo;
    }

    public static ControladorPedidos getControladorPedido() {
        return controladorPedido;
    }
}