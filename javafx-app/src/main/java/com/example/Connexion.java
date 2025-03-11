package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private String url = "jdbc:mysql://localhost:3306/mabase"; // Remplacez "mabase" par le nom de votre base de données
    private String utilisateur = "root"; // Remplacez par votre nom d'utilisateur MySQL
    private String motDePasse = ""; // Remplacez par votre mot de passe MySQL
    private Connection connexion = null;

    public Connexion() {
        try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion
            this.connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion réussie !");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur : Pilote JDBC non trouvé !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connexion;
    }

    public void close() {
        if (this.connexion != null) {
            try {
                this.connexion.close();
                System.out.println("Connexion fermée avec succès.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}