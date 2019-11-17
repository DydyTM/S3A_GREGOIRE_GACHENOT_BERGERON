package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * classe permettant de se connecter à la base de donnée
 */
public class Connect {

    private Connection con1;
    private String address;
    public static final Connect univLorraine = new Connect("jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb");
    public static final Connect localHost = new Connect("jdbc:oracle:thin:@localhost:32118:XE");

    /**
     * constructeur de la classe connect
     * @param a adresse de connection
     */
    private Connect(String a){
        address = a;
    }

    /**
     * constructeur qui cree la connection a la base de donnée
     * @param name nom de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @return connection
     * @throws SQLException
     */
    public Connection connect(String name, String password) throws SQLException  {
        if(con1 == null)
            con1 = DriverManager.getConnection(address, name, password);
        return con1;
    }
}