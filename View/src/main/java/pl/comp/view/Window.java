package pl.comp.view;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Window extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(getFxmlLoader("primary").load(), 860, 620);
        stage.setScene(scene);
        stage.setTitle("Sudoku");
        stage.setResizable(false);
        stage.getIcons().add(new Image("pl/comp/view/icon.png"));
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(getFxmlLoader(fxml).load());
    }

    static void setRoot(Parent p) {
        scene.setRoot(p);
    }

    public static FXMLLoader getFxmlLoader(String fxml) {
        return new FXMLLoader(Window.class.getResource(fxml + ".fxml"),
                ResourceBundle.getBundle("pl/comp/view/Sudoku"));
    }

    public static void main(String[] args) {
        launch();
    }

}