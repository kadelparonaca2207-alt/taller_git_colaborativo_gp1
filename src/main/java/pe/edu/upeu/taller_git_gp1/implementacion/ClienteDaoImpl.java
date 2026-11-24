package pe.edu.upeu.taller_git_gp1.implementacion;

import pe.edu.upeu.taller_git_gp1.interfaces.IClienteDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;
import pe.edu.upeu.taller_git_gp1.utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl implements IClienteDao {

    // La base original y la base que ya tiene el usuario usan distinto nombre para correo.
    private String obtenerColumnaCorreo(Connection cn) throws SQLException {
        boolean tieneCorreo = false;
        boolean tieneCorreoElectronico = false;

        try (PreparedStatement ps = cn.prepareStatement("PRAGMA table_info(cliente)");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String columna = rs.getString("name");
                if ("correo".equalsIgnoreCase(columna)) {
                    tieneCorreo = true;
                }
                if ("correo_electronico".equalsIgnoreCase(columna)) {
                    tieneCorreoElectronico = true;
                }
            }
        }

        if (tieneCorreo) {
            return "correo";
        }
        if (tieneCorreoElectronico) {
            return "correo_electronico";
        }
        return null;
    }

    private boolean existeDni(Connection cn, int dni, int idExcluir) throws SQLException {
        String query = "SELECT COUNT(*) FROM cliente " +
                "WHERE CAST(numero_documento AS TEXT) = ? AND id <> ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, String.valueOf(dni));
            ps.setInt(2, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private boolean tieneTipoDocumento(Connection cn) throws SQLException {
        try (PreparedStatement ps = cn.prepareStatement("PRAGMA table_info(cliente)");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                if ("tipo_documento".equalsIgnoreCase(rs.getString("name"))) {
                    return true;
                }
            }
        }
        return false;
    }

    private Cliente llenarCliente(ResultSet rs, String columnaCorreo) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setApellido(rs.getString("apellido"));
        c.setNumero_documento(rs.getInt("numero_documento"));
        if (columnaCorreo != null) {
            c.setCorreo(rs.getString(columnaCorreo));
        }
        c.setTelefono(rs.getString("telefono"));
        return c;
    }

    @Override
    public boolean insert(Cliente c) {
        boolean flag = false;
        try (Connection cn = ConexionDB.getConnection()) {
            String columnaCorreo = obtenerColumnaCorreo(cn);
            boolean tieneTipo = tieneTipoDocumento(cn);
            if (existeDni(cn, c.getNumero_documento(), -1)) {
                System.out.println("No se insertó el cliente: el DNI ya está registrado.");
                return false;
            }

            String columnas = "nombre, apellido, ";
            String valores = "?, ?, ";
            if (tieneTipo) {
                columnas += "tipo_documento, ";
                valores += "?, ";
            }
            columnas += "numero_documento";
            valores += "?";
            if (columnaCorreo != null) {
                columnas += ", " + columnaCorreo;
                valores += ", ?";
            }
            columnas += ", telefono";
            valores += ", ?";

            String query = "INSERT INTO cliente (" + columnas + ") VALUES (" + valores + ")";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                int parametro = 1;
                ps.setString(parametro++, c.getNombre());
                ps.setString(parametro++, c.getApellido());
                if (tieneTipo) {
                    // El sistema trabaja únicamente con DNI; no se agrega otro campo al formulario.
                    ps.setString(parametro++, "DNI");
                }
                ps.setInt(parametro++, c.getNumero_documento());
                if (columnaCorreo != null) {
                    ps.setString(parametro++, c.getCorreo());
                }
                ps.setString(parametro, c.getTelefono());
                flag = ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Cliente c) {
        boolean flag = false;
        try (Connection cn = ConexionDB.getConnection()) {
            String columnaCorreo = obtenerColumnaCorreo(cn);
            boolean tieneTipo = tieneTipoDocumento(cn);
            if (existeDni(cn, c.getNumero_documento(), c.getId())) {
                System.out.println("No se actualizó el cliente: el DNI pertenece a otro registro.");
                return false;
            }

            String query = "UPDATE cliente SET nombre = ?, apellido = ?";
            if (tieneTipo) {
                query += ", tipo_documento = 'DNI'";
            }
            query += ", numero_documento = ?";
            if (columnaCorreo != null) {
                query += ", " + columnaCorreo + " = ?";
            }
            query += ", telefono = ? WHERE id = ?";

            try (PreparedStatement ps = cn.prepareStatement(query)) {
                int parametro = 1;
                ps.setString(parametro++, c.getNombre());
                ps.setString(parametro++, c.getApellido());
                ps.setInt(parametro++, c.getNumero_documento());
                if (columnaCorreo != null) {
                    ps.setString(parametro++, c.getCorreo());
                }
                ps.setString(parametro++, c.getTelefono());
                ps.setInt(parametro, c.getId());
                flag = ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(Cliente c) {
        boolean flag = false;
        String query = "DELETE FROM cliente WHERE id = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, c.getId());
            flag = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public Cliente searchById(int id) {
        Cliente cliente = null;
        try (Connection cn = ConexionDB.getConnection()) {
            String columnaCorreo = obtenerColumnaCorreo(cn);
            String query = "SELECT * FROM cliente WHERE id = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        cliente = llenarCliente(rs, columnaCorreo);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por ID: " + e.getMessage());
        }
        return cliente;
    }

    @Override
    public Cliente searchByDni(String dni) {
        Cliente cliente = null;
        try (Connection cn = ConexionDB.getConnection()) {
            String columnaCorreo = obtenerColumnaCorreo(cn);
            String query = "SELECT * FROM cliente WHERE CAST(numero_documento AS TEXT) = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, dni);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        cliente = llenarCliente(rs, columnaCorreo);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por DNI: " + e.getMessage());
        }
        return cliente;
    }

    @Override
    public List<Cliente> listAll() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection cn = ConexionDB.getConnection()) {
            String columnaCorreo = obtenerColumnaCorreo(cn);
            String query = "SELECT * FROM cliente ORDER BY id";
            try (PreparedStatement ps = cn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(llenarCliente(rs, columnaCorreo));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }
}
