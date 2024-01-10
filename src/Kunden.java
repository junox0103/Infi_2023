import javax.sound.midi.Soundbank;
import java.sql.*;

public class Kunden {
    private Connection connection;

    public Kunden(Connection connection) {
        this.connection = connection;
    }
    public void createtable() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists Kunden (idk int AUTO_INCREMENT PRIMARY KEY,name text,email text)");
    }



    public void anlegen(String kundenname, String email) throws Exception
    {
        Statement statement = connection.createStatement();

        statement.execute("INSERT INTO Kunden (name, email) VALUES ('" + kundenname + "', '" + email + "')");



    }


    public void bestellungsabfrage(int kundenname) throws Exception
    {
        Statement statement=connection.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT ida,anzahl FROM Bestellungen WHERE idk='" + kundenname + "'");
        int ida = 0;
        int m=0;
        while (resultset.next()) {
            ida = resultset.getInt("ida");
            m=resultset.getInt("anzahl");

        }
        ResultSet resultset2 = statement.executeQuery("SELECT bezeichnung FROM Artikel WHERE ida='" + ida + "'");
        while (resultset2.next()) {
            String ende = resultset2.getString("bezeichnung");
            System.out.println(""+ende+" , anzahl: "+m+"");

        }

    }
}