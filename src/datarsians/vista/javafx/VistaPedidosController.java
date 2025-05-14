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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
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
    private Button btnNuevoPedido;

    private ObservableList<Pedido> listaPedidos;

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
            dialog.setTitle("Nuevo Artículo");
            dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/DatarsiansIco.png"))));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(pane));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
