package datarsians.vista;

import datarsians.controlador.ControladorArticulo;
import datarsians.modelo.Articulo;

import java.util.List;

public class VistaArticulos {
    private final ControladorArticulo controladorArticulo;

    public VistaArticulos(ControladorArticulo controladorArticulo) {
        this.controladorArticulo = controladorArticulo;
    }

    public void menuArticulos() {
        boolean salir = false;
        int opcion;

        do {
            System.out.println("\n========== GESTIÓN DE ARTÍCULOS ==========");
            System.out.println(" 1. Añadir Artículo");
            System.out.println(" 2. Listar Artículos");
            System.out.println(" 0. Volver al Menú Principal");

            opcion = ConsoleHelper.SolicitarNumeroPorConsola(0, 2, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarArticulo();
                    break;
                case 2:
                    listarArticulos();
                    ConsoleHelper.EsperarTecla();
                    break;
                case 0:
                    salir = true;
                    break;
            }
        } while (!salir);
    }

    private void listarArticulos(){
        List<Articulo> articulos = controladorArticulo.listarArticulos();
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos disponibles.");
        } else {
            articulos.forEach(System.out::println);
        }
    }
    private void agregarArticulo() {
        String codigo = ConsoleHelper.SolicitarTextoPorConsola("Ingrese el código del artículo:", null, false);
        String descripcion = ConsoleHelper.SolicitarTextoPorConsola("Ingrese la descripción:", null, false);
        double precioVenta = ConsoleHelper.SolicitarNumeroPorConsola(0f, 10000f, "Ingrese el precio de venta: ");
        double gastosEnvio = ConsoleHelper.SolicitarNumeroPorConsola(0f, 500f, "Ingrese los gastos de envío: ");
        int tiempoPreparacion = ConsoleHelper.SolicitarNumeroPorConsola(1, 1440, "Ingrese el tiempo de preparación en minutos: ");

        Articulo nuevoArticulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
        controladorArticulo.agregarArticulo(nuevoArticulo);
        System.out.println("Artículo agregado correctamente.");
    }
}
