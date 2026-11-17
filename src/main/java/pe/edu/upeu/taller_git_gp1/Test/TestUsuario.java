package pe.edu.upeu.taller_git_gp1.Test;

import pe.edu.upeu.taller_git_gp1.implementacion.UsuarioDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IUsuarioDao;
import pe.edu.upeu.taller_git_gp1.modelo.Usuario;

public class TestUsuario {
    private static IUsuarioDao userDao = new UsuarioDaoImpl();

    public void login(){
        Usuario u = userDao.login("admin", "admin123");
        if (u != null){
            System.out.println("ID_USUARIO: " + u.getIdusuario());
            System.out.println("USUARIO: " + u.getUser());
            System.out.println("TIPO_USER: " + u.getTipoUser());
        } else {
            System.out.println("error login");
        }
    }

    public static void main(String[] args) {
        TestUsuario t = new TestUsuario();
        t.login();
    }
}
