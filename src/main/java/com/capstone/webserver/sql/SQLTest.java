package com.capstone.webserver.sql;

import java.sql.*;

public class SQLTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection con = null;

        String url = "jdbc:mysql://localhost:3306/capstone-schema";
        String userName = "root";
        String password = "root";

        con = DriverManager.getConnection(url, userName, password);
        System.out.println("SQL 연결 완료");
        System.out.println("---------------------------");

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM attendance");
        System.out.println("SQL 쿼리문 출력 완료");
        System.out.println("---------------------------");

        rs.close();
        st.close();
        con.close();
        System.out.println("SQL 연결 종료");
        System.out.println("---------------------------");
    }
}
