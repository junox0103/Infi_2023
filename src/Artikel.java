import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Artikel {
    private Connection connection;

    public Artikel(Connection connection) {
        this.connection = connection;
    }
    public void createtable() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists Artikel (ida int AUTO_INCREMENT PRIMARY KEY, bezeichnung text,preis float);");
    }


    public void anlegen(String artikelbezeichung, Float preis) throws Exception
    {
        Statement statement = connection.createStatement();

        ResultSet resultset = statement.executeQuery("SELECT bezeichnung FROM Artikel");
        boolean vorhanden= false;
        String test;
        while (resultset.next()) {
            test = resultset.getString("bezeichnung");
            if (test.equals(artikelbezeichung))
            {
                vorhanden=true;

            }
        }
        if (vorhanden==false)
        {
            statement.execute("INSERT INTO Artikel (bezeichnung, preis) VALUES ('" + artikelbezeichung + "', '" + preis + "')");
            ResultSet resultset2 = statement.executeQuery("SELECT ida FROM Artikel WHERE bezeichnung='" + artikelbezeichung + "'");
            int lol = 0;
            while (resultset2.next()) {
              lol = resultset2.getInt("ida");
            }
            statement.execute("INSERT INTO LAGER (menge,ida) values (100,"+lol+")");

        } else
        {
            System.out.println("Artikel bereits da udn wurde aufgestockt");
            ResultSet resultset3 = statement.executeQuery("SELECT ida FROM Artikel WHERE bezeichnung='" + artikelbezeichung + "'");
            int ida = 0;
            while (resultset3.next()) {
                ida = resultset3.getInt("ida");
            }
            ResultSet ressi=statement.executeQuery("SELECT menge from Lager where ida="+ida+"");
            int bm=0;
            while (ressi.next()) {
                bm = ressi.getInt("menge");
            }
            bm=bm+100;
            statement.execute("UPDATE Lager set menge="+bm+" where ida="+ida+"" );
        }



    }

    public void aupdate(int artikelid, String artikelbezeichnung, float artikelpreis) throws Exception
    {
        Statement statement=connection.createStatement();
        statement.execute("UPDATE artikel set bezeichnung='"+artikelbezeichnung+"',preis="+artikelpreis+" where ida="+artikelid+"");
    }

    public void adelete(int artikelid) throws Exception
    {
        Statement statement= connection.createStatement();
        statement.execute("Delete from Artikel where ida="+artikelid+"");
    }
}