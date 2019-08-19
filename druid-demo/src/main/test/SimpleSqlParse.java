import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

public class SimpleSqlParse {

    public static void main(String[] args) {
        String sql = "select id, name, age from t_user t where t.age between (18, 25) and t.sex = 'girl' order by t.age desc";
        final String oracle = JdbcConstants.ORACLE;
        String result = SQLUtils.format(sql, oracle);
        System.out.println(result);

        //增加条件
        final String addCondition = SQLUtils.addCondition(sql, "t.name = 'ttang'", oracle);
        System.out.println("增加条件后: " + addCondition);


        final List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, oracle);
        sqlStatements.forEach(sqlStatement -> {
            final OracleSchemaStatVisitor oracleSchemaStatVisitor = new OracleSchemaStatVisitor();

            sqlStatement.accept(oracleSchemaStatVisitor);

            //获取表名
            System.out.println(oracleSchemaStatVisitor.getTables());

            System.out.println(oracleSchemaStatVisitor.getAliasMap());

            System.out.println(oracleSchemaStatVisitor.getConditions());

            System.out.println(oracleSchemaStatVisitor.getColumns());
        });

        //获取条件
    }
}
