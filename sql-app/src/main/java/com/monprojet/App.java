package com.monprojet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mabase";
        String username = "root";
        String password = "";

        // Utilisation du try-with-resources pour gérer la fermeture automatique des ressources
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nom, email FROM utilisateurs")) {

            System.out.println("Liste des utilisateurs :");
            // Parcours du ResultSet
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                System.out.println("ID : " + id + ", Nom : " + nom + ", Email : " + email);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
        }
    }
}