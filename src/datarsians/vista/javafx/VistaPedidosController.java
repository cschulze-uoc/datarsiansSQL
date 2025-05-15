package datarsians.vista.javafx;

import datarsians.controlador.ControladorArticulo;
import datarsians.controlador.ControladorCliente;
import datarsians.controlador.ControladorPedidos;
import datarsians.modelo.Articulo;
import datarsians.modelo.Pedido;
import datarsians.vista.AppContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class VistaPedidosController {

    ControladorCliente controladorCliente;
    ControladorArticulo controladorArticulo;
    ControladorPedidos controladorPedidos;

    @FXML
    private TableView<Pedido> tablaPedidos;

    @FXML
    private TableColumn<Pedido, Integer> colNumero;

    @FXML
    private TableColumn<Pedido, String> colCliente;

    @FXML
    private TableColumn<Pedido, String> colArticulo;

    @FXML
    private TableColumn<Pedido, Integer> colCantidad;

    @FXML
    private TableColumn<Pedido, LocalDateTime> colFecha;

    @FXML
    private Button btnEliminarPedido;

    @FXML
    private Button btnNuevoPedido;

    private ObservableList<Pedido> listaPedidos;

    @FXML
    private TableColumn<Pedido, String> colPrecio;

    @FXML
    private TableColumn<Pedido, String> colEnviado;

    @FXML private ToggleGroup filtroEstadoToggleGroup;
    @FXML private RadioButton radioTodos;
    @FXML private RadioButton radioEnviados;
    @FXML private RadioButton radioNoEnviados;

    @FXML
    public void initialize() {
        this.controladorCliente = AppContext.getControladorCliente();
        this.controladorArticulo = AppContext.getControladorArticulo();
        this.controladorPedidos = AppContext.getControladorPedido();

        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroPedido"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colArticulo.setCellValueFactory(new PropertyValueFactory<>("articulo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHoraPedido"));

        DecimalFormat formatoEuro = new DecimalFormat("#0.00€");
        colPrecio.setCellValueFactory(cellData ->
                {
                    double precio = cellData.getValue().calcularPrecioPedido();
                    return new javafx.beans.property.SimpleStringProperty(formatoEuro.format(precio));
                });

        colEnviado.setCellValueFactory(cellData -> {
                    boolean cancelable = cellData.getValue().esCancelable();
                    boolean enviado = !cancelable;
                    return new javafx.beans.property.SimpleStringProperty(enviado ? "Sí" : "No");
                });
        cargarPedidos();
    }

    public void cargarPedidos() {
        try {
            List<Pedido> pedidos = controladorPedidos.listarPedidos();
            listaPedidos = FXCollections.observableArrayList(pedidos);
            tablaPedidos.setItems(listaPedidos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirFormularioPedido(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/formulario_pedido.fxml"));
            AnchorPane pane = loader.load();

            FormularioPedidoController formController = loader.getController();
            formController.setOnPedidoGuardado(this::cargarPedidos);

            Stage dialog = new Stage();
            dialog.setTitle("Nuevo Pedido");
            dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/DatarsiansIco.png"))));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(pane));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void filtrarPedidos() {
        try {
            List<Pedido> todosLosPedidos = controladorPedidos.listarPedidos();

            List<Pedido> filtrados;
            if (radioEnviados.isSelected()) {
                filtrados = todosLosPedidos.stream()
                        .filter(p -> !p.esCancelable())
                        .toList();
            } else if (radioNoEnviados.isSelected()) {
                filtrados = todosLosPedidos.stream()
                        .filter(Pedido::esCancelable)
                        .toList();
            } else {
                filtrados = todosLosPedidos;
            }

            listaPedidos = FXCollections.observableArrayList(filtrados);
            tablaPedidos.setItems(listaPedidos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminarPedido() {
        Pedido pedidoSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();

        if (pedidoSeleccionado == null) {
            mostrarAlerta("Debes seleccionar un pedido para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar el pedido #" + pedidoSeleccionado.getNumeroPedido() + "?");
        Stage stage = (Stage) confirmacion.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/DatarsiansIco.png")));

        // Esperar la respuesta del usuario
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                String resultado = controladorPedidos.EliminarPedido(pedidoSeleccionado.getNumeroPedido());
                mostrarAlerta(resultado);
                cargarPedidos(); // refrescar la tabla
            }
        });
    }


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/DatarsiansIco.png")));

        alert.showAndWait();
    }
}
