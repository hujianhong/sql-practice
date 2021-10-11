package com.datiehan.practice.sql.server;

import org.apache.calcite.jdbc.CalcitePrepare;
import org.apache.calcite.server.DdlExecutor;
import org.apache.calcite.sql.SqlNode;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 12:11
 */
public class HanDdlExecutor implements DdlExecutor {

    @Override
    public void executeDdl(CalcitePrepare.Context context, SqlNode sqlNode) {
        switch (sqlNode.getKind()) {
            case CREATE_SCHEMA:  // create database

                break;

            case CREATE_TABLE: // create table

                break;

            case ALTER_TABLE: //
                break;

            case DROP_TABLE: // alter table

                break;
            default:
                throw new UnsupportedOperationException("Unsupported sql kind: " + sqlNode.getKind());
        }
    }
}

