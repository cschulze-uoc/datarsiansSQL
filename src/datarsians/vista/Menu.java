package datarsians.vista;


import datarsians.controlador.Controlador;
import datarsians.modelo.*;

import java.util.List;


public class Menu {
    private final Controlador controlador;

    public Menu(Controlador controlador) {
        this.controlador = controlador;
    }



    public void mostrarMenuPrincipal() {
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
                    menuArticulos();
                    break;
                case 2:
                    menuClientes();
                    break;
                case 3:
                    menuPedidos();
                    break;
                case 0:
                    salir = true;
                    break;
            }
        } while (!salir);
    }

    private void menuClientes() {
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

    private Cliente agregarCliente() {
        List<String> listaDnis = controlador.obtenerListaDeDnis();
        List<String> listaEmails = controlador.obtenerListaDeEmails();

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

        System.out.println( controlador.agregarCliente(nuevoCliente) );
        return nuevoCliente;
    }

    private void menuArticulos() {
        boolean salir = false;
        int opcion;

        do {
            System.out.println("\n========== GESTIÓN DE ARTÍCULOS ==========");
            System.out.println(" 1. Añadir Artículo");
            System.out.println(" 2. Listar Artículos");
            System.out.println(" 0. Volver al Menú Principal");

            opcion = ConsoleHelper.SolicitarNumeroPorConsola(0, 2, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarArticulo();
                    break;
                case 2:
                    controlador.listarArticulos();
                    ConsoleHelper.EsperarTecla();
                    break;
                case 0:
                    salir = true;
                    break;
            }
        } while (!salir);
    }

    private void agregarArticulo() {
        String codigo = ConsoleHelper.SolicitarTextoPorConsola("Ingrese el código del artículo:", null, false);
        String descripcion = ConsoleHelper.SolicitarTextoPorConsola("Ingrese la descripción:", null, false);
        double precioVenta = ConsoleHelper.SolicitarNumeroPorConsola(0f, 10000f, "Ingrese el precio de venta: ");
        double gastosEnvio = ConsoleHelper.SolicitarNumeroPorConsola(0f, 500f, "Ingrese los gastos de envío: ");
        int tiempoPreparacion = ConsoleHelper.SolicitarNumeroPorConsola(1, 1440, "Ingrese el tiempo de preparación en minutos: ");

        Articulo nuevoArticulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
        controlador.agregarArticulo(nuevoArticulo);
    }

    private void menuPedidos() {
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


    private void realizarPedido() {
        List<String> listaEmails = controlador.obtenerListaDeEmails();
        // Verificar Cliente
        Cliente cliente = null;
        while (cliente == null) {
            String emailCliente = ConsoleHelper.SolicitarTextoPorConsola("Ingrese el email del cliente:", null,false);
            cliente = controlador.obtenerCliente(emailCliente);

            if (cliente == null) {
                System.out.println("Cliente no encontrado.");
                System.out.println("1. Intentar de nuevo");
                System.out.println("2. Registrar nuevo cliente ");
                int opcion = ConsoleHelper.SolicitarNumeroPorConsola(1, 2, "Seleccione una opción:");

                if (opcion == 2) {
                    cliente = agregarCliente();
                }
            }
        }

        // Seleccionar artículo
        Articulo articulo = ConsoleHelper.seleccionarOpcion(controlador.getArticulos(), Articulo::getDescripcion);
        if (articulo == null) {
            System.out.println("Error: No se seleccionó un artículo válido.");
            return;
        }

        int cantidad = ConsoleHelper.SolicitarNumeroPorConsola(1, 100, "Ingrese la cantidad de unidades: ");
        controlador.realizarPedido(cliente.getEmail(), articulo.getCodigo(), cantidad);
    }

    private void eliminarPedido() {
        int numeroPedido = ConsoleHelper.SolicitarNumeroPorConsola(1, 1000, "Ingrese el número de pedido a eliminar: ");
        String resultado = controlador.EliminarPedido(numeroPedido);
        System.out.println(resultado);
    }

    private void mostrarPedidos(boolean enviados) {
        System.out.println("\n========== " + (enviados ? "PEDIDOS ENVIADOS" : "PEDIDOS PENDIENTES") + " ==========");

        int opcion = ConsoleHelper.SolicitarNumeroPorConsola(1, 2, "¿Desea filtrar por cliente?\n1. Sí\n2. No\n");
        String emailCliente = null;

        if (opcion == 1) {
            emailCliente = ConsoleHelper.SolicitarTextoPorConsola("Ingrese el email del cliente:", null, false);
        }

        List<Pedido> pedidos = controlador.getPedidosFiltrados(emailCliente, enviados);

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

    // ****************************** CLIENTES *******************************

    private void listarClientes(Class<?> tipoCliente) {
        List<Cliente> clientes = controlador.getClientes(tipoCliente);

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
