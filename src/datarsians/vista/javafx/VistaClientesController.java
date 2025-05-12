package datarsians.vista.javafx;


import datarsians.controlador.ControladorCliente;
import datarsians.modelo.Cliente;
import datarsians.modelo.ClienteEstandar;
import datarsians.modelo.ClientePremium;
import datarsians.vista.AppContext;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class VistaClientesController {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colNif;
    @FXML private TableColumn<Cliente, String> colTipo;
    @FXML private ComboBox<String> filtroTipoCliente;

    ControladorCliente controladorCliente;

    public void initialize() throws SQLException {

        this.controladorCliente = AppContext.getControladorCliente();

        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));
        colEmail.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail()));
        colNif.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNif()));
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                (c.getValue() instanceof ClientePremium) ? "Premium" : "Estándar"
        ));

        filtroTipoCliente.setItems(FXCollections.observableArrayList("Todos", "Estándar", "Premium"));
        filtroTipoCliente.setValue("Todos");

        cargarClientes(null);
    }

    private void cargarClientes(Class<?> tipoCliente) throws SQLException {
        List<Cliente> lista = controladorCliente.getClientes(tipoCliente);
        tablaClientes.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void abrirFormularioCliente() {
        FormularioClienteController.mostrar(() -> {
            try {
                cargarClientes(null);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) tablaClientes.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void filtrarClientes(ActionEvent event) {
        String tipo = filtroTipoCliente.getValue();
        Class<?> tipoCliente = switch (tipo) {
            case "Estándar" -> ClienteEstandar.class;
            case "Premium"  -> ClientePremium.class;
            default         -> null;
        };

        try {
            List<Cliente> lista = controladorCliente.getClientes(tipoCliente);
            tablaClientes.getItems().setAll(lista);
        } catch (SQLException e) {
            e.printStackTrace(); // O muestra en una alerta
        }
    }

}