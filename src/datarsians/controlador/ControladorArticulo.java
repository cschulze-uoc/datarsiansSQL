package datarsians.controlador;

import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.modelo.Articulo;

import java.sql.SQLException;
import java.util.List;

public class ControladorArticulo {

    private final ArticuloDAO articuloDAO;

    public ControladorArticulo(ArticuloDAO articuloDAO) {
        this.articuloDAO = articuloDAO;
    }

    public void agregarArticulo(Articulo articulo) {

        try {
            articuloDAO.insertar(articulo);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar artículo en base de datos.");
        }

    }

    public String modificarArticulo(Articulo articulo){
        try{
            if (articulo == null) return "";
            articuloDAO.actualizarArticulo(articulo);
            return "Artículo modificado";
        } catch (SQLException e){
            return e.getMessage();
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

    public List<Articulo> getArticulos() throws SQLException {
        return articuloDAO.obtenerTodos();

    }


}
