import JDBC.Connect;
import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Principale {
    public static void main(String[] args) throws SQLException {
        Connect conn = Connect.univLorraine;
        Connection c = conn.connect("gachenot7u", "toineg225");
        Statement stmt = c.createStatement();
        String req = "Select * from Plat";
        ResultSet rs = stmt.executeQuery(req);
        Utils.printQueryResult(rs);
        c.close();
    }
}