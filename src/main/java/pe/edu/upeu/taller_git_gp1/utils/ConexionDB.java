package pe.edu.upeu.taller_git_gp1.utils;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConexionDB {

    private static Connection connection;

    /*
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.sqlite.JDBC");

                String url = "jdbc:sqlite:C:/sqlite/db/hotel_KAS.db";

                connection = DriverManager.getConnection(url);
                connection.setAutoCommit(true);

                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON");
                }

                System.out.println("Conexion success");
            }

            return connection;

        } catch (Exception e) {
            throw new RuntimeException("Error al conectar a la BD: " + e.getMessage());
        }
    }
    */

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                String userHome = System.getProperty("user.home");

                File directory = new File(userHome + "/HotelKAS");

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                //C:\Users\Nombre\HotelKAS\hotel_KAS.db
                String dbPath = directory.getAbsolutePath() + "/hotel_KAS.db";

                String url = "jdbc:sqlite:" + dbPath;
                System.out.println("Base de datos utilizada: " + dbPath);

                Class.forName("org.sqlite.JDBC");

                connection = DriverManager.getConnection(url);
                connection.setAutoCommit(true);

                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON");
                }

                initDatabase();

                System.out.println("Conexion success");
            }

            return connection;

        } catch (Exception e) {
            throw new RuntimeException("Error al conectar a la BD: " + e.getMessage());
        }
    }

    private static void initDatabase() {

        //Tabla cliente
        String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "apellido TEXT NOT NULL, " +
                "numero_documento INTEGER NOT NULL UNIQUE, " +
                "correo_electronico TEXT, " +
                "telefono TEXT)";

        //Tabla usuario
        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario (" +
                "idusuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "tipoUser TEXT NOT NULL)";

        //Tabla habitacion
        String sqlHabitacion = "CREATE TABLE IF NOT EXISTS habitacion (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "numero_hab INTEGER NOT NULL UNIQUE, " +
                "piso INTEGER NOT NULL, " +
                "tipo TEXT NOT NULL, " +
                "precio REAL NOT NULL, " +
                "estado TEXT NOT NULL DEFAULT 'Disponible')";

        //Tabla reserva
        String sqlReserva = "CREATE TABLE IF NOT EXISTS reserva (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cliente_id INTEGER NOT NULL, " +
                "habitacion_id INTEGER NOT NULL, " +
                "fecha_entrada TEXT NOT NULL, " +
                "fecha_salida TEXT NOT NULL, " +
                "estado TEXT NOT NULL DEFAULT 'Pendiente', " +
                "total REAL NOT NULL DEFAULT 0, " +
                "FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON UPDATE CASCADE ON DELETE RESTRICT, " +
                "FOREIGN KEY (habitacion_id) REFERENCES habitacion(id) ON UPDATE CASCADE ON DELETE RESTRICT)";

        try (Statement stmt = connection.createStatement()) {

            stmt.execute(sqlCliente);
            stmt.execute(sqlUsuario);
            stmt.execute(sqlHabitacion);
            stmt.execute(sqlReserva);
            boolean existeTotal = false;
            try (ResultSet columnas = stmt.executeQuery("PRAGMA table_info(reserva)")) {
                while (columnas.next()) {
                    if ("total".equalsIgnoreCase(columnas.getString("name"))) {
                        existeTotal = true;
                        break;
                    }
                }
            }
            if (!existeTotal) {
                stmt.execute("ALTER TABLE reserva ADD COLUMN total REAL NOT NULL DEFAULT 0");
            }
            stmt.executeUpdate("UPDATE reserva SET total = (julianday(fecha_salida) - julianday(fecha_entrada)) * " +
                    "(SELECT precio FROM habitacion WHERE habitacion.id = reserva.habitacion_id) " +
                    "WHERE total = 0 AND julianday(fecha_salida) > julianday(fecha_entrada)");

            boolean existeAdmin = false;

            try (ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM usuario WHERE user = 'admin'")) {

                if (rs.next() && rs.getInt(1) > 0) {
                    existeAdmin = true;
                }
            }

            if (!existeAdmin) {

                String sqlAdmin = "INSERT INTO usuario (user, password, tipoUser) " +
                        "VALUES ('admin', 'admin123', 'ADMIN')";

                stmt.execute(sqlAdmin);

                System.out.println("Usuario inicial 'admin' creado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al inicializar tablas: " + e.getMessage());
        }
    }
}
