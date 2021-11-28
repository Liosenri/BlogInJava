package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Blog reddit = new Blog();


        Scanner sn = new Scanner(System.in);
        boolean salir= false;
        String opcion;

        while (!salir){
            System.out.println("[1] Crear Post");
            System.out.println("[2] Imprimir Posts");
            System.out.println("[3] Agregar comentario");
            System.out.println("[4] Imprimir comentarios de un Post");
            System.out.println("[5] Eliminar un Post");
            System.out.println("[6] Guardar Blog");
            System.out.println("[9] Salir");
            System.out.println("Seleccione una opcion: ");
            opcion = sn.nextLine();

            switch (opcion){
                case "1":
                    System.out.println("Introduzca el titulo del post");
                    String titulo = sn.nextLine();
                    reddit.addPost(titulo);
                    break;
                case "2":
                    reddit.printPosts();
                    break;
                case "3":
                    reddit.addComment();
                    break;
                case "4":
                    reddit.printPostComments();
                    break;
                case "5":
                    reddit.deletePost();
                    break;
                case "6":
                    reddit.save();
                    break;
                case "9":
                    salir = true;
                    break;
                default:
                    System.out.println("Seleccione una opcion valida");
                    break;
            }
        }
    }
}
