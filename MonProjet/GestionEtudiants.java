package MonProjet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionEtudiants {
    private List<Etudiant> listeEtudiants;

    public GestionEtudiants() {
        this.listeEtudiants = new ArrayList<>();
    }

    public void ajouterEtudiant(String nom, String prenom, String classe) {
        // Vérifier si l'étudiant existe déjà
        for (Etudiant etudiant : listeEtudiants) {
            if (etudiant.getNom().equalsIgnoreCase(nom) && etudiant.getPrenom().equalsIgnoreCase(prenom)) {
                System.out.println("L'étudiant existe déjà dans la liste.");
                return;
            }
        }
        listeEtudiants.add(new Etudiant(nom, prenom, classe));
        System.out.println("Étudiant ajouté avec succès !");
    }

    public void afficherEtudiants() {
        if (listeEtudiants.isEmpty()) {
            System.out.println("Aucun étudiant dans la liste.");
        } else {
            System.out.println("Liste des étudiants :");
            for (Etudiant etudiant : listeEtudiants) {
                System.out.println(etudiant);
            }
        }
    }

    public void supprimerEtudiant(String nom) {
        Iterator<Etudiant> it = listeEtudiants.iterator();
        boolean trouve = false;
        while (it.hasNext()) {
            if (it.next().getNom().equalsIgnoreCase(nom)) {
                it.remove();
                trouve = true;
                System.out.println("Étudiant supprimé avec succès !");
            }
        }
        if (!trouve) {
            System.out.println("Aucun étudiant trouvé avec le nom : " + nom);
        }
    }

    public void rechercherEtudiant(String nom) {
        boolean trouve = false;
        for (Etudiant etudiant : listeEtudiants) {
            if (etudiant.getNom().equalsIgnoreCase(nom)) {
                System.out.println("Étudiant trouvé : " + etudiant);
                trouve = true;
            }
        }
        if (!trouve) {
            System.out.println("Aucun étudiant trouvé avec le nom : " + nom);
        }
    }
}
