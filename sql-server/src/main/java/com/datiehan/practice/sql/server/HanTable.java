package com.datiehan.practice.sql.server;

import org.apache.calcite.DataContext;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.schema.ProjectableFilterableTable;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.Statistic;
import org.apache.calcite.schema.Statistics;
import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.type.SqlTypeName;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 17:25
 */
public class HanTable implements ProjectableFilterableTable {

    private final String tableName;

    private final HanSchema schema;

    private String[] columnNames;
    private SqlTypeName[] sqlTypeNames;

    public HanTable(String tableName, HanSchema schema) {
        this.tableName = tableName;
        this.schema = schema;
        columnNames = new String[5];
        sqlTypeNames = new SqlTypeName[5];
        for (int i = 0; i < 5; i++) {
            columnNames[i] = "name" + i;
            sqlTypeNames[i] = SqlTypeName.VARCHAR;
        }
    }

    @Override
    public Enumerable<Object[]> scan(DataContext dataContext, List<RexNode> filters,
            @Nullable int[] projects) {

        // scan data from storage layer
        List<Object[]> rows = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            String[] values = new String[columnNames.length];
            for (int j = 0; j < columnNames.length; j++) {
                values[j] = "val_" + i + "_" + j;
            }
            rows.add(values);
        }
        return Linq4j.asEnumerable(rows);
    }


    @Override
    public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
        RelDataTypeFactory.Builder builder = relDataTypeFactory.builder();
        for (int i = 0; i < columnNames.length; i++) {
            builder.add(columnNames[i], sqlTypeNames[i]);
        }
        return builder.build();
    }

    @Override
    public Statistic getStatistic() {
        return Statistics.UNKNOWN;
    }

    @Override
    public Schema.TableType getJdbcTableType() {
        return Schema.TableType.TABLE;
    }

    @Override
    public boolean isRolledUp(String s) {
        return false;
    }

    @Override
    public boolean rolledUpColumnValidInsideAgg(String s, SqlCall sqlCall,
            @Nullable SqlNode sqlNode, @Nullable CalciteConnectionConfig calciteConnectionConfig) {
        return false;
    }
}
