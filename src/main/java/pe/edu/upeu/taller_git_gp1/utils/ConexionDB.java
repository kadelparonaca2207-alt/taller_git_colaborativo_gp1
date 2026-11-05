package pe.edu.upeu.taller_git_gp1.utils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConexionDB {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:C:/sqlite/db/hotel_KAJ.db";
                connection = DriverManager.getConnection(url);
                connection.setAutoCommit(true);

                try (Statement st = connection.createStatement()) {
                    st.execute("PRAGMA foreign_keys = ON;");
                }
                System.out.println("Conexión exitosa a SQLite!");
            }
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Error de conexión a la base de datos... " + e.getMessage());
        }
    }
}