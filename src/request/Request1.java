package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * classe de la requete 1
 */
public class Request1 {
    /**
     * methode pour afficher les resultats de la requete 1 (liste  des  plats  (numéro  et nom  du  plat)  servis  à  une période donnée (date début, date fin))
     * @param c connection a la base de donnée
     * @param dateD date de debut
     * @param dateF date de fin
     * @throws SQLException
     */
    public static void request(Connection c, String dateD, String dateF) throws SQLException {
        String req = "SELECT numplat, libelle from plat natural join contient natural join commande where datcom between to_date('" + dateD + "', 'DD/MM/YYYY') and to_date('" + dateF + "', 'DD/MM/YYYY') group by numplat, libelle order by numplat";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        System.out.println("\nRequesting ...\n");
        Utils.printQueryResult(rs);
        System.out.print("\n\n");
    }
}
