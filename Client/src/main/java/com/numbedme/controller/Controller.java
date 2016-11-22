package com.numbedme.controller;

import com.numbedme.model.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by User on 21.11.2016.
 */
public class Controller {

    private Socket socket;
    private Thread sendThread;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String userName;

    @FXML
    private TextField inputField;
    @FXML
    private TextField port;
    @FXML
    private TextField host;
    @FXML
    private TextField name;
    @FXML
    private TextArea outputField;
    @FXML
    private Button connect;
    @FXML
    private Button send;
    @FXML
    private Button disconnect;
    @FXML
    private Label error;

    public Controller() {

    }

    public void connect(){
        try {
            init();

            error.setVisible(false);

            out.writeObject(userName);
            out.flush();

            appendText("Connected to " + socket.getInetAddress().getCanonicalHostName() + " as " + userName);
            sendThread = new Thread(() -> {
                while (!socket.isClosed()){
                    try {
                        Message message = (Message) in.readObject();
                        appendText(message.toString());
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                appendText("Disconnected from server " + socket.getInetAddress().getCanonicalHostName());
            });
            sendThread.start();
        } catch (IOException e) {
            error.setVisible(true);
            error.setText("Unable to connect");
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        socket = new Socket(host.getText(), Integer.parseInt(port.getText()));
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        userName = name.getText();
    }

    public void disconnect(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(){
        if (socket == null || socket.isClosed()){
            error.setText("Please connect first");
            error.setVisible(true);
        } else if (!"".equals(inputField.getText())){
            error.setVisible(false);
            try {
                out.writeObject(new Message(inputField.getText(), userName));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                inputField.setText("");
            }
        }
    }

    @FXML
    public void onEnter(ActionEvent event){
        send();
    }

    private void appendText(String text){
        outputField.appendText(text + "\n");
    }
}
