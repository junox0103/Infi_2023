import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("username:");
        String username = scanner.next();
        System.out.print("Password:");
        String password = scanner.next();

        DatabaseConnection dbConnection = new DatabaseConnection("jdbc:mysql://localhost/kunden_kauf_artikel", username, password);


        Kunden kunden = new Kunden(dbConnection.getConnection());
        Artikel artikel = new Artikel(dbConnection.getConnection());
        Bestellungen bestellungen=new Bestellungen(dbConnection.getConnection());
        artikel.createtable();
        kunden.createtable();
        bestellungen.createtable();
        boolean wiederholen=true;
        while(wiederholen==true) {
            System.out.println("------------------------");
            System.out.println("Was möchten Sie machen?");
            System.out.println("------------------------");
            System.out.println("1.Kunde anlegen");
            System.out.println("2.Neuen Artikel registrieren");
            System.out.println("3.Artikel bestellen");
            System.out.println("4.Bestellungstracker");
            System.out.println("5.Exit");
            int fall = scanner.nextInt();
            if (fall == 1) {
                System.out.println("Gerne! Name?");
                String kundenname = scanner.next();
                System.out.println("Was ist die email?");
                String email = scanner.next();
                kunden.anlegen(kundenname, email);
            } else if (fall == 2) {
                System.out.println("Gerne! Was ist die Bezeichung");
                String artikelbezeichung = scanner.next();
                System.out.println("Und wieviel soll es Kosten?");
                Float preis = scanner.nextFloat();
                artikel.anlegen(artikelbezeichung, preis);
            } else if (fall == 3) {
                System.out.println("Wer will was bestllen?");
                String bestller = scanner.next();
                System.out.println("Was will man bestellen?");
                String bestllenartikel = scanner.next();
                System.out.println("wieviel?");
                int menge = scanner.nextInt();
                bestellungen.bestellen(bestller, bestllenartikel, menge);
                System.out.println("Erfolgreich bestellt");
            } else if (fall==4)
            {
                System.out.println("Für das Tracking wird die Kudnden ID benötigt");
                int kundenid=scanner.nextInt();
                kunden.bestellungsabfrage(kundenid);
            }
            else if (fall==5)
            {
                wiederholen=false;

            }


        }


        dbConnection.closeConnection();
    }
}