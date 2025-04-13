package datarsians.DAO;

import datarsians.excepciones.EmailDuplicado;
import datarsians.excepciones.EmailNoValido;
import datarsians.modelo.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    void insertar(Cliente cliente) throws EmailDuplicado, EmailNoValido;
    Cliente buscarPorEmail(String email) throws SQLException;
    List<Cliente> obtenerTodos() throws SQLException;
    void actualizarClientes(List<Cliente> clientes) throws SQLException;
}