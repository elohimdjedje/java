package com.monprojet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestionUtilisateur {
    private ArrayList<Utilisateur> listUser = new ArrayList<>();

    // Méthode pour ajouter un utilisateur
    public void add(Connexion connect, Scanner sc) {
        sc.nextLine();
        System.out.println("Nom de l'utilisateur");
        String lastName = sc.nextLine();

        System.out.println("Prénom de l'utilisateur");
        String firstName = sc.nextLine();

        System.out.println("Email de l'utilisateur");
        String email = sc.nextLine();

        try {
            String sqlInsert = "INSERT INTO utilisateurs (prenom, nom, email) VALUES (?, ?, ?)";
            PreparedStatement pstmtInsert = connect.getConnection().prepareStatement(sqlInsert);
            pstmtInsert.setString(1, firstName);
            pstmtInsert.setString(2, lastName);
            pstmtInsert.setString(3, email);

            int rowsAffected = pstmtInsert.executeUpdate();
            System.out.println("Insertion réussie, lignes affectées : " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    // Méthode pour charger les utilisateurs depuis la base de données
    public void loadUsers(Connexion connect) {
        try {
            String sqlSelect = "SELECT id, nom, prenom, email FROM utilisateurs";
            PreparedStatement pstmtSelect = connect.getConnection().prepareStatement(sqlSelect);
            ResultSet rs = pstmtSelect.executeQuery();

            listUser.clear(); // Vide la liste avant de la remplir

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");

                Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email);
                listUser.add(utilisateur);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    // Méthode pour lister les utilisateurs
    public void listUsers() {
        if (listUser.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
        } else {
            for (Utilisateur utilisateur : listUser) {
                System.out.println(utilisateur);
            }
        }
    }

    // Méthode pour supprimer un utilisateur par son ID
    public void deleteUserById(Connexion connect, int id) {
        try {
            String sqlDelete = "DELETE FROM utilisateurs WHERE id = ?";
            PreparedStatement pstmtDelete = connect.getConnection().prepareStatement(sqlDelete);
            pstmtDelete.setInt(1, id);

            int rowsAffected = pstmtDelete.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }
}