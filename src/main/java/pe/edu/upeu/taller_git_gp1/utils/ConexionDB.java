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

    //conexion mode dev
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

    //connection mode prod
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                //1. Obtener la carpeta del usuario actual
                String userHome = System.getProperty("user.home");

                File directory = new File(userHome + "/HotelKAS");

                //2. Si la carpeta no existe, la creamos
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                //3. Ruta donde se guardara la base de datos
                //C:\Users\Nombre\HotelKAS\hotel_KAS.db
                String dbPath = directory.getAbsolutePath() + "/hotel_KAS.db";

                String url = "jdbc:sqlite:" + dbPath;

                Class.forName("org.sqlite.JDBC");

                connection = DriverManager.getConnection(url);
                connection.setAutoCommit(true);

                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON");
                }

                //4. Crear las tablas en la BD
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
                "FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON UPDATE CASCADE ON DELETE RESTRICT, " +
                "FOREIGN KEY (habitacion_id) REFERENCES habitacion(id) ON UPDATE CASCADE ON DELETE RESTRICT)";

        try (Statement stmt = connection.createStatement()) {

            stmt.execute(sqlCliente);
            stmt.execute(sqlUsuario);
            stmt.execute(sqlHabitacion);
            stmt.execute(sqlReserva);


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
