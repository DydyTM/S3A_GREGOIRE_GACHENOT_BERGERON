package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private Connection con1;
    private String address;
    public static final Connect univLorraine = new Connect("jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb");
    public static final Connect localHost = new Connect("jdbc:oracle:thin:@localhost:32118:XE");

    private Connect(String a){
        address = a;
    }

    public Connection connect(String name, String password) throws SQLException  {
        if(con1 != null)
            con1 = DriverManager.getConnection(address, name, password);
        return con1;
    }
}