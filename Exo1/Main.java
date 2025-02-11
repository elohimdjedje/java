package EXO1;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

        System.out.println("Entrez la couleur de la voiture");
        String couleur = sc.nextLine();
        boolean found = false;
        
        Voiture v = new Voiture();

        for (String color : v.AutorizedColor){
            if(color.equals(couleur)) {
                v.couleur = color;
                found = true;
                break;
            }
            
        }
        if (found) {

            System.out.println("erreur dans la saisie de la couleur");
            System.exit(1);
        }
    }
}