import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists uebung1");
            statement.executeUpdate("create table  uebung1 (id integer PRIMARY KEY AUTOINCREMENT, menge integer,menge2 integer)");
            int gerade=0;
            int ungerade=0;
            for (int i = 0; i < 20; i++)
            {
                Random rdm=new Random();
                int x= rdm.nextInt(10);
                statement.executeUpdate("insert into uebung1 (menge,menge2) values ("+x+","+x%2+")");
            }
            ResultSet rs = statement.executeQuery("select * from uebung1");
            while(rs.next())
            {
                // read the result set
                System.out.println("mudie = " + rs.getString("menge2"));
                System.out.println("Zahlen = " + rs.getInt("menge"));
                if (rs.getInt("menge")%2==0)
                {
                    gerade=gerade+1;
                }
                else if (rs.getInt("menge")%2==1)
                {
                    ungerade=ungerade+1;
                }
            }
            System.out.println("Es gibt "+gerade+" geraden Zahlen");
            System.out.println("Es gibt "+ungerade+" ungerade Zahlen");
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}