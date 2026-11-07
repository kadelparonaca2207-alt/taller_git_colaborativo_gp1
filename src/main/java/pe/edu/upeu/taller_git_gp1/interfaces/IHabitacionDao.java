package pe.edu.upeu.taller_git_gp1.interfaces;

import pe.edu.upeu.taller_git_gp1.modelo.Habitacion;

import java.util.List;

public interface IHabitacionDao {

    public boolean insert(Habitacion h);
    public boolean update(Habitacion h);
    public boolean delete(Habitacion h);
    public Habitacion searchById(int id);
    public Habitacion searchByNumeroHab(String num_hab);
    public List<Habitacion> listAll();
}
