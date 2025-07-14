import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafxGui.ApplicationRootNode;

import java.net.MalformedURLException;

public class JavaFXApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws MalformedURLException {
        Parent root = new ApplicationRootNode();

        Scene scene = new Scene(root, 800, 450);
        System.out.println(System.getProperty("user.dir"));
        String dir = System.getProperty("user.dir");
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("Lister");
        stage.setScene(scene);
        stage.show();
    }
}
