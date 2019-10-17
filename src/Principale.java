import JDBC.Connect;
import launcher.Launcher;

import java.sql.Connection;

public class Principale {
    public static void main(String[] args) {
        Connection c = // Connect.localHost.connect("user", "pass");
        Launcher.from(/* Requests.class */).launch(c);
    }
}
