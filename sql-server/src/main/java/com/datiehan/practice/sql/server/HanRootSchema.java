package com.datiehan.practice.sql.server;


import org.apache.calcite.schema.Schema;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 18:14
 */
public class HanRootSchema {

    private static final Map<String, Schema> schemas = new ConcurrentHashMap<>();

    static {
        String databaseName = "default";
        schemas.put(databaseName, new HanSchema(databaseName));
    }

    public static Collection<String> getAllDatabases() {
        return schemas.keySet();
    }

    public static Schema getSchema(String databaseName) {
        return schemas.get(databaseName);
    }
}
