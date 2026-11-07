package pe.edu.upeu.taller_git_gp1.Test;

<<<<<<< HEAD
import pe.edu.upeu.taller_git_gp1.implementacion.ClienteDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IClienteDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;

import java.util.List;

public class TestCliente {
    static IClienteDao cl = new ClienteDaoImpl();

    public void insertarCliente() {
        Cliente c = new Cliente();
        c.setId(1);
        c.setNombre("Laura");
        c.setApellido("Ferreira");
        c.setNumero_documento(12345678);
        c.setCorreo("Lauu08@hotmail.com");
        c.setTelefono("123456789");
        boolean result = cl.insert(c);
        if (result) {
            System.out.println("Se insertaron los datos exitosamente.");
        } else {
            System.out.println("Error al insertar datos.");
        }
    }

    public void deleteCliente() {
        Cliente c = new Cliente();
        c.setId(1);
        boolean result = cl.delete(c);
        if (result) {
            System.out.println("Datos eliminados exitosamente.");
        } else {
            System.out.println("Error al eliminar datos.");
        }
    }

    public void updateCliente() {
        Cliente c = new Cliente();
        List<Cliente> list = cl.listAll();
        if (list != null && !list.isEmpty()) {

            c.setId(2);
            c.setNombre("Irene");
            c.setApellido("Cabrera");
            c.setNumero_documento(51439157);
            c.setCorreo("cabrerairene81@outlook.com");
            c.setTelefono("094986210");

            boolean result = cl.update(c);

            if (result) {
                System.out.println("Datos actualizados con exito!");
                System.out.println("ID: " + c.getId());
                System.out.println("NOMBRE: " + c.getNombre());
                System.out.println("APELLIDO: " + c.getApellido());
                System.out.println("NUMERO_DOCUMENTO: " + c.getNumero_documento());
                System.out.println("CORREO_ELECTRONICA: " + c.getCorreo());
                System.out.println("TELEFONO: " + c.getTelefono());
            } else {
                System.out.println("No se pudieron actualizar los datos.");
            }
        } else {
            System.out.println("No se encontraron datos para actualizar.");
        }
    }

    public void listarCliente() {
        List<Cliente> list = cl.listAll();
        if (list != null && !list.isEmpty()) {
            for (Cliente c : list) {
                System.out.println("ID: " + c.getId());
                System.out.println("NOMBRE: " + c.getNombre());
                System.out.println("APELLIDO: " + c.getApellido());
                System.out.println("NUMERO_DOCUMENTO: " + c.getNumero_documento());
                System.out.println("CORREO_ELECTRONICO: " + c.getCorreo());
                System.out.println("TELEFONO: " + c.getTelefono());
            }
        } else {
            System.out.println("No se encontraron datos");
        }
    }

    public static void main(String[] args) {
        TestCliente t = new TestCliente();
        t.insertarCliente();
        // t.updateCliente();
        //t.deleteCliente();
        t.listarCliente();
    }
=======
public class TestCliente {
>>>>>>> dae47a61386747187bd8b06d3783ed4c44bac4ff
}
