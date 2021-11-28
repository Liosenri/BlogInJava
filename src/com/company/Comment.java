package com.company;

public class Comment {
    private String message,id;

    public Comment(String message, String id) {
        this.message = message;
        this.id = id;
    }

    @Override
    public String toString() {
        return id + " : " + message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
