package datarsians.vista.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuPrincipalController {

    @FXML
    public void abrirGestionArticulos(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../main/resources/fxml/vista_articulos.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/DatarsiansIco.png"))));
            stage.setTitle("Gestión de Artículos");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaError("No se pudo abrir la gestión de artículos.");
        }
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void abrirGestionPedidos(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vista_pedidos.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/DatarsiansIco.png"))));
            stage.setTitle("Gestión de Pedidos");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaError("No se pudo abrir la gestión de pedidos.");
        }
    }

    @FXML
    public void abrirGestionClientes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vista_clientes.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/DatarsiansIco.png"))));
            stage.setTitle("Gestión de Clientes");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}