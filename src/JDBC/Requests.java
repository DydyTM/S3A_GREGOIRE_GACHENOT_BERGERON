package JDBC;

import launcher.LauncherEntry;
import launcher.Param;
import request.Request1;

import java.sql.Connection;
import java.sql.SQLException;

public class Requests {
    @LauncherEntry
    public void Req_1(Connection c, @Param(name="date_debut") Object date1, @Param(name="date_fin") Object date2) {
        try {
            Request1.request(c, (String) date1, (String) date2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
