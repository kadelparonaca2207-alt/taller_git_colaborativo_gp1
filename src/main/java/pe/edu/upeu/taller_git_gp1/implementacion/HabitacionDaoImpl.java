package pe.edu.upeu.taller_git_gp1.implementacion;

import pe.edu.upeu.taller_git_gp1.interfaces.IHabitacionDao;
import pe.edu.upeu.taller_git_gp1.modelo.Habitacion;
import pe.edu.upeu.taller_git_gp1.utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDaoImpl implements IHabitacionDao {

    private boolean existeNumero(Connection cn, int numero, int idExcluir) throws SQLException {
        String query = "SELECT COUNT(*) FROM habitacion " +
                "WHERE CAST(numero_hab AS INTEGER) = ? AND id <> ?";
        try (PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, numero);
            st.setInt(2, idExcluir);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public boolean insert(Habitacion h) {
        boolean flag = false;
        String sql = "INSERT INTO habitacion (numero_hab, piso, tipo, precio, estado) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.getConnection()) {
            if (existeNumero(cn, h.getNumero_hab(), -1)) {
                System.out.println("No se insertó la habitación: el número ya está registrado.");
                return false;
            }
            try (PreparedStatement st = cn.prepareStatement(sql)) {
                st.setInt(1, h.getNumero_hab());
                st.setInt(2, h.getPiso());
                st.setString(3, h.getTipo());
                st.setDouble(4, h.getPrecio());
                st.setString(5, h.getEstado());
                flag = st.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar habitación: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Habitacion h) {
        boolean flag = false;
        String query = "UPDATE habitacion SET numero_hab = ?, piso = ?, tipo = ?, precio = ?, " +
                "estado = ? WHERE id = ?";
        try (Connection cn = ConexionDB.getConnection()) {
            if (existeNumero(cn, h.getNumero_hab(), h.getId())) {
                System.out.println("No se actualizó la habitación: el número pertenece a otra habitación.");
                return false;
            }
            try (PreparedStatement st = cn.prepareStatement(query)) {
                st.setInt(1, h.getNumero_hab());
                st.setInt(2, h.getPiso());
                st.setString(3, h.getTipo());
                st.setDouble(4, h.getPrecio());
                st.setString(5, h.getEstado());
                st.setInt(6, h.getId());
                flag = st.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar habitación: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(Habitacion h) {
        boolean flag = false;
        String query = "DELETE FROM habitacion WHERE id = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, h.getId());
            flag = st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar habitación: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public Habitacion searchById(int id) {
        Habitacion hb = null;
        String query = "SELECT * FROM habitacion WHERE id = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    hb = new Habitacion();
                    hb.setId(rs.getInt("id"));
                    hb.setNumero_hab(rs.getInt("numero_hab"));
                    hb.setPiso(rs.getInt("piso"));
                    hb.setTipo(rs.getString("tipo"));
                    hb.setPrecio(rs.getDouble("precio"));
                    hb.setEstado(rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar habitación por ID: " + e.getMessage());
        }
        return hb;
    }

    @Override
    public Habitacion searchByNumeroHab(String num_hab) {
        Habitacion hb = null;
        String query = "SELECT * FROM habitacion WHERE CAST(numero_hab AS INTEGER) = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, Integer.parseInt(num_hab.trim()));
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    hb = new Habitacion();
                    hb.setId(rs.getInt("id"));
                    hb.setNumero_hab(rs.getInt("numero_hab"));
                    hb.setPiso(rs.getInt("piso"));
                    hb.setTipo(rs.getString("tipo"));
                    hb.setPrecio(rs.getDouble("precio"));
                    hb.setEstado(rs.getString("estado"));
                }
            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al buscar habitación por número: " + e.getMessage());
        }
        return hb;
    }

    @Override
    public List<Habitacion> listAll() {
        List<Habitacion> lista = new ArrayList<>();
        String query = "SELECT * FROM habitacion ORDER BY numero_hab";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Habitacion hb = new Habitacion();
                hb.setId(rs.getInt("id"));
                hb.setNumero_hab(rs.getInt("numero_hab"));
                hb.setPiso(rs.getInt("piso"));
                hb.setTipo(rs.getString("tipo"));
                hb.setPrecio(rs.getDouble("precio"));
                hb.setEstado(rs.getString("estado"));
                lista.add(hb);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar habitaciones: " + e.getMessage());
        }
        return lista;
    }
}
