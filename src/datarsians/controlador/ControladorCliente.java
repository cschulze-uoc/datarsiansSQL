package datarsians.controlador;

import datarsians.excepciones.EmailDuplicado;
import datarsians.excepciones.EmailNoValido;
import datarsians.modelo.Cliente;
import datarsians.modelo.Datos;

import java.util.List;
import java.util.NoSuchElementException;

public class ControladorCliente {
    private final Datos datos;

    public ControladorCliente(Datos datos) {
        this.datos = datos;
    }

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
            return null;
        }
    }

    public List<Cliente> listarClientes() {
        return List.copyOf(datos.listarClientes());
    }

    public List<Cliente> getClientes(Class<?> tipoCliente) {
        return datos.listarClientes().stream()
                .filter(cliente -> tipoCliente == null || tipoCliente.isInstance(cliente))
                .toList();

    }
    public List<String> obtenerListaDeDnis() {
        return datos.obtenerListaDeDnis();
    }

    public List<String> obtenerListaDeEmails() {
        return datos.obtenerListaDeEmails();
    }
}
