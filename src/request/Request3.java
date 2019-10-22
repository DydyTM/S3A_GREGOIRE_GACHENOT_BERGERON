package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Request3 {
    public static void request(Connection c, int numtab, String dateD, String dateF) throws SQLException {
        String req = "SELECT nomserv, dataff from serveur natural join affecter where numtab = " + numtab + " and dataff between to_date('" + dateD + "') and to_date('" + dateF + "')";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        Utils.printQueryResult(rs);
    }
}
