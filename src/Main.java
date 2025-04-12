
import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorPedidos;
import datarsians.modelo.Datos;
import datarsians.vista.Menu;

public static void main(String[] args) {
    Datos datos = new Datos();
    // System.out.println( datos.cargarDatosDePrueba() );

    Menu menu = new Menu(
            new ControladorArticulo(datos),
            new ControladorCliente(datos),
            new ControladorPedidos(datos)
    );
    menu.mostrarMenuPrincipal();
}