package main;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Almir
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        URL fxmlUrl;
        fxmlUrl = getClass().getClassLoader().getResource("view/wstr.fxml");
        BorderPane root = FXMLLoader.<BorderPane>load(fxmlUrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("view/style.css");
        primaryStage.setTitle("Web stranice");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
