import java.sql.Connection;
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
}
