package pe.edu.upeu.taller_git_gp1.modelo;

import javafx.beans.property.*;

public class Reserva {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty cliente_id = new SimpleIntegerProperty();
    private final IntegerProperty habitacion_id = new SimpleIntegerProperty();
    private final StringProperty fecha_entrada = new SimpleStringProperty();
    private final StringProperty fecha_salida = new SimpleStringProperty();
    private final StringProperty estado = new SimpleStringProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();
    private final ObjectProperty<Cliente> Cliente = new SimpleObjectProperty<>();
    private final ObjectProperty<Habitacion> Habitacion = new SimpleObjectProperty<>();

    public Reserva() {
    }

    public final Habitacion getHabitacion() {
        return Habitacion.get();
    }

    public final void setHabitacion(Habitacion value) {
        Habitacion.set(value);
    }

    public final ObjectProperty<Habitacion> HabitacionProperty() {
        return Habitacion;
    }


    public final Cliente getCliente() {
        return Cliente.get();
    }

    public final void setCliente(Cliente value) {
        Cliente.set(value);
    }

    public final ObjectProperty<Cliente> ClienteProperty() {
        return Cliente;
    }


    public final double getTotal() {
        return total.get();
    }

    public final void setTotal(double value) {
        total.set(value);
    }

    public final DoubleProperty totalProperty() {
        return total;
    }

    public final String getEstado() {
        return estado.get();
    }

    public final void setEstado(String value) {
        estado.set(value);
    }

    public final StringProperty estadoProperty() {
        return estado;
    }


    public final String getFecha_salida() {
        return fecha_salida.get();
    }

    public final void setFecha_salida(String value) {
        fecha_salida.set(value);
    }

    public final StringProperty fecha_salidaProperty() {
        return fecha_salida;
    }


    public final String getFecha_entrada() {
        return fecha_entrada.get();
    }

    public final void setFecha_entrada(String value) {
        fecha_entrada.set(value);
    }

    public final StringProperty fecha_entradaProperty() {
        return fecha_entrada;
    }


    public final int getHabitacion_id() {
        return habitacion_id.get();
    }

    public final void setHabitacion_id(int value) {
        habitacion_id.set(value);
    }

    public final IntegerProperty habitacion_idProperty() {
        return habitacion_id;
    }


    public final int getCliente_id() {
        return cliente_id.get();
    }

    public final void setCliente_id(int value) {
        cliente_id.set(value);
    }

    public final IntegerProperty cliente_idProperty() {
        return cliente_id;
    }


    public final int getId() {
        return id.get();
    }

    public final void setId(int value) {
        id.set(value);
    }

    public final IntegerProperty idProperty() {
        return id;
    }

}
