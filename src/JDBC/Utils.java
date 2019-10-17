package JDBC;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Utils {
    public static void printQueryResult(ResultSet rs) throws SQLException {
        HashMap<String, LinkedList<String>> results = new HashMap<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int rowCount = 0;

        while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                String key = rsmd.getColumnLabel(i);
                String value = getValueFromMetaDataAt(rs, i);
                results.computeIfPresent(key, (k, l) -> { l.addLast(value); return l; });
                results.putIfAbsent(key, new LinkedList<>(Collections.singletonList(value)));
            }
            rowCount++;
        }
        String maxColumn = Integer.toString(rowCount);

        if (rowCount == 0) {
            System.out.println("<<no result>>");
            return;
        }

        BiFunction<String, Integer, String> padding = (pad, size) -> {
            AtomicReference<String> s = new AtomicReference<>("");
            IntStream.range(0, size).forEach(i -> s.updateAndGet(s_ -> s_ += pad));
            return s.get();
        };

        Function<LinkedList<String>, String> longestIn =
            l -> l.stream().reduce("", (s, s1) -> s.length() >= s1.length() ? s : s1);

        HashMap<String, Integer> paddings = new HashMap<>();
        results.forEach((k, v) -> paddings.put(k, longestIn.apply(v).length()));

        System.out.print(padding.apply(" ", maxColumn.length()) + " ");
        paddings.forEach(
            (k, v) -> System.out.print(
                "| " + k
                + padding.apply(" ", v - k.length()) + " ")
        );
        System.out.println(" |");

        for (int i = 1; i <= rowCount; ++i) {
            final int i_ = i;
            String index = Integer.toString(i);

            System.out.print(index + padding.apply(" ", maxColumn.length() - index.length()) + " ");

            paddings.forEach((k, v) -> {
                LinkedList<String> vals = results.get(k);
                String value = vals.get(i_ - 1);

                System.out.print("| " + value
                    + padding.apply(" ", (v < k.length() ? k.length() : v) - value.length())
                    + " ");
            });

            System.out.println(" |");
        }
    }

    private static String getValueFromMetaDataAt(ResultSet rs, int index) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        switch (rsmd.getColumnType(index)) {
            case Types.INTEGER:
                return Integer.toString(rs.getInt(index));
            case Types.FLOAT:
                return Float.toString(rs.getFloat(index));
            case Types.DOUBLE:
                return Double.toString(rs.getDouble(index));
            case Types.NUMERIC: {
                BigDecimal bd = rs.getBigDecimal(index);
                return (bd == null) ? "NULL" : bd.toString();
            }
            case Types.BOOLEAN:
                return Boolean.toString(rs.getBoolean(index));
            case Types.VARCHAR:
            case Types.CHAR:
            case Types.LONGVARCHAR: {
                String s = rs.getString(index);
                return (s == null) ? "NULL" : "\"" + s + "\"";
            }
            case Types.DATE: {
                Date ts = rs.getDate(index);
                return (ts == null) ? "NULL" : ts.toString();
            }
            case Types.TIME_WITH_TIMEZONE:
            case Types.TIME: {
                Time ts = rs.getTime(index);
                return (ts == null) ? "NULL" : ts.toString();
            }
            case Types.TIMESTAMP_WITH_TIMEZONE:
            case Types.TIMESTAMP: {
                Timestamp ts = rs.getTimestamp(index);
                return (ts == null) ? "NULL" : ts.toString();
            }
            default:
                return String.format("[!] %d ???", rsmd.getColumnType(index));
        }
    }
}
