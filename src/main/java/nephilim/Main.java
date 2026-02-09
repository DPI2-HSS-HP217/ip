package nephilim;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Nephilim nephilim = new Nephilim();

    public Main() throws NephilimException, IOException {

    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setNephilim(nephilim);// inject the Nephilim instance
            fxmlLoader.<MainWindow>getController().greet(); //Send greeting message
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
