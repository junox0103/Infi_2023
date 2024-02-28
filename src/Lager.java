package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Lager {
    private Connection connection;

    public Lager(Connection con) {
        this.connection = con;
    }

    public void createtable() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists Lager (idl int AUTO_INCREMENT PRIMARY KEY,menge integer,ida integer, Foreign key(ida) references artikel(ida) on update cascade on delete cascade)");
    }
    public void lagerbestand(int Artikel) throws  Exception{
        PreparedStatement statement=connection.prepareStatement("SELECT Artikel.bezeichnung, lager.menge from Lager right join Artikel on Artikel.ida=Lager.idl where Artikel.ida=?");


        statement.setInt(1,Artikel);
        ResultSet resultset = statement.executeQuery();
        int menge=0;
        String bezeichnung="";
        while (resultset.next()) {
            menge= resultset.getInt("lager.menge");
            bezeichnung=resultset.getString("Artikel.bezeichnung");
            System.out.println("Es sind noch "+menge+" "+bezeichnung+" da");
        }

    }
    public void lagerbestandinner(int Artikel) throws  Exception{
        PreparedStatement statement=connection.prepareStatement("SELECT Artikel.bezeichnung, lager.menge from Lager inner join Artikel on Artikel.ida=Lager.idl where Artikel.ida=?");


        statement.setInt(1,Artikel);
        ResultSet resultset = statement.executeQuery();
        int menge=0;
        String bezeichnung="";
        while (resultset.next()) {
            menge= resultset.getInt("lager.menge");
            bezeichnung=resultset.getString("Artikel.bezeichnung");
            System.out.println("Es sind noch "+menge+" "+bezeichnung+" da");
        }

    }
}
