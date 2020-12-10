package com.akmal.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class ServerService extends Thread {

    private static final int SERVER_WAITING_TIMEOUT = 10000;

    private ServerSocket server = null;
    private Socket client = null;

    @Override
    public void run() {
        super.run();


    }

    private void init() {

        try {
            InetAddress address = Inet4Address.getByName("192.168.0.133");
            server = new ServerSocket(6000, 0, address);
            server.setSoTimeout(SERVER_WAITING_TIMEOUT);

        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(new TaskWaitingClient()).start();
    }

    public void open() {

    }

    public void close() {
        // close server socket
        try {
            server.close();

            if (client != null)
                client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        server = null;
        client = null;
    }

    private class TaskWaitingClient implements Runnable {


        @Override
        public void run() {

            // start server socket
            try {
                System.out.println("waiting client");
                client = server.accept();

                // connect to client
                System.out.println(client.getRemoteSocketAddress());

                new Thread(new TaskReadClient()).start();

            } catch (IOException e) {

                if (e instanceof SocketException) {
                    System.out.println("server close");
                }

                if (e instanceof SocketTimeoutException) {
                    System.out.println("server timeout");
                }

            }

            System.out.println("thread waiting off");

        }

    }

    private class TaskReadClient implements Runnable {

        @Override
        public void run() {

            try {
                final InputStream in = client.getInputStream();
                final InputStreamReader reader = new InputStreamReader(in);
                final BufferedReader buffer = new BufferedReader(reader);

                while (server != null) {

                    System.out.println(buffer.readLine());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
