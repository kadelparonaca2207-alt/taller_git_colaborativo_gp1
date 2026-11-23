package pe.edu.upeu.taller_git_gp1.implementacion;

import pe.edu.upeu.taller_git_gp1.interfaces.IReservaDao;
import pe.edu.upeu.taller_git_gp1.modelo.Reserva;
import pe.edu.upeu.taller_git_gp1.utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDaoImpl implements IReservaDao {
    @Override
    public boolean insert(Reserva r) {
        boolean flag = false;
        String query = "INSERT INTO reserva (cliente_id, habitacion_id, fecha_entrada, fecha_salida, estado) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, r.getCliente_id());
            ps.setInt(2, r.getHabitacion_id());
            ps.setString(3, r.getFecha_entrada());
            ps.setString(4, r.getFecha_salida());
            ps.setString(5, r.getEstado());
            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar datos " + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Reserva r) {
        boolean flag = false;
        String query = "UPDATE reserva SET cliente_id = ?, habitacion_id = ?, fecha_entrada = ?, fecha_salida = ?, estado = ? WHERE id = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, r.getCliente_id());
            ps.setInt(2, r.getHabitacion_id());
            ps.setString(3, r.getFecha_entrada());
            ps.setString(4, r.getFecha_salida());
            ps.setString(5, r.getEstado());
            ps.setInt(6, r.getId());
            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar datos " + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(Reserva r) {
        boolean flag = false;
        String query = "DELETE FROM reserva WHERE id = ?";

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {

            ps.setInt(1, r.getId());
            ps.executeUpdate();
            flag = true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar reserva: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public Reserva searchById(int id) {
        String query = "SELECT * FROM reserva WHERE id = ?";
        Reserva r = null;

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query);) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r = new Reserva();
                    r.setId(rs.getInt("id"));
                    r.setCliente_id(rs.getInt("cliente_id"));
                    r.setHabitacion_id(rs.getInt("habitacion_id"));
                    r.setFecha_entrada(rs.getString("fecha_entrada"));
                    r.setFecha_salida(rs.getString("fecha_salida"));
                    r.setEstado(rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al realizar la acción " + e.getMessage());
        }
        return r;
    }

    @Override
    public List<Reserva> listAll() {
        boolean flag = false;
        String query = "SELECT * FROM reserva";
        List<Reserva> list = new ArrayList<>();

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reserva r = new Reserva();
                r.setId(rs.getInt("id"));
                r.setCliente_id(rs.getInt("cliente_id"));
                r.setHabitacion_id(rs.getInt("habitacion_id"));
                r.setFecha_entrada(rs.getString("fecha_entrada"));
                r.setFecha_salida(rs.getString("fecha_salida"));
                r.setEstado(rs.getString("estado"));
                list.add(r);
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al listar reservas: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<Reserva> searchByClienteId(int idCliente) {
        String query = "SELECT * FROM reserva WHERE cliente_id = ?";
        List<Reserva> lista = new ArrayList<>();

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reserva r = new Reserva();
                    r.setId(rs.getInt("id"));
                    r.setCliente_id(rs.getInt("cliente_id"));
                    r.setHabitacion_id(rs.getInt("habitacion_id"));
                    r.setFecha_entrada(rs.getString("fecha_entrada"));
                    r.setFecha_salida(rs.getString("fecha_salida"));
                    r.setEstado(rs.getString("estado"));
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar por cliente: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Reserva> searchByHabitacionId(int idHabitacion) {
        String sql = "SELECT * FROM reserva WHERE habitacion_id = ?";
        List<Reserva> lista = new ArrayList<>();

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idHabitacion);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reserva r = new Reserva();
                    r.setId(rs.getInt("id"));
                    r.setCliente_id(rs.getInt("cliente_id"));
                    r.setHabitacion_id(rs.getInt("habitacion_id"));
                    r.setFecha_entrada(rs.getString("fecha_entrada"));
                    r.setFecha_salida(rs.getString("fecha_salida"));
                    r.setEstado(rs.getString("estado"));
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar por habitación: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public boolean isDisponible(int idHabitacion, String fechaEntrada,
                                String fechaSalida, int idReserva) {

        boolean disponible = false;

        String query = "SELECT COUNT(*) FROM reserva " +
                "WHERE habitacion_id = ? " +
                "AND estado != 'Cancelada' " +
                "AND fecha_entrada < ? " +
                "AND fecha_salida > ? " +
                "AND id != ?";

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {

            ps.setInt(1, idHabitacion);
            ps.setString(2, fechaSalida);
            ps.setString(3, fechaEntrada);
            ps.setInt(4, idReserva);

            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                disponible = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar disponibilidad: " + e.getMessage());
        }

        return disponible;
    }
}
