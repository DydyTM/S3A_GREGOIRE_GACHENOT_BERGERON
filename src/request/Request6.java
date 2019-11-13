package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Request6 {
    public static void request(Connection c, Integer numCom) throws SQLException {
        String req = "update COMMANDE set montcom = (select sum(prixunit * quantite) from CONTIENT natural join PLAT where NUMCOM = " + numCom + ") where numcom = " + numCom;
        Statement stmt = c.createStatement();
        stmt.executeQuery(req);
        System.out.println("\nRequesting ...\n");
        System.out.print("Updated\n\n");
    }
}
