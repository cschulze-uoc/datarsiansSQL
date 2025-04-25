package datarsians.vista;

import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorPedidos;
import datarsians.modelo.Articulo;
import datarsians.modelo.Cliente;
import datarsians.modelo.Pedido;

import java.sql.SQLException;
import java.util.List;

public class VistaPedidos {
    private final ControladorArticulo controladorArticulo;
    private final ControladorCliente controladorCliente;
    private final ControladorPedidos controladorPedidos;
    private final VistaCliente vistaCliente;

    public VistaPedidos(ControladorArticulo controladorArticulo, ControladorCliente controladorCliente, ControladorPedidos controladorPedidos, VistaCliente vistaCliente) {
        this.controladorArticulo = controladorArticulo;
        this.controladorCliente = controladorCliente;
        this.controladorPedidos = controladorPedidos;
        this.vistaCliente = vistaCliente;
    }

    public void menuPedidos() throws SQLException {
        boolean salir = false;
        int opcion;

        do {
            System.out.println("\n========== GESTIÓN DE PEDIDOS ==========");
            System.out.println(" 1. Añadir Pedido");
            System.out.println(" 2. Eliminar Pedido");
            System.out.println(" 3. Mostrar Pedidos Pendientes de Envío");
            System.out.println(" 4. Mostrar Pedidos Enviados");
            System.out.println(" 0. Volver al Menú Principal");

            opcion = ConsoleHelper.SolicitarNumeroPorConsola(0, 4, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    realizarPedido();
                    break;
                case 2:
                    eliminarPedido();
                    break;
                case 3:
                    mostrarPedidos(false);
                    break;
                case 4:
                    mostrarPedidos(true);
                    break;
                case 0:
                    salir = true;
                    break;
            }
        } while (!salir);
    }


    private void realizarPedido() throws SQLException {

        // Verificar Cliente
        Cliente cliente = null;
        while (cliente == null) {
            String emailCliente = ConsoleHelper.SolicitarTextoPorConsola("Ingrese el email del cliente:", null,false);
            cliente = controladorCliente.obtenerCliente(emailCliente);

            if (cliente == null) {
                System.out.println("Cliente no encontrado.");
                System.out.println("1. Intentar de nuevo");
                System.out.println("2. Registrar nuevo cliente ");
                int opcion = ConsoleHelper.SolicitarNumeroPorConsola(1, 2, "Seleccione una opción:");

                if (opcion == 2) {
                    cliente = vistaCliente.agregarCliente();
                }
            }
        }

        // Seleccionar artículo
        Articulo articulo = ConsoleHelper.seleccionarOpcion(controladorArticulo.getArticulos(), Articulo::getDescripcion);
        if (articulo == null) {
            System.out.println("Error: No se seleccionó un artículo válido.");
            return;
        }

        int cantidad = ConsoleHelper.SolicitarNumeroPorConsola(1, 100, "Ingrese la cantidad de unidades: ");

        var pedidoRealizado = controladorPedidos.realizarPedido(cliente.getEmail(), articulo.getCodigo(), cantidad);
        if (pedidoRealizado == null) {
            System.out.println("\nNo se ha podido realizar el pedido");
        } else {
            System.out.println("\nPedido realizado correctamente:");
            System.out.println(pedidoRealizado);
        }

    }

    private void eliminarPedido() {
        int numeroPedido = ConsoleHelper.SolicitarNumeroPorConsola(1, 1000, "Ingrese el número de pedido a eliminar: ");
        String resultado = controladorPedidos.EliminarPedido(numeroPedido);
        System.out.println(resultado);
    }

    private void mostrarPedidos(boolean enviados) throws SQLException {
        System.out.println("\n========== " + (enviados ? "PEDIDOS ENVIADOS" : "PEDIDOS PENDIENTES") + " ==========");

        int opcion = ConsoleHelper.SolicitarNumeroPorConsola(1, 2, "¿Desea filtrar por cliente?\n1. Sí\n2. No\n");
        String emailCliente = null;

        if (opcion == 1) {
            emailCliente = ConsoleHelper.SolicitarTextoPorConsola("Ingrese el email del cliente:", null, false);
        }

        List<Pedido> pedidos = controladorPedidos.getPedidosFiltrados(emailCliente, enviados);

        if (pedidos.isEmpty()) {
            System.out.println("No se han encontrado pedidos");
            return;
        }

        for (Pedido pedido : pedidos) {
            System.out.println(pedido.toString());
            System.out.println("----------------------------------");
        }
        ConsoleHelper.EsperarTecla();
    }
}
