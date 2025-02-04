
public class tableau {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("entrer un nom et votre age : ");
        String nom = scanner.nextLine();
        int age = scanner.nextInt();

        System.out.println("bonjour, " + nom +  " votre age est " +age+ "!");

        scanner.close();

        if (age > 18) {
            System.out.println(nom + "  est majeur");
            
        }
        else{
            System.out.println(nom + " est mineur");
        }
    }
}