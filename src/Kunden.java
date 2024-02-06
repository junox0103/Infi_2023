package src;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;

public class Kunden {
    private Connection connection;
    JSONObject jsonObject = new JSONObject();
    JSONArray kunden=new JSONArray();


    public Kunden(Connection connection) {
        this.connection = connection;
    }
    public void createtable() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists Kunden (idk int AUTO_INCREMENT PRIMARY KEY,name text,email text)");
    }

    public void anlegen(String kundenname, String email) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Kunden (name, email) VALUES (?,?)");
        statement.setString(1,kundenname);
        statement.setString(2,email);
        statement.executeUpdate();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", kundenname);
        jsonObject.put("Email", email);
        File file = new File("Kunden.json");
        JSONArray kunden = new JSONArray();
        if (file.exists() && file.length() != 0) {
            JSONParser parser = new JSONParser();
            try (FileReader reader = new FileReader(file)) {
                kunden = (JSONArray) parser.parse(reader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        kunden.add(jsonObject);


        FileWriter writer = new FileWriter("Kunden.json");
        writer.write(kunden.toJSONString());
        writer.flush();






    }
    public void bestellungsabfrage(int kundenname) throws Exception
    {
        Statement statement=connection.createStatement();
        PreparedStatement stm = connection.prepareStatement("SELECT ida,anzahl FROM Bestellungen WHERE idk=?");
        stm.setInt(1,kundenname);
        ResultSet resultset= stm.executeQuery();
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
    public void kupdate(int id,String Name,String Email) throws Exception
    {
        PreparedStatement statement=connection.prepareStatement("UPDATE kunden set name=? , email=? where idk=?");
        statement.setString(1,Name);
        statement.setString(2,Email);
        statement.setInt(3,id);
        statement.executeUpdate();

    }
    public void kdelete(int id)throws Exception
    {
        PreparedStatement statement= connection.prepareStatement("Delete from kunden where idk=?");
        statement.setInt(1,id);
        statement.executeUpdate();
    }
}