import com.numbedme.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by User on 14.11.2016.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("chat.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            ((Controller)loader.getController()).disconnect();
            System.exit(0);
        });
        primaryStage.show();

    }
}
