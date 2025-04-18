package datarsians.vista;
import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorPedidos;

import java.sql.SQLException;

public class MenuPrincipal {
    private final VistaCliente vistaCliente;
    private final VistaArticulos vistaArticulos;
    private final VistaPedidos vistaPedidos;

    public MenuPrincipal(ControladorArticulo controladorArticulo, ControladorCliente controladorCliente, ControladorPedidos controladorPedidos) throws SQLException {
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
