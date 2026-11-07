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
    @Override
    public boolean insert(Habitacion h) {
        boolean flag = false;
        String sql = "INSERT INTO habitacion (id, numero_hab, piso, tipo, precio, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setInt(1, h.getId());
            st.setInt(2, h.getNumero_hab());
            st.setInt(3, h.getPiso());
            st.setString(4, h.getTipo());
            st.setDouble(5, h.getPrecio());
            st.setString(6, h.getEstado());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar habitacion " + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Habitacion h) {
        boolean flag = false;
        String query = "UPDATE habitacion SET numero_hab = ?, piso = ?, tipo = ?, precio = ?, " +
                "estado = ? WHERE id = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
        ) {
            st.setInt(1, h.getNumero_hab());
            st.setInt(2, h.getPiso());
            st.setString(3, h.getTipo());
            st.setDouble(4, h.getPrecio());
            st.setString(5, h.getEstado());
            st.executeUpdate();
            flag = true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar habitacion" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(Habitacion h) {
        boolean flag = false;
        String query = "DELETE FROM habitacion WHERE id = ?";
        try (Connection cn = ConexionDB.getConnection()) {
            PreparedStatement st = (cn.prepareStatement(query));
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar " + e.getMessage());
        }
        return flag;    }

    @Override
    public Habitacion searchById(int id) {
        List<Habitacion> lista = new ArrayList<>();
        Habitacion hb = null;
        String query = "SELECT * FROM habitacion WHERE id = ?;";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery();) {
                if (rs.next()) {
                    hb = new Habitacion();
                    hb.setId(rs.getInt("id"));
                    hb.setNumero_hab(rs.getInt("numero_hab"));
                    hb.setPiso(rs.getInt("piso"));
                    hb.setTipo(rs.getString("tipo"));
                    hb.setPrecio((rs.getInt("precio")));
                    hb.setEstado(rs.getString("estado"));
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LISTAR " + e.getMessage());
        }
        return hb;
    }

    @Override
    public Habitacion searchByNumeroHab(String num_hab) {
        List<Habitacion> lista = new ArrayList<>();
        Habitacion hb = null;
        String query = "SELECT * FROM habitacion WHERE id = ?;";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setString(1, num_hab);
            try (ResultSet rs = st.executeQuery();) {
                if (rs.next()) {
                    hb = new Habitacion();
                    hb.setId(rs.getInt("id"));
                    hb.setNumero_hab(rs.getInt("numero_habitacion"));
                    hb.setPiso(rs.getInt("piso"));
                    hb.setTipo(rs.getString("tipo"));
                    hb.setPrecio((rs.getDouble("precio")));
                    hb.setEstado(rs.getString("estado"));
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LISTAR " + e.getMessage());
        }
        return hb;
    }

    @Override
    public List<Habitacion> listAll() {
        List<Habitacion> lista = new ArrayList<>();
        String query = "SELECT * FROM habitacion;";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                Habitacion hb = new Habitacion();
                hb.setId(rs.getInt("id"));
                hb.setNumero_hab(rs.getInt("numero_hab"));
                hb.setPiso(rs.getInt("piso"));
                hb.setTipo(rs.getString("tipo"));
                hb.setPrecio(rs.getDouble("precio"));
                hb.setEstado((rs.getString("estado")));
                lista.add(hb);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LISTAR " + e.getMessage());
        }
        return lista;    }
}
