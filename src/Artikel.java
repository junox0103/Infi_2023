import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.*;

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
            PreparedStatement stm= connection.prepareStatement("INSERT INTO Artikel (bezeichnung, preis) VALUES (?,?)");
            stm.setString(1,artikelbezeichung);
            stm.setDouble(2,preis);
            stm.executeUpdate();
            PreparedStatement stm2=connection.prepareStatement("SELECT ida FROM Artikel WHERE bezeichnung=?");
            stm2.setString(1,artikelbezeichung);
            ResultSet resultset2 = stm2.executeQuery();
            int lol = 0;
            while (resultset2.next()) {
              lol = resultset2.getInt("ida");
            }
            statement.execute("INSERT INTO LAGER (menge,ida) values (100,"+lol+")");

        } else
        {
            System.out.println("Artikel bereits da udn wurde aufgestockt");
            PreparedStatement stm2=connection.prepareStatement("SELECT ida FROM Artikel WHERE bezeichnung=?");
            stm2.setString(1,artikelbezeichung);
            ResultSet resultset3 = stm2.executeQuery();
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
        PreparedStatement statement=connection.prepareStatement("UPDATE artikel set bezeichnung=?,preis=? where ida=?");
        statement.setString(1,artikelbezeichnung);
        statement.setDouble(2,artikelpreis);
        statement.setInt(3,artikelid);
        statement.executeUpdate();
    }
    public void adelete(int artikelid) throws Exception
    {
        PreparedStatement statment= connection.prepareStatement("Delete from Artikel where ida=?");
        statment.setInt(1,artikelid);
        statment.executeUpdate();
    }
}