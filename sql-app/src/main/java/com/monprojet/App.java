package com.monprojet;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World !");

        Connexion connexion = new Connexion();
        int choix = 0;
        Scanner sc = new Scanner(System.in);
        GestionUtilisateur gu = new GestionUtilisateur();

        do {
            System.out.println("Que voulez-vous faire ?");
            System.out.println("1 - Ajouter un utilisateur");
            System.out.println("2 - Lister les utilisateurs");
            System.out.println("3 - Supprimer un utilisateur par son ID");
            System.out.println("0 - Quitter");
            choix = sc.nextInt();

            switch (choix) {
                case 1:
                    gu.add(connexion, sc);
                    break;
                case 2:
                    gu.loadUsers(connexion); // Charge les utilisateurs depuis la base de données
                    gu.listUsers(); // Affiche la liste des utilisateurs
                    break;
                case 3:
                    System.out.println("Entrez l'ID de l'utilisateur à supprimer :");
                    int id = sc.nextInt();
                    gu.deleteUserById(connexion, id); // Supprime l'utilisateur par son ID
                    break;
                default:
                    System.out.println("L'action demandée n'existe pas !");
                    break;
            }
        } while (choix != 0);

        connexion.close();
        sc.close();
        System.exit(0);
    }
}