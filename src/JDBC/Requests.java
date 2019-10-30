package JDBC;

import launcher.LauncherEntry;
import launcher.Param;
import request.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Requests {
    @LauncherEntry
    public void Req1(Connection c, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request1.request(c, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @LauncherEntry
    public void Req2(Connection c, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request2.request(c, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @LauncherEntry
    public void Req3(Connection c, @Param(name="num_tab") Object numtab, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request3.request(c, (Integer) numtab, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @LauncherEntry
    public void Req4(Connection c, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request4.request(c, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @LauncherEntry
    public void Req5(Connection c, @Param(name="date_debut(\"dd/mm/yyyy\")") Object date1, @Param(name="date_fin(\"dd/mm/yyyy\")") Object date2) {
        try {
            Request5.request(c, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
