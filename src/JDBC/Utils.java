package JDBC;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class Utils {
    public static void printQueryResult(ResultSet rs) throws SQLException {
        while (rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                System.out.print(rsmd.getColumnLabel(i) + ": ");
                switch (rsmd.getColumnType(i)) {
                    case Types.VARCHAR:
                        System.out.print(rs.getString(i) + " ");
                        break;
                    case Types.NUMERIC:
                    case Types.INTEGER:
                        System.out.print(rs.getInt(i) + " ");
                        break;
                    case Types.FLOAT:
                        System.out.print(rs.getFloat(i) + " ");
                        break;
                    case Types.DOUBLE:
                        System.out.print(rs.getDouble(i) + " ");
                        break;
                    default:
                        System.out.print("Unsupported type " + rsmd.getColumnType(i));
                }
                System.out.print("; ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
