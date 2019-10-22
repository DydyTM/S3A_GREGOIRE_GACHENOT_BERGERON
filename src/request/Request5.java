package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Request5 {
    public static void request(Connection c, String dateD, String dateF) throws SQLException {
        String req = "SELECT nomserv from serveur group by nomserv having nomserv not in (SELECT nomserv from commande natural join affecter natural join serveur where dataff between to_date('" + dateD + "', 'DD/MM/YYYY') and to_date('" + dateF + "', 'DD/MM/YYYY'))";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        System.out.println("\nRequesting ...\n");
        Utils.printQueryResult(rs);
        System.out.print("\n\n");
    }
}
