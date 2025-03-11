package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestionUtilisateur {
    private Connection connection;

    public GestionUtilisateur() {
        try {
            // Initialiser la connexion à la base de données
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mabase", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour ajouter un utilisateur
    public void add(Connexion connect, String nom, String prenom, String email) {
        int nextId = getNextAvailableId(connect); // Trouver le prochain ID disponible

        String sqlInsert = "INSERT INTO utilisateurs (id, nom, prenom, email, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmtInsert = connect.getConnection().prepareStatement(sqlInsert)) {
            pstmtInsert.setInt(1, nextId); // Utiliser le prochain ID disponible
            pstmtInsert.setString(2, nom);
            pstmtInsert.setString(3, prenom);
            pstmtInsert.setString(4, email);
            pstmtInsert.setTimestamp(5, java.sql.Timestamp.valueOf(LocalDateTime.now())); // created_at
            pstmtInsert.setTimestamp(6, java.sql.Timestamp.valueOf(LocalDateTime.now())); // updated_at
            pstmtInsert.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    // Méthode pour trouver le prochain ID disponible
    public int getNextAvailableId(Connexion connect) {
        int nextId = 1; // Commence à 1 par défaut
        try {
            // Récupérer tous les ID triés
            String sqlSelect = "SELECT id FROM utilisateurs ORDER BY id";
            PreparedStatement pstmtSelect = connect.getConnection().prepareStatement(sqlSelect);
            ResultSet rs = pstmtSelect.executeQuery();

            // Parcourir les ID pour trouver le premier trou
            while (rs.next()) {
                int currentId = rs.getInt("id");
                if (currentId == nextId) {
                    nextId++; // Si l'ID existe, passer au suivant
                } else {
                    break; // Si un trou est trouvé, retourner cet ID
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
        return nextId;
    }

    // Méthode pour modifier un utilisateur
    public void updateUser(Connexion connect, int id, String nom, String prenom, String email) {
        String sqlUpdate = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement pstmtUpdate = connect.getConnection().prepareStatement(sqlUpdate)) {
            pstmtUpdate.setString(1, nom);
            pstmtUpdate.setString(2, prenom);
            pstmtUpdate.setString(3, email);
            pstmtUpdate.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now())); // updated_at
            pstmtUpdate.setInt(5, id);
            pstmtUpdate.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    // Méthode pour supprimer un utilisateur
    public void deleteUserById(Connexion connect, int id) {
        String sqlDelete = "DELETE FROM utilisateurs WHERE id = ?";
        try (PreparedStatement pstmtDelete = connect.getConnection().prepareStatement(sqlDelete)) {
            pstmtDelete.setInt(1, id);
            pstmtDelete.executeUpdate();
            reorganizeIds(connect); // Réorganiser les ID après la suppression
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    // Méthode pour réorganiser les ID après suppression
    public void reorganizeIds(Connexion connect) {
        try {
            // Récupérer tous les utilisateurs triés par ID
            String sqlSelect = "SELECT id FROM utilisateurs ORDER BY id";
            PreparedStatement pstmtSelect = connect.getConnection().prepareStatement(sqlSelect);
            ResultSet rs = pstmtSelect.executeQuery();

            // Mettre à jour les ID
            int newId = 1;
            while (rs.next()) {
                int oldId = rs.getInt("id");
                if (oldId != newId) {
                    // Mettre à jour l'ID de l'utilisateur
                    String sqlUpdate = "UPDATE utilisateurs SET id = ? WHERE id = ?";
                    PreparedStatement pstmtUpdate = connect.getConnection().prepareStatement(sqlUpdate);
                    pstmtUpdate.setInt(1, newId);
                    pstmtUpdate.setInt(2, oldId);
                    pstmtUpdate.executeUpdate();
                }
                newId++;
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    // Méthode pour réinitialiser l'auto-incrémentation si la table est vide
    public void resetAutoIncrement(Connexion connect) {
        try {
            // Vérifier si la table est vide
            String sqlCount = "SELECT COUNT(*) AS count FROM utilisateurs";
            PreparedStatement pstmtCount = connect.getConnection().prepareStatement(sqlCount);
            ResultSet rs = pstmtCount.executeQuery();
            if (rs.next() && rs.getInt("count") == 0) {
                // Réinitialiser l'auto-incrémentation
                String sqlReset = "ALTER TABLE utilisateurs AUTO_INCREMENT = 1";
                PreparedStatement pstmtReset = connect.getConnection().prepareStatement(sqlReset);
                pstmtReset.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    // Méthode pour charger tous les utilisateurs
    public ArrayList<Utilisateur> loadUsers(Connexion connect) {
        ArrayList<Utilisateur> listUser = new ArrayList<>();
        String sqlSelect = "SELECT id, nom, prenom, email, created_at, updated_at FROM utilisateurs";
        try (PreparedStatement pstmtSelect = connect.getConnection().prepareStatement(sqlSelect)) {
            ResultSet rs = pstmtSelect.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

                Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, createdAt, updatedAt);
                listUser.add(utilisateur);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
        return listUser;
    }

    // Méthode pour rechercher un utilisateur par email
    public ArrayList<Utilisateur> searchUser(Connexion connect, String email) {
        ArrayList<Utilisateur> resultats = new ArrayList<>();
        String sqlSearch = "SELECT id, nom, prenom, email, created_at, updated_at FROM utilisateurs WHERE email = ?";
        try (PreparedStatement pstmtSearch = connect.getConnection().prepareStatement(sqlSearch)) {
            pstmtSearch.setString(1, email);
            ResultSet rs = pstmtSearch.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

                Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, createdAt, updatedAt);
                resultats.add(utilisateur);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
        return resultats;
    }
}