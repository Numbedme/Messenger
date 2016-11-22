package com.numbedme;

import com.numbedme.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by User on 21.11.2016.
 */
public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                System.out.println("Enter start port");
                String message = reader.readLine();
                int port = Integer.parseInt(message);
                new Server(port).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
