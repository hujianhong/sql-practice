package com.datiehan.practice.sql.server;

import org.apache.calcite.avatica.AvaticaConnection;
import org.apache.calcite.avatica.Handler;
import org.apache.calcite.avatica.HandlerImpl;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.jdbc.Driver;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 12:08
 */
public class HanDriver extends Driver {

    public static final String CONNECT_STRING_PREFIX = "jdbc:han:sql:";

    static {
        new HanDriver().register();
    }

    @Override
    protected String getConnectStringPrefix() {
        return CONNECT_STRING_PREFIX;
    }

    @Override
    protected Handler createHandler() {
        return new HandlerImpl() {
            @Override
            public void onConnectionInit(AvaticaConnection connection) throws SQLException {
                CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
                final Properties info = calciteConnection.getProperties();
                for (String database : HanRootSchema.getAllDatabases()) {
                    calciteConnection.getRootSchema().add(database, HanRootSchema.getSchema(database));
                }
                String database = info.getProperty("database");
                if (database != null) {
                    calciteConnection.setSchema(database);
                }
            }
        };
    }
}
