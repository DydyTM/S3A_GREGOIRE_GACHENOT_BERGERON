package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * classe pour la requete 6
 */
public class Request6 {

    /**
     * methode pour afficher les resultats de la requete 6 (calcul du montant  total  d’une  commande  donnée  (numéro  de  commande)  et la mise à jour de la table COMMANDE)
     * @param c connection a la base de donnée
     * @param numCom numéro de commande
     * @throws SQLException
     */
    public static void request(Connection c, Integer numCom) throws SQLException {
        String req = "update COMMANDE set montcom = (select sum(prixunit * quantite) from CONTIENT natural join PLAT where NUMCOM = " + numCom + ") where numcom = " + numCom;
        Statement stmt = c.createStatement();
        stmt.executeQuery(req);
        System.out.println("\nRequesting ...\n");
        System.out.print("Updated\n\n");
    }
}
