package com.datiehan.practice.sql.server;

import org.apache.calcite.avatica.Meta;
import org.apache.calcite.avatica.jdbc.JdbcMeta;
import org.apache.calcite.avatica.remote.LocalService;
import org.apache.calcite.avatica.remote.Service;
import org.apache.calcite.avatica.server.AvaticaHandler;
import org.apache.calcite.avatica.server.AvaticaProtobufHandler;
import org.apache.calcite.avatica.server.HttpServer;
import org.apache.calcite.config.CalciteConnectionProperty;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.ServerConnector;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 15:15
 */
public class RemoteServer {


    public static void main(String[] args) throws SQLException, InterruptedException {
        try {
            Class.forName(HanDriver.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //
        Properties info = new Properties();
        info.setProperty(CalciteConnectionProperty.LEX.name(), "MYSQL");
        info.setProperty(CalciteConnectionProperty.PARSER_FACTORY.name(), HanSqlParserFactory.class.getName());
        info.setProperty(CalciteConnectionProperty.TIME_ZONE.name(), "GMT");
        info.setProperty("database", "default");
        Meta meta = new JdbcMeta(HanDriver.CONNECT_STRING_PREFIX, info);
        int port = 7878;
        Service avaticaService = new LocalService(meta);
        AvaticaHandler avaticaHandler = new AvaticaProtobufHandler(avaticaService);

        // start http server
        HttpServer httpServer = new HttpServer(port, avaticaHandler) {
            @Override
            protected ServerConnector configureConnector(ServerConnector connector, int port) {
                HttpConnectionFactory factory = (HttpConnectionFactory)
                        connector.getDefaultConnectionFactory();
                // 64K
                factory.getHttpConfiguration().setRequestHeaderSize(64 << 10);
                return super.configureConnector(connector, port);
            }
        };
        httpServer.start();

        httpServer.join();

    }

}
