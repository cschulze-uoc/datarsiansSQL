package datarsians.DAO;

import datarsians.modelo.Articulo;

import java.sql.SQLException;
import java.util.List;

public interface    ArticuloDAO {
    void insertar(Articulo articulo) throws SQLException;
    Articulo buscarPorCodigo(String codigo) throws SQLException;
    List<Articulo> obtenerTodos() throws SQLException;
}
