import datarsians.modelo.Articulo;
import datarsians.modelo.Cliente;
import datarsians.modelo.ClientePremium;
import datarsians.modelo.Pedido;

public class PedidoTest {
    public static void main(String[] args) {

        Cliente cliente = new ClientePremium("Ana López", "Avenida Real 456", "87654321B", "ana.lopez@email.com");
        Articulo articulo = new Articulo("A002", "Laptop", 1000.0, 30.0, 45);
        Pedido pedido = new Pedido(cliente, articulo, 1);

        double precioEsperado = (1000.0 + 30.0) * 0.8;
        double precioCalculado = pedido.calcularPrecioPedido();

        if (Math.abs(precioEsperado - precioCalculado) < 0.01) {
            System.out.println("Test correcto.");
        } else {
            System.out.println("Test fallido.");
        }
    }
}
