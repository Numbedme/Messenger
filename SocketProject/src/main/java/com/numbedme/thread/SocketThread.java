package com.numbedme.thread;

import com.numbedme.model.Message;
import com.numbedme.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * Created by User on 21.11.2016.
 */
public class SocketThread implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Logger logger;
    private String userName;
    private boolean interrupted;
    private InputHandler handler;

    public SocketThread(Socket socket) throws IOException {
        this.socket = socket;
        this.logger = Logger.getLogger(SocketThread.class.getName());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.handler = new InputHandler();

    }

    @Override
    public void run() {
        while (!interrupted){
            try {
                Message request = (Message) in.readObject();
                Message response = handler.handle(request);
                Server.getThreads().values()
                        .forEach((thread -> thread.writeMessage(response)));
            } catch (SocketException e){
                logger.info("Connection " + userName + " closed");
                setInterrupted(true);
            }
            catch (Exception e) {
                logger.severe("Error while getting message from user: " + userName);
                e.printStackTrace();
                break;
            }
        }
        Server.getThreads().remove(userName);
        logger.info("Thread of user " + userName + " terminated");
    }

    public synchronized void writeMessage(Message message){
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            logger.severe("Error while delivering message to user: " + userName);
            e.printStackTrace();
        }
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public String getUser() throws IOException, ClassNotFoundException {
        if (userName == null){
            userName = (String) in.readObject();
        }
        logger.info("Created thread for user " + userName);
        return userName;
    }

    public Socket getSocket() {
        return socket;
    }


    class InputHandler {

        public Message handle(Message request) {
            return request;
        }
    }

}
