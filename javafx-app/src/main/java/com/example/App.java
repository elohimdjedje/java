package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface.fxml"));
        VBox root = loader.load(); // Charge l'interface graphique depuis le fichier FXML

        // Créer une scène avec la racine chargée
        Scene scene = new Scene(root, 600, 400);

        // Ajouter une feuille de style CSS (optionnel)
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Configurer la fenêtre principale
        primaryStage.setTitle("Gestion des Utilisateurs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lance l'application JavaFX
    }
}