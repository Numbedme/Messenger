package com.numbedme;

import com.numbedme.model.Message;

import java.io.*;
import java.net.Socket;

/**
 * Created by User on 21.11.2016.
 */
public class Client {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
        }
    }
}
