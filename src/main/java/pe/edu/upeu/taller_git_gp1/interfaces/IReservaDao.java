package pe.edu.upeu.taller_git_gp1.interfaces;

import pe.edu.upeu.taller_git_gp1.modelo.Reserva;

import java.util.List;

public interface IReservaDao {
    public boolean insert(Reserva r);

    public boolean update(Reserva r);

    public boolean delete(Reserva r);

    public Reserva searchById(int id);

    public List<Reserva> listAll();

    public List<Reserva> searchByClienteId(int idCliente);

    public List<Reserva> searchByHabitacionId(int idHabitacion);
}
