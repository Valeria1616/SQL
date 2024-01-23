package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelp {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelp() {

    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static DataHelp.VerificationCode getVerifCode() {
        var codeSQL = "SELECT code FROM auth_codes order by created DESC LIMIT 1";
        var conn = getConn();
        var code = QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelp.VerificationCode(code);
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
        QUERY_RUNNER.execute(connection, "DELETE FROM card_transaction");
        QUERY_RUNNER.execute(connection, "DELETE FROM cards");
        QUERY_RUNNER.execute(connection, "DELETE FROM users");
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
    }

}