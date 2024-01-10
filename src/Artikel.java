import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Artikel {
    private Connection connection;

    public Artikel(Connection connection) {
        this.connection = connection;
    }
    public void createtable() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists Artikel (ida int AUTO_INCREMENT PRIMARY KEY, bezeichnung text,preis float)");
    }


    public void anlegen(String artikelbezeichung, Float preis) throws Exception
    {
        Statement statement = connection.createStatement();

        statement.execute("INSERT INTO Artikel (bezeichnung, preis) VALUES ('" + artikelbezeichung + "', '" + preis + "')");

    }
}