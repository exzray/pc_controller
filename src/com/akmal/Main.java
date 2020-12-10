package com.akmal;

import com.akmal.layout.RootController;
import com.akmal.system.RemoteService;
import com.akmal.system.ServerService;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

    private static final String WINDOW_TITLE = "PC Controller Server";

    private ServerService server;
    private RemoteService remote;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        server = ServerService.getInstance();
        remote = RemoteService.getInstance();

        remote.startService();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("./layout/root.fxml"));
        Parent parent = loader.load();
        RootController controller = loader.getController();
        controller.observeMouseX(remote.getMouseX());
        controller.observeMouseY(remote.getMouseY());
        controller.observeServerRunning(server.getServerStatus());

        stage.setOnCloseRequest(this::windowClose);
        stage.setScene(new Scene(parent));
        stage.setTitle(WINDOW_TITLE);
        stage.show();
    }


    private void windowClose(WindowEvent event) {
        server.stopServer();
        remote.stopService();
    }

}
