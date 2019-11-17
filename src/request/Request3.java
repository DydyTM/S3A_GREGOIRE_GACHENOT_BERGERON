package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * classe pour la requete 3
 */
public class Request3 {

    /**
     * methode pour afficher les resultats de la requete 3 (liste serveurs (nom et date) qui ont servi une table donnée à une période donnée (date début, date fin))
     * @param numtab numero de la table
     * @param dateD date de debut
     * @param dateF date de fin
     * @throws SQLException
     */
    public static void request(Connection c, int numtab, String dateD, String dateF) throws SQLException {
        String req = "SELECT nomserv, dataff from serveur natural join affecter where numtab = " + numtab + " and dataff between to_date('" + dateD + "', 'DD/MM/YYYY') and to_date('" + dateF + "', 'DD/MM/YYYY')";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        System.out.println("\nRequesting ...\n");
        Utils.printQueryResult(rs);
        System.out.print("\n\n");
    }
}
