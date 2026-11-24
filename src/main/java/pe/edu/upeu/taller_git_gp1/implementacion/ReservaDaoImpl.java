package pe.edu.upeu.taller_git_gp1.implementacion;

import pe.edu.upeu.taller_git_gp1.interfaces.IReservaDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;
import pe.edu.upeu.taller_git_gp1.modelo.Habitacion;
import pe.edu.upeu.taller_git_gp1.modelo.Reserva;
import pe.edu.upeu.taller_git_gp1.utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReservaDaoImpl implements IReservaDao {

    private static final String SELECT_RESERVAS =
            "SELECT r.id AS reserva_id, r.cliente_id, r.habitacion_id, " +
            "r.fecha_entrada, r.fecha_salida, r.estado, " +
            "c.id AS cliente_obj_id, c.nombre AS cliente_nombre, " +
            "c.apellido AS cliente_apellido, c.numero_documento AS cliente_documento, " +
            "h.id AS habitacion_obj_id, h.numero_hab AS habitacion_numero, " +
            "h.piso AS habitacion_piso, h.tipo AS habitacion_tipo, " +
            "h.precio AS habitacion_precio, h.estado AS habitacion_estado " +
            "FROM reserva r " +
            "LEFT JOIN cliente c ON c.id = r.cliente_id " +
            "LEFT JOIN habitacion h ON h.id = r.habitacion_id ";

    private Reserva llenarReserva(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setId(rs.getInt("reserva_id"));
        r.setCliente_id(rs.getInt("cliente_id"));
        r.setHabitacion_id(rs.getInt("habitacion_id"));
        r.setFecha_entrada(rs.getString("fecha_entrada"));
        r.setFecha_salida(rs.getString("fecha_salida"));
        r.setEstado(rs.getString("estado"));

        if (rs.getObject("cliente_obj_id") != null) {
            Cliente c = new Cliente();
            c.setId(rs.getInt("cliente_obj_id"));
            c.setNombre(rs.getString("cliente_nombre"));
            c.setApellido(rs.getString("cliente_apellido"));
            c.setNumero_documento(rs.getInt("cliente_documento"));
            r.setCliente(c);
        }

        if (rs.getObject("habitacion_obj_id") != null) {
            Habitacion h = new Habitacion();
            h.setId(rs.getInt("habitacion_obj_id"));
            h.setNumero_hab(rs.getInt("habitacion_numero"));
            h.setPiso(rs.getInt("habitacion_piso"));
            h.setTipo(rs.getString("habitacion_tipo"));
            h.setPrecio(rs.getDouble("habitacion_precio"));
            h.setEstado(rs.getString("habitacion_estado"));
            r.setHabitacion(h);
        }
        return r;
    }

    private List<Reserva> consultarReservas(String query, Integer id) {
        List<Reserva> lista = new ArrayList<>();
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            if (id != null) {
                ps.setInt(1, id);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(llenarReserva(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar reservas: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean insert(Reserva r) {
        boolean flag = false;
        if (!"Cancelada".equalsIgnoreCase(r.getEstado()) &&
                !isDisponible(r.getHabitacion_id(), r.getFecha_entrada(), r.getFecha_salida(), 0)) {
            System.out.println("No se insertó la reserva porque existe un cruce de fechas.");
            return false;
        }
        String query = "INSERT INTO reserva (cliente_id, habitacion_id, fecha_entrada, fecha_salida, estado) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, r.getCliente_id());
            ps.setInt(2, r.getHabitacion_id());
            ps.setString(3, r.getFecha_entrada());
            ps.setString(4, r.getFecha_salida());
            ps.setString(5, r.getEstado());
            flag = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar reserva: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Reserva r) {
        boolean flag = false;
        if (!"Cancelada".equalsIgnoreCase(r.getEstado()) &&
                !isDisponible(r.getHabitacion_id(), r.getFecha_entrada(), r.getFecha_salida(), r.getId())) {
            System.out.println("No se actualizó la reserva porque existe un cruce de fechas.");
            return false;
        }
        String query = "UPDATE reserva SET cliente_id = ?, habitacion_id = ?, fecha_entrada = ?, " +
                "fecha_salida = ?, estado = ? WHERE id = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, r.getCliente_id());
            ps.setInt(2, r.getHabitacion_id());
            ps.setString(3, r.getFecha_entrada());
            ps.setString(4, r.getFecha_salida());
            ps.setString(5, r.getEstado());
            ps.setInt(6, r.getId());
            flag = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar reserva: " + e.getMessage());
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
            flag = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar reserva: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public Reserva searchById(int id) {
        String query = SELECT_RESERVAS + "WHERE r.id = ?";
        List<Reserva> lista = consultarReservas(query, id);
        return lista.isEmpty() ? null : lista.get(0);
    }

    @Override
    public List<Reserva> listAll() {
        return consultarReservas(SELECT_RESERVAS + "ORDER BY r.id", null);
    }

    @Override
    public List<Reserva> searchByClienteId(int idCliente) {
        return consultarReservas(SELECT_RESERVAS + "WHERE r.cliente_id = ? ORDER BY r.id", idCliente);
    }

    @Override
    public List<Reserva> searchByHabitacionId(int idHabitacion) {
        return consultarReservas(SELECT_RESERVAS + "WHERE r.habitacion_id = ? ORDER BY r.id", idHabitacion);
    }

    @Override
    public boolean isDisponible(int idHabitacion, String fechaEntrada,
                                String fechaSalida, int idReserva) {
        try {
            if (fechaEntrada == null || fechaSalida == null ||
                    !LocalDate.parse(fechaSalida).isAfter(LocalDate.parse(fechaEntrada))) {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
        String query = "SELECT COUNT(*) FROM reserva " +
                "WHERE habitacion_id = ? " +
                "AND COALESCE(lower(trim(estado)), '') <> 'cancelada' " +
                "AND date(fecha_entrada) < date(?) " +
                "AND date(fecha_salida) > date(?) " +
                "AND id <> ?";

        try (Connection cn = ConexionDB.getConnection()) {
            String estadoQuery = "SELECT estado FROM habitacion WHERE id = ?";
            try (PreparedStatement estadoPs = cn.prepareStatement(estadoQuery)) {
                estadoPs.setInt(1, idHabitacion);
                try (ResultSet estadoRs = estadoPs.executeQuery()) {
                    if (!estadoRs.next() || "Mantenimiento".equalsIgnoreCase(estadoRs.getString("estado"))) {
                        return false;
                    }
                }
            }

            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setInt(1, idHabitacion);
                ps.setString(2, fechaSalida);
                ps.setString(3, fechaEntrada);
                ps.setInt(4, idReserva);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() && rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar disponibilidad: " + e.getMessage());
            return false;
        }
    }
}
