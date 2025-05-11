package datarsians.vista.javafx;

import datarsians.controlador.ControladorCliente;
import datarsians.modelo.Cliente;
import datarsians.modelo.ClienteEstandar;
import datarsians.modelo.ClientePremium;
import datarsians.vista.AppContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FormularioClienteController {
    @FXML private TextField campoNombre;
    @FXML private TextField campoDomicilio;
    @FXML private TextField campoNif;
    @FXML private TextField campoEmail;
    @FXML private ComboBox<String> comboTipo;
    @FXML private Label lblError;

    private ControladorCliente controlador;
    private Runnable callbackAlCerrar;

    private VistaClientesController vistaClientesController;

    public void initialize() {
        this.controlador = AppContext.getControladorCliente();
        comboTipo.getItems().addAll("Estándar", "Premium");
        comboTipo.setValue("Estándar");
    }

    public void setClientesController(VistaClientesController controller) {
        this.vistaClientesController = controller;
    }

    @FXML
    private void guardarCliente() {
        lblError.setText("");

        String nombre = campoNombre.getText().trim();
        String domicilio = campoDomicilio.getText().trim();
        String nif = campoNif.getText().trim();
        String email = campoEmail.getText().trim();
        String tipo = comboTipo.getValue();

        if (nombre.isEmpty() || domicilio.isEmpty() || nif.isEmpty() || email.isEmpty()) {
            lblError.setText("Todos los campos son obligatorios.");
            return;
        }

        Cliente nuevo = tipo.equals("Estándar") ?
                new ClienteEstandar(nombre, domicilio, nif, email) :
                new ClientePremium(nombre, domicilio, nif, email);

        String resultado = controlador.agregarCliente(nuevo);
        if (resultado.startsWith("Error")) {
            lblError.setText(resultado);
        } else {
            if (vistaClientesController != null) {
                vistaClientesController.filtrarClientes(new ActionEvent());
            }
            cerrarVentana();
            if (callbackAlCerrar != null) callbackAlCerrar.run();
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) campoNombre.getScene().getWindow();
        stage.close();
    }

    public static void mostrar(Runnable onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(FormularioClienteController.class.getResource("/fxml/formulario_cliente.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(FormularioClienteController.class.getResourceAsStream("/img/DatarsiansIco.png"))));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nuevo Cliente");
            stage.setScene(scene);

            FormularioClienteController controlador = loader.getController();
            controlador.callbackAlCerrar = onClose;

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
