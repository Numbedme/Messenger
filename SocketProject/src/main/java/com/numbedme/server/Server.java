package com.numbedme.server;

import com.numbedme.thread.SocketThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by User on 21.11.2016.
 */
public class Server {
    private final int PORT;
    private ServerSocket serverSocket;
    private static Map<String, SocketThread> threads;
    private Logger logger;
    private boolean stopped;

    public Server(int port) {
        PORT = port;
    }

    public Server() {
        this(8085);
    }

    public void start(){
        try {
            init();
            logger.info("Server started on port " + PORT);
            logger.info("Waiting for connections");
            while (!stopped){
                try {
                    Socket socket = serverSocket.accept();
                    logger.info("Accepted client: " + socket.getInetAddress().getCanonicalHostName() + ":" + socket.getLocalPort());
                    SocketThread thread = new SocketThread(socket);
                    String name = thread.getUser();
                    threads.put(name, thread);
                    Thread t = new Thread(thread);
                    t.start();
                } catch (ClassNotFoundException e) {
                    logger.severe("Unable to get user info... Disconnecting");
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            threads.values().forEach((item) -> {
                try {
                    item.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public void init() throws IOException {
        logger = Logger.getLogger(Server.class.getName());
        serverSocket = new ServerSocket(PORT);
        threads = new HashMap<>();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static Map<String, SocketThread> getThreads() {
        return threads;
    }
}
