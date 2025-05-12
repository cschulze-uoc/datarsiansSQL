package datarsians.vista.javafx;

import datarsians.controlador.ControladorArticulo;
import datarsians.modelo.Articulo;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VistaArticulosController {

    @FXML
    private TableView<Articulo> tablaArticulos;

    @FXML
    private TableColumn<Articulo, String> colCodigo;

    @FXML
    private TableColumn<Articulo, String> colDescripcion;

    @FXML
    private TableColumn<Articulo, Double> colPrecioVenta;

    @FXML
    private TableColumn<Articulo, Double> colGastosEnvio;

    @FXML
    private TableColumn<Articulo, Integer> colTiempoPreparacion;

    @FXML
    private Button btnNuevoArticulo;

    private ControladorArticulo controladorArticulo;

    private ObservableList<Articulo> listaArticulos;



    @FXML
    public void initialize() {
        this.controladorArticulo = AppContext.getControladorArticulo();
        cargarArticulos();
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        colGastosEnvio.setCellValueFactory(new PropertyValueFactory<>("gastosEnvio"));
        colTiempoPreparacion.setCellValueFactory(new PropertyValueFactory<>("tiempoPreparacion"));
    }

    private void cargarArticulos() {
        try {
            List<Articulo> articulos = controladorArticulo.getArticulos();
            listaArticulos = FXCollections.observableArrayList(articulos);
            tablaArticulos.setItems(listaArticulos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirFormularioArticulo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/formulario_articulo.fxml"));
            AnchorPane pane = loader.load();

            FormularioArticuloController formController = loader.getController();
            formController.setOnArticuloGuardado(this::cargarArticulos);

            Stage dialog = new Stage();
            dialog.setTitle("Nuevo Artículo");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(pane));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}