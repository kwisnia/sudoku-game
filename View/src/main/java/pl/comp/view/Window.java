package pl.comp.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Window extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(getFxmlLoader("primary").load(), 860, 620);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(getFxmlLoader(fxml).load());
    }

    static void setRoot(FXMLLoader loader) throws IOException {
        scene.setRoot(loader.load());
    }

    public static FXMLLoader getFxmlLoader(String fxml) {
        return new FXMLLoader(Window.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }

}