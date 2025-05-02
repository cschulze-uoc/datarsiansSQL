package datarsians.controlador;

import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.excepciones.EmailDuplicado;
import datarsians.excepciones.EmailNoValido;
import datarsians.excepciones.NifDuplicado;
import datarsians.excepciones.NifNoValido;
import datarsians.modelo.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ControladorCliente {

    private final ClienteDAO clienteDAO;

    public ControladorCliente( ClienteDAO clienteDAO ) {
        this.clienteDAO = clienteDAO;
    }

    public String agregarCliente(Cliente cliente) throws IllegalArgumentException {
        try {
            clienteDAO.insertar(cliente);
            return("Cliente agregado correctamente.");
        }catch (EmailNoValido e) {
            return("Error: " + e.getMessage());
        }catch (EmailDuplicado e) {
            return("Error: " + e.getMessage());
        } catch (NifDuplicado e) {
            return("Error: " + e.getMessage());
        } catch (NifNoValido e)
        {
            return("Error: " + e.getMessage());
        }
    }

    public Cliente obtenerCliente(String email) {
        try {
            return clienteDAO.buscarPorEmail(email);
        } catch (NoSuchElementException e) {
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> listarClientes() throws SQLException {
        return List.copyOf( clienteDAO.obtenerTodos());
    }

    public List<Cliente> getClientes(Class<?> tipoCliente) throws SQLException {
        return clienteDAO.obtenerTodos().stream()
                .filter(cliente -> tipoCliente == null || tipoCliente.isInstance(cliente))
                .toList();

    }
    public List<String> obtenerListaDeDnis() throws SQLException {
        List<String> listaDnis = new ArrayList<>();
        for (Cliente cliente : clienteDAO.obtenerTodos()) {
            listaDnis.add(cliente.getNif());
        }
        return listaDnis;

    }


    public List<String> obtenerListaDeEmails() throws SQLException {
        List<String> listaEmails = new ArrayList<>();
        for (Cliente cliente : clienteDAO.obtenerTodos()) {
            listaEmails.add(cliente.getEmail());
        }
        return listaEmails;
    }
}
