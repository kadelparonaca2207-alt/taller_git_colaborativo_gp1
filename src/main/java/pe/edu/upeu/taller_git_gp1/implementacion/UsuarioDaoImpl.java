package pe.edu.upeu.taller_git_gp1.implementacion;

import pe.edu.upeu.taller_git_gp1.interfaces.IUsuarioDao;
import pe.edu.upeu.taller_git_gp1.modelo.Usuario;
import pe.edu.upeu.taller_git_gp1.utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDaoImpl implements IUsuarioDao {
    @Override
    public Usuario login(String user, String password) {
        Usuario u = null;
        String query = "SELECT * FROM usuario WHERE user = ? AND password = ?;";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)){
            ps.setString(1, user);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    u = new Usuario();
                    u.setIdusuario(rs.getInt("idusuario"));
                    u.setUser(rs.getString("user"));
                    u.setTipoUser(rs.getString("tipoUser"));

                    System.out.println("Login exitoso");
                }
            }
        } catch (SQLException e){
            System.out.println("Error al realizar la operación " + e);
        }
        return u;
    }

    @Override
    public boolean insertUser(Usuario usuario) {
        return false;
    }
}
