package datarsians.vista.javafx;

import datarsians.DAO.factory.DAOFactory;
import datarsians.DAO.factory.TipoDAO;
import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.DAO.interfaz.PedidoDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.Objects;
import java.util.logging.LogManager;

public class MainApp extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");
        System.setProperty("org.slf4j.simpleLogger.log.org.hibernate", "off");
        System.setProperty("org.slf4j.simpleLogger.log.org.hibernate.SQL", "off");
        System.setProperty("org.slf4j.simpleLogger.log.org.hibernate.type.descriptor.sql", "off");
        System.setProperty("org.slf4j.simpleLogger.log.org.hibernate.orm.deprecation", "off");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/DatarsiansIco.png"))));
        primaryStage.setTitle("Gestión de Tienda - Datarsians");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
