package datarsians.vista.javafx;

import datarsians.controlador.ControladorArticulo;
import datarsians.modelo.Articulo;
import datarsians.vista.AppContext;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class FormularioArticuloController {

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtGastosEnvio;
    @FXML
    private Spinner<Integer> spinnerTiempo;
    @FXML
    private Button btnGuardar;

    private ControladorArticulo controladorArticulo;
    private Runnable onArticuloGuardado;

    @FXML
    public void initialize() {
        spinnerTiempo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1440, 30));
        this.controladorArticulo = AppContext.getControladorArticulo();
    }

    public void setOnArticuloGuardado(Runnable onArticuloGuardado) {
        this.onArticuloGuardado = onArticuloGuardado;
    }

    @FXML
    private void guardarArticulo() {
        String codigo = txtCodigo.getText();
        String descripcion = txtDescripcion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        double gastos = Double.parseDouble(txtGastosEnvio.getText());
        int tiempo = spinnerTiempo.getValue();

        Articulo articulo = new Articulo(codigo, descripcion, precio, gastos, tiempo);

        if (controladorArticulo.obtenerArticulo(codigo) != null) {
            controladorArticulo.modificarArticulo(articulo);
        } else {
            controladorArticulo.agregarArticulo(articulo);
        }

        if (onArticuloGuardado != null) {
            onArticuloGuardado.run();
        }

        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) txtCodigo.getScene().getWindow();
        stage.close();
    }


    public static void mostrar(Runnable onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(FormularioArticuloController.class.getResource("/fxml/formulario_articulo.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            stage.getIcons().add(new Image(Objects.requireNonNull(FormularioArticuloController.class.getResourceAsStream("/img/DatarsiansIco.png"))));
            stage.setTitle("Nuevo Artículo");

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);

            FormularioArticuloController controlador = loader.getController();
            controlador.onArticuloGuardado = onClose;

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
