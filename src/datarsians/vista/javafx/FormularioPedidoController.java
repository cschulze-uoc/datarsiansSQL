package datarsians.vista.javafx;

import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorPedidos;
import datarsians.modelo.*;
import datarsians.vista.AppContext;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class FormularioPedidoController {

    @FXML private TextField campoCantidad;
    @FXML private ComboBox<Articulo> comboArticulos;
    @FXML private ComboBox<Cliente> comboClientes;
    @FXML private Label lblError;

    private ControladorPedidos controladorPedidos;
    private ControladorArticulo controladorArticulo;
    private ControladorCliente controladorCliente;
    private Runnable callbackAlCerrar;


    private VistaPedidosController vistaPedidosController;

    public void initialize() throws SQLException {
        this.controladorPedidos = AppContext.getControladorPedido();
        this.controladorArticulo = AppContext.getControladorArticulo();
        this.controladorCliente = AppContext.getControladorCliente();

        comboArticulos.setItems(FXCollections.observableArrayList(controladorArticulo.getArticulos()));
        comboClientes.setItems(FXCollections.observableArrayList(controladorCliente.getClientes(null)));

    }

    @FXML
    private void guardarPedido() {
        lblError.setText("");


        String email = comboClientes.getValue().getEmail();
        String idArticulo = comboArticulos.getValue().getCodigo();
        String strCantidad = campoCantidad.getText().trim();

        if (email.isEmpty() || idArticulo.isEmpty() || strCantidad.isEmpty()) {
            lblError.setText("Todos los campos son obligatorios.");
            return;
        }
        int cantidad = Integer.parseInt(strCantidad);

        Pedido resultado = controladorPedidos.realizarPedido(email,idArticulo,cantidad);

        if (resultado == null) {
            lblError.setText("No se ha podido crear el pedido");
        } else {
            if (vistaPedidosController != null) {
                vistaPedidosController.cargarPedidos();
            }
            cerrarVentana();
            if (callbackAlCerrar != null) callbackAlCerrar.run();
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) campoCantidad.getScene().getWindow();
        stage.close();
    }
    public void setOnPedidoGuardado(Runnable onPedidoGuardado) {
        this.callbackAlCerrar = onPedidoGuardado;
    }
    public static void mostrar(Runnable onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(FormularioClienteController.class.getResource("/fxml/formulario_pedido.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(FormularioPedidoController.class.getResourceAsStream("/img/DatarsiansIco.png"))));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nuevo Cliente");
            stage.setScene(scene);

            FormularioPedidoController controlador = loader.getController();
            controlador.callbackAlCerrar = onClose;

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
