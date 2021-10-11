package com.datiehan.practice.sql.server;

import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.rel.type.RelProtoDataType;
import org.apache.calcite.schema.Function;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.SchemaVersion;
import org.apache.calcite.schema.Schemas;
import org.apache.calcite.schema.Table;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 17:16
 */
public class HanSchema implements Schema {
    private String databaseName;

    private Map<String, Table> tables = new ConcurrentHashMap<>();

    public HanSchema(String databaseName) {
        this.databaseName = databaseName;
        String tableName = "demo";
        this.tables.put(tableName, new HanTable(tableName, this));
    }

    @Override
    public @Nullable Table getTable(String s) {
        return tables.get(s);
    }

    @Override
    public Set<String> getTableNames() {
        return tables.keySet();
    }

    @Override
    public @Nullable RelProtoDataType getType(String s) {
        return null;
    }

    @Override
    public Set<String> getTypeNames() {
        return Collections.emptySet();
    }

    @Override
    public Collection<Function> getFunctions(String s) {
        return Collections.emptyList();
    }

    @Override
    public Set<String> getFunctionNames() {
        return Collections.emptySet();
    }

    @Override
    public @Nullable Schema getSubSchema(String s) {
        return null;
    }

    @Override
    public Set<String> getSubSchemaNames() {
        return Collections.emptySet();
    }

    @Override
    public Expression getExpression(@Nullable SchemaPlus schemaPlus, String s) {
        return Schemas.subSchemaExpression(schemaPlus, s, getClass());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Schema snapshot(SchemaVersion schemaVersion) {
        // important
        return this;
    }
}
