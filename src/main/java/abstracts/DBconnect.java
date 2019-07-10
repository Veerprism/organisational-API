package abstracts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DBconnect {

    public static String getUrl() {
        return url;
    }
    public static String getPassword() {
        return password;
    }

    public static String getUser() {
        return user;
    }
    public static String getUrlTest() {
        return urlTest;
    }


    private static final String url = "jdbc:postgresql://ec2-54-235-92-43.compute-1.amazonaws.com:5432/dem50d28o3djq7";
    private static final String urlTest = "jdbc:postgresql://localhost:5432/organisational_API_test";

    private static final String user = "rzxyycrxguyjow";



    private static  final String password = "4532f6e7d77714d198645f21833bca0aed91afeb610505dacef4c793a2a04959";
    public static Connection conn=null;
    public static Connection connTest=null;

    public static Connection getConn() {
        try {
            conn = DriverManager.getConnection(getUrl(),getUser(), getPassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       return conn;
    }
    public static Connection getConnTest() {
        try {
            connTest = DriverManager.getConnection(getUrlTest(),getUser(), getPassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connTest;
    }
}
