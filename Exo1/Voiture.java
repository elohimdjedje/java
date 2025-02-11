package EXO1;

public class Voiture {
    public final String[] AutorizedColor = {"rouge", "bleu", "vert", "jaune"};
    String marque;
    String modele;
    String couleur;

    public void demarrer(){
             System.out.println("demarrer la voiture");
    }
    public void arreter(){
        System.out.println("arreter la voiture");
    }

    public void freiner(){
        System.out.println("freiner la voiture");
    }


}
