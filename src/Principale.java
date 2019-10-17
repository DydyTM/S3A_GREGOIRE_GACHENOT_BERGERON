import JDBC.Connect;

import java.sql.Connection;
import java.sql.SQLException;

public class Principale {
    public static void main(String[] args) throws SQLException {
        Connection c = Connect.localHost.connect("mesabloo", "root");
        System.out.println(c);
    }
}
