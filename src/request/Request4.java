package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * methode pour la requete 4
 */
public class Request4 {
    /**
     * methode pour afficher les resultats de la requete 4 (Affichage  (dans  un  ordre  décroissant)  du  chiffre  d’affaire  et  le  nombre  de commandes  réalisés  par  chaque  serveur  (nom,  chiffre  d’affaire,  nombre  de commandes) en une période donnée (date début, date fin))
     * @param c connection a la base de donnée
     * @param dateD date de debut
     * @param dateF date de fin
     * @throws SQLException
     */
    public static void request(Connection c, String dateD, String dateF) throws SQLException {
        String req = "SELECT nomserv, sum(montcom), count(numcom) from commande natural join affecter natural join serveur where dataff between to_date('" + dateD + "', 'DD/MM/YYYY') and to_date('" + dateF + "', 'DD/MM/YYYY') group by nomserv";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        System.out.println("\nRequesting ...\n");
        Utils.printQueryResult(rs);
        System.out.print("\n\n");
    }
}
