package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * classe pour la requete 5
 */
public class Request5 {

    /**
     * methode pour afficher les resultats de la requete 5 (liste des serveurs (nom) n’ayant pas réalisé de chiffre d’affaire durant une période donnée (date début, date fin))
     * @param c connection a la base de donnée
     * @param dateD date de debut
     * @param dateF date de fin
     * @throws SQLException
     */
    public static void request(Connection c, String dateD, String dateF) throws SQLException {
        String req = "SELECT nomserv from serveur group by nomserv having nomserv not in (SELECT nomserv from commande natural join affecter natural join serveur where dataff between to_date('" + dateD + "', 'DD/MM/YYYY') and to_date('" + dateF + "', 'DD/MM/YYYY'))";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        System.out.println("\nRequesting ...\n");
        Utils.printQueryResult(rs);
        System.out.print("\n\n");
    }
}
