package datarsians.controlador;

import datarsians.excepciones.EmailDuplicado;
import datarsians.excepciones.EmailNoValido;
import datarsians.modelo.Articulo;
import datarsians.modelo.Cliente;
import datarsians.modelo.Datos;
import datarsians.modelo.Pedido;

import java.util.List;
import java.util.NoSuchElementException;

public class Controlador {
    private Datos datos;

    public Controlador(Datos datos) {
        this.datos = datos;
    }

// Zona clientes
public String agregarCliente(Cliente cliente) throws IllegalArgumentException {
    try {
        datos.agregarCliente(cliente);
        return("Cliente agregado correctamente.");
    }catch (EmailNoValido e) {
        return("Error: " + e.getMessage());
    }catch (EmailDuplicado e) {
        return("Error: " + e.getMessage());
    }
}

public Cliente obtenerCliente(String email) {
    try {
        return datos.obtenerCliente(email);
    } catch (NoSuchElementException e) {
        System.out.println("Error: " + e.getMessage());
        return null;
    }
}

public void listarClientes() {
    List<Cliente> clientes = List.copyOf(datos.listarClientes());
    if (clientes.isEmpty()) {
        System.out.println("No hay clientes registrados.");
    } else {
        clientes.forEach(System.out::println);
    }
}

public List<Cliente> getClientes(Class<?> tipoCliente) {
    return datos.listarClientes().stream()
            .filter(cliente -> tipoCliente == null || tipoCliente.isInstance(cliente))
            .toList();

}

// Zona artículos
public void agregarArticulo(Articulo articulo) {
    datos.agregarArticulo(articulo);
    System.out.println("Artículo agregado correctamente.");
}

public Articulo obtenerArticulo(String codigo) {
    try {
        return datos.obtenerArticulo(codigo);
    } catch (NoSuchElementException e) {
        System.out.println("Error: " + e.getMessage());
        return null;
    }
}

public void listarArticulos() {
    List<Articulo> articulos = datos.listarArticulos();
    if (articulos.isEmpty()) {
        System.out.println("No hay artículos disponibles.");
    } else {
        articulos.forEach(System.out::println);
    }
}
public List<Articulo> getArticulos() {
    return  datos.listarArticulos();

}

// Zona pedidos
public void realizarPedido(String emailCliente, String codigoArticulo, int cantidad) {
    try {
        Cliente cliente = datos.obtenerCliente(emailCliente);
        Articulo articulo = datos.obtenerArticulo(codigoArticulo);

        if (cliente == null || articulo == null) {
            System.out.println("Error: Cliente o artículo no encontrado.");
            return;
        }

        Pedido pedido = new Pedido(cliente, articulo, cantidad);
        datos.agregarPedido(pedido);

        System.out.println("\nPedido realizado correctamente:");
        System.out.println(pedido);
    } catch (Exception e) {
        System.out.println("Error al realizar el pedido: " + e.getMessage());
    }
}


public void listarPedidos() {
    List<Pedido> pedidos = datos.listarPedidos();
    if (pedidos.isEmpty()) {
        System.out.println("No hay pedidos registrados.");
    } else {
        pedidos.forEach(System.out::println);
    }
}

public void verificarCancelacionPedido(int numeroPedido) {
    try {
        Pedido pedido = datos.obtenerPedido(numeroPedido);
        if (pedido.esCancelable()) {
            System.out.println("El pedido " + numeroPedido + " puede ser cancelado.");
        } else {
            System.out.println("El pedido " + numeroPedido + " no puede ser cancelado.");
        }
    } catch (NoSuchElementException e) {
        System.out.println("Error: " + e.getMessage());
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

public List<String> obtenerListaDeDnis() {
    return datos.obtenerListaDeDnis();
}

public List<String> obtenerListaDeEmails() {
    return datos.obtenerListaDeEmails();
}
}
