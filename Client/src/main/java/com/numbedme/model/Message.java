package com.numbedme.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 21.11.2016.
 */
public class Message implements Serializable {
    private String text;
    private String user;
    private Date date;

    public Message(String text, String user) {
        this.text = text;
        this.user = user;
        this.date = new Date();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(user);
        b.append("[");
        b.append(new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(date));
        b.append("] : ");
        b.append(text);
        return b.toString();
    }
}
