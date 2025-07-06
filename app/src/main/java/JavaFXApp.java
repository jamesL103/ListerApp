import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafxGui.ApplicationRootNode;

public class JavaFXApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Parent root = new ApplicationRootNode();

        Scene scene = new Scene(root, 800, 450);

        stage.setTitle("Lister");
        stage.setScene(scene);
        stage.show();
    }
}
