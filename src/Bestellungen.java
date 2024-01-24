import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.time.LocalDate;


public class Bestellungen {
    private Connection connection;

    public Bestellungen(Connection connection) {
        this.connection = connection;
    }

    public void createtable() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists Bestellungen (datum date,idk int, ida int,anzahl int,FOREIGN KEY (idk) REFERENCES Kunden (idk) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY (ida) REFERENCES Artikel (ida) ON UPDATE CASCADE ON DELETE CASCADE)");
    }


    public void bestellen(String bestller, String bestllenartikel, int menge) throws Exception {
        Statement statement = connection.createStatement();

        ResultSet resultset = statement.executeQuery("SELECT idk FROM Kunden WHERE name='" + bestller + "'");
        int idk = 0;
        while (resultset.next()) {
            idk = resultset.getInt("idk");

        }
        ResultSet resultset2 = statement.executeQuery("SELECT ida FROM Artikel WHERE bezeichnung='" + bestllenartikel + "'");
        int ida = 0;

        while (resultset2.next()) {
            ida = resultset2.getInt("ida");
        }
        ResultSet ressi = statement.executeQuery("SELECT menge from Lager where ida=" + ida + "");
        int bm = 0;
        while (ressi.next()) {
            bm = ressi.getInt("menge");
        }
        if (bm > menge) {
            bm = bm - menge;
            LocalDate myObj = LocalDate.now();
            statement.execute("update lager set menge=" + bm + " where ida=" + ida + "");
            statement.execute("insert into bestellungen (datum,idk,ida,anzahl)values (current_date," + idk + " ," + ida + "," + menge + ")");
            System.out.println("Erfolgreich bestellt");
            System.out.println(bm);
        } else {
            System.out.println("soviel ist leider nicht mehr im Lager");
        }
    }
}