package datarsians.controlador;

import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.DAO.interfaz.PedidoDAO;
import datarsians.modelo.Articulo;
import datarsians.modelo.Cliente;
import datarsians.modelo.Pedido;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class ControladorPedidos {
    private final ArticuloDAO articuloDAO;
    private final ClienteDAO clienteDAO;
    private final PedidoDAO pedidoDAO;

    public ControladorPedidos(ArticuloDAO articuloDAO, ClienteDAO clienteDAO, PedidoDAO pedidoDAO) {
        this.articuloDAO = articuloDAO;
        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
    }

    public Pedido realizarPedido(String emailCliente, String codigoArticulo, int cantidad) {
        try {
            Cliente cliente = clienteDAO.buscarPorEmail(emailCliente);
            Articulo articulo = articuloDAO.buscarPorCodigo(codigoArticulo);

            Pedido pedido = new Pedido(cliente, articulo, cantidad);
            pedidoDAO.insertar(pedido);
            return pedido;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Pedido> listarPedidos() throws SQLException {
        return pedidoDAO.obtenerTodos();
    }

    public boolean verificarCancelacionPedido(int numeroPedido) {
        try {
            Pedido pedido = pedidoDAO.buscarPorNumero(numeroPedido);
            return pedido.esCancelable();

        } catch (NoSuchElementException e) {
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String EliminarPedido(int numeroPedido) {
        try {
            pedidoDAO.eliminar(numeroPedido);
            return "Pedido eliminado correctamente";
        } catch (Exception e) {
            return "Error al eliminar el pedido: " + e.getMessage();
        }
    }

    public List<Pedido> getPedidosFiltrados(String emailCliente, boolean enviados) throws SQLException {
        return pedidoDAO.obtenerTodos().stream()
                .filter(p -> (emailCliente == null || p.getCliente().getEmail().equalsIgnoreCase(emailCliente)))
                .filter(p -> p.esCancelable() != enviados)
                .toList();
    }
}
