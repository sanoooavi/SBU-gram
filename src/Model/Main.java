package Model;

import Client.ClientManager;
import Client.Network;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        PageLoader.initStage(stage);
        new PageLoader().load("Login");
    }

    @Override
    //this function happens when the program is opened
    public void init() {
        System.out.println("program opened");
    }

    @Override
    //this function happens when the program is closed
    public void stop() {
        System.out.println("program closed");
    }

    public static void main(String[] args) {
        Network.connectToServer(args);
        launch(args);
    }
}
