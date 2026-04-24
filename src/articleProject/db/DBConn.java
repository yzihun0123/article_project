package articleProject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static Connection dbConn;  // static — 하나만 존재
    public static Connection getConnection() {
        if (dbConn == null) {  // 없을 때만 새로 연결
            try {
                String dbDriver   = "com.mysql.cj.jdbc.Driver";
                String dbUrl      = "jdbc:mysql://localhost:3306/article_db";
                String dbUser     = "root";      // 본인 계정으로 변경
                String dbPassword = "1111";      // 본인 비밀번호로 변경

                Class.forName(dbDriver);
                dbConn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                System.out.println("DB Connection [성공]");

            } catch (SQLException e) {
                System.out.println("DB Connection [실패]");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("드라이버를 찾을 수 없습니다.");
                e.printStackTrace();
            }
        }
        return dbConn;
    }

    public static void close() {
        if (dbConn != null) {
            try {
                if (!dbConn.isClosed()) {
                    dbConn.close();
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            dbConn = null;
        }
    }
}
