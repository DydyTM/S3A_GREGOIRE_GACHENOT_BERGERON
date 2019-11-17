package JDBC;

import launcher.LauncherEntry;
import launcher.Param;
import request.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * classe permettant de faire toutes les requetes SQL du sujet
 */
public class Requests {
    /**
     * methode pour executer la requete 1
     * @param c connection a la base de donnée
     * @param date1 date de debut
     * @param date2 date de fin
     */
    @LauncherEntry
    public void Req1(Connection c, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request1.request(c, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode pour executer la requete 2
     * @param c connection a la base de donnée
     * @param date1 date de debut
     * @param date2 date de fin
     */
    @LauncherEntry
    public void Req2(Connection c, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request2.request(c, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode pour executer la requete 3
     * @param c connection a la base de donnée
     * @param numtab numero de la table
     * @param date1 date de debut
     * @param date2 date de fin
     */
    @LauncherEntry
    public void Req3(Connection c, @Param(name="num_tab") Object numtab, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request3.request(c, (Integer) numtab, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode pour executer la requete 4
     * @param c connection a la base de donnée
     * @param date1 date de debut
     * @param date2 date de fin
     */
    @LauncherEntry
    public void Req4(Connection c, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request4.request(c, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode pour executer la requete 5
     * @param c connection a la base de donnée
     * @param date1 date de debut
     * @param date2 date de fin
     */
    @LauncherEntry
    public void Req5(Connection c, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request5.request(c, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode pour executer la requete 6
     * @param c connection a la base de donnée
     * @param numCom numero de commande
     */
    @LauncherEntry
    public void Req6(Connection c, @Param(name="Numero_de_commande") Object numCom) {
        try {
            Request6.request(c, (Integer)numCom);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
