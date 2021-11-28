package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Blog {
    String directory = "/Users/luismedina/IdeaProjects/Post/src/com/company";
    String postsDirectory = directory + "/posts.txt";
    String commentsDirectory = directory + "/comments.txt";
    ArrayList<Post> posts = new ArrayList<Post>(); // arreglo de comentarios
    private String postNotFoundErrorMessage = "No se encontro el post, volviendo al menu anterior";

    public Blog(){
        loadPosts();
    }

    public void addPost(String title){
        String id = getNextId();
        Post post = new Post(title,id);
        posts.add(post);
        System.out.println("Post creado correctamente : " + post);
    }

    public String getNextId(){
        Integer nextiD = 0;
        if(posts.size() == 0) {
            nextiD = 1;
        } else {
            Post lastPost = posts.get(posts.size() - 1);
            nextiD = new Integer(lastPost.getId()) + 1;
        }
        return nextiD.toString();
    }

    public void printPosts() {
        for (Post post : posts){
            System.out.println(post);
        }
    }

    public void printSubmenu() {
        for (Post post : posts){
            System.out.println("[" +  (post.getId()) + "] Titulo: "  + post.getTitle());
        }
    }

    public void addComment() {
        String opcion;
        Scanner sn = new Scanner(System.in);
        System.out.println("A cual post te gustaria agregar el comentario, Seleccione una opcion: ");
        printSubmenu();
        opcion = sn.nextLine();

        Post selectedPost = null; // objeto temporal para agregar el comentario
        for (Post post : posts){
            if(post.getId().equals(opcion)) {
                selectedPost = post;
            }
        }

        if(selectedPost == null) {
            System.out.println(postNotFoundErrorMessage);
        } else {
            System.out.println("Se encontro el siguiente post");
            System.out.println(selectedPost);
            System.out.println("Escriba el comentario");
            String comment = sn.nextLine();
            selectedPost.addComment(comment);
            System.out.println("Se agrego el comentario correctamente");
        }
    }

    public void printPostComments() {
        String opcion;
        Scanner sn = new Scanner(System.in);
        System.out.println("De cual post te gustaria ver los comentario, Seleccione una opcion: ");
        printSubmenu();
        opcion = sn.nextLine();

        Post selectedPost = null; // objeto temporal para agregar el comentario
        for (Post post : posts){
            if(post.getId().equals(opcion)) {
                selectedPost = post;
            }
        }

        if(selectedPost == null) {
            System.out.println(postNotFoundErrorMessage);
        } else {
            System.out.println("Se encontraron los siguientes comentarios pertenecientes al post : " + selectedPost.getTitle());
            selectedPost.printComments();
        }
    }

    public void deletePost(){
        String opcion;
        Scanner sn = new Scanner(System.in);
        System.out.println("Cual post quieres eliminar, Seleccione una opcion: ");
        printSubmenu();
        opcion = sn.nextLine();

        Post selectedPost = null; // objeto temporal para agregar el comentario
        for (Post post : posts){
            if(post.getId().equals(opcion)) {
                selectedPost = post;
            }
        }

        if(selectedPost == null) {
            System.out.println(postNotFoundErrorMessage);
        } else {
            posts.remove(selectedPost);
            System.out.println("Se elimino el post correctamente");
        }
    }

    public void save(){
        try {
            File postsFile = new File(postsDirectory);
            FileOutputStream postsFileStream = new FileOutputStream(postsFile);
            ObjectOutputStream postsOutput = new ObjectOutputStream(postsFileStream);

            File commentsFile = new File(commentsDirectory);
            FileOutputStream commentsFileStream = new FileOutputStream(commentsFile);
            ObjectOutputStream commentsOutput = new ObjectOutputStream(commentsFileStream);

            for (Post post : posts) {
                postsOutput.writeObject(post.getId()+"-"+post.getTitle()); // se guarda el post en el file
                for (Comment comment: post.getComments()) {
                    commentsOutput.writeObject(post.getId()+"-"+comment.getId()+"-"+comment.getMessage()); // el formato para guardar los comentarios es (IdPost)-(IdComment)-Comment
                }
            }

            commentsOutput.close();
            commentsFileStream.close();
            postsFileStream.close();
            postsOutput.close();
        } catch (IOException e) {
            System.out.println("Error al agregar contacto");
        }
    }

    public void loadPosts() { // cargara los posts al momento de iniciar la aplicacion
        System.out.println("Cargando posts...");
        try {
            File postsFile = new File(postsDirectory);
            FileInputStream postsFileStream = new FileInputStream(postsFile);
            ObjectInputStream postsInput = new ObjectInputStream(postsFileStream);
            while (true) {
                String postString = postsInput.readObject().toString();
                String[] splitPostString = postString.split("-");
                Post post = new Post(splitPostString[1],splitPostString[0]);
                posts.add(post);
            }
        } catch (IOException | ClassNotFoundException e){
            if(e.toString().equals("java.io.EOFException")){
                loadComments();
                return;
            }
            System.out.println("Error al leer el directorio, "+ e);
        }
    }

    public void loadComments(){
        System.out.println("Cargando Comentarios...");
        try {
            File commentsFile = new File(commentsDirectory);
            FileInputStream commentsFileStream = new FileInputStream(commentsFile);
            ObjectInputStream commentsInput = new ObjectInputStream(commentsFileStream);

            while (true) {
                String commentString = commentsInput.readObject().toString();
                String[] splitCommentString = commentString.split("-"); // con este split el arreglo quedaraia de la sigueinte manera idPost-idComment-comment -> [idPost,idComment,comment]
                for (Post post: posts) {
                    if(post.getId().equals(splitCommentString[0])){
                        post.addComment(splitCommentString[2]);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e){
            if(e.toString().equals("java.io.EOFException")){
                return;
            }
            System.out.println("Error al leer el directorio, "+ e);
        }
    }
}
