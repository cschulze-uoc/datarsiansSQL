package datarsians.DAO;

import datarsians.modelo.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    void insertar(Cliente cliente) throws SQLException;
    Cliente buscarPorEmail(String email) throws SQLException;
    List<Cliente> obtenerTodos() throws SQLException;
}