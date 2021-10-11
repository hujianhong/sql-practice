package com.datiehan.practice.sql.client;

import org.apache.calcite.avatica.AvaticaConnection;
import org.apache.calcite.avatica.BuiltInConnectionProperty;
import org.apache.calcite.avatica.ConnectionProperty;
import org.apache.calcite.avatica.DriverVersion;
import org.apache.calcite.avatica.Meta;
import org.apache.calcite.avatica.remote.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 15:46
 */
public class HanDriver extends Driver {

    public static final String CONNECT_STRING_PREFIX = "jdbc:han:sql:";

    public final static String JDBC_PROTOCOL_TERMINATOR = ";";

    public final static String PROPERTY_SEPARATOR = "=";

    static {
        new HanDriver().register();
    }

    @Override
    protected String getConnectStringPrefix() {
        return CONNECT_STRING_PREFIX;
    }

    @Override
    protected DriverVersion createDriverVersion() {
        return super.createDriverVersion();
    }

    @Override
    protected Collection<ConnectionProperty> getConnectionProperties() {
        return super.getConnectionProperties();
    }

    @Override
    public Meta createMeta(AvaticaConnection connection) {
        return super.createMeta(connection);
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (!url.startsWith(getConnectStringPrefix())) {
            return null;
        }
        String prefix = getConnectStringPrefix();
        String urlSuffix = url.substring(prefix.length());
        String[] kvs = urlSuffix.split(JDBC_PROTOCOL_TERMINATOR);
        for (String kv : kvs) {
            String[] fields = kv.split(PROPERTY_SEPARATOR);
            if (fields.length == 2) {
                info.put(fields[0], fields[1]);
            }
        }
        info.put(BuiltInConnectionProperty.SERIALIZATION.camelName(), Serialization.PROTOBUF.name());
        Connection conn = super.connect(CONNECT_STRING_PREFIX, info);
        String schemaName = info.getProperty(BuiltInConnectionProperty.SCHEMA.camelName());
        if (schemaName != null) {
            conn.setSchema(schemaName);
        }
        return conn;
    }
}
