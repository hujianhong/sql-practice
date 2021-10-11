package com.datiehan.practice.sql.server;

import org.apache.calcite.server.DdlExecutor;
import org.apache.calcite.sql.parser.SqlAbstractParserImpl;
import org.apache.calcite.sql.parser.SqlParserImplFactory;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;

import java.io.Reader;

/**
 * @author jianhonghu
 * @email hunter@ibytes.net
 * @date 2021-09-29 17:10
 */
public class HanSqlParserFactory implements SqlParserImplFactory {
    @Override
    public SqlAbstractParserImpl getParser(Reader reader) {
        return SqlParserImpl.FACTORY.getParser(reader);
    }

    @Override
    public DdlExecutor getDdlExecutor() {
        return null;
    }
}
