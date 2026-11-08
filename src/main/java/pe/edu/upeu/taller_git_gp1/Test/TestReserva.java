package pe.edu.upeu.taller_git_gp1.Test;

import pe.edu.upeu.taller_git_gp1.implementacion.ReservaDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IReservaDao;
import pe.edu.upeu.taller_git_gp1.modelo.Reserva;

import java.util.List;

public class TestReserva {

    static IReservaDao re = new ReservaDaoImpl();

    public void insertReserva() {
        Reserva r = new Reserva();
        r.setId(1);
        r.setCliente_id(1);
        r.setHabitacion_id(1);
        r.setFecha_entrada("2026-10-30");
        r.setFecha_salida("2026-11-06");
        r.setEstado("Confirmado");
        boolean result = re.insert(r);
        if (result) {
            System.out.println("Se insertaron los datos exitosamente!");
        } else {
            System.out.println("Error al insertar datos.");
        }
    }

    public void deleteReserva() {
        Reserva r = new Reserva();
        r.setId(1);
        boolean result = re.delete(r);
        if (result) {
            System.out.println("Datos eliminados de manera de exitosa!");
        } else {
            System.out.println("Error al eliminar datos.");
        }
    }

    public void updateReserva() {
        Reserva r = new Reserva();
        List<Reserva> list = re.listAll();
        if (list != null && !list.isEmpty()) {
            r.setId(1);
            r.setCliente_id(2);
            r.setHabitacion_id(2);
            r.setFecha_entrada("2026-11-10");
            r.setFecha_salida("2026-11-15");
            r.setEstado("Pendiente");
            boolean result = re.update(r);
            if (result) {
                System.out.println("Datos actualizados con exito!");
                System.out.println("ID: " + r.getId());
                System.out.println("CLIENTE_ID: " + r.getCliente_id());
                System.out.println("HABITACION_ID: " + r.getHabitacion_id());
                System.out.println("FECHA_ENTRADA: " + r.getFecha_entrada());
                System.out.println("FECHA_SALIDA: " + r.getFecha_salida());
                System.out.println("ESTADO: " + r.getEstado());
            } else {
                System.out.println("No se pudieron actualizar los datos.");
            }
        } else {
            System.out.println("No se encontraron datos para actualizar.");
        }
    }
    public void listarReserva() {
        List<Reserva> list = re.listAll();
        if (list != null && !list.isEmpty()) {
            for (Reserva r : list) {
                System.out.println("ID: " + r.getId());
                System.out.println("CLIENTE_ID: " + r.getCliente_id());
                System.out.println("HABITACION_ID: " + r.getHabitacion_id());
                System.out.println("FECHA_ENTRADA: " + r.getFecha_entrada());
                System.out.println("FECHA_SALIDA: " + r.getFecha_salida());
                System.out.println("ESTADO: " + r.getEstado());
                System.out.println("-----------------------------------");
            }
        } else {
            System.out.println("No se encontraron datos");
        }
    }
    public static void main(String[] args) {
        TestReserva t = new TestReserva();
        t.insertReserva();
        //t.updateReserva();
        //t.deleteReserva();
        //t.listarReserva();
    }
}
