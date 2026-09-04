package ru.netology.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "app";
    private static final String PASSWORD = "pass";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String getVerificationCode(String login) throws SQLException {
        var runner = new QueryRunner();

        String sql = "SELECT code " +
                "FROM auth_codes " +
                "WHERE user_id = (" +
                "SELECT id FROM users WHERE login = ?" +
                ") " +
                "ORDER BY created DESC " +
                "LIMIT 1";

        try (var connection = getConnection()) {
            return runner.query(
                    connection,
                    sql,
                    new ScalarHandler<>(),
                    login
            );
        }
    }

    public static String getUserStatus(String login) throws SQLException {
        var runner = new QueryRunner();

        String sql = "SELECT status FROM users WHERE login = ?";

        try (var connection = getConnection()) {
            return runner.query(
                    connection,
                    sql,
                    new ScalarHandler<>(),
                    login
            );
        }
    }
}
