package com.akmal;

import com.akmal.layout.RootController;
import com.akmal.system.RemoteService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

    private static final String WINDOW_TITLE = "PC Controller Server";

    private RemoteService remote;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        remote = new RemoteService();
        remote.startService();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("./layout/root.fxml"));
        Parent parent = loader.load();
        RootController controller = loader.getController();
        controller.observeMouseX(remote.getMouseX());
        controller.observeMouseY(remote.getMouseY());

        stage.setOnCloseRequest(this::windowClose);
        stage.setScene(new Scene(parent));
        stage.setTitle(WINDOW_TITLE);
        stage.show();
    }


    private void windowClose(WindowEvent event) {
        remote.stopService();
        System.out.println("window close");
    }

}
