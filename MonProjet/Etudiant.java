package MonProjet;

public class Etudiant {
    private String nom;
    private String prenom;
    private String classe;

    public Etudiant(String nom, String prenom, String classe) {
        if (nom == null || prenom == null || classe == null) {
            throw new IllegalArgumentException("Les arguments ne peuvent pas être null");
        }
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getClasse() {
        return classe;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", classe='" + classe + '\'' +
                '}';
    }
}
