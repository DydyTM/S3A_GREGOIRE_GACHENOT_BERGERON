package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Request4 {
    public static void request(Connection c, String dateD, String dateF) throws SQLException {
        String req = "SELECT nomserv, sum(montcom), count(numcom) from commande natural join affecter natural join serveur where dataff between to_date('" + dateD + "') and to_date('" + dateF + "') group by nomserv;";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        Utils.printQueryResult(rs);
    }
}
