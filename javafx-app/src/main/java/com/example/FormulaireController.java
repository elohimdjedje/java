package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FormulaireController {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;

    private GestionUtilisateur gu = new GestionUtilisateur();
    private Connexion connexion = new Connexion();
    private Controller mainController; // Référence au contrôleur principal
    private Utilisateur utilisateurAModifier; // Pour stocker l'utilisateur à modifier

    // Méthode pour définir le contrôleur principal
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    // Méthode pour pré-remplir les champs du formulaire
    public void setFields(Utilisateur utilisateur) {
        this.utilisateurAModifier = utilisateur;
        nomField.setText(utilisateur.getNom());
        prenomField.setText(utilisateur.getPrenom());
        emailField.setText(utilisateur.getEmail());
    }

    // Méthode pour enregistrer l'utilisateur (ajout ou modification)
    @FXML
    public void handleSave() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();

        if (!nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty()) {
            if (utilisateurAModifier == null) {
                // Ajouter un nouvel utilisateur
                gu.add(connexion, nom, prenom, email);
            } else {
                // Modifier un utilisateur existant
                gu.updateUser(connexion, utilisateurAModifier.getId(), nom, prenom, email);
            }

            // Rafraîchir la liste des utilisateurs dans l'interface principale
            if (mainController != null) {
                mainController.handleListUsers();
            }

            // Fermer la fenêtre après l'ajout ou la modification
            nomField.getScene().getWindow().hide();
        } else {
            System.out.println("Veuillez remplir tous les champs !");
        }
    }
}