import JDBC.Connect;
import JDBC.Requests;
import launcher.Launcher;

import java.sql.Connection;
import java.sql.SQLException;

public class Principale {
    public static void main(String[] args) throws SQLException {
        Connection c = // Connect.localHost.connect("user", "pass");
        Launcher.from(Requests.class).launch(c);
    }
}
