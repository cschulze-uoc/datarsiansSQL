package datarsians.modelo;

import datarsians.DAO.ClienteDAO;
import datarsians.factory.DAOFactory;
import datarsians.DAO.PedidoDAO;
import datarsians.excepciones.EmailDuplicado;
import datarsians.excepciones.EmailNoValido;

import datarsians.DAO.ArticuloDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Datos {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean esEmailValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private final ClienteDAO clienteDAO;
    private final ArticuloDAO articuloDAO;
    private final PedidoDAO pedidoDAO;

    public Datos() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.clienteDAO = factory.getClienteDAO();
        this.articuloDAO = factory.getArticuloDAO();
        this.pedidoDAO = factory.getPedidoDAO();
    }

    // Zona clientes
    public void agregarCliente(Cliente cliente) throws EmailDuplicado, EmailNoValido {
        String email = cliente.getEmail();

        if (!esEmailValido(email)) {
            throw new EmailNoValido("El email no es válido.");
        }

        try {
            if (clienteDAO.buscarPorEmail(email) != null) {
                throw new EmailDuplicado("Error: El email ya está registrado.");
            }

            clienteDAO.insertar(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar cliente en base de datos.");
        }
    }

    public Cliente obtenerCliente(String email) {
        try {
            return clienteDAO.buscarPorEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existeCliente(String email) {
        return obtenerCliente(email) != null;
    }

    public List<Cliente> listarClientes() {
        try {
            return clienteDAO.obtenerTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Zona artículos
    public void agregarArticulo(Articulo articulo) {
        try {
            articuloDAO.insertar(articulo);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar artículo en base de datos.");
        }
    }

    public Articulo obtenerArticulo(String codigo) {
        try {
            return articuloDAO.buscarPorCodigo(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Articulo> listarArticulos() {
        try {
            return articuloDAO.obtenerTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Zona pedidos
    public void agregarPedido(Pedido pedido) {
        try {
            pedidoDAO.insertar(pedido);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar pedido.");
        }
    }

    public List<Pedido> listarPedidos() {
        try {
            return pedidoDAO.obtenerTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Pedido obtenerPedido(int numeroPedido) {
        try {
            return pedidoDAO.buscarPorNumero(numeroPedido);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String eliminarPedido(int numeroPedido) {
        Pedido pedido = obtenerPedido(numeroPedido);

        if (pedido == null) {
            return "Pedido no encontrado.";
        }

        if (!pedido.esCancelable()) {
            return "No se puede eliminar el pedido. Ya ha sido enviado.";
        }

        try {
            pedidoDAO.eliminar(numeroPedido);
            return "Pedido eliminado correctamente.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar el pedido.";
        }
    }

    public List<String> obtenerListaDeDnis() {
        List<String> listaDnis = new ArrayList<>();
        for (Cliente cliente : listarClientes()) {
            listaDnis.add(cliente.getNif());
        }
        return listaDnis;
    }

    public List<String> obtenerListaDeEmails() {
        List<String> listaEmails = new ArrayList<>();
        for (Cliente cliente : listarClientes()) {
            listaEmails.add(cliente.getEmail());
        }
        return listaEmails;
    }

    // Datos de muestra para probar
    public void cargarDatosDePrueba() {
        System.out.println("Cargando datos de prueba...");

        Cliente cliente1 = new ClienteEstandar("Jordi Pujol", "Carrer de Balmes, 123, Barcelona", "12345678A", "jordi.pujol@uoc.edu");
        Cliente cliente2 = new ClienteEstandar("Núria Soler", "Avinguda Diagonal, 450, Barcelona", "87654321B", "nuria.soler@uoc.edu");
        Cliente cliente3 = new ClientePremium("Marc Rovira", "Passeig de Gràcia, 99, Barcelona", "11223344C", "marc.rovira@uoc.edu");
        Cliente cliente4 = new ClientePremium("Laia Vives", "Rambla de Catalunya, 55, Barcelona", "22334455D", "laia.vives@uoc.edu");

        try {
            agregarCliente(cliente1);
            agregarCliente(cliente2);
            agregarCliente(cliente3);
            agregarCliente(cliente4);
        } catch (Exception e) {
            System.out.println("Error al agregar clientes: " + e.getMessage());
        }

        Articulo articulo1 = new Articulo("A001", "Auriculars Bluetooth", 49.99, 5.99, 120);
        Articulo articulo2 = new Articulo("A002", "Teclat mecànic RGB", 89.99, 7.99, 180);
        Articulo articulo3 = new Articulo("A003", "Monitor 27'' 4K", 349.99, 14.99, 240);
        Articulo articulo4 = new Articulo("A004", "Cadira ergonòmica d'oficina", 199.99, 19.99, 300);

        agregarArticulo(articulo1);
        agregarArticulo(articulo2);
        agregarArticulo(articulo3);
        agregarArticulo(articulo4);

        agregarPedido(new Pedido(cliente1, articulo1, 2));
        agregarPedido(new Pedido(cliente3, articulo3, 1));
        agregarPedido(new Pedido(cliente2, articulo2, 1));
        agregarPedido(new Pedido(cliente4, articulo4, 1));

        System.out.println("Datos de prueba cargados correctamente.");
    }
}
