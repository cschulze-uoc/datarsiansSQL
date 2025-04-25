package datarsians.vista;

import datarsians.controlador.ControladorCliente;
import datarsians.modelo.Cliente;
import datarsians.modelo.ClienteEstandar;
import datarsians.modelo.ClientePremium;

import java.sql.SQLException;
import java.util.List;

public class VistaCliente {
    private final ControladorCliente controladorCliente;
    public VistaCliente (ControladorCliente controladorCliente){
        this.controladorCliente = controladorCliente;

    }
    public void menuClientes() throws SQLException {
        boolean salir = false;
        int opcion;

        do {
            System.out.println("\n========== GESTIÓN DE CLIENTES ==========");
            System.out.println(" 1. Añadir Cliente");
            System.out.println(" 2. Listar Todos los Clientes");
            System.out.println(" 3. Mostrar Clientes Estándar");
            System.out.println(" 4. Mostrar Clientes Premium");
            System.out.println(" 0. Volver al Menú Principal");

            opcion = ConsoleHelper.SolicitarNumeroPorConsola(0, 4, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    listarClientes(null); // Muestra todos los clientes
                    break;
                case 3:
                    listarClientes(ClienteEstandar.class); // Solo Estándar
                    break;
                case 4:
                    listarClientes(ClientePremium.class); // Solo Premium
                    break;
                case 0:
                    salir = true;
                    break;
            }
        } while (!salir);
    }

    public Cliente agregarCliente() throws SQLException {
        List<String> listaDnis = controladorCliente.obtenerListaDeDnis();
        List<String> listaEmails = controladorCliente.obtenerListaDeEmails();

        String nombre = ConsoleHelper.SolicitarTextoPorConsola("Ingrese el nombre del cliente:", null, false);
        String domicilio = ConsoleHelper.SolicitarTextoPorConsola("Ingrese el domicilio:", null, false);
        String nif = ConsoleHelper.SolicitarDniPorConsola("Ingrese el NIF:", listaDnis);
        if(nif == null) {
            System.out.println("Registro cancelado.");
            return null;
        }
        String email = ConsoleHelper.SolicitarEmailPorConsola("Ingrese el email:", listaEmails);

        int tipoCliente = ConsoleHelper.SolicitarNumeroPorConsola(1, 2, "Seleccione el tipo de cliente (1. Estándar, 2. Premium): ");
        Cliente nuevoCliente = (tipoCliente == 1)
                ? new ClienteEstandar(nombre, domicilio, nif, email)
                : new ClientePremium(nombre, domicilio, nif, email);

        System.out.println( controladorCliente.agregarCliente(nuevoCliente) );
        return nuevoCliente;
    }

    private void listarClientes(Class<?> tipoCliente) throws SQLException {
        List<Cliente> clientes = controladorCliente.getClientes(tipoCliente);

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("\n========== LISTADO DE CLIENTES ==========");
        for (Cliente cliente : clientes) {
            if (tipoCliente == null || tipoCliente.isInstance(cliente)) {
                System.out.println(cliente);
                System.out.println("----------------------------------");
            }
        }
        ConsoleHelper.EsperarTecla();
    }
}
