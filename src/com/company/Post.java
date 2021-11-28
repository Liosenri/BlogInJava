package com.company;

import java.util.ArrayList;

public class Post {
    private String title, id;
    private ArrayList<Comment> comments = new ArrayList<Comment>(); // arreglo de comentarios

    public Post(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public void addComment(String comment){
        Integer position = comments.size() + 1;
        Comment temporalComment = new Comment(comment,position.toString());
        comments.add(temporalComment);
    }

    public void printComments() {
        for (Comment comment : comments){
            System.out.println(comment);
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", comentarios=" + comments +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
