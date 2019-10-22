import JDBC.Connect;
import JDBC.Requests;
import launcher.Launcher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Principale {
    public static void main(String[] args) {

        boolean connected = false;
        Connection c = null;
        Scanner sc = new Scanner(System.in);

        System.out.println("Veuillez vous authentifier");
        while(!connected) {
            try {
                System.out.print("Nom d'utilisateur : ");
                String user = sc.nextLine();
                System.out.print("Mot de passe : ");
                String password = sc.nextLine();
                c = Connect.univLorraine.connect(user, password);
                connected = true;
            } catch (SQLException e) {
                System.out.println("Erreur d'authentification, veuillez reesayer");
            }
        }
        Launcher.from(Requests.class).launch(c);
    }
}
