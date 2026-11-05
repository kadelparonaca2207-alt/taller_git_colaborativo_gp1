package pe.edu.upeu.taller_git_gp1.Test;

import pe.edu.upeu.taller_git_gp1.utils.ConexionDB;

import java.sql.Connection;

public class TestConexion {
    public static void testConexion(){
        try {
            Connection con = ConexionDB.getConnection();
            if (con != null && !con.isClosed()){
                System.out.println("Conexión exitosa!");
            } else {
                System.out.println("Error al establecer la conexión...");
            }
        } catch (Exception e){
            System.out.println("Error de conexión... " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testConexion();
    }
}


