import java.awt.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("username:");
        String username = scanner.next();
        System.out.print("Password:");
        String password = scanner.next();
        //LocalDateTime localDateTime = LocalDateTime.now();
        DatabaseConnection dbConnection = new DatabaseConnection("jdbc:mysql://localhost/kunden_kauf_artikel", username, password);
        Kunden kunden = new Kunden(dbConnection.getConnection());
        Artikel artikel = new Artikel(dbConnection.getConnection());
        Bestellungen bestellungen = new Bestellungen(dbConnection.getConnection());
        Lager lager = new Lager(dbConnection.getConnection());
        artikel.createtable();
        kunden.createtable();
        bestellungen.createtable();
        lager.createtable();
        boolean wiederholen = true;
        while (wiederholen == true) {
            System.out.println("------------------------");
            System.out.println("Was möchten Sie machen?");
            System.out.println("------------------------");
            System.out.println("1.Kunde behandeln");
            System.out.println("2.Artikel behandeln");
            System.out.println("3.Schwarzmarkt :)");
            System.out.println("4.Bestellungstracker");
            System.out.println("5.Exit");
            int fall = scanner.nextInt();
            if (fall == 1) {
                System.out.println("Was möchten sie im Bereich 'Kunden' machen ?");
                System.out.println("1.Neuen Kunden anlagen");
                System.out.println("2.Kunden Updaten");
                System.out.println("3.Kunden destroyer");
                int fall2 = scanner.nextInt();
                if (fall2 == 1) {
                    System.out.println("Gerne! Name?");
                    String kundenname = scanner.next();
                    System.out.println("Was ist die email?");
                    String email = scanner.next();
                    kunden.anlegen(kundenname, email);
                } else if (fall2 == 2) {
                    System.out.println("Welcher Kunde wird Ugedatet?");
                    int kundenid = scanner.nextInt();
                    System.out.println("Der Neue Name?");
                    String kundenname = scanner.next();
                    System.out.println("Die neue Email?");
                    String kundenmail = scanner.next();
                    kunden.kupdate(kundenid, kundenname, kundenmail);
                } else if (fall2 == 3) {
                    System.out.println("Welcher Kunde wird gehadet?");
                    int kundenid = scanner.nextInt();
                    kunden.kdelete(kundenid);
                }
            } else if (fall == 2) {
                System.out.println("Was soll mit dem Artikel passieren?");
                System.out.println("1.Artikel registrieren");
                System.out.println("2.Artikel- bestellen");
                System.out.println("3.Artikel update :)");
                System.out.println("4.Artikel delete");
                int fall2 = scanner.nextInt();
                if (fall2 == 1) {
                    System.out.println("Gerne! Was ist die Bezeichung");
                    String artikelbezeichung = scanner.next();
                    System.out.println("Und wieviel soll es Kosten?");
                    Float preis = scanner.nextFloat();
                    artikel.anlegen(artikelbezeichung, preis);
                } else if (fall2 == 2) {
                    System.out.println("Wer will was bestllen?");
                    String bestller = scanner.next();
                    System.out.println("Was will man bestellen?");
                    String bestllenartikel = scanner.next();
                    System.out.println("wieviel?");
                    int menge = scanner.nextInt();
                    bestellungen.bestellen(bestller, bestllenartikel, menge);
                } else if (fall2 == 3) {
                    System.out.println("Welcher Artikel wird Ugedatet? (id)");
                    int artikelid = scanner.nextInt();
                    System.out.println("Der Neue Name?");
                    String artikelbezeichnung = scanner.next();
                    System.out.println("Der neue Preis?");
                    float artikelpreis = scanner.nextFloat();
                    artikel.aupdate(artikelid, artikelbezeichnung, artikelpreis);
                } else if (fall2 == 4) {
                    System.out.println("Welcher Artikel wird gehadet?(id)");
                    int artikelid = scanner.nextInt();
                    artikel.adelete(artikelid);
                }
            } else if (fall == 3) {
                System.out.println("Diese Seite wurde runtergenommen (ERRO0:404)");
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI("https://citizen.bmi.gv.at/at.gv.bmi.fnsbazweb-p/baz/public/BuergeranzeigeInfo"));
            } else if (fall == 4) {
                System.out.println("Für das Tracking wird die Kudnden ID benötigt");
                int kundenid = scanner.nextInt();
                kunden.bestellungsabfrage(kundenid);
            } else if (fall == 5) {
                wiederholen = false;
            }
        }
        dbConnection.closeConnection();
    }
}