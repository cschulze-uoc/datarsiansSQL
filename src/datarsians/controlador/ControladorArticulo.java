package datarsians.controlador;

import datarsians.modelo.Articulo;
import datarsians.modelo.Datos;

import java.util.List;
import java.util.NoSuchElementException;

public class ControladorArticulo {

    private final Datos datos;

    public ControladorArticulo(Datos datos) {
        this.datos = datos;
    }

    public void agregarArticulo(Articulo articulo) {
        datos.agregarArticulo(articulo);
    }

    public Articulo obtenerArticulo(String codigo) {
        try {
            return datos.obtenerArticulo(codigo);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public List<Articulo> listarArticulos() {
        return datos.listarArticulos();

    }
    public List<Articulo> getArticulos() {
        return  datos.listarArticulos();

    }
}
