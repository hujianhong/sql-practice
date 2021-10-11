package com.datiehan.practice.sql.server;

import org.apache.calcite.config.CalciteConnectionProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 15:15
 */
public class LocalServer {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName(HanDriver.class.getName());
            Properties info = new Properties();
            info.setProperty(CalciteConnectionProperty.LEX.name(), "MYSQL");
            info.setProperty(CalciteConnectionProperty.PARSER_FACTORY.name(), HanSqlParserFactory.class.getName());
            info.setProperty(CalciteConnectionProperty.TIME_ZONE.name(), "GMT");
            info.setProperty("database", "default");
            conn = DriverManager.getConnection(HanDriver.CONNECT_STRING_PREFIX, info);
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
