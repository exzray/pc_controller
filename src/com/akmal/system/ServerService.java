package com.akmal.system;

import javafx.beans.property.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService extends Service<Void> {

    private static final int SERVER_WAITING_TIMEOUT = 1000;

    private static ServerService instance;

    private final BooleanProperty serverStatus = new SimpleBooleanProperty(false);
    private final ObjectProperty<Socket> client = new SimpleObjectProperty<>(null);
    private final StringProperty address = new SimpleStringProperty("");
    private final IntegerProperty port = new SimpleIntegerProperty(8080);

    private ServerSocket server = null;


    public static ServerService getInstance() {
        if (instance == null)
            instance = new ServerService();

        return instance;
    }

    public void startServer() {
        serverStatus.set(true);
        reset();
        start();
        System.out.println("server start");
    }

    public void stopServer() {
        if (server == null) return;

        server = null;
        serverStatus.set(false);
        System.out.println("server stop");
    }

    public BooleanProperty getServerStatus() {
        return serverStatus;
    }

    @Override
    protected Task<Void> createTask() {
        return new ConnectionTask();
    }

    private class ConnectionTask extends Task<Void> {

        @Override
        protected Void call() throws Exception {

            // TODO: 10/12/2020 replace hardcoded ip with dynamic ip
            InetAddress address = InetAddress.getByName("192.168.0.133");

            if (server == null)
                server = new ServerSocket(port.get(), 0, address);
            server.setSoTimeout(SERVER_WAITING_TIMEOUT);

            while (serverStatus.get()) {

                // waiting client
                Socket client = server.accept();

                if (client == null)
                    System.out.println("timeout");

            }

            return null;
        }
    }

}
