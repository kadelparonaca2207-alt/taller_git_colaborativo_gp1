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
    @Override
    public boolean insert(Cliente c) {
        boolean flag = false;
        String query = "INSERT INTO cliente (nombre, apellido, numero_documento, " +
                "correo_electronico, telefono) VALUES (?, ?, ?, ?, ?);";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setInt(3, c.getNumero_documento());
            ps.setString(4, c.getCorreo());
            ps.setString(5, c.getTelefono());
            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente " + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Cliente c) {
        return false;
    }

    @Override
    public boolean delete(Cliente c) {
        return false;
    }

    @Override
    public Cliente searchById(int id) {
        return null;
    }

    @Override
    public Cliente searchByDni(String dni) {
        return null;
    }

    @Override
    public List<Cliente> listAll() {
        List<Cliente> lista = new ArrayList<>();
        String query = "SELECT * FROM cliente;";

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Cliente cl = new Cliente();

                cl.setId(rs.getInt("id"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellido"));
                cl.setNumero_documento(rs.getInt("numero_documento"));
                cl.setCorreo(rs.getString("correo_electronico"));
                cl.setTelefono(rs.getString("telefono"));

                lista.add(cl);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LISTAR " + e.getMessage());
        }

        return lista;
    }
}
