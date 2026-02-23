package com.rasmia.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL =
            "jdbc:mariadb://localhost:3306/tiendaweb?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASS = "1234"; // la misma que usas en DBeaver

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se pudo cargar el driver de MariaDB", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
