package com.datiehan.practice.sql.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 15:24
 */
public class SqlClient {
    private static final Logger LOG = LoggerFactory.getLogger(SqlClient.class);

    public static void main(String[] args) {
        String url = "jdbc:han:sql:url=http://127.0.0.1:7878;schema=default";
        Connection conn = null;
        try {
            Class.forName(HanDriver.class.getName());
            conn = DriverManager.getConnection(url);
            try (Statement stmt = conn.createStatement()) {
                String queryDataSql = "select * from demo";
                ResultSet rs = stmt.executeQuery(queryDataSql);
                while (rs.next()) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 5; i++) {
                        String name = rs.getString(i + 1);
                        builder.append(name).append(" ");
                    }
                    System.out.println(builder.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
