package pe.edu.upeu.taller_git_gp1.interfaces;

import pe.edu.upeu.taller_git_gp1.modelo.Usuario;

public interface IUsuarioDao {
    public Usuario login(String user, String password);

    public boolean insertUser(Usuario usuario);
}
