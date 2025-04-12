package datarsians.controlador;

import datarsians.modelo.Articulo;
import datarsians.modelo.Cliente;
import datarsians.modelo.Datos;
import datarsians.modelo.Pedido;

import java.util.List;
import java.util.NoSuchElementException;

public class ControladorPedidos {

    private final Datos datos;

    public ControladorPedidos(Datos datos) {
        this.datos = datos;
    }

    public Pedido realizarPedido(String emailCliente, String codigoArticulo, int cantidad) {
        try {
            Cliente cliente = datos.obtenerCliente(emailCliente);
            Articulo articulo = datos.obtenerArticulo(codigoArticulo);

            Pedido pedido = new Pedido(cliente, articulo, cantidad);
            datos.agregarPedido(pedido);
            return pedido;
        } catch (Exception e) {
            return null;
        }
    }


    public List<Pedido> listarPedidos() {
        return datos.listarPedidos();
    }

    public boolean verificarCancelacionPedido(int numeroPedido) {
        try {
            Pedido pedido = datos.obtenerPedido(numeroPedido);
            return pedido.esCancelable();

        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String EliminarPedido(int numeroPedido) {
        try {
            return datos.eliminarPedido(numeroPedido);
        } catch (Exception e) {
            return "Error al eliminar el pedido: " + e.getMessage();
        }
    }

    public List<Pedido> getPedidosFiltrados(String emailCliente, boolean enviados) {
        return datos.listarPedidos().stream()
                .filter(p -> (emailCliente == null || p.getCliente().getEmail().equalsIgnoreCase(emailCliente)))
                .filter(p -> p.esCancelable() != enviados)
                .toList();
    }
}
