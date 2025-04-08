import datarsians.controlador.Controlador;
import datarsians.modelo.Datos;
import datarsians.vista.Menu;

public class Main {
    public static void main(String[] args) {
        Datos datos = new Datos();
        // datos.cargarDatosDePrueba();

        Controlador controlador = new Controlador(datos);
        Menu menu = new Menu(controlador);
        menu.mostrarMenuPrincipal();
    }
}