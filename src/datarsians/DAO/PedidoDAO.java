package datarsians.DAO;

import datarsians.modelo.Pedido;

import java.sql.SQLException;
import java.util.List;

public interface PedidoDAO {
    void insertar(Pedido pedido) throws SQLException;
    Pedido buscarPorNumero(int numero) throws SQLException;
    List<Pedido> obtenerTodos() throws SQLException;
    void eliminar(int numero) throws SQLException;
}
