package pe.edu.upeu.taller_git_gp1.Test;

import pe.edu.upeu.taller_git_gp1.implementacion.HabitacionDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IHabitacionDao;
import pe.edu.upeu.taller_git_gp1.modelo.Habitacion;

import java.util.List;

public class TestHabitacion {
    static IHabitacionDao hb = new HabitacionDaoImpl();

    public void insertarHabitacion() {
        Habitacion h = new Habitacion();
        h.setId(1);
        h.setNumero_hab(100);
        h.setPiso(1);
        h.setTipo("individual");
        h.setPrecio(30.0);
        h.setEstado("disponible");
        boolean result = hb.insert(h);
        if (result) {
            System.out.println("Se insertaron los datos exitosamente!");
        } else {
            System.out.println("Error al insertar datos.");
        }
    }

    public void deleteHabitacion() {
        Habitacion h = new Habitacion();
        h.setId(1);
        boolean result = hb.delete(h);
        if (result) {
            System.out.println("Datos eliminados de manera exitosa!");
        } else {
            System.out.println("Error al eliminar datos");
        }
    }

    public void updateHabitacion() {
        Habitacion h = new Habitacion();
        List<Habitacion> list = hb.listAll();
        if (list != null && !list.isEmpty()) {
            Habitacion habitacion = list.get(0);

            h.setNumero_hab(200);
            h.setPiso(2);
            h.setTipo("Matrimonial");
            h.setPrecio(51439157);
            h.setEstado("Pendiente");

            boolean result = hb.update(h);

            if (result) {
                System.out.println("Datos actualizados con exito!");
                System.out.println("ID: " + h.getId());
                System.out.println("NUMERO_HABITACION: " + h.getNumero_hab());
                System.out.println("PISO: " + h.getPiso());
                System.out.println("TIPO: " + h.getTipo());
                System.out.println("PRECIO: " + h.getPrecio());
                System.out.println("ESTADO: " + h.getEstado());
            } else {
                System.out.println("No se pudieron actualizar los datos.");
            }
        } else {
            System.out.println("No se encontraron datos para actualizar.");
        }
    }

    public void listarHabitacion() {
        List<Habitacion> list = hb.listAll();
        if (list != null && !list.isEmpty()) {
            for (Habitacion h : list) {
                System.out.println("ID: " + h.getId());
                System.out.println("NUMERO_HAB: " + h.getNumero_hab());
                System.out.println("PISO: " + h.getPiso());
                System.out.println("TIPO: " + h.getTipo());
                System.out.println("PRECIO: " + h.getPrecio());
                System.out.println("ESTADO: " + h.getEstado());
            }
        } else {
            System.out.println("No se encontraron datos");
        }
    }

    public static void main(String[] args) {
        TestHabitacion t = new TestHabitacion();
        // t.insertarHabitacion();
        //t.deleteHabitacion();
        // t.listarHabitacion();
        //t.updateHabitacion();
    }
}
