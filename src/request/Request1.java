package request;

import JDBC.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Request1 {
    public static void request(Connection c, String dateD, String dateF) throws SQLException {
        String req = "SELECT numplat, libelle from plat natural join contient natural join commande where datcom between to_date('" + dateD + "') and to_date('" + dateF + "') group by numplat, libelle order by numplat";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        System.out.println("\nRequesting ...\n");
        Utils.printQueryResult(rs);
        System.out.print("\n\n");
    }
}
