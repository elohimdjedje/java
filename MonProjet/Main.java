package MonProjet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionEtudiants gestion = new GestionEtudiants();
        Scanner scanner = new Scanner(System.in);
        int choix = 0;

        do {
            System.out.println("\nMenu :");
            System.out.println("1. Ajouter un étudiant");
            System.out.println("2. Afficher la liste des étudiants");
            System.out.println("3. Supprimer un étudiant");
            System.out.println("4. Rechercher un étudiant");
            System.out.println("5. Quitter");
            System.out.print("Choix : ");

            try {
                choix = scanner.nextInt();
                scanner.nextLine(); // Pour consommer la nouvelle ligne après nextInt()
            } catch (Exception e) {
                System.out.println("Erreur : veuillez entrer un nombre valide.");
                scanner.nextLine(); // Pour vider le buffer en cas d'erreur
                continue; // Revenir au début de la boucle
            }

            switch (choix) {
                case 1:
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Prénom : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Classe : ");
                    String classe = scanner.nextLine();

                    // Validation des entrées
                    if (nom.trim().isEmpty() || prenom.trim().isEmpty() || classe.trim().isEmpty()) {
                        System.out.println("Erreur : tous les champs doivent être remplis.");
                    } else {
                        gestion.ajouterEtudiant(nom, prenom, classe);
                    }
                    break;
                case 2:
                    gestion.afficherEtudiants();
                    break;
                case 3:
                    System.out.print("Nom de l'étudiant à supprimer : ");
                    String nomSuppr = scanner.nextLine();
                    gestion.supprimerEtudiant(nomSuppr);
                    break;
                case 4:
                    System.out.print("Nom de l'étudiant à rechercher : ");
                    String nomRecherche = scanner.nextLine();
                    gestion.rechercherEtudiant(nomRecherche);
                    break;
                case 5:
                    System.out.println("Programme terminé.");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choix != 5);

        scanner.close();
    }
}
