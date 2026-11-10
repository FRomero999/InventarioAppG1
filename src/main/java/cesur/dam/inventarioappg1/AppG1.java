package cesur.dam.inventarioappg1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppG1 extends Application {
    static Scene scene;
    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage=stage;
        setescena("views/login-view.fxml");
        stage.show();
    }
    public static void setescena(String fil) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppG1.class.getResource(fil));
        Scene es =new Scene(fxmlLoader.load());
        stage.setScene(es);
    }
}
