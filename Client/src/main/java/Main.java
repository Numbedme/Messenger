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
        /*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter user name");
            String name = reader.readLine();
            System.out.println("Enter host name");
            String host = reader.readLine();
            System.out.println("Enter port");
            String port = reader.readLine();

            Socket s = new Socket(host, Integer.parseInt(port));
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            out.writeObject(name);
            out.flush();

            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Message m = (Message) in.readObject();
                        System.out.println(m.toString());
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();

            while (true){
                String text = reader.readLine();
                out.writeObject(new Message(text, name));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
