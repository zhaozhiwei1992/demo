package com.example.springbootdruid.filter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.jdbc.*;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * @Title: DispatchPartitionFilter
 * @Package com/example/springbootdruid/filter/DispatchPartitionFilter.java
 * @Description: 继承druid过滤器，在数据库持久化前搞一些小动作
 * @author zhaozhiwei
 * @date 2021/7/5 下午9:12
 * @version V1.0
 */
public class DispatchPartitionFilter extends FilterEventAdapter {

    /**
     * @Fields log : 记录日志信息
     */
    private static Log logger = LogFactory.getLog(DispatchPartitionFilter.class);

    private static Map<String, Map<String, Object>> connResources = new HashMap<>();

    private static Map<String,  List<String>> tablecols = new HashMap<>();

    /**
     * @data: 2021/7/5-下午9:17
     * @User: zhaozhiwei
     * @method: dataSource_getConnection
      * @param chain :
     * @param dataSource :
     * @param maxWaitMillis :
     * @return: com.alibaba.druid.pool.DruidPooledConnection
     * @Description: 获取数据库sessionId, 可以如果sessionId不同可针对连接做初始化操作，比如设置财政区划
     */
    @Override
    public DruidPooledConnection dataSource_getConnection(FilterChain chain, DruidDataSource dataSource,
                                                          long maxWaitMillis) throws SQLException {
        DruidPooledConnection conn = chain.dataSource_connect(dataSource, maxWaitMillis);
        String connectionId = null;
        Connection rawObject = null;

        if (conn.getConnectionHolder() != null) {
            ConnectionProxy connection = (ConnectionProxy) conn.getConnectionHolder().getConnection();
            rawObject = connection.getRawObject();
            Object retObj = null;
            Field field = null;
            try {
                if (connection.getClientInfo() != null && connection.getClientInfo().getProperty("url") != null && connection.getClientInfo().getProperty("url").indexOf("dm") != -1) {
                    field = rawObject.getClass().getDeclaredField("gS");
                } else if("com.aliyun.polardb.Driver".equals(dataSource.getDriverClassName()) || dataSource.getRawJdbcUrl().indexOf("polardb") > -1){
                    Method concatMethod = rawObject.getClass().getMethod("getBackendPID", null);
                    retObj = concatMethod.invoke(rawObject, null);
                } else {
                    field = rawObject.getClass().getDeclaredField("sessionId");
                }

                if(!Objects.isNull(field) && Objects.isNull(retObj)){
                    field.setAccessible(true);
                    retObj = field.get(rawObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(Objects.isNull(retObj)){
                // 如果没有获取到会话id, 则通过随机数处理，理论上不重复,可保证区划年度正确设置
                connectionId = UUID.randomUUID().toString();
            }else{
                connectionId = retObj + "";
            }
            logger.debug("conn-" + connectionId + ":" + rawObject);
        }
        return conn;
    }

    private Map<String, Object> getYearProvince() {
        Map<String, Object> yearProvince = new HashMap<>();
        yearProvince.put("year", "2021");
        yearProvince.put("province", "33");
        yearProvince.put("userid", "123456");
        return yearProvince;
    }

    /**
     * 设置连接中的财年财政信息
     *
     * @param year
     * @param province
     * @param con
     */
    private void setYearProvince(String userid, String year, String province,  String orgcode, Connection con) throws SQLException {
        if (StringUtils.isEmpty(year) || StringUtils.isEmpty(province)) {
            logger.debug("财年财政信息错误，无法设置jdbc连接");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select global_multyear_cz.Secu_f_GLOBAL_SetPARM('");
        if (userid != null && !"".equals(userid.trim())) {
            sb.append(userid);
        }
        sb.append("','");
        if (province != null && !"".equals(province.trim())) {
            sb.append(province);
        }
        sb.append("','");
        if (year != null && !"".equals(year.trim())) {
            sb.append(year);
        }
        sb.append("','");
        if (orgcode != null && !"".equals(orgcode.trim())) {
            sb.append(orgcode);
        }
        sb.append("')  from dual ");
        String dataSql = sb.toString();
        try {
            Statement st = con.createStatement();
            logger.debug(dataSql);
            st.execute(dataSql);
            st.close();
        } catch (Throwable e) {
            logger.info("财年财政信息错误，" + e.getMessage());
        }
    }



    private List<String> getTableProvinceYearCols(ConnectionProxy conn, String tablename) {
        List<String> cols = new ArrayList<>();
        if(!tablecols.containsKey(tablename)) {
            String sql = "select column_name as columncode  from user_tab_columns where table_name = ? and column_name in('PROVINCE','YEAR') ";
            try {
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.setString(1, tablename);
                ResultSet rs = psmt.executeQuery();
                while (rs.next()) {
                    cols.add(rs.getString("columncode").toLowerCase());
                }
                tablecols.put(tablename,cols);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            cols = tablecols.get(tablename);
        }
        return cols;
    }

    @Override
    public String connection_nativeSQL(FilterChain chain, ConnectionProxy connection, String sql) throws SQLException {
        sql = addProvinceYear_SQLStatement(connection, sql);
        return super.connection_nativeSQL(chain, connection, sql);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection, String sql)
            throws SQLException {
        sql = addProvinceYear_SQLStatement(connection, sql);
        return super.connection_prepareStatement(chain, connection, sql);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                              String sql, int autoGeneratedKeys) throws SQLException {
        sql = addProvinceYear_SQLStatement(connection, sql);
        return super.connection_prepareStatement(chain, connection, sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                              String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        sql = addProvinceYear_SQLStatement(connection, sql);
        return super.connection_prepareStatement(chain,connection, sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                              String sql, int resultSetType, int resultSetConcurrency,
                                                              int resultSetHoldability) throws SQLException {
        sql = addProvinceYear_SQLStatement(connection, sql);
        return super.connection_prepareStatement(chain, connection, sql, resultSetType, resultSetConcurrency,
                resultSetHoldability);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                              String sql, int[] columnIndexes) throws SQLException {
        sql = addProvinceYear_SQLStatement(connection, sql);
        return super.connection_prepareStatement(chain, connection, sql, columnIndexes);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                              String sql, String[] columnNames) throws SQLException {
        sql = addProvinceYear_SQLStatement(connection, sql);
        return super.connection_prepareStatement(chain, connection, sql, columnNames);
    }

    private String addProvinceYear_SQLStatement(ConnectionProxy connection, String sql) {
        if (sql != null && sql.toUpperCase().indexOf("TABLESPACE")==-1 && sql.toLowerCase().indexOf("insert") != -1 && sql.toLowerCase().indexOf("select") == -1 && (sql.toLowerCase().indexOf("province") == -1 || sql.toLowerCase().indexOf("year") == -1)) {
            //System.out.println(sql);
            try {
                SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcConstants.ORACLE);
                SQLStatement stmt = parser.parseStatementList().get(0);
                if (stmt instanceof SQLInsertStatement) {
                    SQLInsertStatement istmt = (SQLInsertStatement) stmt;
                    String tablename = istmt.getTableSource().toString().toUpperCase();
                    //System.out.println(tablename);
                    List<String> cols = this.getTableProvinceYearCols(connection, tablename);
                    if(cols.size()>0) {
                        List<SQLExpr> columns = istmt.getColumns();
                        for (int i = 0; i < columns.size(); i++) {
                            SQLIdentifierExpr column = (SQLIdentifierExpr) columns.get(i);
                            String columnname = column.getLowerName();
                            if (cols.contains(columnname)) {
                                cols.remove(columnname);
                            }
                        }

                        if (cols.size() > 0) {
                            Map<String, Object> yearProvince = new HashMap<>();
                            yearProvince.putAll(getYearProvince());
                            List<SQLExpr> values = istmt.getValues().getValues();
                            for (String column : cols) {
                                columns.add(new SQLIdentifierExpr(column));
                                if ("province".equalsIgnoreCase(column)) {
                                    String province = (String) yearProvince.get("province");
                                    values.add(new SQLIdentifierExpr(province));
                                } else {
                                    String year = (String) yearProvince.get("year");
                                    values.add(new SQLIdentifierExpr(year));
                                }
                            }
                            StringBuffer outputsql = new StringBuffer();
                            istmt.output(outputsql);
                            sql = outputsql.toString().replaceAll("\n\t", "").replaceAll("\n", "");
                            //System.out.println(sql);
                        }
                    }

                }
            } catch (Throwable e) {
                logger.warn(sql + "\n" + e.getMessage());
            }
        }
        return sql;
    }

    @Override
    public boolean statement_execute(FilterChain chain, StatementProxy statement, String sql) throws SQLException {
        sql = addProvinceYear_SQLStatement(statement.getConnectionProxy(), sql);
        return super.statement_execute(chain, statement, sql);
    }

    @Override
    public boolean statement_execute(FilterChain chain, StatementProxy statement, String sql, int autoGeneratedKeys)
            throws SQLException {
        sql = addProvinceYear_SQLStatement(statement.getConnectionProxy(), sql);
        return super.statement_execute(chain, statement, sql, autoGeneratedKeys);
    }

    @Override
    public boolean statement_execute(FilterChain chain, StatementProxy statement, String sql, int columnIndexes[])
            throws SQLException {
        sql = addProvinceYear_SQLStatement(statement.getConnectionProxy(), sql);
        return super.statement_execute(chain, statement, sql, columnIndexes);
    }

    @Override
    public boolean statement_execute(FilterChain chain, StatementProxy statement, String sql, String columnNames[])
            throws SQLException {
        sql = addProvinceYear_SQLStatement(statement.getConnectionProxy(), sql);
        return super.statement_execute(chain,statement, sql, columnNames);
    }

    @Override
    public int statement_executeUpdate(FilterChain chain, StatementProxy statement, String sql) throws SQLException {
        sql = addProvinceYear_SQLStatement(statement.getConnectionProxy(), sql);
        return super.statement_executeUpdate(chain,statement, sql);
    }

    @Override
    public int statement_executeUpdate(FilterChain chain, StatementProxy statement, String sql, int autoGeneratedKeys)
            throws SQLException {
        sql = addProvinceYear_SQLStatement(statement.getConnectionProxy(), sql);
        return super.statement_executeUpdate(chain, statement, sql, autoGeneratedKeys);
    }

    @Override
    public int statement_executeUpdate(FilterChain chain, StatementProxy statement, String sql, int columnIndexes[])
            throws SQLException {
        sql = addProvinceYear_SQLStatement(statement.getConnectionProxy(), sql);
        return super.statement_executeUpdate(chain, statement, sql, columnIndexes);
    }

    @Override
    public int statement_executeUpdate(FilterChain chain, StatementProxy statement, String sql, String columnNames[])
            throws SQLException {
        sql = addProvinceYear_SQLStatement(statement.getConnectionProxy(), sql);
        return super.statement_executeUpdate(chain, statement, sql, columnNames);
    }


    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        if (sql != null && sql.toUpperCase().indexOf("TABLESPACE")==-1 && sql.toUpperCase().indexOf("INSERT") != -1  && sql.toLowerCase().indexOf("select") == -1) {
            //System.out.println(sql);
            try {
                SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcConstants.ORACLE);
                SQLStatement stmt = parser.parseStatementList().get(0);
                if (stmt instanceof SQLInsertStatement) {
                    SQLInsertStatement istmt = (SQLInsertStatement) stmt;
                    String tablename = istmt.getTableSource().toString().toUpperCase();
                    //System.out.println(tablename);
                    Map<Integer, JdbcParameter> parameters = statement.getParameters();
                    Map<String, Object> yearProvince = new HashMap<>();
                    yearProvince.putAll(getYearProvince());
                    List<SQLExpr> columns = istmt.getColumns();
                    for (int i = 0; i < columns.size(); i++) {
                        SQLExpr column = columns.get(i);
                        if ("province".equalsIgnoreCase(column.toString())) {
                            JdbcParameter value = parameters.get(i);
                            if (value instanceof JdbcParameterNull) {
                                String province = (String) yearProvince.get("province");
                                setParameterValue(parameters, statement, i, province);

                            }
                        } else if ("year".equalsIgnoreCase(column.toString())) {
                            JdbcParameter value = parameters.get(i);
                            if (value instanceof JdbcParameterNull) {
                                String year = (String) yearProvince.get("year");
                                setParameterValue(parameters, statement, i, year);
                            }
                        }
                    }

                }
            } catch (Throwable e) {
                logger.warn(sql + "\n" + e.getMessage());
            }
        }
    }

    @Override
    protected void statementExecuteBatchBefore(StatementProxy statement) {
        String sql = "";
        if (statement instanceof PreparedStatementProxyImpl){
            sql = ((PreparedStatementProxyImpl) statement).getSql();
        }else{
            sql = statement.getBatchSql();
        }
        statementExecuteUpdateBefore(statement, sql);
        super.statementExecuteBatchBefore(statement);
    }
    @Override
    public void preparedStatement_addBatch(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        String sql = statement.getSql();
        statementExecuteUpdateBefore(statement, sql);
        super.preparedStatement_addBatch(chain, statement);
    }
    /**
     * 设置参数值
     *
     * @param parameters
     * @param statement
     * @param i
     * @param value
     */
    private void setParameterValue(Map<Integer, JdbcParameter> parameters, StatementProxy statement, int i, String value) {
        parameters.put(i, new JdbcParameterString(value));
        try {
            Object classObject = statement.getRawObject();
            Method concatMethod = classObject.getClass().getMethod("setString", int.class, String.class);
            if (concatMethod != null) {
                concatMethod.setAccessible(true);
                concatMethod.invoke(classObject, i + 1, value);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
