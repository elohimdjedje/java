package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {
    // Références aux éléments de l'interface principale
    @FXML private TableView<Utilisateur> userTable;
    @FXML private TableColumn<Utilisateur, Integer> idColumn;
    @FXML private TableColumn<Utilisateur, String> nomColumn;
    @FXML private TableColumn<Utilisateur, String> prenomColumn;
    @FXML private TableColumn<Utilisateur, String> emailColumn;
    @FXML private TextField searchField;

    // Instances des classes de gestion et de connexion
    private GestionUtilisateur gu = new GestionUtilisateur();
    private Connexion connexion = new Connexion();

    // Méthode d'initialisation (appelée automatiquement après le chargement de l'interface)
    @FXML
    public void initialize() {
        // Configurer les colonnes de la table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Charger les utilisateurs au démarrage
        handleListUsers();
    }

    // Méthode pour ouvrir le formulaire d'ajout d'utilisateur
    @FXML
    public void handleAddUser() {
        try {
            // Charger le fichier FXML du formulaire
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/formulaire.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur du formulaire
            FormulaireController formulaireController = loader.getController();
            formulaireController.setMainController(this); // Passer la référence du contrôleur principal

            // Créer une nouvelle fenêtre pour le formulaire
            Stage stage = new Stage();
            stage.setTitle("Ajouter un utilisateur");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du formulaire : " + e.getMessage());
        }
    }

    // Méthode pour ouvrir le formulaire de modification d'utilisateur
    @FXML
    public void handleEditUser() {
        Utilisateur selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                // Charger le fichier FXML du formulaire
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/formulaire.fxml"));
                Parent root = loader.load();

                // Récupérer le contrôleur du formulaire
                FormulaireController formulaireController = loader.getController();
                formulaireController.setMainController(this); // Passer la référence du contrôleur principal
                formulaireController.setFields(selectedUser); // Pré-remplir les champs avec les données de l'utilisateur sélectionné

                // Créer une nouvelle fenêtre pour le formulaire
                Stage stage = new Stage();
                stage.setTitle("Modifier un utilisateur");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erreur lors du chargement du formulaire : " + e.getMessage());
            }
        } else {
            System.out.println("Aucun utilisateur sélectionné !");
        }
    }

    // Méthode pour supprimer un utilisateur
    @FXML
    public void handleDeleteUser() {
        Utilisateur selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            gu.deleteUserById(connexion, selectedUser.getId());
            handleListUsers(); // Rafraîchir la liste après la suppression
        } else {
            System.out.println("Aucun utilisateur sélectionné !");
        }
    }

    // Méthode pour rechercher un utilisateur par email
    @FXML
    public void handleSearchUser() {
        String searchTerm = searchField.getText();
        if (!searchTerm.isEmpty()) {
            ArrayList<Utilisateur> resultats = gu.searchUser(connexion, searchTerm);
            ObservableList<Utilisateur> items = FXCollections.observableArrayList(resultats);
            userTable.setItems(items); // Afficher les résultats dans la table
        } else {
            handleListUsers(); // Si le champ de recherche est vide, afficher tous les utilisateurs
        }
    }

    // Méthode pour charger et afficher tous les utilisateurs
    @FXML
    public void handleListUsers() {
        ArrayList<Utilisateur> utilisateurs = gu.loadUsers(connexion);
        ObservableList<Utilisateur> items = FXCollections.observableArrayList(utilisateurs);
        userTable.setItems(items); // Afficher les utilisateurs dans la table
    }
}