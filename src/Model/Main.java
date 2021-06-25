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

    /**
     * main method is the first you should run so that you get connected to the server
     * and that launches the application
     * then after this happened the app comes up for you and then you can
     * get started
     * @param args
     */
    public static void main(String[] args) {
        Network.connectToServer(args);
        launch(args);
    }
}
